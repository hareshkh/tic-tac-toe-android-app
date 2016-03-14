package com.example.haresh.tic_tac_toe_app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import static com.example.haresh.tic_tac_toe_app.R.id.RGtwoPlayer;

public class MainActivity extends AppCompatActivity {

    Button b1, b2, b3, play, highScore;
    boolean singleSelected = true;
    RadioButton easy, difficult, singleDevice, twoDevice;
    RadioGroup singlePlayer, twoPlayer;
    SharedPreferences sharedPrefs;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefs = getSharedPreferences("NAMES", MODE_PRIVATE);
        two_player_names.p1Name = sharedPrefs.getString("PLAYER1", two_player_names.p1Name);
        two_player_names.p2Name = sharedPrefs.getString("PLAYER2", two_player_names.p2Name);
        TwoDevice2P_names.MyName = sharedPrefs.getString("MYNAME", TwoDevice2P_names.MyName);

        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        play = (Button) findViewById(R.id.play);
        highScore = (Button) findViewById(R.id.highScores);
        highScore.setVisibility(View.INVISIBLE);
        easy = (RadioButton) findViewById(R.id.easyRBtn);
        easy.setChecked(true);
        difficult = (RadioButton) findViewById(R.id.difficultRBtn);
        singleDevice = (RadioButton) findViewById(R.id.singleRBtn);
        singleDevice.setChecked(true);
        twoDevice = (RadioButton) findViewById(R.id.difficultRBtn);

        singlePlayer = (RadioGroup) findViewById(R.id.RGsinglePlayer);
        singlePlayer.setVisibility(View.VISIBLE);
        twoPlayer = (RadioGroup) findViewById(RGtwoPlayer);
        twoPlayer.setVisibility(View.INVISIBLE);
        b2.setAlpha((float) 0.3);
        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b1.setAlpha((float) 1);
                        b2.setAlpha((float) 0.3);
                        singleSelected = true;
                        singlePlayer.setVisibility(View.VISIBLE);
                        twoPlayer.setVisibility(View.INVISIBLE);
                        highScore.setVisibility(View.INVISIBLE);
                    }
                }
        );
        b2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b1.setAlpha((float) 0.3);
                        b2.setAlpha((float) 1);
                        singleSelected = false;
                        singlePlayer.setVisibility(View.INVISIBLE);
                        twoPlayer.setVisibility(View.VISIBLE);
                        highScore.setVisibility(View.VISIBLE);
                    }
                }
        );
        b3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;
                        intent = new Intent(MainActivity.this, about.class);
                        startActivity(intent);
                    }
                }
        );
        play.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (singleSelected) {
                            if (easy.isChecked()) {
                                Intent intent;
                                intent = new Intent(MainActivity.this, one_player_easy.class);
                                startActivity(intent);
                            } else if (difficult.isChecked()) {
                                Intent intent;
                                intent = new Intent(MainActivity.this, one_player_difficult.class);
                                startActivity(intent);
                            }
                        } else {
                            if (singleDevice.isChecked()) {
                                Intent intent;
                                intent = new Intent(MainActivity.this, two_player_names.class);
                                startActivity(intent);
                            } else {
                                Intent intent;
                                intent = new Intent(MainActivity.this, TwoDevice2P_names.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
        );

        highScore.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, highscores2p.class);
                        startActivity(intent);
                    }
                }
        );
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.haresh.tic_tac_toe_app/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putString("PLAYER1", two_player_names.p1Name);
        edit.putString("PLAYER2", two_player_names.p2Name);
        edit.putString("MYNAME", TwoDevice2P_names.MyName);
        edit.commit();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.haresh.tic_tac_toe_app/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
