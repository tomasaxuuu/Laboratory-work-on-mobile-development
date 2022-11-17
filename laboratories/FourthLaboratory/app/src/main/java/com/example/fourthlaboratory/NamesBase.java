package com.example.fourthlaboratory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class NamesBase {

    public static final String DATABASE_NAME = "list_db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Names";
    private final SQLiteDatabase mDataBase;

    // названия колонок
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_STEPS = "steps";
    public static final String COLUMN_POINTS = "points";

    // номера колонок
    public static  int NUM_COLUMN_ID = 0;
    public static  int NUM_COLUMN_DATE = 1;
    public static int NUM_COLUMN_TIME = 2;
    public static int NUM_COLUMN_POINTS = 3;
    public static  int NUM_COLUMN_STEPS = 4;

    public NamesBase(Context context) {
        DBHelper helper = new DBHelper(context);
        mDataBase = helper.getWritableDatabase();
        helper.onCreate(mDataBase);
    }

    // извлечение данных из таблицы
    public ArrayList<Names> selectAll() {

        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Names> list = new ArrayList<Names>();
        mCursor.moveToFirst();

        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String date = mCursor.getString(NUM_COLUMN_DATE);
                int time = mCursor.getInt(NUM_COLUMN_TIME);
                int points = mCursor.getInt(NUM_COLUMN_POINTS);
                int steps = mCursor.getInt(NUM_COLUMN_STEPS);
                Names stats = new Names(id, date, time, points, steps);

                list.add(stats);
            } while (mCursor.moveToNext());
        }

        mCursor.close();

        return list;
    }

    // сортировка по очкам
    String orderBy = "points";
    public Names bestPointsSelect() {

        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, orderBy + " DESC");
        ArrayList<Names> list = new ArrayList<Names>();
        mCursor.moveToFirst();

        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String date = mCursor.getString(NUM_COLUMN_DATE);
                int time = mCursor.getInt(NUM_COLUMN_TIME);
                int points = mCursor.getInt(NUM_COLUMN_POINTS);
                int steps = mCursor.getInt(NUM_COLUMN_STEPS);
                Names stats = new Names(id, date, time, points, steps);
                list.add(stats);
            } while (mCursor.moveToNext());
        }

        mCursor.close();

        return list.get(0);
    }

    // сортировка по времени
    String orderTime = "time";
    public Names bestTimeSelect() {

        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, orderTime + " DESC");
        ArrayList<Names> list = new ArrayList<Names>();
        mCursor.moveToFirst();

        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String date = mCursor.getString(NUM_COLUMN_DATE);
                int time = mCursor.getInt(NUM_COLUMN_TIME);
                int points = mCursor.getInt(NUM_COLUMN_POINTS);
                int steps = mCursor.getInt(NUM_COLUMN_STEPS);
                Names stats = new Names(id, date, time, points, steps);
                list.add(stats);
            } while (mCursor.moveToNext());
        }

        mCursor.close();

        return list.get(0);
    }
    String orderResults = "points";
    public ArrayList<Names> bestResultSelect() {

        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, orderResults + " DESC");
        ArrayList<Names> list = new ArrayList<Names>();
        mCursor.moveToFirst();

        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String date = mCursor.getString(NUM_COLUMN_DATE);
                int time = mCursor.getInt(NUM_COLUMN_TIME);
                int points = mCursor.getInt(NUM_COLUMN_POINTS);
                int steps = mCursor.getInt(NUM_COLUMN_STEPS);
                Names stats = new Names(id, date, time, points, steps);
                list.add(stats);
            } while (mCursor.moveToNext());
        }

        mCursor.close();

        return list;
    }

    String orderWorstTime= "time";
    public ArrayList<Names> worstTimeSelect() {

        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, orderWorstTime + " DESC");
        ArrayList<Names> list = new ArrayList<Names>();
        mCursor.moveToFirst();

        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String date = mCursor.getString(NUM_COLUMN_DATE);
                int time = mCursor.getInt(NUM_COLUMN_TIME);
                int points = mCursor.getInt(NUM_COLUMN_POINTS);
                int steps = mCursor.getInt(NUM_COLUMN_STEPS);
                Names stats = new Names(id, date, time, points, steps);

                list.add(stats);
            } while (mCursor.moveToNext());
        }

        mCursor.close();

        return list;
    }

    // вставка записи в БД
    public long add(String date, String time, String points, String steps) {

        // НОМЕР КОЛОНКИ - ДАННЫЕ
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_TIME, time);
        cv.put(COLUMN_POINTS, points);
        cv.put(COLUMN_STEPS, steps);

        return mDataBase.insert(TABLE_NAME, null, cv);
    }
    public void clear() { // очистка БД

        mDataBase.execSQL("DROP TABLE " + TABLE_NAME);
    }
}
