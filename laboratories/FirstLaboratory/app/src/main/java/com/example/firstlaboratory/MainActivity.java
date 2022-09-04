package com.example.firstlaboratory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText op1 = findViewById(R.id.op1);
        EditText op2 = findViewById(R.id.op2);
        Button sumBtn = findViewById(R.id.sumBtn);
        Button minusBtn = findViewById(R.id.minusBtn);
        Button appBtn = findViewById(R.id.appBtn);
        Button delBtn = findViewById(R.id.delBtn);

        sumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    float first = Float.parseFloat(op1.getText().toString());
                    float second = Float.parseFloat(op2.getText().toString());
                    Intent finalResult = new Intent(MainActivity.this, ResultActivity.class);
                    finalResult.putExtra("Result", first + second);
                    startActivity(finalResult);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error),
                            Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    float first = Float.parseFloat(op1.getText().toString());
                    float second = Float.parseFloat(op2.getText().toString());
                    Intent finalResult = new Intent(MainActivity.this, ResultActivity.class);
                    finalResult.putExtra("Result", first - second);
                    startActivity(finalResult);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error),
                            Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        appBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    float first = Float.parseFloat(op1.getText().toString());
                    float second = Float.parseFloat(op2.getText().toString());
                    Intent finalResult = new Intent(MainActivity.this, ResultActivity.class);
                    finalResult.putExtra("Result", first * second);
                    startActivity(finalResult);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error),
                            Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float first = Float.parseFloat(op1.getText().toString());
                float second = Float.parseFloat(op2.getText().toString());;
                try {
                    Intent finalResult = new Intent(MainActivity.this, ResultActivity.class);
                    finalResult.putExtra("Result", first / second);
                    if(second == 0) {
                        throw new Exception();
                    }
                    startActivity(finalResult);
                } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error),
                                Toast.LENGTH_LONG).show();
                        return;
                }

            }
        });
    }
}