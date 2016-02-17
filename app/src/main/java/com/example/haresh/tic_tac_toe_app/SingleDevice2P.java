package com.example.haresh.tic_tac_toe_app;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SingleDevice2P extends AppCompatActivity {

    public static Activity act_2p_single;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        act_2p_single = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_device2_p);
    }
}
