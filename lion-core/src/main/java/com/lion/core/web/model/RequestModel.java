package com.lion.core.web.model;

import com.alibaba.fastjson.JSONObject;

public class RequestModel {
    private final JSONObject params;

    public RequestModel(JSONObject params) {
        this.params=params;
    }

    public JSONObject getParams() {
        return params;
    }
}
