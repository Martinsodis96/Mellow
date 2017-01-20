package com.mellow.security.jwt;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.io.IOException;

//https://auth0.com/blog/securing-spring-boot-with-jwts/
public class AuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //TODO authenticate token.
        System.out.println("Authenticate token");
/*        Authentication authentication = new TokenAuthenticationService().getAuthentication((HttpServletRequest)servletRequest);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(servletRequest,servletResponse);*/
    }
}
