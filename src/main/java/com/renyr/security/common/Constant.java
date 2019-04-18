package com.renyr.security.common;

import java.util.UUID;

public class Constant {

    /**
     * 身份验证请求头的key
     */
    public static final String TOKEN_KEY = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";

    /**
     *用户登录时的路径
     */
    public static final String LOGIN_PATH = "/user/login";
    public static final String JWT_ID = UUID.randomUUID().toString();

    /**
     * 加密密文
     */
    public static final String JWT_SECRET = "NDM3YjliMTYtMTgzMC00MGRiLThjMTItNmY0MzMwYzU2ZWQ5";
    /**
     * 过期时间
     * 30分钟
     */
    public static final int JWT_TTL = 30*60*1000;  //millisecond

}
