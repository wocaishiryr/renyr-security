package com.renyr.security.common;

public enum SecurityConstant {
    TOKEN_SPLIT("Bearer ","token分割"),
    JWT_SIGN_KEY("hushao","JWT签名加密key"),
    HEADER("Authorization","token参数头"),
    AUTHORITIES("authorities","权限参数头");

    private final String code;
    private final String desc;

    SecurityConstant(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
