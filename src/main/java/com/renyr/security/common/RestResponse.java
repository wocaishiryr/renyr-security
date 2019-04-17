package com.renyr.security.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class RestResponse<T> {

    private int code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private String msg;
    private boolean status;

    private List<T> list;
    private long total;
    private T obj;

    public RestResponse() {

    }

    public RestResponse(int code) {
        this.code=code;
    }

    public RestResponse(int code, String msg) {
        this.code=code;
    }

    public RestResponse(List<T> list) {
        this.code=HttpStatus.OK.value();
        this.list = list;
        this.total=list.size();
    }

    public RestResponse(String msg) {
        this.code=HttpStatus.OK.value();
        this.msg=msg;
    }

    public static RestResponse Success(){
        return new RestResponse(ResponseCode.SUCCESS.getCode());
    }

    public static RestResponse Success(String msg){
        return new RestResponse(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static RestResponse Error(ResponseCode responseCode){
        return new RestResponse(responseCode.getCode(),responseCode.getDesc());
    }

    public static RestResponse Error(String msg){
        return new RestResponse(ResponseCode.INTERNAL_SERVER_ERROR.getCode(),msg);
    }
    public static RestResponse Error(int status,String msg){
        return new RestResponse(status,msg);
    }

}
