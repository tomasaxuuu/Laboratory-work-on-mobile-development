package com.example.fourthlaboratory;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GraphView extends View {

    Paint black = new Paint();
    Paint red = new Paint();
    Paint yellow = new Paint();
    Paint greenYellow = new Paint();
    Paint blue = new Paint();

    private int x = 1;
    private int y = 1;
    private float arrSize, hMargin, wMargin;
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
            {1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

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
        yellow.setColor(Color.rgb(189, 183, 107));
        blue.setColor(Color.rgb(0, 0, 255));

        float width = getWidth();
        float height = getHeight();

        if (width / height < w / h) {
            arrSize = getWidth() / (w + 1);
        }
        else {
            arrSize = getHeight() / (h + 1);
        }

        hMargin = (width - h * arrSize) / 2;
        wMargin = (height - w * arrSize) / 2;
        canvas.translate(hMargin, wMargin);

        // рисование лабиринта
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                switch (arr[j][i]) {
                    // field
                    case 0:
                        canvas.drawCircle(i * 70, j * 70, 30, yellow);
                        break;
                    // wall
                    case 1:
                        canvas.drawCircle(i * 70, j * 70, 30, black);
                        break;
                    // finish
                    case 2:
                        canvas.drawCircle(i * 70, j * 70, 30, blue);
                        break;
                    // trap
                    case 4:
                        canvas.drawCircle(i * 70, j * 70, 30, greenYellow);
                        break;
                    }
            }

        }
        canvas.drawCircle(x * 70, y * 70, 25, red);
    }

    private void movePlayer(Direction direction) {
        // сообщение о результате
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // победа
        builder.setTitle(R.string.victoryTitle);
        builder.setMessage(R.string.victoryMessage);
        AlertDialog victoryDialog = builder.create();
        // поражение
        builder.setTitle(R.string.lostTitle);
        builder.setMessage(R.string.lostMessage);
        AlertDialog lostDialog = builder.create();

        switch (direction) {
            case UP:
                if (arr[y - 1][x] == 0) {
                    y--;
                }
                else if (arr[y - 1][x] == 4) {
                    lostDialog.show();
                    y = 1;
                    x = 1;
                }
                else if (arr[y - 1][x] == 2) {
                    victoryDialog.show();
                    y--;

                }
                break;
            case DOWN:
                if (arr[y + 1][x] == 0) {
                    y++;
                }
                else if (arr[y + 1][x] == 4) {
                    lostDialog.show();
                    y = 1;
                    x = 1;
                }
                else if (arr[y + 1][x] == 2) {
                    victoryDialog.show();
                    y++;
                }
                break;
            case LEFT:
                if (arr[y][x - 1] == 0) {
                    x--;
                }
                else if (arr[y][x - 1] == 4) {
                    lostDialog.show();
                    y = 1;
                    x = 1;
                }
                else if (arr[y][x - 1] == 2) {
                    victoryDialog.show();
                    x--;
                }
                break;
            case RIGHT:
                if (arr[y][x + 1] == 0) {
                    x++;
                }
                else if (arr[y][x + 1] == 4) {
                    lostDialog.show();
                    y = 1;
                    x = 1;
                }
                else if (arr[y][x + 1] == 2) {
                    victoryDialog.show();
                    x++;
                }
                break;
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {

            float x1 = event.getX();
            float y1 = event.getY();

            float playerCenterX = hMargin + (arr[0][x] + 0.5f) * arrSize;
            float playerCenterY = wMargin + (arr[y][0] + 0.5f) * arrSize;

            float dx = x1 - playerCenterX;
            float dy = y1 - playerCenterY;

            float absDx = Math.abs(dx);
            float absDy = Math.abs(dy);

            if (absDx > arrSize || absDy > arrSize) {

                if (absDx > absDy) {
                    // move in x-direction
                    if (dx > 0)
                        movePlayer(Direction.RIGHT);
                    else
                        movePlayer(Direction.LEFT);
                } else {
                    // move in y-direction
                    if (dy > 0)
                        movePlayer(Direction.DOWN);
                    else
                        movePlayer(Direction.UP);
                }
            }

            return true;
        }
        return super.onTouchEvent(event);
    }
}

