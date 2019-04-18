package com.renyr.security.service.impl;

import com.renyr.security.service.AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Override
    public String login() {
        return null;
    }

    @Override
    public String logout() {
        return null;
    }

    @Override
    public boolean checkToken() {
        return false;
    }

    @Override
    public String reflashToken() {
        return null;
    }
}
