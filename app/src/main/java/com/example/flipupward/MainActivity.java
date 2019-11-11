package com.example.flipupward;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private View fourCardGame;
    private View nineCardGame;
    private View sixteenCardGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        fourCardGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CardView.arrListCardView.clear();
                Intent startFourCardGameActivityIntent = new Intent(MainActivity.this, CardGameActivity.class);
                startFourCardGameActivityIntent.putExtra("dimensions", 2);
                startActivity(startFourCardGameActivityIntent);
            }
        });

        nineCardGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CardView.arrListCardView.clear();
                Intent startNineCardGameActivityIntent = new Intent(MainActivity.this, CardGameActivity.class);
                startNineCardGameActivityIntent.putExtra("dimensions", 3);
                startActivity(startNineCardGameActivityIntent);
            }
        });

        sixteenCardGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                CardView.arrListCardView.clear();
                Intent startSixteenCardGameActivityIntent = new Intent(MainActivity.this, CardGameActivity.class);
                startSixteenCardGameActivityIntent.putExtra("dimensions", 4);
                startActivity(startSixteenCardGameActivityIntent);
            }
        });
    }

    private void findViews() {
        fourCardGame = (Button)findViewById(R.id.fourcardgamebtn);
        nineCardGame = (Button) findViewById(R.id.ninecardgamebtn);
        sixteenCardGame = (Button) findViewById(R.id.sixteencardgamebtn);
    }
}
