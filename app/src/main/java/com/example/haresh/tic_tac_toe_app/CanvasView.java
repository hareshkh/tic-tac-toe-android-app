package com.example.haresh.tic_tac_toe_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Haresh on 17-01-2016.
 */
public class CanvasView extends View { //you have to create a new java file and then insert the same file in the xml of the page in which you want the canvas
    Paint paint = new Paint();
    Paint paintx = new Paint();
    Paint painto = new Paint();
    Paint painto1 = new Paint();
    boolean oncewin = false;
    int[][] a = new int[3][3];
    float[][] midx = new float[3][3];
    float[][] midy = new float[3][3];
    int turn = 0;
    Context ctx;
    float canvasSide,cellSide;
    DatabaseHandler dbh;
    public void init(){

        for (int row=0;row<3;row++){
            for (int col=0;col<3;col++){
                Resources r = getResources();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, r.getDisplayMetrics());
                a[row][col]=0;
                midx[row][col]=((px/6)+(col*(px/3)));
                midy[row][col]=((px/6)+(row*(px/3)));
            }
        }

        oncewin=false;
        turn = 0;
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx=context;
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10f);
        paint.setColor(Color.rgb(245, 125, 10));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);

        paintx.setStrokeWidth(15f);
        paintx.setColor(Color.rgb(105, 240, 174));
        paintx.setStyle(Paint.Style.STROKE);
        paintx.setStrokeJoin(Paint.Join.ROUND);

        painto.setColor(Color.rgb(10, 125, 245));
        painto.setStyle(Paint.Style.FILL);

        painto1.setColor(Color.rgb(255, 224, 178));
        painto1.setStyle(Paint.Style.FILL);

        init();
        dbh=new DatabaseHandler(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Resources r = getResources();
        float pxi = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, r.getDisplayMetrics());
        canvasSide=pxi;
        cellSide=canvasSide/3;
        canvas.drawLine(cellSide, 0, cellSide, canvasSide, paint);
        canvas.drawLine(2 * cellSide, 0, 2 * cellSide, canvasSide, paint);
        canvas.drawLine(0, cellSide, canvasSide, cellSide, paint);
        canvas.drawLine(0, 2 * cellSide, canvasSide, 2 * cellSide, paint);
        for (int row=0;row<3;row++) {
            for (int col = 0; col < 3; col++) {
                if (a[row][col]==1) {
                    canvas.drawLine((midx[row][col] - ((4*cellSide)/11)), (midy[row][col] - ((4*cellSide)/11)), (midx[row][col] + ((4*cellSide)/11)), (midy[row][col] + ((4*cellSide)/11)), paintx);
                    canvas.drawLine((midx[row][col] + ((4*cellSide)/11)), (midy[row][col] - ((4*cellSide)/11)), (midx[row][col] - ((4*cellSide)/11)), (midy[row][col] + ((4*cellSide)/11)), paintx);
                }
                else if (a[row][col]==2){
                    canvas.drawCircle(midx[row][col],midy[row][col], (float) ((4*cellSide)/11),painto);
                    canvas.drawCircle(midx[row][col],midy[row][col], (float) ((13*cellSide)/44),painto1);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN) {
            float touchX = event.getX();
            float touchY = event.getY();
            if (touchX < canvasSide && touchX > 0 && touchY < canvasSide && touchX > 0) {
                int col = (int) (touchX / cellSide);
                int row = (int) (touchY / cellSide);
                //Toast.makeText(ctx,row+" : "+col,Toast.LENGTH_SHORT).show();
                if (a[row][col] == 0) {
                    a[row][col]++;
                    turn++;

                    if (turn % 2 == 0) {
                        a[row][col]++;
                    }
                    invalidate();
                    check();
                }
            }
        }
        return true;
    }

    public void showAlert(String str){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                    {
                        two_player.act_2p.finish();
                    }
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(str).setPositiveButton("Ok!", dialogClickListener).show();
    }



    public void updateWin(int i){
        if(i==1){
            String tmp="FOUND";
            try{
                tmp=dbh.checkUser(two_player_names.p1Name);
            }
            catch(Exception e){
                Toast.makeText(ctx,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            if(tmp.equals("FOUND")){
                scoreboard sb=dbh.getPlayer(two_player_names.p1Name);
                scoreboard sb2=new scoreboard(sb.get_id(),sb.get_name(),(sb.get_score())+1);
                dbh.updatePlayer(sb2);
            }
            else{
                scoreboard sb=new scoreboard(dbh.getPlayerCount(),two_player_names.p1Name,1);
                dbh.addScore(sb);
            }
        }
        else if (i==2){
            String tmp="FOUND";
            try{
                tmp=dbh.checkUser(two_player_names.p1Name);
            }
            catch(Exception e){
                Toast.makeText(ctx,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            if(tmp.equals("FOUND")){
                scoreboard sb=dbh.getPlayer(two_player_names.p2Name);
                scoreboard sb2=new scoreboard(sb.get_id(),sb.get_name(),(sb.get_score())+1);
                dbh.updatePlayer(sb2);
            }
            else{
                scoreboard sb=new scoreboard(dbh.getPlayerCount(),two_player_names.p2Name,1);
                dbh.addScore(sb);
            }
        }
    }

    public void check()
    {
        if (!oncewin)
        {
            if (a[0][0]==a[0][1] && a[0][1]==a[0][2])
            {
                if (a[0][0]==1)
                {
                    //Toast.makeText(getContext(),"Player 1 wins! ",Toast.LENGTH_SHORT).show();
                    showAlert(two_player_names.p1Name + " wins! ");
                    updateWin(1);
                    oncewin = true;
                }
                else if(a[0][0]==2)
                {
                    //Toast.makeText(getContext(),"Player 2 wins! ",Toast.LENGTH_SHORT).show();
                    showAlert(two_player_names.p2Name + " wins! ");
                    updateWin(2);
                    oncewin = true;
                }
            }

            if (a[1][0]==a[1][1] && a[1][1]==a[1][2])
            {
                if (a[1][0]==1)
                {
                    //Toast.makeText(getContext(),"Player 1 wins! ",Toast.LENGTH_SHORT).show();
                    showAlert(two_player_names.p1Name + " wins! ");
                    updateWin(1);
                    oncewin = true;
                }
                else if(a[1][0]==2)
                {
                    //Toast.makeText(getContext(),"Player 2 wins! ",Toast.LENGTH_SHORT).show();
                    showAlert(two_player_names.p2Name + " wins! ");
                    updateWin(2);
                    oncewin = true;
                }
            }

            if (a[2][0]==a[2][1] && a[2][1]==a[2][2])
            {
                if (a[2][0]==1)
                {
                    //Toast.makeText(getContext(),"Player 1 wins! ",Toast.LENGTH_SHORT).show();
                    showAlert(two_player_names.p1Name + " wins! ");
                    updateWin(1);
                    oncewin = true;
                }
                else if(a[2][0]==2)
                {
                    //Toast.makeText(getContext(),"Player 2 wins! ",Toast.LENGTH_SHORT).show();
                    showAlert(two_player_names.p2Name + " wins! ");
                    updateWin(2);
                    oncewin = true;
                }
            }

            if (a[0][0]==a[1][0] && a[1][0]==a[2][0])
            {
                if (a[0][0]==1)
                {
                    //Toast.makeText(getContext(),"Player 1 wins! ",Toast.LENGTH_SHORT).show();
                    showAlert(two_player_names.p1Name + " wins! ");
                    updateWin(1);
                    oncewin = true;
                }
                else if(a[0][0]==2)
                {
                    //Toast.makeText(getContext(),"Player 2 wins! ",Toast.LENGTH_SHORT).show();
                    showAlert(two_player_names.p2Name + " wins! ");
                    updateWin(2);
                    oncewin = true;
                }
            }

            if (a[0][1]==a[1][1] && a[1][1]==a[2][1])
            {
                if (a[0][1]==1)
                {
                    //Toast.makeText(getContext(),"Player 1 wins! ",Toast.LENGTH_SHORT).show();
                    showAlert(two_player_names.p1Name + " wins! ");
                    updateWin(1);
                    oncewin = true;
                }
                else if(a[0][1]==2)
                {
                    //Toast.makeText(getContext(),"Player 2 wins! ",Toast.LENGTH_SHORT).show();
                    showAlert(two_player_names.p2Name + " wins! ");
                    updateWin(2);
                    oncewin = true;
                }
            }

            if (a[0][2]==a[1][2] && a[1][2]==a[2][2])
            {
                if (a[0][2]==1)
                {
                    //Toast.makeText(getContext(),"Player 1 wins! ",Toast.LENGTH_SHORT).show();
                    showAlert(two_player_names.p1Name + " wins! ");
                    updateWin(1);
                    oncewin = true;
                }
                else if(a[0][2]==2)
                {
                    //Toast.makeText(getContext(),"Player 2 wins! ",Toast.LENGTH_SHORT).show();
                    showAlert(two_player_names.p2Name + " wins! ");
                    updateWin(2);
                    oncewin = true;
                }
            }

            if (a[0][0]==a[1][1] && a[1][1]==a[2][2])
            {
                if (a[0][0]==1)
                {
                    //Toast.makeText(getContext(),"Player 1 wins! ",Toast.LENGTH_SHORT).show();
                    showAlert(two_player_names.p1Name + " wins! ");
                    updateWin(1);
                    oncewin = true;
                }
                else if(a[0][0]==2)
                {
                    //Toast.makeText(getContext(),"Player 2 wins! ",Toast.LENGTH_SHORT).show();
                    showAlert(two_player_names.p2Name + " wins! ");
                    updateWin(2);
                    oncewin = true;
                }
            }

            if (a[0][2]==a[1][1] && a[1][1]==a[2][0])
            {
                if (a[0][2]==1)
                {
                    //Toast.makeText(getContext(),"Player 1 wins! ",Toast.LENGTH_SHORT).show();
                    showAlert(two_player_names.p1Name + " wins! ");
                    updateWin(1);
                    oncewin = true;
                }
                else if(a[0][2]==2)
                {
                    //Toast.makeText(getContext(),"Player 2 wins! ",Toast.LENGTH_SHORT).show();
                    showAlert(two_player_names.p2Name + " wins! ");
                    updateWin(2);
                    oncewin = true;
                }
            }

            if (turn == 9 && !oncewin)
            {
                //Toast.makeText(getContext(),"Match results in a draw!",Toast.LENGTH_SHORT).show();
                showAlert("Match results in a draw!");
                updateWin(0);
            }
        }
    }
}