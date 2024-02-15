package com.booleanuk.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Response<T> {
    protected String status;
    protected T data;

    public void set(String status, T data) {
        this.status = status;
        this.data =   data;
    }
}
