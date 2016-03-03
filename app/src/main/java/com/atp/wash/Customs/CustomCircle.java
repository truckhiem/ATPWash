package com.atp.wash.Customs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.atp.wash.R;

public class CustomCircle extends View {

    private static final int START_ANGLE_POINT = -90;
    private float strokeWidth;
    private float recLeft, recTop, diameter;
    private final Paint paint;
    private RectF rect;

    private float angle;
	private float parentWidth = 0;
	private float parentHeight = 0;
	

    public CustomCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        strokeWidth = convertDPToPixel(20);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        //Circle color
        paint.setColor(getResources().getColor(R.color.light_blue));

        //Initial Angle (optional, it can be zero)
        angle = 0;
        parentWidth = convertDPToPixel(300);
        parentHeight = convertDPToPixel(300);
        diameter = convertDPToPixel(150);

        recLeft = convertDPToPixel(74);
        recTop = convertDPToPixel(109);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rect = new RectF(recLeft, recTop, recLeft + diameter, recTop + diameter);

        canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
    
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
////        if(parentWidth == 0 && parentWidth == 0){
//	    	parentWidth = MeasureSpec.getSize(widthMeasureSpec);
//	        parentHeight = MeasureSpec.getSize(heightMeasureSpec);
////        }
//        this.setMeasuredDimension(parentWidth, parentHeight);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    private void initDiameter(){
        parentWidth = this.getWidth() - 100;
        parentHeight = this.getHeight() - 100;
    }

    private float convertDPToPixel(int dpValue){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
        return px;
    }
}