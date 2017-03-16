package br.com.paulomalem.calculoimc;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class SobreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "Fonte01.ttf");
        TextView myTextView = (TextView) findViewById(R.id.textImc);
        myTextView.setTypeface(myTypeface);
    }
}

