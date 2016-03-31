package com.example.slf.task_learn;

import android.app.Activity;


import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;
import java.util.List;


public class MainActivity extends Activity implements SurfaceHolder.Callback {
    private SurfaceView mSurfaceView;
    private Camera mCamera;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        mSurfaceView = (SurfaceView) findViewById(R.id.mySurfaceView);
       // mCamera=Camera.open();
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(MainActivity.this); // 回调接口
        holder.setFormat(PixelFormat.TRANSPARENT);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        // SurfaceView创建时，建立Camera和SurfaceView的联系
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


        // SurfaceView尺寸发生改变时（首次在屏幕上显示同样会调用此方法），初始化mCamera参数，启动Camera预览

        Camera.Parameters parameters = mCamera.getParameters();// 获取mCamera的参数对象
        Camera.Size largestSize = getBestSupportedSize(parameters
                .getSupportedPreviewSizes());
        parameters.setPreviewSize(largestSize.width, largestSize.height);// 设置预览图片尺寸
        largestSize = getBestSupportedSize(parameters
                .getSupportedPictureSizes());// 设置捕捉图片尺寸
        parameters.setPictureSize(largestSize.width, largestSize.height);
        mCamera.setParameters(parameters);

        try {
            mCamera.startPreview();
        } catch (Exception e) {
            if (mCamera != null) {
                mCamera.release();
                mCamera = null;
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // SurfaceView销毁时，取消Camera预览
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // 开启相机
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            mCamera = Camera.open(0);
            // i=0 表示后置相机
        } else
            mCamera = Camera.open();
    }

    @Override
    public void onPause() {
        super.onPause();
        // 释放相机
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }

    }

    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes) {
        // 取能适用的最大的SIZE
        Camera.Size largestSize = sizes.get(0);
        int largestArea = sizes.get(0).height * sizes.get(0).width;
        for (Camera.Size s : sizes) {
            int area = s.width * s.height;
            if (area > largestArea) {
                largestArea = area;
                largestSize = s;
            }
        }
        return largestSize;
    }
}
