package com.example.flipupward;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GameActivity extends AppCompatActivity {

    List<RandomNumberModel> randomNumList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        recyclerView = findViewById(R.id.rVGame);

        int dimensions = getIntent().getExtras().getInt("dimensions");

        for (int i = 1; i<=dimensions*dimensions; i++){
            RandomNumberModel data = new RandomNumberModel();
            data.setNum(NumGen.generate());
            randomNumList.add(data);
        }

        MyCardAdapter adapter = new MyCardAdapter(this, randomNumList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, dimensions));

    }
}
