package com.example.contactlist_practice;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView textview;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK) {
                        Uri uri = result.getData().getData();
                        String id = uri.getLastPathSegment();
                        getContacts(id);
                    }
                }
            });

    private void getContacts(String id) {
        Cursor cursor = null;
        String name = "";
        try{
            cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.CONTACT_ID + "= ?",
                    new String[]{id},
                    null);

            if(cursor.moveToFirst()){
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                println("Name : " + name);
                String columns[] = cursor.getColumnNames();

                for(String str : columns){
                    int index = cursor.getColumnIndex(str);
                    String columnOutput = ("#" + index + " -> " + "[" + columns +"]" + cursor.getString(index));
                    println(columnOutput);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //위험권한 요청
        permissionsRequest();

        textview = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectContacts();
            }
        });

    }

    private void permissionsRequest() {
        String permissions[] = {Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS };
        List<String> requestPermission = new ArrayList<>();

        for(String p :permissions) {
            int check = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if(check == PackageManager.PERMISSION_DENIED){
                if(!ActivityCompat.shouldShowRequestPermissionRationale(this, p)){
                   requestPermission.add(p);
                }
                else{
                    showToast("권한이 필수적입니다.");
                }
            }

        }

        if(requestPermission.size() >0) {
            ActivityCompat.requestPermissions(this, requestPermission.toArray(new String[requestPermission.size()]), 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case 101 :
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    showToast("권한 승인");
                }
                else{
                    showToast("권한 거부");
                }
                break;
        }
        return;



    }

    private void selectContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        launcher.launch(intent);
    }


    public void println(String data){
        textview.append(data +"\n");
    }

    public void showToast(String data){
        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
    }

}