package com.lw.demo.android.samples;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void listTest(){
        List<String> list = new ArrayList<>();
        for (int i =0;i<3;i++){
            list.add(i+"");
        }
        list.add(1,"test");

        Log.i("TestTrace","");
    }
}