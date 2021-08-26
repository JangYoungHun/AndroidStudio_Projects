package com.example.capture_intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.security.Permission;
import java.security.Permissions;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    File file;

    Uri fileUri;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        showToast("Camera Result_OK");

                        try {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 6;
                            Bitmap img = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                            imageView.setImageBitmap(img);
                        }
                        catch (Exception e){
                            showToast("Uri 로부터 이미지 로드 에러");

                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> requests = new ArrayList<>();
        String permissions[] = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
        for(String permission : permissions){
       if(ContextCompat.checkSelfPermission(getApplicationContext(),permission) == PackageManager.PERMISSION_DENIED){
            if(!ActivityCompat.shouldShowRequestPermissionRationale(this,permission)){
                requests.add(permission);
            }
        }
        }
        if(requests != null && requests.size() >0)
            ActivityCompat.requestPermissions(this,requests.toArray(new String[requests.size()]),100);





        imageView = findViewById(R.id.imageView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }

    private void takePicture() {
        file = createFile();
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        }
        catch (Exception e){

        }

        if(Build.VERSION.SDK_INT >= 24){
            fileUri = FileProvider.getUriForFile(this,BuildConfig.APPLICATION_ID,file);
        }
        else{
            Uri.fromFile(file);
        }
        Log.d("Main", "File uri : " + fileUri);
        showToast("File uri : " + fileUri);


        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        launcher.launch(cameraIntent);
    }

    private File createFile() {
        String fileName = "capture.jpg";
        Log.d("Main", "getFilesDir(): " + getFilesDir());
        File outFile = new File(getFilesDir(),fileName);
        Log.d("Main", "File path : " + outFile.getAbsolutePath());
        showToast("File path : " + outFile.getAbsolutePath());
        return outFile;
    }


    void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}