package com.example.tomeksz.spiewnik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ShowActivity extends AppCompatActivity {

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Intent intent = getIntent();
        String lyrics = intent.getStringExtra(MainActivity.EXTRA_TEXT_LYRICS2);

        txt = (TextView) findViewById(R.id.txt);

        txt.setText(lyrics);
    }
}
