package com.restaurant.dinner.request.proxy;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * HTTP请求代理的基类，通过HTTP方式访问的业务的代理类需继承此类
 *
 * @date 2017-04-11
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public abstract class BaseHttpProxy {
    protected Retrofit retrofit;

    protected BaseHttpProxy() {
        this(null, null, null);
    }

    protected BaseHttpProxy(String rootPath) {
        this(rootPath, null, null);
    }
    protected BaseHttpProxy(String rootPath, CallAdapter.Factory callAdapterFactory) {
        this(rootPath, callAdapterFactory, null);
    }
    protected BaseHttpProxy(String rootPath, Converter.Factory converterFactory) {
        this(rootPath, null, converterFactory);
    }
    protected BaseHttpProxy(String rootPath, CallAdapter.Factory callAdapterFactory, Converter.Factory converterFactory) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(rootPath);

        if (callAdapterFactory != null) {
            builder.addCallAdapterFactory(callAdapterFactory);
        }
        if (converterFactory != null) {
            builder.addConverterFactory(converterFactory);
        }

        retrofit = builder.build();
    }

    protected <T> T createApiInstance(Class<T> cls) {
        return this.retrofit.create(cls);
    }

}
