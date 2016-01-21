package com.example.haresh.tic_tac_toe_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class highscores2p extends AppCompatActivity {

    TextView txtHigh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores2p);
        txtHigh= (TextView) findViewById(R.id.txtHigh);
        txtHigh.setText(" ");
        DatabaseHandler dbh=new DatabaseHandler(this);
        List<scoreboard> res=dbh.getAllContacts();
        for (scoreboard sb : res){
            txtHigh.setText(txtHigh.getText()+"/n"+sb.get_name()+" : "+sb.get_score());
        }
    }
}
