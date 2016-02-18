package com.example.haresh.tic_tac_toe_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TwoDevice2P_names extends AppCompatActivity {

    public static String MyName;
    public static String OpponentName;
    Button btn;
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_device2_p_names);
        btn = (Button) findViewById(R.id.btn_find);
        name = (EditText) findViewById(R.id.myName);
        name.setText("");
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyName = name.getText().toString();
                        if(MyName.trim().equals("")){
                            name.setError("Enter Name");
                        }
                        else{
                            Intent intent = new Intent(TwoDevice2P_names.this,BluetoothActivity.class);
                            startActivity(intent);
                        }
                    }
                }
        );
    }
}
