package com.vawo.foundation.stock.vo;

public class TushareResponse {
    private int code;

    private String msg;

    private TushareData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TushareData getData() {
        return data;
    }

    public void setData(TushareData data) {
        this.data = data;
    }
}
