package com.example.contentprovider_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPerson();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryPerson();
            }
        });
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePerson();
            }
        });
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePerson();
            }
        });

    }

    private void deletePerson() {
        String uriStr = "content://com.example.contentprovider_practice/person";
        Uri uri = new Uri.Builder().build().parse(uriStr);

        String selection = "name = ?";
        String selectionArgs[] = new String[]{"john"};

        int count = getContentResolver().delete(uri,selection,selectionArgs);
        println("delete 결과" + count);
    }

    private void updatePerson() {
        String uriStr = "content://com.example.contentprovider_practice/person";
        Uri uri = new Uri.Builder().build().parse(uriStr);

        String selection = "mobile = ?";
        String selectionArgs[] = new String[]{"010-1234-1234"};
        ContentValues updateValues = new ContentValues();
        updateValues.put("mobile", "010-2222-3333");
        int count = getContentResolver().update(uri, updateValues,selection,selectionArgs);
        println("update 결과" + count);
    }

    private void queryPerson() {
        try {
            String uriStr = "content://com.example.contentprovider_practice/person";
            Uri uri = new Uri.Builder().build().parse(uriStr);
            String[] colums = new String[]{"name", "age", "mobile"};
            Cursor cursor = getContentResolver().query(uri, colums, null, null, "name ASC");
            println("query 결과 : " + cursor.getCount());

            int index = 0;
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(colums[0]));
                int age = cursor.getInt(cursor.getColumnIndex(colums[1]));
                String mobile = cursor.getString(cursor.getColumnIndex(colums[2]));

                println("#" + index + " -> " + name + ", " + age + ", " + mobile);
                index += 1;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void insertPerson() {
        println("insertPerson 호출");
        String uriStr = "content://com.example.contentprovider_practice/person";
        Uri uri = new Uri.Builder().build().parse(uriStr);
        Cursor cursor = getContentResolver().query(uri, null,null,null,null);
        String[] columns = cursor.getColumnNames();
        for(int i =0; i<columns.length; i++){
            println("#" + i + " : " + columns[i]);
        }

        ContentValues values = new ContentValues();
        values.put("name", "john");
        values.put("age", "20");
        values.put("mobile", "010-1234-1234");

        uri = getContentResolver().insert(uri,values);
        println("insert 결과 : " + uri.toString());
    }


    void println(String log){
        textView.append(log + "\n");
    }
}