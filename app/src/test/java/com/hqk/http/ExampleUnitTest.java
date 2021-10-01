package com.hqk.http;

import com.hqk.http.http.HttpUrl;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testHttp() throws Exception {
        final HttpUrl httpUrl = new
                HttpUrl("http://restapi.amap.com/v3/weather/weatherInfo?city=长沙&key=13cb58f5884f9749287abbead9c658f2");


    }
}