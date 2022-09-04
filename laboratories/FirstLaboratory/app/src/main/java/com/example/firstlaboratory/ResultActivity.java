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

        float res = getIntent().getFloatExtra("Result", 0);
        TextView resultText = findViewById(R.id.result);
        if (res == Math.round(res)) {
            resultText.setText(getResources().getString(R.string.resultInteger, res));
        } else {
            resultText.setText(getResources().getString(R.string.resultFloat, res));
        }
        Button back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}