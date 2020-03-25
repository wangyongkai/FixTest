package com.ljj.fixtest;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getResources().getColor()
        //getAssets()

        final TextView textView = (TextView) findViewById(R.id.mytv);
        textView.setText(new Test().showText());

    }
}
