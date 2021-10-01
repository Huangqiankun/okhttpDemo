package com.hqk.http;

import com.hqk.http.http.HttpCodec;
import com.hqk.http.http.HttpUrl;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HttpUnitTest {


    @Test
    public void testHttp() throws Exception {
        String path = "http://restapi.amap.com/v3/weather/weatherInfo?city=长沙&key=13cb58f5884f9749287abbead9c658f2";

        HttpUrl httpUrl = new HttpUrl(path);
        //拼接请求头报文
        StringBuilder request = new StringBuilder();
        request.append("GET ");
        request.append(httpUrl.getFile());
        //http有哪些协议  1992年0.9版本的协议//只有GET，没有POST head  95年后http 1.0版本之后，可以支持图片，长链接，
        // 2002年 1.1版本http协议诞生了，
        // https诞生之后，以前是裸奔，穿了衣服奔跑，2012年谷歌推出必须使用https之后，https慢慢的成为主流
        //到现在，http依然能用
        request.append(" HTTP/1.1\r\n");
        request.append("Host: "+httpUrl.getHost());
        request.append("\r\n");
        request.append("\r\n");
        //没有请求体，就不封装
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(httpUrl.getHost(),httpUrl.getPort()));

        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

        new Thread(){
            @Override
            public void run() {
                super.run();
                HttpCodec httpCodec = new HttpCodec();

                try {
                    String content = httpCodec.readLine(is);
                    System.out.println(content);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

        os.write(request.toString().getBytes());
        os.flush();

        Thread.sleep(3000);

    }
}