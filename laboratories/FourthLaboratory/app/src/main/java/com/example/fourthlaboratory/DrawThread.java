package com.example.fourthlaboratory;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class DrawThread extends Thread {

    public boolean runFlag = false;
    private final SurfaceHolder surfaceHolder;

    Paint wallBlack = new Paint();
    Paint playerRed = new Paint();
    Paint yellowField = new Paint();
    Paint trapGreen = new Paint();
    Paint blueFinish = new Paint();
    Paint teleportPurple = new Paint();
    Paint result = new Paint();
    Paint white = new Paint();

    private int x = 1;
    private int y = 1;
    private int stepsCount = 0;
    private int resultCount = 0;
    private long startTime, endTime;
    private float arrSize, hMargin, wMargin;
    private float h = 10;
    private float w = 10;
    private final int[][] arrExample = {
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
    private final int[][] arrLvl2 = {
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
    private final int[][] arrLvl3 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 6, 1},
            {1, 1, 0, 4, 4, 4, 4, 4, 0, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 0, 1, 1, 1, 1, 1, 0, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    private int numberLvl = 1;

    public float playerCenterX = hMargin + (arrLvl1[0][x] + 0.5f) * arrSize;
    public float playerCenterY = wMargin + (arrLvl1[y][0] + 0.5f) * arrSize;

    private final Bitmap startBtn;
    private final Bitmap victoryBtn;
    private final Bitmap lvl1Btn;
    private final Bitmap lvl2Btn;
    private final Bitmap lvl3Btn;
    public int state;

    private float btnLeft = 0;
    private float btnTop = 0;
    private float btnWidth = 0;
    private float btnHeight = 0;

    private float btnLeft1 = 0;
    private float btnTop1 = 0;
    private float btnWidth1 = 0;
    private float btnHeight1 = 0;

    public String dataStamp;
    public String resultStr;
    public String steps;
    public String time;

    public void doClick(float x, float y) {
        switch (state) {
            case 0:
                state = 1;
                break;
            case 1:
                state = 2;
                break;
            case 3:
                state = 2;
                numberLvl = 2;
                newLvl();
                break;
            case 4:
                state = 2;
                numberLvl = 3;
                newLvl();
                break;
            case 5:
                state = 6;
                break;
            case 6:
                state ++;
                break;
        }
    }

    public DrawThread(SurfaceHolder surfaceHolder, Resources resources) {
        this.surfaceHolder = surfaceHolder;
        startBtn = BitmapFactory.decodeResource(resources, R.drawable.start);
        lvl1Btn = BitmapFactory.decodeResource(resources, R.drawable.lvl1);
        lvl2Btn = BitmapFactory.decodeResource(resources, R.drawable.lvl2);
        lvl3Btn = BitmapFactory.decodeResource(resources, R.drawable.lvl3);
        victoryBtn = BitmapFactory.decodeResource(resources, R.drawable.victory);
        state = 0;
    }

    public void setRunning(boolean runFlag) {
        this.runFlag = runFlag;
    }

    private void newLvl() {
        switch (numberLvl) {
            case 1:
                arrLvl1 = arrExample;
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

    public void movePlayer(Direction direction) {
        // действия при прохождении по массиву
        switch (direction) {
            case UP:
                if (arrLvl1[y - 1][x] == 0) {
                    y--;
                    stepsCount++;
                }
                else if (arrLvl1[y - 1][x] == 4) {
                    resultCount --;
                    stepsCount++;
                    switch (numberLvl) {
                        case 1:
                            y = 1;
                            x = 1;
                            newLvl();
                            break;
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
                else if (arrLvl1[y - 1][x] == 2) {
                    resultCount ++;
                    stepsCount++;
                    numberLvl = 2;
                    y--;
                    state = 3;
                    newLvl();
                }
                else if (arrLvl1[y - 1][x] == 5) {
                    resultCount ++;
                    stepsCount++;
                    numberLvl = 3;
                    y--;
                    state = 4;
                    newLvl();
                }
                else if (arrLvl1[y - 1][x] == 6) {
                    resultCount ++;
                    stepsCount++;
                    y--;
                    state = 5;
                }
                else if (arrLvl1[y - 1][x] == 3) {
                    resultCount += 2;
                    stepsCount++;
                    y = 1;
                    x = 1;
                }
                break;

            case DOWN:
                if (arrLvl1[y + 1][x] == 0) {
                    stepsCount++;
                    y++;
                }
                else if (arrLvl1[y + 1][x] == 4) {
                    resultCount --;
                    stepsCount++;
                    switch (numberLvl) {
                        case 1:
                            y = 1;
                            x = 1;
                            newLvl();
                            break;
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
                else if (arrLvl1[y + 1][x] == 2) {
                    stepsCount++;
                    resultCount ++;
                    numberLvl = 2;
                    y++;
                    state = 3;
                    newLvl();
                }
                else if (arrLvl1[y + 1][x] == 5) {
                    stepsCount++;
                    resultCount ++;
                    numberLvl = 3;
                    y++;
                    state = 4;
                    newLvl();
                }
                else if (arrLvl1[y + 1][x] == 6) {
                    stepsCount++;
                    resultCount ++;
                    y++;
                    state = 5;
                }
                else if (arrLvl1[y + 1][x] == 3) {
                    resultCount += 2;
                    stepsCount++;
                    y = 1;
                    x = 1;
                }
                break;
            case LEFT:
                if (arrLvl1[y][x - 1] == 0) {
                    stepsCount++;
                    x--;
                }
                else if (arrLvl1[y][x - 1] == 4) {
                    resultCount --;
                    stepsCount++;
                    switch (numberLvl) {
                        case 1:
                            y = 1;
                            x = 1;
                            newLvl();
                            break;
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
                else if (arrLvl1[y][x - 1] == 2) {
                    stepsCount++;
                    resultCount ++;
                    numberLvl = 2;
                    x--;
                    state = 3;
                    newLvl();
                }
                else if (arrLvl1[y][x - 1] == 5) {
                    stepsCount++;
                    resultCount ++;
                    numberLvl = 3;
                    x--;
                    state = 4;
                    newLvl();
                }
                else if (arrLvl1[y][x - 1] == 6) {
                    stepsCount++;
                    resultCount ++;
                    x--;
                    state = 5;
                }
                else if (arrLvl1[y][x - 1] == 3) {
                    resultCount += 2;
                    stepsCount++;
                    y = 1;
                    x = 1;
                }
                break;
            case RIGHT:
                if (arrLvl1[y][x + 1] == 0) {
                    stepsCount++;
                    x++;
                }
                else if (arrLvl1[y][x + 1] == 4) {
                    resultCount --;
                    stepsCount++;
                    switch (numberLvl) {
                        case 1:
                            y = 1;
                            x = 1;
                            newLvl();
                            break;
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
                else if (arrLvl1[y][x + 1] == 2) {
                    stepsCount++;
                    resultCount ++;
                    numberLvl = 2;
                    x++;
                    state = 3;
                    newLvl();
                }
                else if (arrLvl1[y][x + 1] == 5) {
                    stepsCount++;
                    resultCount ++;
                    numberLvl = 3;
                    x++;
                    state = 4;
                    newLvl();
                }
                else if (arrLvl1[y][x + 1] == 6) {
                    stepsCount++;
                    resultCount ++;
                    x++;
                    state = 5;
                }
                else if (arrLvl1[y][x + 1] == 3) {
                    resultCount += 2;
                    stepsCount++;
                    y = 1;
                    x = 1;
                }
                break;
        }
    }

    private void doMenuAction() {
        Canvas canvas = null;
        try {
            canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                canvas.drawColor(Color.BLACK);
                Paint p = new Paint();
                if (btnLeft == 0) {
                    btnWidth = startBtn.getWidth();
                    btnHeight = startBtn.getHeight();
                    btnLeft = canvas.getWidth() / 2f - btnWidth / 2f;
                    btnTop = canvas.getHeight() / 2f - btnHeight / 2f;
                }
                canvas.drawBitmap(startBtn, btnLeft, btnTop, p);
            }

        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
    private void doLvl1Action() {
        Canvas canvas = null;
        try {
            canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                canvas.drawColor(Color.BLACK);
                Paint p = new Paint();
                if (btnLeft == 0) {
                    btnWidth = startBtn.getWidth();
                    btnHeight = startBtn.getHeight();
                }
                canvas.drawBitmap(lvl1Btn, btnWidth - btnWidth / 2 - 10, btnHeight + btnHeight / 3, p);
            }

        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
    private void doLvl2Action() {
        Canvas canvas = null;
        try {
            canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                canvas.drawColor(Color.BLACK);
                Paint p = new Paint();
                if (btnLeft == 0) {
                    btnWidth = startBtn.getWidth();
                    btnHeight = startBtn.getHeight();
                }
                canvas.drawBitmap(lvl2Btn, btnWidth - btnWidth / 2 - 10, btnHeight + btnHeight / 3, p);
            }

        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
    private void doLvl3Action() {
        Canvas canvas = null;
        try {
            canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                canvas.drawColor(Color.BLACK);
                Paint p = new Paint();
                if (btnLeft == 0) {
                    btnWidth = startBtn.getWidth();
                    btnHeight = startBtn.getHeight();
                    btnLeft = canvas.getWidth() / 2f - btnWidth / 2f;
                    btnTop = canvas.getHeight() / 2f - btnHeight / 2f;
                }
                canvas.drawBitmap(lvl3Btn, btnWidth - btnWidth / 2 - 10, btnHeight + btnHeight / 3, p);
            }

        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void doGameAction() {
        Canvas canvas = null;
        try {
            canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                result.setColor(Color.rgb(0, 0, 0));
                playerRed.setColor(Color.rgb(255, 0, 0));
                wallBlack.setColor(Color.rgb(0, 0, 0));
                trapGreen.setColor(Color.rgb(0, 128, 0));
                yellowField.setColor(Color.rgb(189, 183, 107));
                blueFinish.setColor(Color.rgb(0, 0, 255));
                teleportPurple.setColor(Color.rgb(146, 110, 174));
                white.setColor(Color.WHITE);
                result.setStyle(Paint.Style.FILL);
                canvas.drawPaint(result);
                result.setColor(Color.BLACK);
                result.setTextSize(50);
                float width = canvas.getWidth();
                float height = canvas.getHeight();

                if (width / height < w / h) {
                    arrSize = canvas.getWidth() / (w + 1);
                } else {
                    arrSize = canvas.getHeight() / (h + 1);
                }

                hMargin = (width - h * arrSize) / 2;
                wMargin = (height - w * arrSize) / 2;
                canvas.translate(hMargin, wMargin);
                playerCenterX = hMargin + (arrLvl1[0][x] + 0.5f) * arrSize;
                playerCenterY = wMargin + (arrLvl1[y][0] + 0.5f) * arrSize;

                // рисование лабиринта
                    for (int j = 0; j < h; j++) {
                        for (int i = 0; i < w; i++) {
                            switch (arrLvl1[j][i]) {
                                // field
                                case 0:
                                    canvas.drawCircle(i * 70, j * 70, 35, yellowField);
                                    break;
                                // wall
                                case 1:
                                    canvas.drawCircle(i * 70, j * 70,35, white);
                                    break;
                                // finish
                                case 2:
                                case 5:
                                case 6:
                                    canvas.drawCircle(i * 70, j * 70, 35, blueFinish);
                                    break;
                                // teleport
                                case 3:
                                    canvas.drawCircle(i * 70, j * 70, 35, teleportPurple);
                                    break;
                                // trap
                                case 4:
                                    canvas.drawCircle(i * 70, j * 70, 35, trapGreen);
                                    break;
                            }
                        }
                    }
                    canvas.drawCircle(x * 70, y * 70, 35, wallBlack);
                    canvas.drawCircle(x * 70, y * 70, 30, white);
                    canvas.drawCircle(x * 70, y * 70, 25, trapGreen);
                    canvas.drawCircle(x * 70, y * 70, 20, blueFinish);
                    canvas.drawCircle(x * 70, y * 70, 15, yellowField);
                    canvas.drawCircle(x * 70, y * 70, 10, teleportPurple);
                }

        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void doVictoryAction() {
        Canvas canvas = null;
        try {
            canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                canvas.drawColor(Color.BLACK);
                Paint p = new Paint();
                if (btnLeft1 == 0) {
                    btnWidth1 = victoryBtn.getWidth();
                    btnHeight1 = victoryBtn.getHeight();
                    btnLeft1 = canvas.getWidth() / 2f - btnWidth1 / 2f;
                    btnTop1 = canvas.getHeight() / 2f - btnHeight1 / 2f;
                }

                canvas.drawBitmap(victoryBtn, btnLeft1, btnTop1, p);
            }

        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void doResults () {
        resultStr = "" + resultCount;
        steps = "" + stepsCount;
        time = "" + (endTime - startTime) / 1000000000;
        dataStamp = "" + new SimpleDateFormat("dd.MM.yyyy").
                format(Calendar.getInstance().getTime());
        Canvas canvas = null;
        try {
            canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                canvas.drawColor(Color.BLACK);
                Paint p = new Paint();
                p.setTextSize(80);
                p.setColor(Color.YELLOW);
                canvas.drawText("Ваша статистика:", canvas.getWidth() / 2f - 340,
                        canvas.getHeight() / 5f * 1.25f, p);
                p.setTextSize(40);
                p.setColor(Color.WHITE);
                canvas.drawText("Кол-во очков: " + resultStr, canvas.getWidth() / 2f - 340,
                        canvas.getHeight() / 5f * 1.5f, p);
                canvas.drawText("Кол-во шагов: " + steps,
                        canvas.getWidth() / 2f - 340, canvas.getHeight() / 5f * 1.75f, p);
                canvas.drawText("Дата прохождения игры: " + dataStamp,
                        canvas.getWidth() / 2f - 340, canvas.getHeight() / 5f * 2f, p);
                canvas.drawText("Время прохождения: " + time + " sec",
                        canvas.getWidth() / 2f - 340, canvas.getHeight() / 5f * 2.25f, p);

            }
        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
    public float getCx() {
        return playerCenterX;
    }
    public float getCy() {
        return playerCenterY;
    }
    public float getArrSize() {
        return arrSize;
    }

    @Override
    public void run() {
        while (runFlag) {
            switch (state) {
                case 0:
                    startTime = System.nanoTime();
                    doMenuAction();
                    break;
                case 1:
                    doLvl1Action();
                    break;
                case 2:
                    doGameAction();
                    break;
                case 3:
                    doLvl2Action();
                    break;
                case 4:
                    doLvl3Action();
                    break;
                case 5:
                    endTime = System.nanoTime();
                    doVictoryAction();
                    break;
                case 6:
                    doResults();
                    break;
            }
        }
    }
}

