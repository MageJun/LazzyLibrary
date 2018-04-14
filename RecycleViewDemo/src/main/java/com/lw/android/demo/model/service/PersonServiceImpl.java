package com.lw.android.demo.model.service;

import com.lw.android.demo.model.PersonalData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zed on 2018/4/14.
 */

public class PersonServiceImpl implements IPersonService {

    protected PersonServiceImpl(){}

    @Override
    public List<PersonalData> listPersons() {

        return createTmpData(20);
    }

    private List<PersonalData> createTmpData(int count){
        List<PersonalData> list = new ArrayList<PersonalData>();

        for(int i = 0;i<count;i++){
            PersonalData data = new PersonalData();
            data.setName("第"+i+"数据层");
            list.add(data);
        }
        return list;
    }
}
