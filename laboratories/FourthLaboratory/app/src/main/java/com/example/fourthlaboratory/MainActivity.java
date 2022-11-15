package com.example.fourthlaboratory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String date;
    String time;
    String points;
    String steps;

    private NamesBase DBConnector;
    private ArrayList<String> stats = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    public void UpdateList () {

        adapter.clear();

        for(Names n: DBConnector.selectAll()) {
            adapter.add(n.getId() + ": Дата: " + n.getDate() + ", Время: " + n.getTime() + ", Очки: "
                    + n.getPoints() + ", Шаги: " + n.getSteps());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBConnector = new NamesBase(this);

        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        points = getIntent().getStringExtra("points");
        steps = getIntent().getStringExtra("steps");

        if(date != null && time != null && points != null && steps != null) {

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stats);
            DBConnector.add(date, time, points, steps);
            UpdateList();
            date = null;
            time = null;
            points = null;
            steps = null;
        }

        Button maze = findViewById(R.id.game);
        Button db = findViewById(R.id.db);

        maze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(new GameView(view.getContext()));
            }
        });

        db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_list);

                Button back = findViewById(R.id.backBtn);
                Button clear = findViewById(R.id.clearBtn);
                ListView list = findViewById(R.id.itemsList);

                adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, stats);
                UpdateList();

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i1 = new Intent(view.getContext(), MainActivity.class);
                        startActivity(i1);
                    }
                });

                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (list.getCount() > 0) {
                            adapter.clear();
                            DBConnector.clear();
                        }

                    }
                });

                list.setAdapter(adapter);
            }
        });
    }
}