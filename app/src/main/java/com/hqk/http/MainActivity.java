package com.hqk.http;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hqk.http.fenfa.GouwuchePart;
import com.hqk.http.fenfa.LiveEvent;
import com.hqk.http.fenfa.LivePart;
import com.hqk.http.fenfa.RedPackPart;
import com.hqk.http.fenfa.SmallVideoPart;
import com.hqk.http.http.HttpCodec;
import com.hqk.http.http.HttpUrl;
import com.hqk.http.zeren.AliPay;
import com.hqk.http.zeren.PayChain;
import com.hqk.http.zeren.PayRequest;
import com.hqk.http.zeren.WxPay;
import com.hqk.http.zerenlian.Boss;
import com.hqk.http.zerenlian.Director;
import com.hqk.http.zerenlian.GroupLeader;
import com.hqk.http.zerenlian.Manager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String content="";
    //先定义
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private List<LivePart> mParts=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(this);
        setContentView(R.layout.activity_main);
        register();
    }




    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    //然后通过一个函数来申请
    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void testHttp(){
        String path = "http://jz.yxg12.cn/old.php";
        try {
            HttpUrl httpUrl = new HttpUrl(path);
            //请求的报文，拼接请求头
            StringBuffer request = new StringBuffer();
            request.append("GET ");
            request.append(httpUrl.getFile());
            request.append(" HTTP/1.1\r\n");
            request.append("Host: "+httpUrl.getHost());
            request.append("\r\n");
            request.append("\r\n");
            //如果有请求体，就需要拼接请求体
            //封装socket
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(httpUrl.getHost(),httpUrl.getPort()));

            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();

            new Thread(){
                @Override
                public void run() {
                    HttpCodec httpCodec = new HttpCodec();
                    try {
                        //读一行  响应行
                        String responseLine = httpCodec.readLine(is);
                        System.out.println("响应行：" + responseLine);

                        //读响应头
                        Map<String, String> headers = httpCodec.readHeaders(is);
                        for (Map.Entry<String, String> entry : headers.entrySet()) {
                            System.out.println(entry.getKey() + ": " + entry.getValue());
                        }

                        //读响应体 ? 需要区分是以 Content-Length 还是以 Chunked分块编码
                        if (headers.containsKey("Content-Length")) {
                            int length = Integer.valueOf(headers.get("Content-Length"));
                            byte[] bytes = httpCodec.readBytes(is, length);
                            content = new String(bytes);
                            System.out.println("响应:"+content);


                        } else {
                            //分块编码
                            String response = httpCodec.readChunked(is);
                            content = response;
                            System.out.println("响应:"+content);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();


            //发送请求
            os.write(request.toString().getBytes());
            os.flush();
            Thread.sleep(3000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //请求劫持
    public void testNet(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                testHttp();
                //testHttps();
                //testHttps2();
            }
        }).start();

       // testOkhttp();
    }

    public void testNet2(View view) {
        /*OkHttpManager.getInstance().asyncGet(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("netWork",response.body().string());
            }
        },"http://jz.yxg12.cn/old.php");*/

//        bxMoney();
        scanMoney();
    }

    private void scanMoney() {
        PayRequest payRequest = new PayRequest();
        payRequest.setPayCode(3);
        PayChain chain = new PayChain();
        chain.add(new AliPay());
        chain.add(new WxPay());
        chain.barCode(payRequest, chain);
    }

    //直播项目，小窗口流，红包，购物车，单一职责原则
    private void startLive(){
        //显示红包按钮，显示小窗口流，显示购物车……
        for(LivePart part:mParts){
            part.dispatch(new LiveEvent());
        }
    }

    //注册这些小部件,okHttp,事件分发，拦截器，责任链模式
    private void register(){
        mParts.add(new RedPackPart());
        mParts.add(new GouwuchePart());
        mParts.add(new SmallVideoPart());
    }

    //员工要报销  员工-》组长-》主管-》经理-》老板
    //员工报销8000块
    private void bxMoney(){
        GroupLeader groupLeader = new GroupLeader();
        Director director = new Director();
        Manager manager = new Manager();
        Boss boss = new Boss();
        //设置上级领导处理者对象
        groupLeader.nextHandler = director;
        director.nextHandler = manager;
        manager.nextHandler = boss;
        //这种责任链不好，还需要指定下一个处理对象
        //发起报账申请
        groupLeader.handleRequest(8000);
    }

    public void testFenfa(View view) {
        startLive();
    }
}
