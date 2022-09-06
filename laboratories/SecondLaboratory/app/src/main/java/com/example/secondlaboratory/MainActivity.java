package com.example.secondlaboratory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView editItem = findViewById(R.id.editItem);
        Button addItem = findViewById(R.id.addItem);
        ListView list = findViewById(R.id.list);
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_multiple_choice, arrayList);

        list.setAdapter(adapter);
        adapter.add("first");
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editItem.length() != 0) {
                    adapter.add(editItem.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.emptyItem),
                            Toast.LENGTH_LONG).show();
                }

                // скрытие клавиатуры
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                // очистка editText
                editItem.setText("");
            }
        });

    }
}