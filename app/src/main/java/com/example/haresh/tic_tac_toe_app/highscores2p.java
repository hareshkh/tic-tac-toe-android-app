package com.example.haresh.tic_tac_toe_app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class highscores2p extends AppCompatActivity {

    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores2p);
        Button clear= (Button) findViewById(R.id.clear);
        clear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseHandler dbh = new DatabaseHandler(highscores2p.this);
                        dbh.deleteAll();
                        finish();
                    }
                }
        );
        TextView list1 = (TextView) findViewById(R.id.list1);
        TextView list2 = (TextView) findViewById(R.id.list2);
        DatabaseHandler dbh=new DatabaseHandler(this);
        List<scoreboard> res=dbh.getAllContacts();
        for (scoreboard sb : res){
            i++;
            list1.setText(list1.getText()+"\n" + i + ". " + sb.get_name());
            list2.setText(list2.getText()+"\n"+ sb.get_score());
        }
    }
}
