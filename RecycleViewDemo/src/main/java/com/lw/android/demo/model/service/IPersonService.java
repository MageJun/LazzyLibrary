package com.lw.android.demo.model.service;

/**
 * Created by zed on 2018/4/14.
 */

public interface IPersonService {

    void listPersons();

    void setPersonServiceListener(PersonServiceDataModelListener listener);
}
