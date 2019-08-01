package com.lw.demo.android.samples;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import android.util.Log;

import com.helper.SqliteHelper;
import com.zed3.sipua.xydj.ui.dao.GroupInviteDao;
import com.zed3.sipua.xydj.ui.dao.domain.GroupInvite;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private final static String TAG = "TestTrace";
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.lw.demo.adnroid.samples", appContext.getPackageName());
    }

    @Test
    public void daoTest() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        GroupInviteDao dao = new GroupInviteDao(appContext);
        GroupInvite invite = new GroupInvite();
        invite.setGroupName("组1");
        invite.setInviteUser("李明");
        invite.setInvitedUser("张三");
        invite.setType(GroupInvite.INVITE_TYPE.RECEIVE);
        invite.setTime(System.currentTimeMillis());
        GroupInvite result = (GroupInvite) dao.inert(invite);
        assertNotNull(result);
        Log.i(TAG,"result = "+result.toString());
    }
    @Test
    public void queryTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        GroupInviteDao dao = new GroupInviteDao(appContext);
        List<GroupInvite> results = dao.query();
        assertNotNull(results);
        Log.i(TAG,"results = "+results.toString());

        int raws = dao.delete(results.get(0));
        Log.i(TAG,"d_id = "+raws);

    }

    @Test
    public void sqliteHelperTest(){
        String sql = SqliteHelper.getOffsetDayStart(6,false);
    }
}
