package com.learn.security.response;

import com.alibaba.fastjson.JSONObject;
import com.learn.security.utils.ESUtils;

/**
 * 统一响应结果
 * Created by Ale on 2020/9/4
 */
public class ResponseData extends JSONObject {

    public ResponseData(){ }

    public ResponseData(long id, ErrorData error) {
        this.put("id", id);
        this.put("code", error.getLevel());
        this.put("data", error);
    }

    private JSONObject getData() {
        JSONObject data = ESUtils.jsonGetObject(this, "data");
        if (data == null) {
            data = new JSONObject();
            this.put("data", data);
        }
        return data;
    }

    public ResponseData set(String key, Object value) {
        this.getData().put(key, value);
        return this;
    }

    public static ResponseData data() {
        ResponseData responseData = new ResponseData();
        responseData.put("code", 0);
        responseData.put("data", new JSONObject());
        return responseData;
    }

    public static ResponseData success() {
        ResponseData responseData = new ResponseData();
        responseData.put("code", 0);
        responseData.getData().put("success", true);
        return responseData;
    }

    public static ResponseData operateFail() {
        return operateFail("");
    }

    public static ResponseData operateFail(String message) {
        ResponseData d = new ResponseData();
        d.put("code", 0);
        JSONObject data = d.getData();
        data.put("success", false);
        data.put("message", message);
        return d;
    }


    // TODO:权限错误处理

    /**
     * 系统级错误处理
     * @param friendlyMessage
     * @return
     */
    public static ResponseData systemError(String friendlyMessage) {
        return systemError(friendlyMessage, "", "");
    }

    public static ResponseData systemError(String friendlyMessage, String detailMessage) {
        return systemError(friendlyMessage, detailMessage, "");
    }

    public static ResponseData systemError(String friendlyMessage, String detailMessage, String errCode) {
        ResponseData responseData = new ResponseData();
        responseData.put("code", ErrorLevel.System.getLevel());
        responseData.put("data", new ErrorData(ErrorLevel.System, errCode, friendlyMessage, detailMessage));
        return responseData;
    }

    /**
     * 用户级错误处理
     * @param friendlyMessage
     * @return
     */
    public static ResponseData userError(String friendlyMessage) {
        return userError(friendlyMessage, "", "");
    }

    public static ResponseData userError(String friendlyMessage, String detailMessage) {
        return userError(friendlyMessage, detailMessage, "");
    }

    public static ResponseData userError(String friendlyMessage, String detailMessage, String errCode) {
        ResponseData d = new ResponseData();
        d.put("code", ErrorLevel.User.getLevel());
        d.put("data", new ErrorData(ErrorLevel.User, errCode, friendlyMessage, detailMessage));
        return d;
    }

}
