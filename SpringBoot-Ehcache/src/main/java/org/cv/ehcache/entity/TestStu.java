package org.cv.ehcache.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Ale on 2020/4/1
 */
public class TestStu implements Serializable {
    @Setter
    @Getter
    private Integer id;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private LocalDateTime date;

    public TestStu() {
        super();
    }

    public TestStu(String name, LocalDateTime date) {
        this.name = name;
        this.date = date;
    }

    public TestStu(Integer id, String name, LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    @Override
    public String toString() {
        return "TestStu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
