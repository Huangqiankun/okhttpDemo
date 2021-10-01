package com.hqk.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
//自定义拦截器，拦截的响应头
public class NetCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originResponse = chain.proceed(request);
        //http 1.0 的版本只有pragma
        //Cache-Control 1.1版本有的
        //设置响应的缓存时间为60秒，即设置Cache-Control头，
        // 并移除pragma消息头，因为pragma也是控制缓存的一个消息头属性
        //关于Pragma:no-cache，跟Cache-Control: no-cache相同。
        // Pragma: no-cache兼容http 1.0 ，Cache-Control: no-cache是http 1.1提供的。
        // 因此，Pragma: no-cache可以应用到http 1.0 和http 1.1，
        // 而Cache-Control: no-cache只能应用于http 1.1.
        //一般用于访问量大的接口并且不会实时改变的接口，列表页，拼多多，60s，
        originResponse = originResponse.newBuilder()
                .removeHeader("pragma")
                .header("Cache-Control", "max-age=60")
                .build();

        return originResponse;
    }
}
