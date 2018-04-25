package com.lw.android.demo.model.service;

import com.lw.android.demo.model.PersonalData;

import java.util.List;

/**
 * Created by zed on 2018/4/25.
 */

public interface PersonServiceDataModelListener {


    void onError(int mode);

    void onSuccess(List<PersonalData> datas);

}
