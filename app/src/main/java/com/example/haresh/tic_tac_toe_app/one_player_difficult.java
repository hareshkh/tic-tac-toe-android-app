package com.example.haresh.tic_tac_toe_app;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class one_player_difficult extends AppCompatActivity {

    public static Activity act_1d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        act_1d = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_player_difficult);
    }
}
