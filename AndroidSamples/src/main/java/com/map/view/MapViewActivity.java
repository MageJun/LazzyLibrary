package com.map.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.animation.AlphaAnimation;
import com.baidu.mapapi.animation.Animation;
import com.baidu.mapapi.animation.ScaleAnimation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Circle;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Text;
import com.baidu.mapapi.model.LatLng;
import com.lw.demo.android.samples.R;
import com.lw.demo.android.samples.SlidingUpPannelLayoutCustom;


public class MapViewActivity extends AppCompatActivity {

    private static final String TAG = MapViewActivity.class.getSimpleName();
    private MapView mMapView;
    private BaiduMap mMap;
    private LocationClient mLocClient;
    private CircleOptions co1,co2,co3;//圆形覆盖物
    private Circle c1,c2,c3;//圆形覆盖物
    private int mCircleRadius = 200;//圆形覆盖物半径，默认200，实际取值屏幕宽度/4
    private MyLocationData locData;//当前位置信息
    private boolean isFirstLoc=true;
    private boolean isFirstLoad = true;
    private Marker mLocMarker;//用来替代“我的位置"的marker
    private Text mTextOverlay;
    private InfoWindow mTextInfoWindow;
    private View mTilView;
    private BitmapDescriptor mLocMarkerIcon = BitmapDescriptorFactory.fromResource(R.drawable.marker_icon_start);//当前位置的Icon
    private SlidingUpPannelLayoutCustom mSlidingLayout;
    private static final int REQUEST_LOCATION = 0;
    private static final int START_ANIMATION = 1;
    private static final int MSG_START_C1 = 0;
    private static final int MSG_START_C2 = 1;
    private static final int MSG_START_C3 = 2;
    private static final int MSG_START_TIME = 3;
    private int mCount = 0;
    private Context mContext;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REQUEST_LOCATION:
                    requestLocation();
                    break;
                case START_ANIMATION:
                    int start = msg.arg1;
                    switch (start){
                        case MSG_START_C1:
                            scalecircle(c1);
                            break;
                        case MSG_START_C2:
                            scalecircle(c2);
                            break;
                        case MSG_START_C3:
                            scalecircle(c3);
                            break;
                        case MSG_START_TIME:
                            drawTitleMarkerInfo();
                            mCount--;
                            if(mCount<0){
                                mCount = 0;
                                return ;
                            }
                            Message message = Message.obtain();
                            message.what = START_ANIMATION;
                            message.arg1 = MSG_START_TIME;
                            mHandler.sendMessageDelayed(message,1000);
                            break;
                    }
                    break;
            }

        }
    };
    private TextView mPopText,mTilMarkerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        mContext = this;
        initView();
        initData();
        initLocation();
    }

    private void initView() {
         mMapView = findViewById(R.id.map_view);
         mCircleRadius = getResources().getDisplayMetrics().widthPixels/4;
         mSlidingLayout = findViewById(R.id.sliding_layout);
         mSlidingLayout.setOverlayed(true);
         mSlidingLayout.setMainCanTouch(false);
        invisibleMapInfo();
    }

    private void invisibleMapInfo(){
        //隐藏百度的LOGO
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }

        // 不显示地图上比例尺
        mMapView.showScaleControl(false);

        // 不显示地图缩放控件（按钮控制栏）
        mMapView.showZoomControls(false);
    }


    private void initData() {
        mMap = mMapView.getMap();
        mMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {//地图加载完成后，开始定位
                if(isFirstLoad){
                    isFirstLoad = false;
                    mHandler.sendEmptyMessageDelayed(REQUEST_LOCATION,1000);
                }
            }
        });
    }

    private void initLocation() {
        // 开启定位图层
        mMap.setMyLocationEnabled(true);
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(mListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5*60*1000);//定位间隔5分钟
        mLocClient.setLocOption(option);
    }

    private void requestLocation() {
        if(!mLocClient.isStarted()) {
            // 定位初始化
            mLocClient.start();
        }
        else{
            mLocClient.requestLocation();
        }
    }

    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
           Log.i(TAG,"位置更新");
            // map view 销毁后不在处理新接收的位置
            if (bdLocation == null || mMapView == null) {
                return;
            }
            locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    /*.direction(mCurrentDirection)*/.latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();

            addOverlay();
