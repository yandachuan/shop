package com.neuedu.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ServerResponse {

    private Integer status;   //200成功 500失败
    private String message;
    private Map<String, Object> data = new HashMap<>();

    private ServerResponse(){
    }

    //成功
    public static ServerResponse success(String message){

        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setStatus(Const.SUCCESS);
        serverResponse.setMessage(message);
        return serverResponse;
    }

    //添加数据
    public ServerResponse data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    //失败
    public static ServerResponse error(String message){

        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setStatus(Const.ERROR);
        serverResponse.setMessage(message);
        return serverResponse;
    }

}
