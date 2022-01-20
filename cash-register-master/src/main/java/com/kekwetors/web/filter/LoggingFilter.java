package com.kekwetors.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

import static java.lang.String.format;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String servletPath = request.getServletPath();

        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder headers = new StringBuilder();

        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headers.append(format("{%s: %s} ", headerName, request.getHeader(headerName)));
            }
        }

        Enumeration<String> parameterNames = request.getParameterNames();
        StringBuilder parameters = new StringBuilder();

        if (parameterNames != null) {
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                parameters.append(format("{%s: %s} ", parameterName, request.getParameter(parameterName)));
            }
        }

        final String INCOMING_REQUEST = "================== INCOMING REQUEST ==================\n";
        log.info(INCOMING_REQUEST + "ServletPath: [{}]\nURL: [{}]\nHeaders: [{}]\nParameters: [{}]",
                servletPath, request.getRequestURL(), headers, parameters);

        chain.doFilter(servletRequest, servletResponse);
    }

}
