package com.example.thirdlaboratory;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GraphView extends View {
    private float x1 = 1;
    private float x2 = 5;
    float y1 = (2 * x1) + 1;
    float y2 = (2 * x2) + 1;
    Paint black = new Paint();
    Paint red = new Paint();

    public GraphView (Context context) {
        super(context);
    }

    public GraphView (Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.rgb(255,255,255));
        black.setStrokeWidth(3);
        black.setColor(Color.rgb(0, 0, 0));
        red.setStrokeWidth(3);
        red.setColor(Color.rgb(255, 0, 0));

        float getWidth = getWidth();
        float getHeight = getHeight();

        // начало координат
        float startX = getWidth / 2;
        float startY = getHeight / 2;

        // белый фон
        canvas.drawColor(Color.rgb(255, 255, 255));

        // ось координат
        canvas.drawLine(startX, 20, startX, getHeight - 20, black);
        canvas.drawLine(20, startY, getWidth - 20, startY, black);

        // стрелки на конце
        canvas.drawLine(startX - 20, 40, startX, 20, black);
        canvas.drawLine(startX + 20, 40, startX, 20, black);

        canvas.drawLine(getWidth - 40, getHeight - startY - 20, getWidth - 20, startY, black);
        canvas.drawLine(getWidth - 40, getHeight - startY + 20, getWidth - 20, startY, black);

        // график, не доделанный
        if(x1 >= 0 && x2 >= 0) {
            canvas.drawLine(startX + x1 * 10, startY - y1 * 20, startX + x2 * 10, startY - y2 * 20, red);
        }
        else if(x1 >= 0 && x2 < 0) {
            canvas.drawLine(startX + x1 * 10, startY - y1 * 20, startX - x2 * -10, startY - y2 * -20, red);
        }
        else if(x1 < 0 && x2 >= 0) {
            canvas.drawLine(startX - x1 * -10, startY - y1 * -20, startX + x2 * 10, startY - y2 * 20, red);
        }
        else if(x1 < 0 && x2 < 0) {
            canvas.drawLine(startX - x1 * -10, startY - y1 * -20, startX - x2 * -10, startY - y2 * -0, red);
        }
    }
    public void getXs(float firstX, float secondX) {
        x1 = firstX;
        x2 = secondX;
        invalidate();
    }
}