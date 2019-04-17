package com.renyr.security.web;

import com.renyr.security.common.Constant;
import com.renyr.security.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("Authorization")
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping("login")
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(Constant.TOKEN_KEY,"123456");
        return "登录成功";
    }

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("logout")
    public String logout() {
        return "退出登录";
    }

}
