package com.lw.android.demo.presenter;

import com.lw.android.demo.model.PersonalData;
import com.lw.android.demo.model.service.GlobalServiceManager;
import com.lw.android.demo.model.service.IPersonService;
import com.lw.android.demo.model.service.PersonServiceDataModelListener;
import com.lw.android.demo.view.RecyclerViewer;

import java.util.List;

/**
 * Created by zed on 2018/4/13.
 */

public class RecyclerViewPresenter extends BasePresenter implements PersonServiceDataModelListener{


    private IPersonService mPersonService;

    private RecyclerViewer<PersonalData> mViewer;

    private List<PersonalData> mData;

    public RecyclerViewPresenter(RecyclerViewer viewer){
        mPersonService = GlobalServiceManager.getIPersonService();
        mPersonService.setPersonServiceListener(this);
        this.mViewer = viewer;
    }

    public void getData(){
        showProgress();
        mPersonService.listPersons();
    }

    private void showProgress(){
        mViewer.showProgress();
    }

    private void hideProgress(){
        mViewer.hideProgress();

    }


    @Override
    public void onError(int mode) {
        hideProgress();
    }

    @Override
    public void onSuccess(List<PersonalData> datas) {
        hideProgress();
        mViewer.onDataNotifytion(datas);
    }


}
