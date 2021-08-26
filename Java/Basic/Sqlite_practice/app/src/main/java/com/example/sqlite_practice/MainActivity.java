package com.example.sqlite_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    TextView logText;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logText = findViewById(R.id.logText);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                println("Database 생성 중...");
                String dataBaseName = editText1.getText().toString();
                if(dataBaseName == null || dataBaseName.equals(""))
                    println("Database 이름을 입력해주세요");
                else
                    createDataBase(dataBaseName);

            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                println("Table 생성 중...");
                String tableName = editText2.getText().toString();

                if(tableName == null || tableName.equals(""))
                    println("Table 이름을 입력해주세요");

                else
                    createTable(tableName);

            }
        });


    }

    private void createTable(String tableName) {
        if(database==null){
            println("데이터베이스를 생성 해주세요");
            return;
        }

        String query = "create table if not exists " + tableName + " (_id integer PRIMARY KEY autoincrement, name text, age integer, mobile text)";

        database.execSQL(query);
        println("Table 생성 됨");
    }

    private void createDataBase(String dataBaseName) {
        database = openOrCreateDatabase(dataBaseName,MODE_PRIVATE,null);
        println("Database 생성 됨");
    }

    void println(String log){
        logText.append(log + "\n");
    }


}