package com.hades.farm.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * Created by xiaoxu on 2018/3/9.
 */
public class RequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(new CustomeizedRequest((HttpServletRequest) request), response);
    }

    private class CustomeizedRequest extends HttpServletRequestWrapper {
        public CustomeizedRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getContentType() {
            return "application/json";
        }
    }

    @Override
    public void destroy() {

    }
}
