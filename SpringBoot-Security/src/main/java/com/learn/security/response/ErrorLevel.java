package com.learn.security.response;

/**
 * Created by Ale on 2020/9/4
 */
public enum ErrorLevel {

    System(1),
    SessionTimeout(2),
    PermissionDenied(3),
    User(100);

    private int level = 0;

    private ErrorLevel(int level) {
        this.level = level;
    }

    public String toString() {
        return String.valueOf(this.level);
    }

    public int getLevel() {
        return this.level;
    }

}
