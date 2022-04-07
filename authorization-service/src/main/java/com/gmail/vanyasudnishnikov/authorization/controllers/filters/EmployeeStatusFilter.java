package com.gmail.vanyasudnishnikov.authorization.controllers.filters;

import com.gmail.vanyasudnishnikov.authorization.repository.model.StatusEnum;
import com.gmail.vanyasudnishnikov.authorization.service.impl.UserDetailsImpl;
import com.gmail.vanyasudnishnikov.authorization.service.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Slf4j
@AllArgsConstructor
@Component
public class EmployeeStatusFilter extends GenericFilterBean {
    @Autowired
    private JwtUtil jwtUtils;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null) {
            StatusEnum statusEnum = null;
            try {
                String jwt = headerAuth.substring("Bearer ".length());
                String username = jwtUtils.getUsernameFromJwtToken(jwt);
                UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
                String status = userDetails.getStatus();
                statusEnum = StatusEnum.valueOf(status);
            } catch (Exception e) {
                log.error("Unable to set user status: {}", e.getMessage());
            }
            if (statusEnum == StatusEnum.DISABLED) {
                throw new IllegalArgumentException("Ваша учетная запись заблокирована. Обратитесь к Администратору");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


}
