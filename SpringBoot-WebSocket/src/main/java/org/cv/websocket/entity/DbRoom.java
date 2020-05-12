package org.cv.websocket.entity;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Ale on 2020/4/11
 */
public class DbRoom implements Serializable {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String uname;

    @Getter
    @Setter
    private String remark;

    @Getter
    @Setter
    private String coverurl;

    @Getter
    @Setter
    private long uid;

    @Getter
    @Setter
    private int type;

    public DbRoom() {
        super();
    }

    public DbRoom(String uname, String remark, String coverurl, long uid, int type) {
        this.uname = uname;
        this.remark = remark;
        this.coverurl = coverurl;
        this.uid = uid;
        this.type = type;
    }

    @Override
    public String toString() {
        return "DbRoom{" +
                "id=" + id +
                ", uname='" + uname + '\'' +
                ", remark='" + remark + '\'' +
                ", coverurl='" + coverurl + '\'' +
                ", uid=" + uid +
                ", type=" + type +
                '}';
    }
}
