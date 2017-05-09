/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002 JAFFA Development Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Redistribution and use of this software and associated documentation ("Software"),
 * with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

/*
 * SoapMessageFilter.java
 *
 */
package org.jaffa.soa.transformation.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.jaffa.soa.transformation.helper.TransformationBean;
import org.jaffa.soa.transformation.helper.TransformationHelper;
import org.jaffa.soa.transformation.meta.MetaDataRepository;

/**
 * <P>
 * The SoapMessageFilter will intercept the request and response messages
 * for WebServiceController. The filter will check the request URI and find
 * it on the mapping. If requested URI exist in mapping it will follow the
 * transformation process, otherwise our normal contract last web service
 * will get invoked.
 *
 * <p>
 * And this filter extracts the soap message from HttpRequest and call the
 * transformation helper to transform the   message into Contract First
 * service accepted format. The transformed soap message will get wrapped
 * into SoapRequestWrapper. The new custom SoapResponseWrapper is getting
 * created and passed to the chain. The soap message in response wrapper
 * will get transformed into external soap message format (OAGIS format).
 *
 * @author SaravananN
 */
public class SoapMessageFilter implements Filter {

    private static Logger log = Logger.getLogger(SoapMessageFilter.class);

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    /**This method will do the transformation from inbound soap message to
     * soap message accepted by internal contract first service based on
     * mapping information.
     *
     * @param request. The ServletRequest.
     * @param response. The ServletReponse.
     * @param chain. The FilterChain.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        /**
         * If the instance of servlet is http then extracting the soapmessage and
         * Transform it into soap message accepted by internal service.
         */
        if (request instanceof HttpServletRequest) {
            try {

                HttpServletRequest httpRequest = (HttpServletRequest) request;

                // Getting the endpoint name from Request URI.
                String endPoint = httpRequest.getRequestURI();
                endPoint = endPoint.substring(endPoint.lastIndexOf("/") + 1);

                if (MetaDataRepository.getInstance().getEndPointMapping().containsKey(endPoint)) {
                    //Extracting the soap message from the http request body.
                    String inboundSoapMessage = extractSoapMessage(request);

                    if (log.isDebugEnabled()) {
                        log.debug("Inbound Soap Message :" + inboundSoapMessage);
                    }

                    //Transforming the inbound soap message.
                    TransformationBean transformationBean = TransformationHelper.transform(inboundSoapMessage, endPoint, TransformationHelper.DIRECTION_IN);

                    if (log.isDebugEnabled()) {
                        log.debug("Transformed Soap Request :" + transformationBean.getTransformedMessage());
                    }

                    //Creating custom request wrapper and setting transformed soapmessage.
                    SoapRequestWrapper soapRequestWrapper = new SoapRequestWrapper(httpRequest);
                    soapRequestWrapper.setSoapMessage(transformationBean.getTransformedMessage());

                    //Creating custom response.
                    SoapResponseWrapper soapResponseWrapper = new SoapResponseWrapper((HttpServletResponse) response);
                    soapRequestWrapper.setAttribute("realEndPoint", transformationBean.getRealEndPoint());

                    //calling the chain with custom request and response wrapper.
                    chain.doFilter(soapRequestWrapper, soapResponseWrapper);

                    //getting the internal service soap message and tranforming into external format
                    if (soapResponseWrapper instanceof SoapResponseWrapper) {
                        String outboundSoapMessage = soapResponseWrapper.toString();

                        transformationBean = null;

                        if (log.isDebugEnabled()) {
                            log.debug("Outbound Soap Message :" + outboundSoapMessage);
                        }

                        transformationBean = TransformationHelper.transform(outboundSoapMessage, endPoint, TransformationHelper.DIRECTION_OUT);

                        if (log.isDebugEnabled()) {
                            log.debug("Transformed Outbound Soap Message :" + transformationBean.getTransformedMessage());
                        }

                        response.getWriter().write(transformationBean.getTransformedMessage());
                    }
                } else {
                    request.setAttribute("realEndPoint", endPoint);
                    chain.doFilter(request, response);
                }

            } catch (Exception ex) {
                log.error(ex);
                throw new ServletException(ex);
            }

        } else {//if the request/response is not http then ignoring the transformation.
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    /**
     * This method will extract the soap message
     * from request.
     *
     * @param request. The ServletRequest.
     * @return. The inbound soap message.
     * @throws IOException
     */
    private String extractSoapMessage(ServletRequest request) throws IOException {

        InputStream inStream = request.getInputStream();
        InputStreamReader reader = new InputStreamReader(inStream);
        BufferedReader bReader = new BufferedReader(reader);

        StringBuffer sb = new StringBuffer();
        String inputLine;
        while ((inputLine = bReader.readLine()) != null) {
            sb.append(inputLine);
            sb.append("\n");
        }

        return sb.toString();
    }
}
