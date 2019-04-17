package com.renyr.security.common;

public enum  ResponseCode {
    SUCCESS(200,"SUCCESS"),
    BAD_REQUEST(400,"客户端请求语法错误，不能被服务器理解"),
    UNAUTHORIZED(401,"未经授权"),
    FORBIDDEN(403,"访问拒绝"),
    NOT_FOUND(404,"URL错误"),
    INTERNAL_SERVER_ERROR(500,"服务器错误"),


    USER_ACCOUNT_ERROR(20001,"用户名或密码错误！"),
    USER_ACCOUNT_FORBIDDEN(20002,"该账号被禁用,请联系管理员!"),
    USER_UNKNOWN_ERROR(20003,"登录失败，未知错误！");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
