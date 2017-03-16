package br.com.paulomalem.calculoimc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();


        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Fonte01.ttf");
        TextView myTextView = (TextView) findViewById(R.id.textImc);
        myTextView.setTypeface(myTypeface);


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}