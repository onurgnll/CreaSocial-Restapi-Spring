package com.app.facebookclone.response;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(int status, Object object) {

        Map<String, Object> map = new HashMap<>();

        map.put("status", status);
        map.put("data", object);

        if (status >= 400) {
            map.put("success", false);
        } else {
            map.put("success", true);

        }
        return new ResponseEntity<Object>(map, HttpStatusCode.valueOf(status));


    }

}
