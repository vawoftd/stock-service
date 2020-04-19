package com.vawo.foundation.demo.utils.rest;

/**
 * RestClientException
 *
 * @author vawo
 * @date 2019-03-28
 */
public class RestClientException extends Exception {

    public RestClientException() {
    }

    public RestClientException(String message) {
        super(message);
    }

    public RestClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestClientException(Throwable cause) {
        super(cause);
    }

    public RestClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
