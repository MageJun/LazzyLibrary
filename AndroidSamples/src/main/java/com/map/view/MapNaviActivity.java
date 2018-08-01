package com.map.view;

import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManagerFactory;
import com.baidu.navisdk.adapter.IBNRouteGuideManager;
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
import com.zed3.sipua.xydj.ui.TestDemoMainActivity;

import java.util.ArrayList;
import java.util.List;

public class MapNaviActivity extends AppCompatActivity {

    private static final String TAG = "MapNaviTrace";
    private final static String SD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    private final static String APP_SD_ROOT_NAME = AppApplication.sContext.getPackageName();

    private SpeechSynthesizer speechSynthesizer = SpeechSynthesizer.getInstance();
    private boolean isSpeaking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_navi);
        initNavi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechSynthesizer.release();
        speechSynthesizer = null;

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

    private void startNavi() {
        BNRoutePlanNode sNode = new BNRoutePlanNode(116.30784537597782, 40.057009624099436, "百度大厦", "百度大厦", BNRoutePlanNode.CoordinateType.BD09LL);
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
}
