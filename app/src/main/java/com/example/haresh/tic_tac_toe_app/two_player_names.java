package com.example.haresh.tic_tac_toe_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class two_player_names extends AppCompatActivity {

    Button start2p;
    EditText p1, p2;
    static String p1Name = "Player 1";
    static String p2Name = "Player 2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_names);
        p1 = (EditText) findViewById(R.id.p1name);
        p2 = (EditText) findViewById(R.id.p2name);
        p1.setText(p1Name);
        p2.setText(p2Name);
        start2p = (Button) findViewById(R.id.start2p);
        start2p.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(two_player_names.this, SingleDevice2P.class);
                        if ((!Objects.equals(p1.getText().toString(), "")) && (!Objects.equals(p2.getText().toString(), "")) && (!Objects.equals(p1.getText().toString(), p2.getText().toString())) && (p1.getText().toString().length() <= 10) && (p2.getText().toString().length() <= 10)) {
                            p1Name = p1.getText().toString().trim().toUpperCase();
                            p2Name = p2.getText().toString().trim().toUpperCase();
                            DatabaseHandler dbh = new DatabaseHandler(two_player_names.this);
                            String tmp = dbh.checkUser(two_player_names.p1Name);
                            if (tmp.equals("FOUND")) {
                                //Toast.makeText(two_player_names.this,"found 1",Toast.LENGTH_SHORT).show();
                            } else {
                                scoreboard sb = new scoreboard(dbh.getPlayerCount(), two_player_names.p1Name, 0);
                                //Toast.makeText(two_player_names.this,"adding  1",Toast.LENGTH_SHORT).show();
                                dbh.addScore(sb);
                            }

                            String tmp2 = dbh.checkUser(two_player_names.p2Name);
                            if (tmp2.equals("FOUND")) {
                                //Toast.makeText(two_player_names.this,"found 2",Toast.LENGTH_SHORT).show();

                            } else {
                                scoreboard sb = new scoreboard(dbh.getPlayerCount(), two_player_names.p2Name, 0);
                                //Toast.makeText(two_player_names.this,"adding 2",Toast.LENGTH_SHORT).show();
                                dbh.addScore(sb);
                            }
                            startActivity(intent);
                        } else if (Objects.equals(p1.getText().toString(), "")) {
                            p1.setError("Enter Player 1 Name");
                        } else if (Objects.equals(p2.getText().toString(), "")) {
                            p2.setError("Enter Player 2 Name");
                        } else if (Objects.equals(p1.getText().toString(), p2.getText().toString())) {
                            Toast.makeText(two_player_names.this, "Enter different names", Toast.LENGTH_SHORT).show();
                        } else if (p1.getText().toString().length() > 10) {
                            Toast.makeText(two_player_names.this, "Shorten player 1 name", Toast.LENGTH_SHORT).show();
                        } else if (p2.getText().toString().length() > 10) {
                            Toast.makeText(two_player_names.this, "Shorten player 2 name", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
}
