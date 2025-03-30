package com.example.jobs_top.dto.res;

public class ApiResponse<T> extends BaseResponse {
    private T data;
    public ApiResponse(int status, String message, T data) {
        super(status, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
