package com.zed3.sipua.xydj.ui.presenter;

public abstract class BasePresenter<T> {

    public abstract void attach(T viewer);
    public abstract void detach(T viewer);
}
