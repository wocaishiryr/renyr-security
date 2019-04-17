package com.renyr.security.security;

import com.alibaba.fastjson.JSON;
import com.renyr.security.common.ResponseCode;
import com.renyr.security.common.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        RestResponse sr=null;
        if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
            sr=RestResponse.Error(ResponseCode.USER_ACCOUNT_ERROR.getCode(), ResponseCode.USER_ACCOUNT_ERROR.getDesc());

        } else if (exception instanceof DisabledException) {
            sr=RestResponse.Error(ResponseCode.USER_ACCOUNT_FORBIDDEN.getCode(),ResponseCode.USER_ACCOUNT_FORBIDDEN.getDesc());
        } else {
            sr=RestResponse.Error(ResponseCode.USER_UNKNOWN_ERROR.getCode(),ResponseCode.USER_UNKNOWN_ERROR.getDesc());
        }

        String msg= JSON.toJSONString(sr);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(msg);
        out.close();
    }

}
