package com.lw.android.demo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.lw.android.demo.model.PersonalData;
import com.lw.android.demo.model.service.GlobalServiceManager;
import com.lw.android.demo.model.service.IPersonService;

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
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.lw.android.demo", appContext.getPackageName());
    }

   @Test
    public void testPersonService(){
        IPersonService service = GlobalServiceManager.getIPersonService();

        List<PersonalData> list = service.listPersons();
        assertNotNull(list);
    }
}
