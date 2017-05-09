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
 * URLHelper.java
 *
 * Created on August 23, 2001, 4:48 PM
 */
package org.jaffa.util;

import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.net.URI;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import org.jaffa.config.Config;

/** Utility class to manipulate URL's and provide some additional capability for using them.
 *
 * @author PaulE
 * @version 1.0
 */
public class URLHelper {

    private static Logger log = Logger.getLogger(URLHelper.class.getName());
    /** This contains the name of the URL protocol that implies the resouce can be located in the classpath */
    public static final String PROTOCOL_CLASSPATH = "classpath";
    /** This contains the name of the URL protocol that implies the resource can be found relative to the location of the web-root on the local file system,
     * This assumes that you are running this inside a servlet, on a web server
     */
    public static final String PROTOCOL_WEBROOT = "webroot";
    /** This is the default value for the initial page of the application */
    public static final String DEFAULT_PAGE = "index.html";

    /** Based on a HttpRequest, this routine will ingure out the URL that represents the root of
     * the web application. It derives the URL protocol, host, port and application.
     * An example of a returned string may be http://www.example.org/myApp/
     * or https://www.example.com:888/SecureApp/
     *
     * @param request A HttpRequest used to derive information about the root of the web applicatoin
     * @return  a string that represents the base URL for this web application
     */
    public static String getBase(HttpServletRequest request) {
        StringBuffer buf = new StringBuffer();
        buf.append(request.getScheme());
        buf.append("://");
        buf.append(request.getServerName());
        if ("http".equals(request.getScheme()) && (80 == request.getServerPort())) {
            ;
        } else if ("https".equals(request.getScheme()) && (443 == request.getServerPort())) {
            ;
        } else {
            buf.append(':');
            buf.append(request.getServerPort());
        }
        buf.append(request.getContextPath() + "/");
        return buf.toString();
    }

    /** Build up a valid URL string based on the supplied source URL string.
     * If the supplied string in null, use the string defined in DEFAULT_PAGE and
     * append this to web root ( using URLHelper.getBase() )
     * If the supplied string already is a valid url, leave it as is.
     * If it is currently invalid, see if it can be made valid by making it relative to
     * the web application base ( using URLHelper.getBase() ), if so return this value
     * If this still doesn't yield a valid url, assume it was null, and build it based on
     * DEFAULT_PAGE and getBase()
     *
     * @param url The URL to build and validate
     * @param req The httpRequest needed to derive the web app base
     * @return A fully expressed valid URL
     */
    public static String buildUrl(String url, HttpServletRequest req) {
        String oldUrl = url;

        if (url == null) {
            url = getBase(req) + DEFAULT_PAGE;
        } else {
            try {
                URL u = new URL(url);
            } catch (java.net.MalformedURLException e) {
                // What should be done? ... Try to add the base on....
                url = getBase(req) + (url.charAt(0) == '/' ? url.substring(1) : url);

                // See if this is OK now...
                try {
                    URL u = new URL(url);
                } catch (java.net.MalformedURLException ex) {
                    // Default to the base plus the DEFAULT PAGE
                    log.info("Invalid URL : " + url);
                    url = getBase(req) + DEFAULT_PAGE;
                }
            }
        }
        log.debug("Converted URL " + oldUrl + " to " + url);
        return url;
    }

    /** Get a complete string based representation of a request's source URL include query parameters
     *
     * @param request HttpRequest containing the url to extracts
     * @return  string representation of URL
     */
    public static String getFullUrl(HttpServletRequest request) {
        return request.getRequestURL().toString() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
    }

    /** This method will try to load the input resource off the classpath.
     * If its not found, then it will look for it in the filesystem.
     * A null will be returned, if the resource could not be located.
     *
     * @param resourceName the resource to be located.
     * @throws IOException if any error occurs while opening the stream.
     * @return an input stream for reading from the resource.
     */
    public static InputStream getInputStream(String resourceName)
            throws IOException {
        InputStream stream = null;

        URL url = getUrl(resourceName);
        if (url != null) {
            stream = url.openStream();
        }

        return stream;
    }

    /** This method will try to load the input resource off the classpath.
     * If its not found, then it will look for it in the filesystem.
     * A null will be returned, if the resource could not be located.
     *
     * This method merely invokes the newExtendedURL() method, returning a null if any exception is raised.
     *
     * @param resourceName the resource to be located.
     * @return a URL for reading from the resource.
     * @deprecated Use the newExtendedURL() method.
     */
    public static URL getUrl(String resourceName) {
        URL url = null;
        try {
            url = newExtendedURL(resourceName);
        } catch (MalformedURLException e) {
        // do nothing
        }
        return url;
    }

