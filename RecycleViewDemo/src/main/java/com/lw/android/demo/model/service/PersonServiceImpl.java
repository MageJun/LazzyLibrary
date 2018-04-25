package com.lw.android.demo.model.service;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.lw.android.demo.model.PersonalData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zed on 2018/4/14.
 */

public class PersonServiceImpl implements IPersonService {
    private static final String TAG = PersonServiceImpl.class.getSimpleName();

    private HandlerThread mThread;
    private Handler mHandler;

    private static final int MSG_LISTPERSONS = 1;

    protected PersonServiceImpl(){
        mThread = new HandlerThread("PersonServiceImpl thread");
        mThread.start();

        mHandler = new Handler(mThread.getLooper()){

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case MSG_LISTPERSONS:
                        listPersonsInner();
                        break;
                }
            }
        };
    }

    @Override
    public void listPersons() {
        mHandler.sendEmptyMessage(MSG_LISTPERSONS);
    }
    private void listPersonsInner(){
        Log.i(TAG,"listPersonsInner tName = "+Thread.currentThread().getName());
        try{
            List<PersonalData> data = createTmpData(20);
            Log.i(TAG,"listPersonsInner datas = "+data.toString());

            if(mListener!=null){
                mListener.onSuccess(data);
            }
        }catch (Exception e){
            if(mListener!=null){
                mListener.onError(-1);
            }
        }

    }

    private PersonServiceDataModelListener mListener;
    @Override
    public void setPersonServiceListener(PersonServiceDataModelListener listener) {
        mListener = listener;
    }

    private static final String[] urls = {"http://img4.imgtn.bdimg.com/it/u=3502465005,4153501499&fm=200&gp=0.jpg",
    "http://img2.imgtn.bdimg.com/it/u=431792944,1008990118&fm=200&gp=0.jpg",
    "http://img5.imgtn.bdimg.com/it/u=1946205328,3631959401&fm=200&gp=0.jpg",
    "http://img5.imgtn.bdimg.com/it/u=394265007,236680862&fm=200&gp=0.jpg",
    "http://img4.imgtn.bdimg.com/it/u=3863710847,2820277857&fm=200&gp=0.jpg",
    "http://pic27.photophoto.cn/20130601/0036036822545933_b.jpg",
    "http://img3.duitang.com/uploads/item/201209/28/20120928220354_TtLZS.thumb.700_0.jpeg",
    "http://img.pconline.com.cn/images/upload/upc/tx/itbbs/1309/21/c32/26062383_1379767339683_mthumb.jpg",
    "http://up.enterdesk.com/edpic_source/60/5b/5e/605b5e574a186d61b0e1bd1d80e2aff1.jpg",
    "http://img1.3lian.com/2015/a1/95/d/148.jpg",
    "http://image.tianjimedia.com/uploadImages/2014/336/54/6996H6I02KO2.jpg",
    "http://image.tianjimedia.com/uploadImages/2014/107/16/16WWUAAXZY8S.jpg",
    "http://up.enterdesk.com/edpic_source/e5/8d/fb/e58dfb2548e2059b785d42033500459f.jpg",
    "http://up.enterdesk.com/edpic_source/2e/ef/e8/2eefe86601b51146caf6f97016500ddc.jpg",
    "http://up.enterdesk.com/edpic_source/58/b4/89/58b489170c411bd992cf43e1d291f529.jpg"};

    private List<PersonalData> createTmpData(int count){
        List<PersonalData> list = new ArrayList<PersonalData>();

        for(int i = 0;i<count;i++){
            PersonalData data = new PersonalData();
            data.setName("第"+i+"数据层");
            Random random = new Random();
            int r = random.nextInt(14);
            if(r<0||r>=urls.length){
                r= 0;
            }
            data.setImgUrl(urls[r]);
            list.add(data);
        }

        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }
}
