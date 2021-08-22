package com.example.album_practice;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.security.Permission;

public class MainActivity extends AppCompatActivity  {

    ImageView imageView;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        showToast("Result_OK");

                        Uri uri = result.getData().getData();
                        ContentResolver resolver = getContentResolver();
                        try {
                            InputStream inputStream = resolver.openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            imageView.setImageBitmap(bitmap);
                            inputStream.close();

                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] permissions ={Manifest.permission.READ_EXTERNAL_STORAGE};
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
           if(!ActivityCompat.shouldShowRequestPermissionRationale(this,  Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(this, permissions,101);
           }
        }

        imageView= findViewById(R.id.imageView);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getImage();
            }
        });



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


    private void getImage() {
        ContentResolver resolver = getContentResolver();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }

    public void showToast(String data){
        Toast.makeText(getApplicationContext(), data,Toast.LENGTH_SHORT).show();
    }

}