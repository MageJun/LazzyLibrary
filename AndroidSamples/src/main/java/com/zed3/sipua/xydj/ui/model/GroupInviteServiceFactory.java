package com.zed3.sipua.xydj.ui.model;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class GroupInviteServiceFactory {

    private GroupInviteServiceFactory(){}

    private static GroupInviteServiceFactory sInstance;

    protected static GroupInviteServiceFactory getsInstance() {
        if(sInstance==null){
            sInstance  = new GroupInviteServiceFactory();
        }
        return sInstance;
    }

    public IGroupInviteService newGroupInviteServiceInstance(){
            GroupInviteServiceImpl impl  =new GroupInviteServiceImpl();
            GroupInviteServiceProxy proxy = new GroupInviteServiceProxy();
            proxy.bindTag(impl);
            IGroupInviteService service = (IGroupInviteService) Proxy.newProxyInstance(impl.getClass().getClassLoader(),
                    impl.getClass().getInterfaces(),proxy);
            return service;
    }

    private static class GroupInviteServiceProxy implements InvocationHandler{

        private Object mTarget;

        public void bindTag(Object obj){
            this.mTarget  = obj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(mTarget,args);
        }
    }

}
