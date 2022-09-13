package com.example.thirdlaboratory;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GraphView extends View {

    public GraphView (Context context) {
        super(context);
    }

    public GraphView (Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.rgb(0, 255, 0));
        Paint p = new Paint();
        p.setColor(Color.rgb(0, 0, 255));
        canvas.drawLine(0, 0, this.getWidth(), this.getHeight(), p);
    }

}
