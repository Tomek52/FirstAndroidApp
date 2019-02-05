package com.example.tomeksz.spiewnik;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT_LYRICS2 = "com.example.tomeksz.spiewnik.EXTRA_TEXT_LYRICS2";

    public DatabaseHelper myDb;

    private Button btnAddSong, btnSearch, btnInfo, btnDelete, btnShowTxt;
    private EditText txtSearchTitle, txtSearchAuthor, txtSearchLevel;
    private Spinner spinner;

    private String selectedSong;
    String oldTitle = "asdasd";

    String[] elements = {""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String title = intent.getStringExtra(AddActivity.EXTRA_TEXT_TITLE);
        String author = intent.getStringExtra(AddActivity.EXTRA_TEXT_AUTHOR);
        String lyrics = intent.getStringExtra(AddActivity.EXTRA_TEXT_LYRICS);
        String level = intent.getStringExtra(AddActivity.EXTRA_TEXT_LEVEL);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elements);
        final List<String> songs = new ArrayList<>();


        myDb = new DatabaseHelper(this);
        myDb.getWritableDatabase();

        btnAddSong = (Button) findViewById(R.id.btnAdd);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnInfo = (Button) findViewById(R.id.btnInfo);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnShowTxt = (Button) findViewById(R.id.btnShowTxt);
        txtSearchTitle = (EditText) findViewById(R.id.txtSearchTitle);
        txtSearchAuthor = (EditText) findViewById(R.id.txtSearchAuthor);
        txtSearchLevel = (EditText) findViewById(R.id.txtSearchLevel);
        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setAdapter(adapter);

        if(title != oldTitle){
            oldTitle=title;
            if(myDb.insertData(title, author, level, lyrics)){
                //Toast.makeText(MainActivity.this, "Dodano utwór", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "Nie dodano", Toast.LENGTH_SHORT).show();
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int id, long position) {
                String label = arg0.getItemAtPosition(id).toString();

                if(label.contains(" ")){
                    label = label.substring(0, label.indexOf(" "));
                    selectedSong = label;
                    Toast.makeText(MainActivity.this, "Wybrano", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // ta metoda wykonuje sie gdy lista zostanie wybrana, ale nie zostanie wybrany żaden element z listy
            }
        });

        btnDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer deleted = myDb.deleteData(selectedSong);

                if(deleted>0){
                    Toast.makeText(MainActivity.this, "Usunięto", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnShowTxt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Cursor cur = myDb.getLyrics(selectedSong);
                if(cur.getCount() == 0){
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
                else{

                    StringBuffer buffer =  new StringBuffer();
                    while(cur.moveToNext()) {
                        buffer.append(cur.getString(1) + "\n");
                        buffer.append(cur.getString(2) + "\n\n");
                        buffer.append(cur.getString(4)+"\n");
                    }
                    openShowActivity(buffer.toString());
                }

            }
        });

        btnAddSong.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                openAddActivity();
            }
        });

        btnInfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String txt = "Aplikacja powstała w ramach projektu na przedmiot Programowanie systemów mobilnych\n\nAutor: Tomasz Szpytma";
                showMessage("Info", txt);
            }
        });

        btnSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                //Cursor cur = myDb.getAllData();
                Cursor cur = myDb.getData(txtSearchTitle.getText().toString(),txtSearchAuthor.getText().toString(),txtSearchLevel.getText().toString());
                songs.clear();
                if(cur.getCount() == 0){
                    Toast.makeText(MainActivity.this, "Nie znaleziono", Toast.LENGTH_SHORT).show();
                }
                else{

                    StringBuffer buffer =  new StringBuffer();
                    while(cur.moveToNext()) {
                        buffer.append("ID: " + cur.getString(0) + "\n");
                        buffer.append("TITLE: " + cur.getString(1) + "\n");
                        buffer.append("AUTHOR: " + cur.getString(2) + "\n");
                        buffer.append("LEVEL: " + cur.getString(3) + "\n");
                        buffer.append("LYRICS: " + cur.getString(4)+"\n");

                        songs.add(cur.getString(0) + " " + cur.getString(1)+ " " + cur.getString(2) + " " +cur.getString(3));
                    }

                    loadSpinnerData(songs);
                    Toast.makeText(MainActivity.this, "Znaleziono", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void openAddActivity(){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void openShowActivity(String txt){
        Intent intent = new Intent(this, ShowActivity.class);
        intent.putExtra(EXTRA_TEXT_LYRICS2, txt);
        startActivity(intent);
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void loadSpinnerData(List<String> songs){
        List<String> labels = songs;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
}





