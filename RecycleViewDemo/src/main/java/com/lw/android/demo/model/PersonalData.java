package com.lw.android.demo.model;

/**
 * Created by zed on 2018/4/13.
 */

public class PersonalData {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PersonalData{" +
                "name='" + name + '\'' +
                '}';
    }
}
