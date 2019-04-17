package com.renyr.security.security.jwt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.renyr.security.common.Constant;
import com.renyr.security.common.ResponseCode;
import com.renyr.security.common.SecurityConstant;
import com.renyr.security.model.User;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = request.getSession();
        Object authorization = session.getAttribute(Constant.TOKEN_KEY);
        System.out.println(authorization);
        if ("123456".equals(authorization)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader(SecurityConstant.HEADER.getCode());
        if (token!=null){
            Claims claims = null;
            try {
                claims= Jwts.parser()
                        .setSigningKey(SecurityConstant.JWT_SIGN_KEY.getCode())
                        .parseClaimsJws(token.replace(SecurityConstant.TOKEN_SPLIT.getCode(), ""))
                        .getBody();
                //获取用户名
                String username = claims.getSubject();

                //获取权限（角色）
                List<GrantedAuthority> authorities=null;
                String authority = claims.get(SecurityConstant.AUTHORITIES.getCode()).toString();

                if(authority!=null){
                    List<String> list = JSONObject.parseArray(authority,String.class);
                    authorities= list.stream().map(a->new SimpleGrantedAuthority(a)).collect(Collectors.toList());

                }
                if(username!=null) {
                    //此处password不能为null
                    User user=new User(username,"",authorities);
                    return new UsernamePasswordAuthenticationToken(user, null, authorities);
                }

            }catch (Exception e) {
                logger.error("Token已过期: {} " + e);

                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.println("Token已过期");
                out.close();
            }
        }
        return null;
    }
}
