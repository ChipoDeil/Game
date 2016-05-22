package com.example.yaroslav.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;


/**
 * Created by yaroslav on 20.05.2016.
 */
public class Sprite {
    int x, y;
    int currentFrame = 0;
    int direction = 3;
    int xSpeed, ySpeed;
    int height, width;
    Bitmap m;
    MainActivity.MySurfaceView sv;
    boolean run;
    public Sprite(MainActivity.MySurfaceView mySurfaceView, Bitmap man){
        m = man;
        sv = mySurfaceView;
        height = m.getHeight() / 4;
        width = m.getWidth() / 6;
        x = width/2 - width/2%5;
        y = height/2 - height/2 %5;
        xSpeed = 5;
        ySpeed = 5;
    }

    private void update(int xd, int yd) throws InterruptedException {
        // 0 = up
        // 1 = left
        // 2 = down
        // 3 = right

        if(x < xd){
            x += xSpeed;
            direction = 3;
            run = true;
        }else if(x > xd){
            x -= xSpeed;
            direction = 1;
            run = true;
        }else if(y < yd){
            y += ySpeed;
            direction = 0;
            run = true;
        }else if(y > yd){
            y -= ySpeed;
            direction = 2;
            run = true;
        }else{
            run = false;
        }
        /*if(x > sv.getWidth() - width - xSpeed){
            direction = 2;
        }
        if(y > sv.getHeight() - height - ySpeed){
            direction = 1;
        }
        if(x + xSpeed < 0){
            direction = 0;
        }
        if(y + ySpeed < 0){
            direction = 3;
        }*/
        Thread.sleep(50);
        currentFrame = ++currentFrame % 6;
    }
    public void onDraw(Canvas canvas, int xd, int yd, float xe, float ye) {
        xd = (xd - width/2) - (xd - width/2)%5;
        yd = (yd - height/2) - (yd - height/2)%5;
        try {
            update(xd, yd);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        if(!run){
            currentFrame = 1;
        }else{
            canvas.drawCircle(xe - 3, ye - 3, 6, paint);
        }
        int scrX = currentFrame * width;
        int scrY = height * direction;
        Rect src = new Rect(scrX, scrY, scrX + width, scrY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(m, src, dst, null);
    }

}
