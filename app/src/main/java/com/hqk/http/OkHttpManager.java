package com.hqk.http;

import android.os.Environment;

import java.io.File;

import okhttp3.Cache;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpManager {
    private OkHttpClient client;

    private OkHttpManager() {
        // 缓存目录
        File file = new File(Environment.getExternalStorageDirectory(), "a_cache");
        // 缓存大小
        int cacheSize = 10 * 1024 * 1024;
        client = new OkHttpClient.Builder()
                .cache(new Cache(file, cacheSize)) // 配置缓存
                .addNetworkInterceptor(new NetCacheInterceptor())
                .build();
    }

    public static OkHttpManager getInstance() {
        return OkHttpHolder.instance;
    }

    private static class OkHttpHolder {
        private static final OkHttpManager instance = new OkHttpManager();
    }

    public void asyncGet(Callback callback,String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }
}