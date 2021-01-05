package com.libiao.libiaodemo.okhttp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.libiao.libiaodemo.okhttp.R;

import java.io.IOException;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class TestOkhttpActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okhttp_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder()
//                            .url(appendParam("http://www.163.com?xx=1", "taid", UUID.randomUUID().toString()))
//                            .build();
//                    okhttp3.Response response = client.newCall(request).execute();
//
//                    String responseSource = response.networkResponse() != null
//                            ? ("(network: " + response.networkResponse().code() + " over " + response.protocol() + ")")
//                            : "(cache)";
//
//                    Log.i("libiao", "responseSource = " + responseSource);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    public static String appendParam(String url, String key, String value) {
        if (url.contains(key + "=")) {
            return url;
        }
        String newUrl = "";
        int index = url.indexOf("?");
        String[] array = null;
        String anchor = "";
        if (url.indexOf("#") > 0) {
            array = url.split("#");
        }
        if (array != null) {
            if (array.length > 0) {
                newUrl = array[0];
            }
            if (array.length > 1) {
                anchor = array[1];
            }
        } else {
            newUrl = url;
        }

        if (index > 0) {
            if (url.endsWith("&") || url.endsWith("?")) {
                newUrl += (key + "=" + value);
            } else {
                newUrl += ("&" + key + "=" + value);
            }
        } else {
            newUrl += ("?" + key + "=" + value);
        }
        return newUrl + anchor;
    }

    public void sharesInfo(View v) {
        startActivity(new Intent(this, SharesInfoActivity.class));
    }
}
