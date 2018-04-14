package com.lw.android.demo.model.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zed on 2018/4/14.
 */

 public class PersonServiceFactory {

    private static PersonServiceFactory sInstance;

    private PersonServiceFactory(){}

    protected static PersonServiceFactory getInstance(){
        if(sInstance == null){
            sInstance = new PersonServiceFactory();
        }

        return sInstance;
    }


    public IPersonService newPersonServiceInstance(){
        PersonServiceImpl impl = new PersonServiceImpl();
        IPersonServiceProxy proxy = new IPersonServiceProxy();
        proxy.bind(impl);
        IPersonService service = (IPersonService) Proxy.newProxyInstance(
                                impl.getClass().getClassLoader(),
                                impl.getClass().getInterfaces(),proxy);

        return service;

    }

    /**
     * Created by zed on 2018/4/14.
     */

    private static class IPersonServiceProxy implements InvocationHandler {


        private Object targetObj;

        protected IPersonServiceProxy(){}

        protected void bind(Object targetObj){
            this.targetObj = targetObj;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(targetObj,args);
        }
    }
}

