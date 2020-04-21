package com.vawo.foundation.stock.vo;

import java.util.HashMap;
import java.util.Map;

public class TushareParam {
    private String api_name;

    private String token;

    private Map<String, Object> params = new HashMap<>();

    private String fields;

    public TushareParam(String api_name, String token) {
        this.api_name = api_name;
        this.token = token;
    }

    public TushareParam() {
    }

    public void addParam(String name, String value) {
        params.put(name, value);
    }

    public void addParams(Map<String, Object> params) {
        params.putAll(params);
    }

    public String getApi_name() {
        return api_name;
    }

    public void setApi_name(String api_name) {
        this.api_name = api_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
