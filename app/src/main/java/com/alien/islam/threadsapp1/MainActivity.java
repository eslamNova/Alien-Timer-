package com.alien.islam.threadsapp1;

import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity {

    int des , sec , min ;
    TextView Des , Sec , Min , line1 , line2 ;
    LinearLayout Lmain , Lcounter , Lbutton ;
    Button b1 , b2 , b3 ;

    MyThread thread ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Des = (TextView) findViewById(R.id.decSec);
        Sec = (TextView) findViewById(R.id.sec);
        Min = (TextView) findViewById(R.id.min);
        line1 = (TextView) findViewById(R.id.line1);
        line2 = (TextView) findViewById(R.id.line2);
        Lmain = (LinearLayout) findViewById(R.id.mainLayout);
        Lcounter = (LinearLayout) findViewById(R.id.counterLayout);
        Lbutton = (LinearLayout) findViewById(R.id.buttonLayout);
        b1 = (Button) findViewById(R.id.START);
        b2 = (Button) findViewById(R.id.PAUSE);
        b3 = (Button) findViewById(R.id.RESET);

        des = sec = min = 0 ;
        thread = new  MyThread() ;



    }

    @Override
    protected void onPause() {
        super.onPause();
        thread.setCurrentState(MyThread.STATE_PAUSED);
    }


    public void click(View v){
        if(v.getId()==R.id.START){
            if(thread.getCurrentState()==MyThread.STATE_RUNNING) return;
            thread = new MyThread() ;
            thread.start();
        }
        else if (v.getId()==R.id.PAUSE){
            thread.setCurrentState(MyThread.STATE_PAUSED);
        }
        else if (v.getId()==R.id.RESET){
            if(thread.getCurrentState()==MyThread.STATE_RUNNING) thread.setCurrentState(MyThread.STATE_RESET);
            else{
                min = sec = des = 0 ;
                String textDes = String.format("%02d",des);
                String textSec = String.format("%02d",sec);
                String textMin = String.format("%02d",min);
                Des.setText(textDes);
                Sec.setText(textSec);
                Min.setText(textMin);
            }
        }
    }











    public class MyThread extends Thread {

        static final int STATE_RUNNING=0 , STATE_PAUSED=1, STATE_RESET=2 ;
        int state = -1 ;
        @Override
        public void run() {
            state = STATE_RUNNING ;
            while (true){
                if(des==9) {
                    des= -1 ;
                    if(sec==59){
                        sec=-1 ;
                        min++ ;
                    }
                    sec++ ;
                }
                des++ ;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //String text = String.format("%02d:%02d:%02d",minutes,seconds,decisecond);
                       // counterTextView.setText(text);
                        String textDes = String.format("%02d",des);
                        String textSec = String.format("%02d",sec);
                        String textMin = String.format("%02d",min);
                        Des.setText(textDes);
                        Sec.setText(textSec);
                        Min.setText(textMin);
                    }
                });
                if(state!=STATE_RUNNING) break;
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            if(state==STATE_RESET){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        des = sec = min = 0 ;
                        String textDes = String.format("%02d",des);
                        String textSec = String.format("%02d",sec);
                        String textMin = String.format("%02d",min);
                        Des.setText(textDes);
                        Sec.setText(textSec);
                        Min.setText(textMin);
                    }
                });
            }
        }
        public void setCurrentState(int state){
            this.state = state ;
        }

        public int getCurrentState() {
            return state;
        }

    }

    public void NightMode (View view){
        Lmain.setBackgroundResource(R.color.NightModeMain);
        Lcounter.setBackgroundResource(R.color.NightModeCounter);
        Lbutton.setBackgroundResource(R.color.NightModeMain);
        Des.setTextColor(getResources().getColor(R.color.RED));
        Sec.setTextColor(getResources().getColor(R.color.RED));
        Min.setTextColor(getResources().getColor(R.color.RED));
        b1.setTextColor(getResources().getColor(R.color.RED));
        b2.setTextColor(getResources().getColor(R.color.RED));
        b3.setTextColor(getResources().getColor(R.color.RED));
        b1.setBackgroundColor(getResources().getColor(R.color.NightModeCounter));
        b2.setBackgroundColor(getResources().getColor(R.color.NightModeCounter));
        b3.setBackgroundColor(getResources().getColor(R.color.NightModeCounter));
        line1.setBackgroundColor(getResources().getColor(R.color.RED));
        line2.setBackgroundColor(getResources().getColor(R.color.RED));


    }
}







