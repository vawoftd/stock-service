package com.vawo.foundation.demo.utils.result;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class BaseResult<T> implements Serializable {
    /**
     * 返回异常码
     */
    @ApiModelProperty(value = "异常码")
    private String errorCode = "0";
    /**
     * 返回异常消息
     */
    @ApiModelProperty(value = "返回消息")
    private String errorMsg;
    /**
     * 返回具体的业务数据
     */
    @ApiModelProperty(value = "业务数据")
    private T data;

    /**
     * 返回具体的业务数据
     */
    @ApiModelProperty(hidden = true, value = "返回堆栈异常信息,正常情况下为空，只发生在业务没有定义异常码的情况")
    private String detail;


    public boolean isSuccess() {
        return errorCode.equals(0);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
