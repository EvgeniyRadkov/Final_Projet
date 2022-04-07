package com.gmail.vanyasudnishnikov.employee.controllers.filters;

import com.gmail.vanyasudnishnikov.employee.repository.feign.TokenFeign;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtTokenFilter implements Filter {
    private TokenFeign tokenFeign;


    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper((HttpServletRequest) request);
        String authorization = requestWrapper.getHeader("Authorization");
        String token = authorization.substring("Bearer ".length());
        Boolean validToken = tokenFeign.isValid(token);
        if (validToken) {
            chain.doFilter(request, response);
        } else {
            throw new IOException("token is invalid");
        }
    }

    @Override
    public void destroy() {
    }
}
