package com.example.slf.task_learn;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MyDraw extends SurfaceView implements SurfaceHolder.Callback{

    private float x,y;
    protected SurfaceHolder sh;
    private Canvas canvas;
    private int mWidth;
    private int mHeight;
    //判断是否是第一次触摸
    private boolean ifFirst=true;
    public MyDraw(Context context, AttributeSet attrs) {
        super(context,attrs);
        // TODO Auto-generated constructor stub
        sh = getHolder();
        sh.addCallback(this);
        sh.setFormat(PixelFormat.TRANSPARENT);
        setZOrderOnTop(true);
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int w, int h) {
        // TODO Auto-generated method stub
        mWidth = w;
        mHeight = h;
    }

    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO Auto-generated method stub

    }

    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub

    }
    void clearDraw()
    {
        canvas = sh.lockCanvas();

        Paint p = new Paint();
        //清屏
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(p);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));

        sh.unlockCanvasAndPost(canvas);

    }

    public void drawLine(float x,float y)
    {
        if (!ifFirst){
            clearDraw();
        }
       ifFirst=false;
        canvas = sh.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT);
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.RED);
        p.setStyle(Style.STROKE);
        canvas.drawLine(mWidth / 2, 0, x, y, p);

        Paint textPaint = new Paint( Paint.ANTI_ALIAS_FLAG);

        textPaint.setTextSize(35);

        textPaint.setColor(Color.WHITE);
        double angle=y/(x-mWidth/2);
        angle= (float) Math.atan(angle)*180/Math.PI;


        canvas.drawText(""+angle,x,y+30,textPaint);
       // canvas.drawCircle(110, 110, 10.0f, p);
        sh.unlockCanvasAndPost(canvas);

    }

    // 触摸事件
    public boolean onTouchEvent(MotionEvent event) {
        y = event.getY();
        x = event.getX();
        drawLine(x,y);
        return true;
    }

}

