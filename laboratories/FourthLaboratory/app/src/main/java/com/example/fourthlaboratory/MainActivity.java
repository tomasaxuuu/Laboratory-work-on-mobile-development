package com.example.fourthlaboratory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button nameOtp = findViewById(R.id.nameBtn);
        EditText name = findViewById(R.id.name);
        GameView maze = findViewById(R.id.maze);
        nameOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maze.getName(name.getText().toString());
                name.setText("");
            }
        });
    }
}