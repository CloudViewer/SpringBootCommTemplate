package com.learn.security.response;

import com.alibaba.fastjson.JSONObject;
import com.learn.security.utils.ESUtils;

/**
 * Created by Ale on 2020/9/4
 */
public class ErrorData extends JSONObject {

    public int getLevel() {
        return ESUtils.jsonGetInt(this,"errorLevel");
    }

    public ErrorData(ErrorLevel level, String code, String friendlyMessage, String detailMessage) {
        this.put("errorLevel", level);
        this.put("code", code);
        this.put("friendlyMessage", friendlyMessage);
        this.put("detailMessage", detailMessage);
    }
}
