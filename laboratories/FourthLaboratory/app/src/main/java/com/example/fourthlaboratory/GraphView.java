package com.example.fourthlaboratory;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GraphView extends View {
    Paint black = new Paint();
    Paint red = new Paint();
    Paint pink = new Paint();
    Paint greenYellow = new Paint();
    Paint blue = new Paint();
    private int x = 1;
    private int y = 1;
    private int h = 10;
    private int w = 10;
    private int[][] arr = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
            {1, 0, 1, 0, 4, 0, 1, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 1},
            {1, 0, 4, 0, 1, 0, 4, 0, 2, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 4, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    public GraphView(Context context) {
        super(context);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        red.setColor(Color.rgb(255, 0, 0));
        black.setColor(Color.rgb(0, 0, 0));
        greenYellow.setColor(Color.rgb(0, 128, 0));
        pink.setColor(Color.rgb(255, 0, 255));
        blue.setColor(Color.rgb(0, 0, 255));
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                switch (arr[j][i]) {
                    case 0:
                        canvas.drawCircle(28 + i * 45, 28 + j * 45, 20, pink);
                    break;
                    case 1:
                        canvas.drawCircle(28 + i * 45, 28 + j * 45, 20, black);
                        break;
                    case 2:
                        canvas.drawCircle(28 + i * 45, 28 + j * 45, 20, blue);
                    break;
                    case 4:
                        canvas.drawCircle(28 + i * 45, 28 + j * 45, 20, greenYellow);
                    break;
                }
            }

        }
        canvas.drawCircle(28 + x * 45, 28 + y * 45, 20, red);
        }
    }

