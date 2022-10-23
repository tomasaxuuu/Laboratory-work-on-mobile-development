package com.example.fourthlaboratory;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameView extends View {

    Paint wallBlack = new Paint();
    Paint playerRed = new Paint();
    Paint yellowField = new Paint();
    Paint trapGreen = new Paint();
    Paint blueFinish = new Paint();
    Paint teleportPurple = new Paint();
    Paint result = new Paint();
    private int x = 1;
    private int y = 1;
    private float arrSize, hMargin, wMargin;
    private final float h = 10;
    private final float w = 10;

    private int[][] arrLvl1 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 0, 0, 1, 4, 2, 1},
            {1, 0, 1, 0, 4, 0, 1, 4, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 4, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 4, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 4, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 4, 0, 1},
            {1, 0, 4, 0, 1, 0, 4, 4, 0, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    private int[][] arrLvl2 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 5, 1},
            {1, 0, 0, 4, 1, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 4, 1, 1, 4, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 4, 1, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 3, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    private int[][] arrLvl3 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 6, 1},
            {1, 1, 0, 4, 4, 4, 4, 4, 0, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 0, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    private int numberLvl = 1;
    private int countResult = 0;
    private String name = "";
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
        switch (numberLvl) {
            case 1:
                x = 1;
                y = 1;
                break;
            case 2:
                arrLvl1 = arrLvl2;
                x = 1;
                y = 8;
                break;
            case 3:
                arrLvl1 = arrLvl3;
                x = 2;
                y = 8;
                break;
        }
    }
    private void lostLvl() {
        switch (numberLvl) {
            case 1:
            case 2:
                numberLvl = 1;
                newLvl();
                break;
            case 3:
                numberLvl = 2;
                newLvl();
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        result.setColor(Color.rgb(250, 235, 215));
        playerRed.setColor(Color.rgb(255, 0, 0));
        wallBlack.setColor(Color.rgb(0, 0, 0));
        trapGreen.setColor(Color.rgb(0, 128, 0));
        yellowField.setColor(Color.rgb(189, 183, 107));
        blueFinish.setColor(Color.rgb(0, 0, 255));
        teleportPurple.setColor(Color.rgb(146,110,174));
        result.setStyle(Paint.Style.FILL);
        canvas.drawPaint(result);
        result.setColor(Color.BLACK);
        result.setTextSize(50);
        canvas.drawText("Points of " + name + ": " + countResult,
                getWidth() / 2 - 278, 50, result);

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
                        canvas.drawCircle(i * 50, j * 50, 25, yellowField);
                        break;
                    // wall
                    case 1:
                        canvas.drawCircle(i * 50, j * 50, 25, wallBlack);
                        break;
                    // finish
                    case 2:
                    case 5:
                    case 6:
                        canvas.drawCircle(i * 50, j * 50, 25, blueFinish);
                        break;
                    // teleport
                    case 3:
                        canvas.drawCircle(i * 50, j * 50, 25, teleportPurple);
                        break;
                    // trap
                    case 4:
                        canvas.drawCircle(i * 50, j * 50, 25, trapGreen);
                        break;
                    }
            }

        }
        canvas.drawCircle(x * 50, y * 50, 20, playerRed);

    }

    private void movePlayer(Direction direction) {

        // message about result
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // victory
        builder.setTitle(R.string.victoryTitle);
        builder.setMessage(R.string.victoryMessage1);
        AlertDialog victoryDialog1 = builder.create();

        builder.setTitle(R.string.victoryTitle);
        builder.setMessage(R.string.victoryMessage2);
        AlertDialog victoryDialog2 = builder.create();

        builder.setTitle(R.string.victoryTitle);
        builder.setMessage(R.string.victoryMessage3);
        AlertDialog victoryDialog3 = builder.create();

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
                    countResult -= 2;
                }
                else if (arrLvl1[y - 1][x] == 2) {
                    numberLvl = 2;
                    victoryDialog1.show();
                    y--;
                    newLvl();
                    countResult += 2;
                }
                else if (arrLvl1[y - 1][x] == 5) {
                    numberLvl = 3;
                    victoryDialog2.show();
                    y--;
                    newLvl();
                    countResult += 4;
                }
                else if (arrLvl1[y - 1][x] == 6) {
                    numberLvl = 1;
                    victoryDialog3.show();
                    y--;
                    countResult += 8;
                }
                else if (arrLvl1[y - 1][x] == 3) {
                    teleportDialog.show();
                    y = 1;
                    x = 1;
                    countResult += 1;
                }
                break;

            case DOWN:
                if (arrLvl1[y + 1][x] == 0) {
                    y++;
                }
                else if (arrLvl1[y + 1][x] == 4) {
                    lostDialog.show();
                    lostLvl();
                    countResult -= 2;
                }
                else if (arrLvl1[y + 1][x] == 2) {
                    numberLvl = 2;
                    victoryDialog1.show();
                    y++;
                    newLvl();
                    countResult += 2;
                }
                else if (arrLvl1[y + 1][x] == 5) {
                    numberLvl = 3;
                    victoryDialog2.show();
                    y++;
                    newLvl();
                    countResult += 4;
                }
                else if (arrLvl1[y + 1][x] == 6) {
                    numberLvl = 1;
                    victoryDialog3.show();
                    y++;
                    countResult += 8;
                }
                else if (arrLvl1[y + 1][x] == 3) {
                    teleportDialog.show();
                    y = 1;
                    x = 1;
                    countResult += 1;
                }
                break;
            case LEFT:
                if (arrLvl1[y][x - 1] == 0) {
                    x--;
                }
                else if (arrLvl1[y][x - 1] == 4) {
                    lostDialog.show();
                    lostLvl();
                    countResult -= 2;
                }
                else if (arrLvl1[y][x - 1] == 2) {
                    numberLvl = 2;
                    victoryDialog1.show();
                    x--;
                    newLvl();
                    countResult += 2;
                }
                else if (arrLvl1[y][x - 1] == 5) {
                    numberLvl = 3;
                    victoryDialog2.show();
                    x--;
                    newLvl();
                    countResult += 4;
                }
                else if (arrLvl1[y][x - 1] == 6) {
                    numberLvl = 1;
                    victoryDialog3.show();
                    x--;
                    countResult += 8;
                }
                else if (arrLvl1[y][x - 1] == 3) {
                    teleportDialog.show();
                    y = 1;
                    x = 1;
                    countResult += 1;
                }
                break;
            case RIGHT:
                if (arrLvl1[y][x + 1] == 0) {
                    x++;
                }
                else if (arrLvl1[y][x + 1] == 4) {
                    lostDialog.show();
                    lostLvl();
                    countResult -= 2;
                }
                else if (arrLvl1[y][x + 1] == 2) {
                    numberLvl = 2;
                    victoryDialog1.show();
                    x++;
                    newLvl();
                    countResult += 2;
                }
                else if (arrLvl1[y][x + 1] == 5) {
                    numberLvl = 3;
                    victoryDialog2.show();
                    x++;
                    newLvl();
                    countResult += 4;
                }
                else if (arrLvl1[y][x + 1] == 6) {
                    numberLvl = 1;
                    victoryDialog3.show();
                    x++;
                    countResult += 8;

                }
                else if (arrLvl1[y][x + 1] == 3) {
                    teleportDialog.show();
                    y = 1;
                    x = 1;
                    countResult += 1;
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
    public void getName(String nameOF) {
        this.name = nameOF;
        invalidate();
    }
}

