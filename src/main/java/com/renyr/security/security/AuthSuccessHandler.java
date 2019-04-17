package com.renyr.security.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.renyr.security.common.RestResponse;
import com.renyr.security.common.SecurityConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        //获取登陆成功用户名
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        List<String> authorities=((UserDetails) authentication.getPrincipal()).getAuthorities().stream().map(a->new String(((GrantedAuthority) a).getAuthority())).collect(Collectors.toList());
        //登陆成功生成JWT
        String token = Jwts.builder()
                //主题 放入用户名
                .setSubject(username)
                //自定义属性 放入用户拥有权限
                .claim(SecurityConstant.AUTHORITIES.getCode(), JSONObject.toJSONString(authorities))
                //失效时间 7天
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 *7))
                //签名算法和密钥
                .signWith(SignatureAlgorithm.HS512,SecurityConstant.JWT_SIGN_KEY.getCode())
                .compact();
        token = SecurityConstant.TOKEN_SPLIT.getCode() + token;

        String msg= JSON.toJSONString(new RestResponse(token));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(msg);
        out.close();
    }
}
