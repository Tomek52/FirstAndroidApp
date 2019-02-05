package com.example.tomeksz.spiewnik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AddActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT_TITLE = "com.example.tomeksz.spiewnik.EXTRA_TEXT_TITLE";
    public static final String EXTRA_TEXT_AUTHOR = "com.example.tomeksz.spiewnik.EXTRA_TEXT_AUTHOR";
    public static final String EXTRA_TEXT_LYRICS = "com.example.tomeksz.spiewnik.EXTRA_TEXT_LYRICS";
    public static final String EXTRA_TEXT_LEVEL = "com.example.tomeksz.spiewnik.EXTRA_TEXT_LEVEL";

    private Button btnSaveSong;
    private EditText txtTitle, txtAuthor, txtLyrics, txtLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnSaveSong = (Button) findViewById(R.id.btnSave);


        btnSaveSong.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }

    public void openMainActivity(){
        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtAuthor = (EditText) findViewById(R.id.txtAuthor);
        txtLyrics = (EditText) findViewById(R.id.txtLyrics);
        txtLevel = (EditText) findViewById(R.id.txtLevel);

        String title = txtTitle.getText().toString();
        String author = txtAuthor.getText().toString();
        String lyrics = txtLyrics.getText().toString();
        String level = txtLevel.getText().toString();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_TEXT_TITLE, title);
        intent.putExtra(EXTRA_TEXT_AUTHOR, author);
        intent.putExtra(EXTRA_TEXT_LYRICS, lyrics);
        intent.putExtra(EXTRA_TEXT_LEVEL, level);

        startActivity(intent);
    }

}