//            mMap.setMyLocationData(locData);
            if(isFirstLoc){
                isFirstLoc = false;
                Message msg1 = Message.obtain();
                msg1.what = START_ANIMATION;
                Message msg2 = Message.obtain(msg1);
                Message msg3 = Message.obtain(msg1);
                Message msg4 = Message.obtain(msg1);
                msg1.arg1 = MSG_START_C1;
                mHandler.sendMessageDelayed(msg1,0);
                msg2.arg1 = MSG_START_C2;
                mHandler.sendMessageDelayed(msg2,800);
                msg3.arg1 = MSG_START_C3;
                mHandler.sendMessageDelayed(msg3,1600);
                msg4.arg1 = MSG_START_TIME;
                mCount = 45;
                mHandler.sendMessage(msg4);

            }
        }
    };
    /**
     * 添加覆盖物
     */
    private void addOverlay() {
        Log.i(TAG,"addOverlay");
        if(locData!=null){
            LatLng ll = new LatLng(locData.latitude,locData.longitude);
            if(mLocMarker==null){
                MarkerOptions options = new MarkerOptions();
                options.position(ll).icon(mLocMarkerIcon).anchor(/*mLocMarkerIcon.getBitmap().getWidth()/2*/0.5f,1.0f).zIndex(Integer.MAX_VALUE);
                options.animateType(MarkerOptions.MarkerAnimateType.drop);
                options.flat(false);
                options.perspective(false);
                mLocMarker = (Marker) mMap.addOverlay(options);
                Log.i(TAG,"ax = "+mLocMarker.getAnchorX()+"ay = "+mLocMarker.getAnchorY());
            }else{
                mLocMarker.setPosition(ll);
            }

            if(c1==null){
                co1 = new CircleOptions();
                co1.radius(mCircleRadius);
                co1.center(ll);
                co1.fillColor(Color.argb(0, 191, 226, 206));
                c1= (Circle) mMap.addOverlay(co1);
            }else{
                c1.setCenter(ll);
            }
            if(c2==null){
                co2 = new CircleOptions();
                co2.center(ll);
                co2.radius(mCircleRadius);
                co2.fillColor(Color.argb(0, 191, 226, 206));
                c2 = (Circle) mMap.addOverlay(co2);
            }else{
                c2.setCenter(ll);
            }

            if(c3==null){
                co3 = new CircleOptions();
                co3.center(ll);
                co3.radius(mCircleRadius);
                co3.fillColor(Color.argb(0, 191, 226, 206));
                c3 = (Circle) mMap.addOverlay(co3);
            }else{
                c3.setCenter(ll);
            }

            drawTitleMarkerInfo();
            setMapCenter(mLocMarker.getPosition());
        }
    }

    private void drawTitleMarkerInfo(){
        if(mTilView==null){
            mTilView = LayoutInflater.from(this).inflate(R.layout.map_title_marker,null);
            mTilMarkerInfo = mTilView.findViewById(R.id.til_marker_info);
        }
        updateInfoWindow();
        mTilView.setOnClickListener(mInfoWindowClickListener);

    }

    private boolean updateInfoWindow(){
        String  text = "时间：" + mCount;
        if(mCount>0){
            text = "时间："+mCount;
        }else{
            mMapView.removeView(mTilView);
            return false;
        }
        if(mLocMarker!=null&&mTilMarkerInfo!=null && mTilView!=null) {
            ViewGroup.LayoutParams params = new MapViewLayoutParams.Builder()
                    .layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)// 按照经纬度设置位置
                    .position(mLocMarker.getPosition())
                    .width(MapViewLayoutParams.WRAP_CONTENT)
                    .height(MapViewLayoutParams.WRAP_CONTENT)
                    .yOffset(-mLocMarker.getIcon().getBitmap().getHeight())
                    .build();
            mTilMarkerInfo.setText(text);
            //解决更新InfoWindow内容时，百度地图自动生成新的View，但之前的View没有移除，导致出现重影的问题
            mMapView.removeView(mTilView);
            mMapView.addView(mTilView, params);
        }
        return true;
    }

    private View.OnClickListener mInfoWindowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(mContext,"infoWindow click",Toast.LENGTH_SHORT).show();
        }
    };

    private void setMapCenter(LatLng latLng) {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng).zoom(19.0f);
        Point scrrentPoint = new Point();
        scrrentPoint.x = getResources().getDisplayMetrics().widthPixels / 2;
        scrrentPoint.y = getResources().getDisplayMetrics().heightPixels / 2;
