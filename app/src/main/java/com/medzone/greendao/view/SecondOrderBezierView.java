package com.medzone.greendao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.medzone.greendao.R;

/**
 * Created by Administrator on 2016/11/28.
 */

public final class SecondOrderBezierView extends View {
    private static final float DEFAULT_POINT_RADIUS = 2F;
    private static final int DEFAULT_POINT_COLOR = Color.BLUE;
    private static final int DEFAULT_LINE_COLOR = Color.BLACK;
    private static final int DEFAULT_BEZIER_COLOR = Color.RED;

    private Paint pointPaint, linePaint, bezierPaint;
    private float pointRadius;
    private int pointColor, lineColor, bezierColor;

    private float startPointX, startPointY;
    private float endPointX, endPointY;
    private float controllerPointX, controllerPointY;

    private int pressCount = 0;
    private Path path;

    public SecondOrderBezierView(Context context) {
        super(context);
        pointPaint = new Paint();
        linePaint = new Paint();
        bezierPaint = new Paint();

        pointPaint.setStyle(Paint.Style.FILL);
        linePaint.setStyle(Paint.Style.STROKE);
        bezierPaint.setStyle(Paint.Style.STROKE);

        path = new Path();
    }

    public SecondOrderBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
        pointPaint = new Paint();
        linePaint = new Paint();
        bezierPaint = new Paint();

        pointPaint.setStyle(Paint.Style.FILL);
        linePaint.setStyle(Paint.Style.STROKE);
        bezierPaint.setStyle(Paint.Style.STROKE);

        path = new Path();
    }

    public SecondOrderBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
        pointPaint = new Paint();
        linePaint = new Paint();
        bezierPaint = new Paint();

        pointPaint.setStyle(Paint.Style.FILL);
        linePaint.setStyle(Paint.Style.STROKE);
        bezierPaint.setStyle(Paint.Style.STROKE);

        path = new Path();
    }

    public SecondOrderBezierView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
        pointPaint = new Paint();
        linePaint = new Paint();
        bezierPaint = new Paint();

        pointPaint.setStyle(Paint.Style.FILL);
        linePaint.setStyle(Paint.Style.STROKE);
        bezierPaint.setStyle(Paint.Style.STROKE);

        path = new Path();
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SecondOrderBezierView, defStyleAttr, defStyleRes);
        pointRadius = array.getDimension(R.styleable.SecondOrderBezierView_point_radius, DEFAULT_POINT_RADIUS);
        pointColor = array.getColor(R.styleable.SecondOrderBezierView_point_color, DEFAULT_POINT_COLOR);
        lineColor = array.getColor(R.styleable.SecondOrderBezierView_line_color, DEFAULT_LINE_COLOR);
        bezierColor = array.getColor(R.styleable.SecondOrderBezierView_bezier_color, DEFAULT_BEZIER_COLOR);
        array.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                if (pressCount == 0) {
                    pressCount++;
                    startPointX = event.getX();
                    startPointY = event.getY();
                    invalidate();
                } else if (pressCount == 1) {
                    pressCount++;
                    endPointX = event.getX();
                    endPointY =event.getY();
                    invalidate();
                } else if (pressCount == 2) {
                    pressCount = 0;
                    controllerPointX = event.getX();
                    controllerPointY = event.getY();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                controllerPointX = event.getX();
                controllerPointY = event.getY();
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();

        pointPaint.setColor(pointColor);
        canvas.drawCircle(startPointX, startPointY, pointRadius, pointPaint);
        canvas.drawCircle(endPointX, endPointY, pointRadius, pointPaint);
        canvas.drawCircle(controllerPointX, controllerPointY, pointRadius, pointPaint);

        linePaint.setColor(lineColor);
        canvas.drawLine(startPointX, startPointY, controllerPointX, controllerPointY, linePaint);
        canvas.drawLine(controllerPointX, controllerPointY, endPointX, endPointY, linePaint);

        bezierPaint.setColor(bezierColor);
        path.moveTo(startPointX, startPointY);
        path.quadTo(controllerPointX, controllerPointY, endPointX, endPointY);
        canvas.drawPath(path, bezierPaint);
    }
}
