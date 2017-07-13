package com.vinctor.nested.utils;

import android.widget.Toast;

import com.vinctor.nested.MyApplication;


public class ToastUtil {
    public static void show(String msg) {
        Toast.makeText(MyApplication.getIntance(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg) {
        Toast.makeText(MyApplication.getIntance(), msg, Toast.LENGTH_LONG).show();
    }
}
