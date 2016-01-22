package com.example.haresh.tic_tac_toe_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class highscores2p extends AppCompatActivity {

    TextView txtHigh;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores2p);
        txtHigh= (TextView) findViewById(R.id.txtHigh);
        txtHigh.setText(" ");
        i=0;
        DatabaseHandler dbh=new DatabaseHandler(this);
        List<scoreboard> res=dbh.getAllContacts();
        for (scoreboard sb : res){
            i++;
            txtHigh.setText(txtHigh.getText()+"\n" + i + ". " + sb.get_name() + '\t' + ": " +sb.get_score());
        }
    }
}
