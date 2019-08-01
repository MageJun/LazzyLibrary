package com.zed3.sipua.xydj.ui.groupinviteinfo.helper;

import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;

public class RecyclerViewOnItemTouchListener implements RecyclerView.OnItemTouchListener {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            switch (e.getAction()&MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    return false;
                case MotionEvent.ACTION_POINTER_DOWN:
                    return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
}
