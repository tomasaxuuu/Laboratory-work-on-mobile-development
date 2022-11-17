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
    int time;
    String points;
    int steps;

    private NamesBase DBConnector;
    private ArrayList<String> stats = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    // вывод списка
    public void UpdateList () {
        adapter.clear();

        for(Names n: DBConnector.selectAll()) {
            adapter.add(n.getId() + ": Дата: " + n.getDate() + ", Время: " + n.getTime() + ", Очки: "
                    + n.getPoints() + ", Шаги: " + n.getSteps());
        }

    }

    // вывод лучшего результата
    public void UpdateListPoints () {
        adapter.clear();

        Names n = DBConnector.bestPointsSelect();
            adapter.add(n.getId() + ": Дата: " + n.getDate() + ", Время: " + n.getTime() + ", Очки: "
                    + n.getPoints() + ", Шаги: " + n.getSteps());


    }

    // вывод худшего времени
    public void UpdateListTime () {
        adapter.clear();

        Names n = DBConnector.bestTimeSelect();
        adapter.add(n.getId() + ": Дата: " + n.getDate() + ", Время: " + n.getTime() + ", Очки: "
                + n.getPoints() + ", Шаги: " + n.getSteps());
    }

    // вывод списка по убыванию очков
    public void UpdateListPointsUb () {
        adapter.clear();

        for(Names n: DBConnector.bestResultSelect()) {
            adapter.add(n.getId() + ": Дата: " + n.getDate() + ", Время: " + n.getTime() + ", Очки: "
                    + n.getPoints() + ", Шаги: " + n.getSteps());
        }


    }

    // вывод списка по убыванию времени
    public void UpdateListTimeUb () {
        adapter.clear();

        for(Names n: DBConnector.worstTimeSelect()) {
            adapter.add(n.getId() + ": Дата: " + n.getDate() + ", Время: " + n.getTime() + ", Очки: "
                    + n.getPoints() + ", Шаги: " + n.getSteps());
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBConnector = new NamesBase(this);

        // получение статистики из gameView
        date = getIntent().getStringExtra("date");
        time = getIntent().getIntExtra("time", 0);
        points = String.valueOf(getIntent().getIntExtra("points", 0));
        steps = getIntent().getIntExtra("steps", 0);

        // добавление статистики в список и бд, если данные не null
        if(date != null && time != 0 && points != null && steps != 0) {

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stats);
            DBConnector.add(date, String.valueOf(time), points, String.valueOf(steps));
            UpdateList();

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
                Button bestPoints = findViewById(R.id.pointsBest);
                Button bestTime = findViewById(R.id.timeBest);
                Button pointsUb = findViewById(R.id.pointsUb);
                Button timeUb = findViewById(R.id.timeUb);
                Button seeList = findViewById(R.id.seeList);
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

                bestPoints.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UpdateListPoints();
                    }
                });

                bestTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UpdateListTime();
                    }
                });

                pointsUb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UpdateListPointsUb();
                    }
                });

                timeUb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UpdateListTimeUb();
                    }
                });

                seeList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UpdateList();
                    }
                });

                list.setAdapter(adapter);
            }
        });
    }
}