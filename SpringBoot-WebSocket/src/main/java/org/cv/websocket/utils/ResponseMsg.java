package org.cv.websocket.utils;

import java.util.List;

/**
 * Created by Ale on 2020/4/1
 */
public class ResponseMsg<E> {

    private boolean success;

    private List<E> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public ResponseMsg() {
        this.success = false;
        this.data = null;
    }

    public ResponseMsg(boolean success, List<E> data) {
        this.success = success;
        this.data = data;
    }
}