//        builder.targetScreen(scrrentPoint);
        mMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

    }

    private void clearOverlay(){
        mMap.clear();
        c1 = null;
        c2 = null;
        c3 = null;
        mTilView = null;
        mLocMarker = null;
    }

    /**
     * 位置波纹扩散动画
     * @param ac
     */
    private void scalecircle(final Circle ac) {
        Interpolator interpolator1 = new LinearInterpolator();
        ValueAnimator vm = ValueAnimator.ofFloat(0,(float)ac.getRadius());
        vm.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curent = (float) animation.getAnimatedValue();
                ac.setRadius((int) curent);
            }
        });
        ValueAnimator vm1 = ValueAnimator.ofInt(125,0);
        vm1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int color = (int) animation.getAnimatedValue();
                ac.setFillColor(Color.argb(color, 191, 226, 206));
            }
        });
        vm.setRepeatCount(Integer.MAX_VALUE);
        vm.setRepeatMode(ValueAnimator.RESTART);
        vm1.setRepeatCount(Integer.MAX_VALUE);
        vm1.setRepeatMode(ValueAnimator.RESTART);
        AnimatorSet set = new AnimatorSet();
        set.play(vm).with(vm1);
        set.setDuration(2500);
        set.setInterpolator(interpolator1);
        set.start();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLocClient!=null){
            mLocClient.stop();
            mLocClient.unRegisterLocationListener(mListener);
            mLocClient = null;
        }
        mMap.setMyLocationEnabled(false);
        clearOverlay();
        mMapView.onDestroy();
        mMapView = null;
    }

    /**
     * 创建缩放动画
     */
    private Animation getScaleAnimation() {
        ScaleAnimation mScale = new ScaleAnimation(1f, 2f, 1f);
        mScale.setDuration(2000);
        mScale.setRepeatMode(Animation.RepeatMode.RESTART);//动画重复模式
        mScale.setRepeatCount(1);//动画重复次数
        mScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }

            @Override
            public void onAnimationCancel() {
            }

            @Override
            public void onAnimationRepeat() {
            }
        });

        return mScale;
    }

    /**
     * 创建透明度动画
     */
    private Animation getAlphaAnimation() {
        AlphaAnimation mAlphaAnimation = new AlphaAnimation(1f, 0f, 1f);
        mAlphaAnimation.setDuration(3000);
        mAlphaAnimation.setRepeatCount(1);
        mAlphaAnimation.setRepeatMode(Animation.RepeatMode.RESTART);
        mAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }

            @Override
            public void onAnimationCancel() {
            }

            @Override
            public void onAnimationRepeat() {
            }
        });

        return mAlphaAnimation;
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.request_loc_icon:
                mHandler.sendEmptyMessage(REQUEST_LOCATION);
                break;
            case R.id.btn_submit:

                break;
        }
    }
}
