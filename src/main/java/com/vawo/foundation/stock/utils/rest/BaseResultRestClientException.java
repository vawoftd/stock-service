package com.vawo.foundation.stock.utils.rest;

import com.vawo.foundation.stock.utils.result.BaseResult;

public class BaseResultRestClientException extends RestClientException {

    private BaseResult<?> result;

    public BaseResultRestClientException(BaseResult<?> result) {
        this.result = result;
    }

    public BaseResultRestClientException(String message, BaseResult<?> result) {
        super(message);
        this.result = result;
    }

    public BaseResultRestClientException(String message, Throwable cause, BaseResult<?> result) {
        super(message, cause);
        this.result = result;
    }

    public BaseResultRestClientException(Throwable cause, BaseResult<?> result) {
        super(cause);
        this.result = result;
    }


    public <T> BaseResult<T> getBaseResult() {
        return (BaseResult<T>) result;
    }
}
