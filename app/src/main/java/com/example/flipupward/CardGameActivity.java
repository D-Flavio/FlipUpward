package com.example.flipupward;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;

public class CardGameActivity extends AppCompatActivity {

    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_game);

        int dimensions = getIntent().getExtras().getInt("dimensions");

        gridLayout = findViewById(R.id.grid_layout);

        initGrid(gridLayout, dimensions, this);

    }

    public void initGrid(GridLayout gridLayout, int dimensions, Context current){

        Context context = current;
        int sizeX = dimensions;
        int sizeY = dimensions;
        gridLayout.setRowCount(sizeY);
        gridLayout.setColumnCount(sizeX);

        for (int row = 0; row < sizeY; ++row) {
            for (int column = 0; column < sizeX; ++column) {
                CardView card = new CardView(current, dimensions);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(row, 1F),
                        GridLayout.spec(column, 1F)
                );

                params.width = 0;
                params.height = 0;
                params.setMargins(5,5,5,5);
                gridLayout.addView(card, params);
            }
        }

    }
}
