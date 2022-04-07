package com.gmail.radzkovevgeni.legal.controllers.filters;


import com.gmail.radzkovevgeni.legal.service.FeignService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtTokenFilter implements Filter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN = "Bearer ";
    private static final String MESSAGE_WHEN_TOKEN_IS_INVALID = "token is invalid";
    private FeignService feignService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper((HttpServletRequest) request);
        String authorization = requestWrapper.getHeader(AUTHORIZATION_HEADER);
        String token = authorization.substring(BEARER_TOKEN.length());
        Boolean validToken = feignService.isValid(token);
        if (validToken) {
            chain.doFilter(request, response);
        } else {
            throw new IOException(MESSAGE_WHEN_TOKEN_IS_INVALID);
        }
    }

    @Override
    public void destroy() {
    }
}
