package com.test.zyl.mylib;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.test.zyl.mylib.aspectj.NeedPermission;

public class MainActivity extends AppCompatActivity {
    private static final String METHOD_LOG="MethodAspectj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(METHOD_LOG, "MainActivity中的onCreate()执行啦：");
        openCamera();
    }

    @NeedPermission(value = Manifest.permission_group.CAMERA,requestCode = 10001)
    public void openCamera(){
        Log.e(METHOD_LOG, "MainActivity中的openCamera()执行啦：");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
