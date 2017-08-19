package com.clampx.widgets;

import android.content.Context;
import android.content.res.TypedArray;
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

public class NestedProgress extends View implements View.OnClickListener{

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
    int angleChangeBlue = -7;
    int angleChangeGreen = -7;
    int angleChangeRed = 7;
    boolean rotate = true;

    int outerArcWidth = 75;
    int outerArcHeight= 75;

    int colorCircle1 = R.color.colorPrimary;
    int colorCircle2 = R.color.red;
    int colorCircle3 = R.color.green;



    public NestedProgress(Context context) {
        super(context,null);
        initPaint(context);
    }
    public NestedProgress(Context context, AttributeSet attrs) {
        super(context,attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Progress,
                0, 0);

        try {
            colorCircle1 = a.getColor(R.styleable.Progress_colorArcOuter, ContextCompat.getColor(context, colorCircle1));
            colorCircle2 = a.getColor(R.styleable.Progress_colorArcMiddle, ContextCompat.getColor(context, colorCircle2));
            colorCircle3 = a.getColor(R.styleable.Progress_colorArcInner, ContextCompat.getColor(context, colorCircle3));
        } finally {
            a.recycle();
        }
        initPaint(context);
    }

    private void initPaint(Context context) {
        if(colorCircle1 == 0){
            colorCircle1 = ContextCompat.getColor(context, R.color.colorPrimary);
            colorCircle2 = ContextCompat.getColor(context, R.color.red);
            colorCircle3 = ContextCompat.getColor(context, R.color.green);

        }

        paintBlue.setColor(colorCircle1);
        paintBlue.setStyle(Paint.Style.STROKE);
        paintBlue.setStrokeWidth(5);
        paintGreen.setColor(colorCircle3);
        paintGreen.setStyle(Paint.Style.STROKE);
        paintGreen.setStrokeWidth(5);
        paintRed.setColor(colorCircle2);
        paintRed.setStyle(Paint.Style.STROKE);
        paintRed.setStrokeWidth(5);
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rectBlue = new RectF(10, 10, outerArcWidth, outerArcHeight);
        RectF rectRed = new RectF(20, 20, outerArcWidth -10, outerArcHeight - 10);
        RectF rectGreen = new RectF(30, 30, outerArcWidth -20 , outerArcHeight - 20);
        if (rotate) {
            arcStartAngleBlue = (arcStartAngleBlue + angleChangeBlue) %360;
            arcStartAngleGreen = (arcStartAngleGreen + angleChangeGreen) %360;
            arcStartAngleRed = (arcStartAngleRed + angleChangeRed) %360;
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
