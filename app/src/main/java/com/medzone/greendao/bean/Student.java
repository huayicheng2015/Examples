package com.medzone.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2016/11/15.
 */

@Entity
public class Student {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Property(nameInDb = "STUDENT_NAME")
    private String name;

    @Generated(hash = 225149174)
    public Student(Long id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 1556870573)
    public Student() {
    }

    @Override
    public String toString() {
        return id + ":" + name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
