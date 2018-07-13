package com.android.kotlindemo.presenter;

import com.android.kotlindemo.view.IViewer;

public abstract class TestBasePresenter {

    protected IViewer mViewer;

    public void bindView(IViewer viewer){
        this.mViewer = viewer;
    }
}
