package com.example.fourthlaboratory;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String date;
    int time;
    String points;
    int steps;

    private NamesBase DBConnector;
    private ArrayList<String> stats = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;


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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myStats = database.getReference("stats");
        ArrayList<String> messages = new ArrayList<>();
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
            myStats.push().setValue("Дата: " + date + ", Время: " + String.valueOf(time) + ", Очки: " + points + ", Шаги: " + String.valueOf(steps));
            UpdateList();

        }

        Button maze = findViewById(R.id.game);
        Button db = findViewById(R.id.db);
        Button fireBase = findViewById(R.id.fireBaseBtn);
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
                        if (list.getCount() > 0) {
                            UpdateListPoints();
                        }
                    }
                });

                bestTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (list.getCount() > 0) {
                            UpdateListTime();
                        }
                    }
                });

                pointsUb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (list.getCount() > 0) {
                            UpdateListPointsUb();
                        }
                    }
                });

                timeUb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (list.getCount() > 0) {
                            UpdateListTimeUb();
                        }
                    }
                });

                seeList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (list.getCount() > 0) {
                            UpdateList();
                        }
                    }
                });



                list.setAdapter(adapter);
            }
        });
        fireBase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_list_tasks);
                Button backFB = findViewById(R.id.backFB);
                ListView listFB = findViewById(R.id.itemsFireBase);
                Button deleteFB = findViewById(R.id.deleteFB);
                backFB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i1 = new Intent(view.getContext(), MainActivity.class);
                        startActivity(i1);
                    }
                });

                deleteFB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(listFB.getCount() > 0) {
                            myStats.getRef().removeValue();
                            Intent i = new Intent(view.getContext(), MainActivity.class);
                            startActivity(i);
                        }
                    }
                });

                myStats.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String msg = snapshot.getValue(String.class);
                        messages.add(msg);

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
                myStats.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        adapter1 = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, messages);
                        adapter1.notifyDataSetChanged();
                        listFB.setAdapter(adapter1);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });
    }
}