    /** Create a URL object from a string, this can handle the two new Custom URL
     * protocols, 'classpath:///' and 'webroot:///'. If either of these new ones are
     * used they will be converted into the appropriate 'file://' format.
     * If no protocol is specified, then it'll try to load the input resource off the classpath.
     * If its not found, then it will look for the input resource in the filesystem.
     *
     * @param url source URL that may use one of the new protocols
     * @throws MalformedURLException if the supplied URL is not valid, or can't be translated into something that is valid
     * @return valid URL object, as these two new protocols are not really supported by the java.net.URL object
     */
    public static URL newExtendedURL(String url) throws MalformedURLException {
        // A null input is not allowed
        if (url == null) {
            throw new IllegalArgumentException("The input url cannot be null");
        }

        // try to read the resource off the filesystem
        URL u = null;
        u = getUrlFromFilesystem(url);

        if (u == null) {
            // Now try to read it bu using the protocols
            // convert the input into a URI, so as to obtain its scheme
            URI uri = null;
            try {
                uri = new URI(url);
            } catch (java.net.URISyntaxException e) {
                throw new MalformedURLException(e.getMessage());
            }

            // Check the scheme
            if (uri.getScheme() != null) {
                String path = uri.getPath().substring(uri.getPath().startsWith("/") ? 1 : 0);
                if (uri.getScheme().equalsIgnoreCase(PROTOCOL_CLASSPATH)) {
                    u = getUrlFromClasspath(path);
                    if (u == null) {
                        throw new MalformedURLException("Can't Locate Resource in Classpath - " + path);
                    }
                } else if (uri.getScheme().equalsIgnoreCase(PROTOCOL_WEBROOT)) {
                    String root = (String) Config.getProperty(Config.PROP_WEB_SERVER_ROOT, "file:///");
                    String separator = "/"; // This is a normal url seperator
                    try {
                        if ((new URI(root)).getScheme().equalsIgnoreCase("file")) {
                            separator = File.separator;
                        }
                    } catch (java.net.URISyntaxException e) {
                    }
                    u = new URL(root + (root.endsWith(separator) ? "" : separator) + path);
                } else {
                    u = new URL(url);
                }
            } else {
                // Finally try to read the resource off the classpath
                u = getUrlFromClasspath(url);
                if (u == null) {
                    throw new MalformedURLException("Can't Locate Resource in Classpath or the Filesystem - " + url);
                }
            }
        }
        return u;
    }

    /** Search for the input resource in the classpath */
    private static URL getUrlFromClasspath(String resourceName) {
        URL url = null;
        ClassLoader classLoader = URLHelper.class.getClassLoader();
        url = classLoader.getResource(resourceName);
        if (url == null) {
            url = classLoader.getResource("resources/" + resourceName);
        }
        if (url == null) {
            url = ClassLoader.getSystemResource(resourceName);
        }
        return url;
    }

    /** Search for the input resource in the filesystem */
    private static URL getUrlFromFilesystem(String resourceName) {
        URL url = null;
        File f = new File(resourceName);
        try {
            if (f.exists() && f.isFile()) {
                // The following works in Java1.6
                //// The following does not work if the filename contains space-characters and if we do a url.openStream() on it.
                ////url = f.toURI().toURL();
                //url = f.toURL();
                url = f.toURI().toURL();
            }
        } catch (MalformedURLException e) {
            // do nothing
            url = null;
        }
        return url;
    }

    /** Test rig
     * @param args none required
     */
    public static void main(String[] args) {
        // @todo : move this to a unit or httpunit test rig
        try {
            String a = "classpath:///org/jaffa/config/framework.properties";
            System.out.println("URL: " + a);
            System.out.println("Real URL: " + newExtendedURL(a).toExternalForm());

            String b = "webroot:///index.html";
            System.out.println("URL: " + b);
            System.out.println("Real URL: " + newExtendedURL(b).toExternalForm());

            String c = "file:///index.html";
            System.out.println("URL: " + c);
            System.out.println("Real URL: " + newExtendedURL(c).toExternalForm());

            String d = "org/jaffa/config/framework.properties";
            System.out.println("URL: " + d);
            System.out.println("Real URL: " + newExtendedURL(d).toExternalForm());

            String e = "bin";
            System.out.println("URL: " + e);
            System.out.println("Real URL: " + newExtendedURL(e).toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
