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

public final class ThreeOrderBezierView extends View {
    private static final float DEFAULT_POINT_RADIUS = 4F;
    private static final int DEFAULT_POINT_COLOR = Color.BLUE;
    private static final int DEFAULT_LINE_COLOR = Color.BLACK;
    private static final int DEFAULT_BEZIER_COLOR = Color.RED;

    private Paint pointPaint, linePaint, bezierPaint;
    private float pointRadius;
    private int pointColor, lineColor, bezierColor;

    private int pressCount = 0;
    private Path path;
    private float startPointX, startPointY;
    private float endPointX, endPointY;
    private float ctrlPoint1X, ctrlPoint1Y, ctrlPoint2X, ctrlPoint2Y;

    public ThreeOrderBezierView(Context context) {
        super(context);
        init();
    }

    public ThreeOrderBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
        init();
    }

    public ThreeOrderBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
        init();
    }

    public ThreeOrderBezierView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        path = new Path();
        pointPaint = new Paint();
        linePaint = new Paint();
        bezierPaint = new Paint();
        pointPaint.setStyle(Paint.Style.FILL);
        linePaint.setStyle(Paint.Style.STROKE);
        bezierPaint.setStyle(Paint.Style.STROKE);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ThreeOrderBezierView, defStyleAttr, defStyleRes);
        pointRadius = array.getDimension(R.styleable.ThreeOrderBezierView_cubic_point_radius, DEFAULT_POINT_RADIUS);
        pointColor = array.getColor(R.styleable.ThreeOrderBezierView_cubic_point_color, DEFAULT_POINT_COLOR);
        lineColor = array.getColor(R.styleable.ThreeOrderBezierView_cubic_line_color, DEFAULT_LINE_COLOR);
        bezierColor = array.getColor(R.styleable.ThreeOrderBezierView_cubic_bezier_color, DEFAULT_BEZIER_COLOR);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pointPaint.setColor(pointColor);
        linePaint.setColor(lineColor);
        bezierPaint.setColor(bezierColor);

        canvas.drawCircle(startPointX, startPointY, pointRadius, pointPaint);
        canvas.drawCircle(ctrlPoint1X, ctrlPoint1Y, pointRadius, pointPaint);
        canvas.drawCircle(ctrlPoint2X, ctrlPoint2Y, pointRadius, pointPaint);
        canvas.drawCircle(endPointX, endPointY, pointRadius, pointPaint);

        if (pressCount == 4) {
            canvas.drawLine(startPointX, startPointY, ctrlPoint1X, ctrlPoint1Y, linePaint);
            canvas.drawLine(ctrlPoint1X, ctrlPoint1Y, ctrlPoint2X, ctrlPoint2Y, linePaint);
            canvas.drawLine(ctrlPoint2X, ctrlPoint2Y, endPointX, endPointY, linePaint);

            path.reset();
            path.moveTo(startPointX, startPointY);
            path.cubicTo(ctrlPoint1X, ctrlPoint1Y, ctrlPoint2X, ctrlPoint2Y, endPointX,endPointY);
            canvas.drawPath(path, bezierPaint);
        }
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
                    ctrlPoint1X = event.getX();
                    ctrlPoint1Y = event.getY();
                    invalidate();
                } else if (pressCount == 2) {
                    pressCount++;
                    ctrlPoint2X = event.getX();
                    ctrlPoint2Y = event.getY();
                    invalidate();
                } else if (pressCount == 3) {
                    pressCount++;
                    endPointX = event.getX();
                    endPointY = event.getY();
                    invalidate();
                } else if (pressCount == 4) {
                    pressCount = 0;
                }
                break;
        }
        return true;
    }
}
