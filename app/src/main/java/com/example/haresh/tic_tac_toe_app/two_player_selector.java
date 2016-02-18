package com.example.haresh.tic_tac_toe_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class two_player_selector extends AppCompatActivity {
    Button singleplay2p,doubleplay2p,high2p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_selector);
        singleplay2p = (Button) findViewById(R.id.singleplay2p);
        doubleplay2p = (Button) findViewById(R.id.doubleplay2p);
        high2p = (Button) findViewById(R.id.high2P);
        singleplay2p.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(two_player_selector.this,two_player_names.class);
                        startActivity(intent);
                    }
                }
        );
        doubleplay2p.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(two_player_selector.this,TwoDevice2P_names.class);
                        startActivity(intent);
                    }
                }
        );
        high2p.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(two_player_selector.this,highscores2p.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
