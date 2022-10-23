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

public class GameView extends View {

    Paint wallBlack = new Paint();
    Paint playerRed = new Paint();
    Paint yellowField = new Paint();
    Paint trapGreen = new Paint();
    Paint blueFinish = new Paint();
    Paint teleportPurple = new Paint();

    private int x = 1;
    private int y = 1;
    private float arrSize, hMargin, wMargin;
    private int h = 10;
    private int w = 10;
    private int[][] arrLvl1 = {
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
    private int[][] arrLvl2 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 2, 1},
            {1, 0, 0, 4, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 4, 1, 1, 4, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 2, 1, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 3, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void newLvl() {
        arrLvl1 = arrLvl2;
        x = 1;
        y = 8;
    }

    private void lostLvl() {
        arrLvl2 = arrLvl1;
        x = 1;
        y = 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        playerRed.setColor(Color.rgb(255, 0, 0));
        wallBlack.setColor(Color.rgb(0, 0, 0));
        trapGreen.setColor(Color.rgb(0, 128, 0));
        yellowField.setColor(Color.rgb(189, 183, 107));
        blueFinish.setColor(Color.rgb(0, 0, 255));
        teleportPurple.setColor(Color.rgb(146,110,174));

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
                switch (arrLvl1[j][i]) {
                    // field
                    case 0:
                        canvas.drawCircle(i * 70, j * 70, 30, yellowField);
                        break;
                    // wall
                    case 1:
                        canvas.drawCircle(i * 70, j * 70, 30, wallBlack);
                        break;
                    // finish
                    case 2:
                        canvas.drawCircle(i * 70, j * 70, 30, blueFinish);
                        break;
                    // teleport
                    case 3:
                        canvas.drawCircle(i * 70, j * 70, 30, teleportPurple);
                        break;
                    // trap
                    case 4:
                        canvas.drawCircle(i * 70, j * 70, 30, trapGreen);
                        break;
                    }
            }

        }
        canvas.drawCircle(x * 70, y * 70, 25, playerRed);
    }

    private void movePlayer(Direction direction) {

        // message about result
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // victory
        builder.setTitle(R.string.victoryTitle);
        builder.setMessage(R.string.victoryMessage);
        AlertDialog victoryDialog = builder.create();
        // lost
        builder.setTitle(R.string.lostTitle);
        builder.setMessage(R.string.lostMessage);
        AlertDialog lostDialog = builder.create();
        // teleport
        builder.setTitle(R.string.teleportTitle);
        builder.setMessage(R.string.teleportMessage);
        AlertDialog teleportDialog = builder.create();

        switch (direction) {
            case UP:
                if (arrLvl1[y - 1][x] == 0) {
                    y--;
                }
                else if (arrLvl1[y - 1][x] == 4) {
                    lostDialog.show();
                    lostLvl();
                }
                else if (arrLvl1[y - 1][x] == 2) {
                    victoryDialog.show();
                    y--;
                    newLvl();
                }
                else if (arrLvl1[y - 1][x] == 3) {
                    teleportDialog.show();
                    y = 1;
                    x = 1;
                }
                break;

            case DOWN:
                if (arrLvl1[y + 1][x] == 0) {
                    y++;
                }
                else if (arrLvl1[y + 1][x] == 4) {
                    lostDialog.show();
                    lostLvl();
                }
                else if (arrLvl1[y + 1][x] == 2) {
                    victoryDialog.show();
                    y++;
                    newLvl();
                }
                else if (arrLvl1[y + 1][x] == 3) {
                    teleportDialog.show();
                    y = 1;
                    x = 1;
                }
                break;
            case LEFT:
                if (arrLvl1[y][x - 1] == 0) {
                    x--;
                }
                else if (arrLvl1[y][x - 1] == 4) {
                    lostDialog.show();
                    lostLvl();
                }
                else if (arrLvl1[y][x - 1] == 2) {
                    victoryDialog.show();
                    x--;
                    newLvl();
                }
                else if (arrLvl1[y][x - 1] == 3) {
                    teleportDialog.show();
                    y = 1;
                    x = 1;
                }
                break;
            case RIGHT:
                if (arrLvl1[y][x + 1] == 0) {
                    x++;
                }
                else if (arrLvl1[y][x + 1] == 4) {
                    lostDialog.show();
                    lostLvl();
                }
                else if (arrLvl1[y][x + 1] == 2) {
                    victoryDialog.show();
                    x++;
                    newLvl();
                }
                else if (arrLvl1[y][x + 1] == 3) {
                    teleportDialog.show();
                    y = 1;
                    x = 1;
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
            float playerCenterX = hMargin + (arrLvl1[0][x] + 0.5f) * arrSize;
            float playerCenterY = wMargin + (arrLvl1[y][0] + 0.5f) * arrSize;
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

