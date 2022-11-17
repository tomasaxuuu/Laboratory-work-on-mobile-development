package com.example.fourthlaboratory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, NamesBase.DATABASE_NAME, null, NamesBase.DATABASE_VERSION);
    }

    // создание таблицы
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE IF NOT EXISTS " + NamesBase.TABLE_NAME + " (" +
                NamesBase.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NamesBase.COLUMN_DATE + " TEXT, " +
                NamesBase.COLUMN_TIME + " INTEGER, " +
                NamesBase.COLUMN_POINTS + " INTEGER, " +
                NamesBase.COLUMN_STEPS + " INTEGER); ";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { // Обнуляет предыдущею таблицу и создаёт новую. Срабатывает при изменении структуры базы данных
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NamesBase.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
