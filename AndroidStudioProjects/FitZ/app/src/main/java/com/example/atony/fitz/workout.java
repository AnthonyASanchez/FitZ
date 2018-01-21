package com.example.atony.fitz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Handler;


public class workout extends AppCompatActivity {

    private boolean on = false;
    private String finalTime;
    private TextView textViewStopWatch;
    private Button buttonStart;
    private Button buttonStop;
    private long seconds = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);
        textViewStopWatch = (TextView) findViewById(R.id.textViewStopWatch);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                on = true;
                runTimer();
            }
        });
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                on = false;
                stopTimer();
                Bundle b  = new Bundle();
                b.putString("time",finalTime);
                finish();
                Intent intent = new Intent(workout.this, posting.class);
                intent.putExtras(b);
                startActivity(intent);

            }
        });



    }

    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int min = (int)seconds / 60;
                int sec = (int)seconds % 60;
                textViewStopWatch.setText(String.format("%02d:%02d",min,sec));
                if(on) {
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }
    private void stopTimer(){
        finalTime = textViewStopWatch.getText().toString();
    }
}
