package com.renyr.security.security.jwt;

import com.renyr.security.common.Constant;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = request.getSession();
        Object authorizationObj = session.getAttribute(Constant.TOKEN_KEY);
        System.out.println(authorizationObj);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);

//        if ("123456".equals(authorizationObj)) {
//            chain.doFilter(request, response);
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null, null, null);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            return;
//        }
//
//        if (authorizationObj == null)
//            return;
//
//        String authorization = authorizationObj.toString();
//        if (authorization.startsWith(Constant.TOKEN_PREFIX)) {
//            String token = authorization.substring(Constant.TOKEN_PREFIX.length());
//
//            if (JwtTokenUtil.validateToken(token)) {
//                chain.doFilter(request, response);
//            } else {
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                return;
//            }
//        }
//
//        chain.doFilter(request, response);
    }

}
