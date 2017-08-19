package com.clampx.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ykhan on 08/08/2017.
 */

public class SlowFastProgress extends View implements View.OnClickListener {

    private static final String TAG = "Custom";
    Paint paintGray = new Paint();
    Paint paintGreen = new Paint();
    Paint paintRed = new Paint();
    int arcStartAngleGray = 0;
    int arcSweepAngleGray = 360;
    int arcStartAngleGreen = 0;
    int arcSweepAngleGreen = 90;
    int arcStartAngleRed = 180;
    int arcSweepAngleRed = 90;
    int angleChangeGreen = 5;
    int angleChangeRed = 5;
    boolean rotate = false;

    int toggleIncrement = 1;
    int outerArcWidth = 75;
    int outerArcHeight = 75;
    boolean goingDown = true;
    int breakVal = 3;


    public SlowFastProgress(Context context) {
        super(context, null);
        initPaint(context);
    }

    public SlowFastProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(context);
    }

    private void initPaint(Context context) {
        paintGray.setColor(Color.TRANSPARENT);
        paintGray.setStyle(Paint.Style.STROKE);
        paintGray.setStrokeWidth(10);
        paintGreen.setColor(ContextCompat.getColor(context, R.color.green));
        paintGreen.setStyle(Paint.Style.STROKE);
        paintGreen.setStrokeWidth(10);
        paintRed.setColor(ContextCompat.getColor(context, R.color.red));
        paintRed.setStyle(Paint.Style.STROKE);
        paintRed.setStrokeWidth(10);
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rectGray = new RectF(10, 10, outerArcWidth, outerArcHeight);
        RectF rectRed = new RectF(10, 10, outerArcWidth, outerArcHeight);
        RectF rectGreen = new RectF(10, 10, outerArcWidth, outerArcHeight);
        if (rotate) {
            arcStartAngleGreen = (arcStartAngleGreen + angleChangeGreen) % 360;
            arcStartAngleRed = (arcStartAngleRed + angleChangeRed) % 360;
            int angleIncrement = 1;
            if (arcSweepAngleGreen >= 120) {
                goingDown = true;
            } else if (arcSweepAngleGreen <= 15) {
                goingDown = false;
            }
            double val = ((double) (arcSweepAngleGreen / 10));
            if (breakVal%2==0) {
            breakVal = 0;
            if (goingDown) {
                arcSweepAngleGreen -= angleIncrement;
                arcSweepAngleRed -= angleIncrement;
            } else {
                arcSweepAngleGreen += angleIncrement;
                arcSweepAngleRed += angleIncrement;
            }
            angleChangeGreen = 14 - (int) val;
            if (angleChangeGreen < 4) {
                angleChangeGreen = 4;
            }
            else if(angleChangeGreen > 10){
                angleChangeGreen = 10;
            }

            angleChangeRed = angleChangeGreen;
        }
        breakVal++;
        }
        canvas.drawArc(rectGray, arcStartAngleGray, arcSweepAngleGray, false, paintGray);
        canvas.drawArc(rectGreen, arcStartAngleGreen, arcSweepAngleGreen, false, paintGreen);
        canvas.drawArc(rectRed, arcStartAngleRed, arcSweepAngleRed, false, paintRed);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        outerArcWidth = w - 5;
        outerArcHeight = outerArcWidth;
        Log.i(TAG, "onSizeChanged: width: " + w);
        Log.i(TAG, "onSizeChanged: height: " + h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: width: " + widthMeasureSpec);
        Log.i(TAG, "onMeasure: height: " + heightMeasureSpec);
    }

    @Override
    public void onClick(View view) {
        rotate = !rotate;
    }
}
