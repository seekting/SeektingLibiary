
package com.seekting.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

public class PaoPaoDrawable extends Drawable {

    int textPadding = 7;

    int textSize;

    public int num = 1;

    Drawable src;

    Paint paint = new Paint();

    Paint textPaint = new Paint();

    int textColor;

    int textBackground;

    int maxNum = -1;

    int srcWidth;

    int srcHeight;

    public PaoPaoDrawable(Drawable src, int textSize, int textColor, int textBackground, int maxNum) {
        this.textSize = textSize;
        this.src = src;
        this.textColor = textColor;
        this.textBackground = textBackground;
        this.maxNum = maxNum;

        init();
    }

    private void init() {

        int initWidth = src.getIntrinsicWidth();
        int initHeight = src.getIntrinsicHeight();
        Rect rect = new Rect(0, 0, initWidth, initHeight);
        setBounds(rect);
        paint.setAntiAlias(true);
        textPaint.setAntiAlias(true);
        // textPaint.setTextAlign(Align.CENTER);
        srcWidth = initWidth;
        srcHeight = initHeight;
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

    }

    public int getIntrinsicWidth() {

        srcWidth = src.getIntrinsicWidth();
        return srcWidth;
    }

    public int getIntrinsicHeight() {

        srcHeight = src.getIntrinsicHeight();
        return srcHeight;
    }

    @Override
    public boolean setState(int[] stateSet) {

        if (src instanceof StateListDrawable) {

            boolean state = src.setState(stateSet);

            if (state) {
                onStateChange(stateSet);
            }
            return state;
        }
        return true;
    }

    @Override
    public boolean isStateful() {
        boolean isStateful = src.isStateful();
        return isStateful;
    }

    @Override
    public ConstantState getConstantState() {
        return src.getConstantState();
    }

    @Override
    public void draw(Canvas canvas) {
        if (src instanceof StateListDrawable) {
            System.out.println("xx");
        }
        Rect rect = new Rect(0, 0, srcWidth, srcHeight);
        src.setBounds(rect);

        Drawable drawable = src.getCurrent();
        System.out.println("drawable" + drawable);

        src.draw(canvas);

        if (num > 0) {
            int numStr = num;
            if (num >= maxNum) {
                numStr = maxNum;
            }
            String text = String.valueOf(numStr);
            float length = textPaint.measureText(text);
            float beginX = srcWidth - length - textPadding * 2;
            float endX = srcWidth;
            float beginY = 0;
            FontMetrics fm = textPaint.getFontMetrics();
            float endY = fm.descent - fm.ascent;
            float d = endY;

            paint.setStyle(Style.FILL);
            paint.setColor(textBackground);
            float o_x = (beginX + endX) / 2;
            float o_y = (beginY + endY) / 2;
            // canvas.drawCircle(o_x, o_y, (endY - beginY) / 2, paint);
            RectF oval = new RectF();
            oval.top = beginY;
            oval.bottom = endY;
            oval.left = beginX;
            oval.right = endX;
            canvas.drawArc(oval, 0, 360, true, paint);

            float baseLine = endY - fm.descent;

            canvas.drawText(String.valueOf(numStr), o_x - length / 2, baseLine, textPaint);
            //
            // FontMetrics fm = textPaint.getFontMetrics();
            //
            // textPaint.setColor(Color.BLACK);
            // canvas.save();
            // canvas.translate(0, 30);
            // canvas.drawText("xxYzÔÂ", 0, 0, textPaint);
            // canvas.drawLine(0, fm.ascent, bitmap.getWidth(), fm.ascent,
            // textPaint);
            // canvas.drawLine(0, fm.descent, bitmap.getWidth(), fm.descent,
            // textPaint);
            //
            // canvas.restore();
        }
    }

    public float getFontHeight() {
        FontMetrics fm = textPaint.getFontMetrics();
        return fm.descent - fm.ascent;
        // return (int)Math.ceil(fm.descent - fm.top) + 2;
        // return (int)Math.ceil(fm.top - fm.bottom);
    }

    @Override
    public void setAlpha(int alpha) {

        src.setAlpha(alpha);

    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        src.setColorFilter(cf);

    }

    @Override
    public int getOpacity() {
        return src.getOpacity();
    }

    @Override
    protected boolean onStateChange(int[] state) {

        if (src instanceof StateListDrawable) {
            this.invalidateSelf();
            return true;
        }
        return false;
    }
}
