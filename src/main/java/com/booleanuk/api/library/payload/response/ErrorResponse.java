package com.booleanuk.api.library.payload.response;

import java.util.HashMap;

public class ErrorResponse extends Response<HashMap<String, String>>{

    public ErrorResponse(String message) {
        super("error", new HashMap<>(){{put("message", message);}});
    }
}
