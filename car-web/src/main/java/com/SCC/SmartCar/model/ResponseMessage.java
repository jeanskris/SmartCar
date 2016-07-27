package com.SCC.SmartCar.model;

import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ZJDX on 2016/6/25.
 */
@ResponseBody
public class ResponseMessage {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
