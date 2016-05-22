package com.example.yaroslav.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;

import java.util.Random;

public class MainActivity extends Activity implements View.OnTouchListener {
    MySurfaceView mv;
    Bitmap ball, man;
    Sprite sprite;
    float x, y ,g;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mv = new MySurfaceView(this);
        mv.setOnTouchListener(this);
        ball = BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
        man = BitmapFactory.decodeResource(getResources(), R.drawable.spritessheet);
        x = y = 0;
        g = 10;
        setContentView(mv);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mv.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mv.resume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent me) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch(me.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = me.getX();
                y = me.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                x = me.getX();
                y = me.getY();
                break;
            case MotionEvent.ACTION_UP:
                x = me.getX();
                y = me.getY();
                break;
        }
        return true;
    }

    public class MySurfaceView extends SurfaceView implements Runnable{

        boolean update = false;
        Thread t = null;
        Paint paint = new Paint();
        SurfaceHolder holder;

        public MySurfaceView(Context context) {
            super(context);
            holder = getHolder();
        }
        public void resume(){
            update = true;
            t = new Thread(this);
            t.start();
        }
        public void pause(){
            update = false;
            while(true){
                try{t.join();} catch (InterruptedException e) {e.printStackTrace();}
                break;
            }
            t = null;
        }
        @Override
        public void run() {
            sprite = new Sprite(this, man);
            while(update){
                if(!holder.getSurface().isValid()) {
                    continue;
                }
                Canvas c = holder.lockCanvas();
                onMyDraw(c);
                holder.unlockCanvasAndPost(c);
            }
        }
        protected void onMyDraw(Canvas canvas){
            canvas.drawARGB(255, 150, 150, 10);
            //canvas.drawBitmap(ball, 400, 400, null);
            sprite.onDraw(canvas, (int)(x - x%5), (int)(y - y%5), x, y);
        }

    }
}
