package com.example.mediastore_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ContentResolver resolver;
    Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        resolver = getContentResolver();
        // saveFile("TEST SaveFile Using MediaStore!!!!!!");
        readFile();
    }


    public void saveFile(String writeString) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, "test1.txt");
        values.put(MediaStore.Downloads.MIME_TYPE, "text/*");
        if (Build.VERSION.SDK_INT > -Build.VERSION_CODES.Q) {
            values.put(MediaStore.Downloads.IS_PENDING, 1);
        }

        fileUri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);

        try {
            ParcelFileDescriptor pfd = resolver.openFileDescriptor(fileUri, "w", null);
            if (pfd == null) {
                showToast("pfd null 오류");
            } else {
                FileOutputStream fo = new FileOutputStream(pfd.getFileDescriptor());
                fo.write(writeString.getBytes());
                fo.close();
                if (Build.VERSION.SDK_INT > -Build.VERSION_CODES.Q) {
                    values.clear();
                    values.put(MediaStore.Downloads.IS_PENDING, 0);
                    resolver.update(fileUri, values, null, null);
                }
            }

        } catch (Exception e) {

        }

    }


    void readFile() {
        String[] columns = new String[]{
                MediaStore.Downloads._ID,
                MediaStore.Downloads.DISPLAY_NAME,
                MediaStore.Downloads.MIME_TYPE
        };

        Cursor cursor = resolver.query(MediaStore.Downloads.EXTERNAL_CONTENT_URI, columns, null, null, null);

        int id = -1;
        if (cursor != null && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                if (cursor.getString(cursor.getColumnIndex(MediaStore.Downloads.DISPLAY_NAME)).equals("test1.txt") ) {
                    id = cursor.getPosition();
                    break;
                }
                cursor.moveToNext();
            }

            if(id == -1){
                showToast("해당 파일 없음");
                return;
            }

            String uri = MediaStore.Downloads.EXTERNAL_CONTENT_URI.toString() + "/" + cursor.getString(0);
            showToast(uri);
            try {
                InputStream is = resolver.openInputStream(Uri.parse(uri));
                if(is != null){
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String str;
                    while((str = br.readLine())!= null){
                        showToast(str);
                        textView.append(str);
                    }
                    br.close();
                    is.close();
                }
                else
                    showToast("InputStrean null 오류");


            } catch (Exception e) {

            }


        } else {
            showToast("cursor null 에러");
        }
    }


    void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}