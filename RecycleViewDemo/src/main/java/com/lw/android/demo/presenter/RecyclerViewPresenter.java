package com.lw.android.demo.presenter;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.lw.android.demo.model.PersonalData;
import com.lw.android.demo.model.service.GlobalServiceManager;
import com.lw.android.demo.model.service.IPersonService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zed on 2018/4/13.
 */

public class RecyclerViewPresenter extends BasePresenter {


    public interface Listener{
        void dateRefreshCompleted(List<PersonalData> data);
    }

    private IPersonService mPersonService;

    private List<PersonalData> mData;

    private Listener mListener;

    private HandlerThread mHadlerThread = new HandlerThread("RVPresenterThread");

    private Handler handler = new Handler(mHadlerThread.getLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    public RecyclerViewPresenter(){
        mPersonService = GlobalServiceManager.getIPersonService();
        mHadlerThread.start();
    }

    public void setmListener(Listener listener){
        this.mListener = listener;
    }

    public List<PersonalData> getData(){
        if(mData==null){
            requestData();
        }

        return mData;
    }

    public void refreshData(){
        requestData();
    }

    private void requestData(){
        mData  = mPersonService.listPersons();
    }

}
