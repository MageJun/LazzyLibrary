package com.map.view;

import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.lw.map.lib.location.service.LocationChangeEvent;
import com.android.lw.map.lib.location.service.LocationServiceManager;
import com.android.lw.map.lib.overlayutil.DrivingRouteOverlay;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManagerFactory;
import com.baidu.navisdk.adapter.IBNRoutePlanManager;
import com.baidu.navisdk.adapter.IBNTTSManager;
import com.baidu.navisdk.adapter.IBaiduNaviManager;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.lazzy.common.lib.utils.L;
import com.lw.demo.android.samples.AppApplication;
import com.lw.demo.android.samples.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MapNaviActivity extends AppCompatActivity {

    private static final String TAG = "MapNaviTrace";
    private final static String SD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    private final static String APP_SD_ROOT_NAME = AppApplication.sContext.getPackageName();

    private SpeechSynthesizer speechSynthesizer = SpeechSynthesizer.getInstance();
    private boolean isSpeaking = false;

    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private boolean isFirstLoad = false;
    private LatLng mLocLatLng;
    private BDLocation mLocData;
    private LatLng sLatlng,eLatlng;
    private RoutePlanSearch mRouteSearch;
    private DrivingRouteOverlay mDrivingRouteOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_navi);
        EventBus.getDefault().register(this);
        isFirstLoad = true;
        initView();
        initNavi();
        initData();
    }

    private void initData() {
        //北大 116.32608,39.997896
        //天安门  116.401969,39.913385
        sLatlng = new LatLng(39.997896,116.32608);
        eLatlng = new LatLng(39.913385,116.401969);

        mRouteSearch = RoutePlanSearch.newInstance();
        mRouteSearch.setOnGetRoutePlanResultListener(mRoutePlanListener);
        mDrivingRouteOverlay = new DrivingRouteOverlay(mBaiduMap){

        };

    }

    private void initView() {
        mMapView = findViewById(R.id.mapview);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {//地图加载完成后，开始定位
                if(isFirstLoad){
                    isFirstLoad = false;
                    requestLocation();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isFirstLoad){
            requestLocation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechSynthesizer.release();
        speechSynthesizer = null;
        EventBus.getDefault().unregister(this);
        mBaiduMap.clear();
        mBaiduMap = null;
        mMapView.onDestroy();
        mMapView = null;
        mRouteSearch.destroy();

    }

    private long time = 0l;
    private void requestLocation(){
        if(System.currentTimeMillis()-time<3*1000){
            return ;
        }
        LocationServiceManager.getInstance().requestLocation();
        time = System.currentTimeMillis();
    }

    private void zoomToSpan(List<LatLng> latLngs) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < latLngs.size(); i++) {
            builder.include(latLngs.get(i));
        }
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder.build()));
    }

    private void moveTo(LatLng latLng) {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng).zoom(19.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleLocationEvent(LocationChangeEvent event){
        if(event!=null){
            BDLocation locationData = event.getBdLocation();
            if(locationData!=null){
                mLocData = locationData;
                mLocLatLng = new LatLng(locationData.getLatitude(),locationData.getLongitude());
                if(mLocData!=null)
                    sLatlng = mLocLatLng;
                moveTo(mLocLatLng);
            }
        }
    }

    private void initNavi() {
        /**
         * 参数1 Activity，最好是应用主页面
         * 参数2：系统SD卡根目录路径
         * 参数3：应用在SD卡中的目录名
         * 参数4：初始状态监听方法
         */
        BaiduNaviManagerFactory.getBaiduNaviManager().init(this, SD_ROOT, APP_SD_ROOT_NAME, new IBaiduNaviManager.INaviInitListener() {
            public static final String TAG ="NaviInitTrace" ;

            @Override
            public void onAuthResult(int i, String s) {
                L.i(TAG,"onAuthResult i = "+i + ",s = "+s);
            }

            @Override
            public void initStart() {
                L.i(TAG,"initStart ");
            }

            @Override
            public void initSuccess() {
                L.i(TAG,"initSuccess ");

                initTTS();
            }

            @Override
            public void initFailed() {
                L.i(TAG,"initFailed ");
            }
        });
    }
    /**
     * 初始化百度TTS
     */
    private void initTTS() {
        isSpeaking = false;
        speechSynthesizer.setContext(this);
        speechSynthesizer.setAppId("11613278");
        speechSynthesizer.setApiKey("xnOWC7swG88qzBX6XBZUT1Aj","8WUPpPHRhecdIe0TRAZnvcSsohfRMzDu");
        speechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
            @Override
            public void onSynthesizeStart(String s) {

            }

            @Override
            public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

            }

            @Override
            public void onSynthesizeFinish(String s) {

            }

            @Override
            public void onSpeechStart(String s) {
                isSpeaking = true;
            }

            @Override
            public void onSpeechProgressChanged(String s, int i) {

            }

            @Override
            public void onSpeechFinish(String s) {
                isSpeaking = false;
            }

            @Override
            public void onError(String s, SpeechError speechError) {
                isSpeaking = false;
            }
        });

        int initCode = speechSynthesizer.initTts(TtsMode.MIX);//离在线混合模式
        L.i(TAG,"TTS initCode = "+initCode);
        BaiduNaviManagerFactory.getTTSManager().initTTS(new IBNTTSManager.IBNOuterTTSPlayerCallback() {
            @Override
            public int getTTSState() {
                /** 播放器空闲 */
//            int PLAYER_STATE_IDLE = 1;
//            /** 播放器正在播报 */
//            int PLAYER_STATE_PLAYING = 2;
                return isSpeaking?2:1;
            }

            @Override
            public int playTTSText(String s, String s1, int i, String s2) {
                return speechSynthesizer.speak(s);
            }

            @Override
            public void stopTTS() {
                speechSynthesizer.pause();
            }
        });
    }
    private boolean isRoutePlatn = false;
    private void startNavi() {
        if(!isRoutePlatn){
            showRoute();
            return ;
        }
        isRoutePlatn = false;
        BNRoutePlanNode sNode = new BNRoutePlanNode(116.30784537597782, 40.057009624099436, "百度大厦", "百度大厦", BNRoutePlanNode.CoordinateType.BD09LL);
        if(mLocData!=null){
            sNode =new BNRoutePlanNode(mLocData.getLongitude(), mLocData.getLatitude(), mLocData.getBuildingName(), mLocData.getDistrict(), BNRoutePlanNode.CoordinateType.BD09LL);
        }
        BNRoutePlanNode  eNode = new BNRoutePlanNode(116.40386525193937, 39.915160800132085, "北京天安门", "北京天安门", BNRoutePlanNode.CoordinateType.BD09LL);

        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);

            BaiduNaviManagerFactory.getRoutePlanManager().routeplanToNavi(
                    list,
                    IBNRoutePlanManager.RoutePlanPreference.ROUTE_PLAN_PREFERENCE_DEFAULT,
                    null,
                    new Handler(Looper.getMainLooper()) {
                        @Override
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_START:
                                    Toast.makeText(MapNaviActivity.this, "算路开始", Toast.LENGTH_SHORT)
                                            .show();
                                    break;
                                case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_SUCCESS:
                                    Toast.makeText(MapNaviActivity.this, "算路成功", Toast.LENGTH_SHORT)
                                            .show();
                                    break;
                                case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_FAILED:
                                    Toast.makeText(MapNaviActivity.this, "算路失败", Toast.LENGTH_SHORT)
                                            .show();
                                    break;
                                case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_TO_NAVI:
                                    Toast.makeText(MapNaviActivity.this, "算路成功准备进入导航", Toast.LENGTH_SHORT)
                                            .show();

                                    Intent intent = new Intent(MapNaviActivity.this,
                                            NaviShowActivity.class);
                                    startActivity(intent);
                                    break;
                                default:
                                    // nothing
                                    break;
                            }
                        }
                    });
        }
    }

    private void showRoute() {
        /**
         * 设置当前城市,检索打车信息时必需
         * currentCity(java.lang.String cityName)
         设置起点
         DrivingRoutePlanOption   from(PlanNode from)
         设置途经点
         DrivingRoutePlanOptionpassBy(java.util.List<PlanNode> wayPoints)
         设置驾车路线规划策略
         DrivingRoutePlanOption policy(DrivingRoutePlanOption.DrivingPolicy policy)
         设置终点
         DrivingRoutePlanOption to(PlanNode to)
         设置是否支持路况数据
         DrivingRoutePlanOption trafficPolicy(DrivingRoutePlanOption.DrivingTrafficPolicy trafficPolicy)


         DrivingPolicy policy
         设置驾车路线规划策略
         ECAR_AVOID_JAM
         驾车策略： 躲避拥堵
         ECAR_DIS_FIRST
         驾乘检索策略常量：最短距离
         ECAR_FEE_FIRST
         驾乘检索策略常量：较少费用
         ECAR_TIME_FIRST
         驾乘检索策略常量：时间优先


         */
        PlanNode sNode = PlanNode.withLocation(sLatlng);
        PlanNode tNode =  PlanNode.withLocation(eLatlng);
        DrivingRoutePlanOption option = new DrivingRoutePlanOption();
        option.from(sNode).to(tNode);
        //时间优先
        option.policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_TIME_FIRST);
        //显示路线和交通情况
        option.trafficPolicy(DrivingRoutePlanOption.DrivingTrafficPolicy.ROUTE_PATH_AND_TRAFFIC);
        mRouteSearch.drivingSearch(option);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.request_loc_icon:
                requestLocation();
                break;
            case R.id.navi_icon:
                startNavi();
                break;
        }
    }

    private OnGetRoutePlanResultListener mRoutePlanListener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            //获取驾车路线
            if(drivingRouteResult!=null&&drivingRouteResult.getRouteLines()!=null&&drivingRouteResult.getRouteLines().size()>0){
                DrivingRouteLine line = drivingRouteResult.getRouteLines().get(0);
                mDrivingRouteOverlay.setData(line);
                mDrivingRouteOverlay.addToMap();
                mDrivingRouteOverlay.zoomToSpan();
            }
            isRoutePlatn = true;
        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }
    };
}
