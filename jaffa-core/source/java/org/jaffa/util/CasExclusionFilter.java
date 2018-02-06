package org.jaffa.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by mattwayles on 2/6/2018.
 */
public class CasExclusionFilter implements Filter{

    public void init(FilterConfig request) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.getRequestDispatcher(((HttpServletRequest) request).getServletPath() +
                StringUtils.defaultString(((HttpServletRequest)request).getPathInfo()) ).forward(request, response);
    }

    public void destroy() {

    }

}
