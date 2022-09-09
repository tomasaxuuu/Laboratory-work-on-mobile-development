package com.example.secondlaboratory;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // создание списочного массива используя класс ArrayList
    public static  ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView editItem = findViewById(R.id.editItem);
        Button addItemBtn = findViewById(R.id.addItem);
        Button choiceBtn = findViewById(R.id.choice);
        Button resetBtn = findViewById(R.id.reset);
        Button outBtn = findViewById(R.id.output);
        ListView list = findViewById(R.id.list);
        TextView name = findViewById(R.id.text);

        // создание адаптера для работы с элементами списка (передается имя файла(Активити),
        //идентификатор каждого элемента списка из list_item.xml(TextView для элементов),
        // и ссылка на массив
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, MainActivity.arrayList);
        list.setAdapter(adapter);

        // проверка на ориентацию устройства
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // смена названий кнопок и полей при смене ориентации
            outBtn.setText(R.string.outputEn);
            choiceBtn.setText(R.string.choiceEn);
            resetBtn.setText(R.string.resetEn);
            editItem.setHint(R.string.enterItemEn);
            name.setText(R.string.nameAddEn);
        }

        // добавление в список
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editItem.length() != 0) {
                    adapter.add(editItem.getText().toString());
                } else {
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.emptyItemEn),
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.emptyItem),
                                Toast.LENGTH_LONG).show();
                    }
                }

                // присвоение элементам списка красной гор.линии
                list.getDivider();

                // скрытие клавиатуры
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                // очистка editText
                editItem.setText("");
            }
        });

        // присвоение каждому элементу списка значения - выбор(мультивыбор)
        choiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < list.getCount(); i++) {
                    list.setItemChecked(i, true);
                }
            }
        });

        // сброс значения - выбор с каждого элемента списка
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < list.getCount(); i++) {
                    list.setItemChecked(i, false);
                }
            }
        });

        outBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray check = list.getCheckedItemPositions();
                String checkTrue = "";
                for(int i = 0; i < list.getCount(); i++) {
                    if(check.get(i)) {
                        checkTrue += i + 1 + ": " +  list.getItemAtPosition(i).toString() + "\n";
                    }
                }
                if(checkTrue.length() != 0) {
                    Toast.makeText(getBaseContext(), checkTrue,
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.errorOutEn),
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.errorOut),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}