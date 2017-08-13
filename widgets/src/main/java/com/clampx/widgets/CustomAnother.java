package com.clampx.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ykhan on 08/08/2017.
 */

public class CustomAnother extends View implements View.OnClickListener{

    private static final String TAG = "Custom";
    Paint paintBlue = new Paint();
    Paint paintGreen = new Paint();
    Paint paintRed = new Paint();
    int arcStartAngleBlue = 0;
    int arcSweepAngleBlue = 120;
    int arcStartAngleGreen = 120;
    int arcSweepAngleGreen = 120;
    int arcStartAngleRed = 240;
    int arcSweepAngleRed = 120;
    int angleChangeBlue = 1;
    int angleChangeGreen = 1;
    int angleChangeRed = 1;
    boolean rotate = false;

    int toggleIncrement = 1;
    int outerArcWidth = 75;
    int outerArcHeight= 75;


    public CustomAnother(Context context) {
        super(context,null);
        initPaint(context);
    }
    public CustomAnother(Context context, AttributeSet attrs) {
        super(context,attrs);
        initPaint(context);
    }

    private void initPaint(Context context) {
        paintBlue.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        paintBlue.setStyle(Paint.Style.STROKE);
        paintBlue.setStrokeWidth(5);
        paintGreen.setColor(ContextCompat.getColor(context,R.color.green));
        paintGreen.setStyle(Paint.Style.STROKE);
        paintGreen.setStrokeWidth(5);
        paintRed.setColor(ContextCompat.getColor(context,R.color.red));
        paintRed.setStyle(Paint.Style.STROKE);
        paintRed.setStrokeWidth(5);
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rectBlue = new RectF(10, 10, outerArcWidth, outerArcHeight);
        RectF rectRed = new RectF(10, 10, outerArcWidth, outerArcHeight);
        RectF rectGreen = new RectF(10, 10, outerArcWidth, outerArcHeight);
        if (rotate) {
            arcStartAngleBlue = (arcStartAngleBlue + angleChangeBlue) %360;
            arcStartAngleGreen = (arcStartAngleGreen + angleChangeGreen) %360;
            arcStartAngleRed = (arcStartAngleRed + angleChangeRed) %360;
            int angleChange = 1;
            if (toggleIncrement ==1){
                arcSweepAngleBlue += angleChange;
                //arcStartAngleBlue-= angleChange;
                arcSweepAngleGreen -= angleChange;
                arcStartAngleGreen += angleChange;
            }
            else if (toggleIncrement ==2){
                arcSweepAngleGreen += angleChange;
                //arcStartAngleGreen -= angleChange;
                arcSweepAngleRed -= angleChange;
                arcStartAngleRed += angleChange;
            }
            else if (toggleIncrement ==3){
                arcSweepAngleRed += angleChange;
                //arcStartAngleRed -= angleChange;
                arcSweepAngleBlue -= angleChange;
                arcStartAngleBlue += angleChange;
            }
            if(toggleIncrement ==1 && (arcSweepAngleBlue >=235 || arcSweepAngleBlue < -235)){
                toggleIncrement = 2;
            }
            else if(toggleIncrement ==2 && (arcSweepAngleGreen >=235 || arcSweepAngleGreen <= -235)){
                toggleIncrement = 3;
            }
            else if(toggleIncrement ==3 && (arcSweepAngleRed >=235 || arcSweepAngleRed <=-235)){
                toggleIncrement = 1;
            }

        }
        canvas.drawArc(rectBlue, arcStartAngleBlue, arcSweepAngleBlue, false, paintBlue);
        canvas.drawArc(rectGreen, arcStartAngleGreen, arcSweepAngleGreen, false, paintGreen);
        canvas.drawArc(rectRed, arcStartAngleRed, arcSweepAngleRed, false, paintRed);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        outerArcWidth = w-5;
        outerArcHeight= outerArcWidth;
        Log.i(TAG, "onSizeChanged: width: "+w);
        Log.i(TAG, "onSizeChanged: height: "+h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: width: "+widthMeasureSpec);
        Log.i(TAG, "onMeasure: height: "+heightMeasureSpec);
    }

    @Override
    public void onClick(View view) {
        rotate = !rotate;
    }
}
