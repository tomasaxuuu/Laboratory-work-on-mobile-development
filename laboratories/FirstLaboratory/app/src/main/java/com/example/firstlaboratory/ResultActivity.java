package com.example.firstlaboratory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView resultText = findViewById(R.id.result);
        Button back = findViewById(R.id.backBtn);
        // получение данных намерения из другой Activity
        float res = getIntent().getFloatExtra("Result", 0);
        if (res != (int)(res)) {
            resultText.setText(getResources().getString(R.string.resultFloat, res));
        } else {
            resultText.setText(getResources().getString(R.string.resultInteger, res));
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}