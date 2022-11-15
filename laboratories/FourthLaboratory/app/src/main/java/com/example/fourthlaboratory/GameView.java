package com.example.fourthlaboratory;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    DrawThread drawThread;

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        drawThread = new DrawThread(surfaceHolder, getResources());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
    }

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {

        // определение нажатия
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            drawThread.doClick(event.getX(), event.getY());
            if(drawThread.state == 7) {
                drawThread.runFlag = false;
                drawThread.state = 0;
                Intent i = new Intent(getContext(), MainActivity.class);
                i.putExtra("date", drawThread.dataStamp);
                i.putExtra("time", drawThread.time);
                i.putExtra("points", drawThread.resultStr);
                i.putExtra("steps", drawThread.steps);
                getContext().startActivity(i);
            }
            return true;
        }
        // определение стороны движения, то есть куда нам двигаться
        if (event.getAction() == MotionEvent.ACTION_MOVE) {

            // получение событий по осям
            float x1 = event.getX();
            float y1 = event.getY();
            // определение центра ячейки игрока
            // выбор направления движения
            float dx = x1 - drawThread.getCx();
            float dy = y1 - drawThread.getCy();
            float absDx = Math.abs(dx);
            float absDy = Math.abs(dy);

            if (absDx > drawThread.getArrSize() || absDy > drawThread.getArrSize()) {
                // выбор направления движения
                if (absDx > absDy) {
                    // move in x-direction
                    if (dx > 0)
                        drawThread.movePlayer(Direction.RIGHT);
                    else
                        drawThread.movePlayer(Direction.LEFT);
                } else {
                    // move in y-direction
                    if (dy > 0)
                        drawThread.movePlayer(Direction.DOWN);
                    else
                        drawThread.movePlayer(Direction.UP);
                }
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
}

