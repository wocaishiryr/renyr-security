package com.renyr.security.service;

public interface AuthorizationService {

    String login();

    String logout();

    boolean checkToken();

    String reflashToken();

}
