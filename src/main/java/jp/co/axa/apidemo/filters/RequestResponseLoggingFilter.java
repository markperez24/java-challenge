package jp.co.axa.apidemo.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestResponseLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // Log Incoming request
        logger.info("Incoming request: {} {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());

        // Pass the Request on to the next filter or servlet in the chain
        chain.doFilter(request, response);

        // Log Outgoing response
        logger.info("Outgoing response: {} {}", httpServletResponse.getStatus(), httpServletResponse.getContentType());

        // Log Path from the response
        logger.info("Response end for : {} {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
    }
}