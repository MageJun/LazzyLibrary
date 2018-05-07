package com.zed3.sipua.ui.presenter;

public abstract class BasePresenter<T> {

    public abstract void attach(T t);
    public abstract void detach(T t);
}
