package com.dashboard.saas.dtos.baseresponse;

import com.dashboard.saas.dtos.CategoryResponseDTO;
import com.dashboard.saas.dtos.OrderResponseDTO;
import com.dashboard.saas.dtos.ProductVariantResponseDTO;
import com.dashboard.saas.entities.Order;

import java.util.List;

public class BaseAPIResponse<T> {


    private String message;
    private T data;
    private boolean success;

    public BaseAPIResponse() {
    }

    public BaseAPIResponse(String message, T data, boolean success) {
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public BaseAPIResponse(List<Order> orders) {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
