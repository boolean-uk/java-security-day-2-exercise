package com.booleanuk.api.library.payload.response;

import lombok.Getter;

@Getter
public class Response<T> {
    protected String status;
    protected T data;

    public Response() {
    }

    public Response (String status, T data) {
        this.status = status;
        this.data = data;
    }

    public void set(T data) {
        this.status = "success";
        this.data = data;
    }
}
