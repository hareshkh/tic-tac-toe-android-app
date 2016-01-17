package com.example.haresh.tic_tac_toe_app;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class one_player_easy extends AppCompatActivity {

    public static Activity act_1e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        act_1e=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_player_easy);
    }
}
