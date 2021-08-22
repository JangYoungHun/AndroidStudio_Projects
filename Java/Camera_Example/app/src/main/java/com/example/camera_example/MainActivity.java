package com.example.camera_example;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button camera_btn;
    ImageView imageView;
    static final int REQUEST_IMAGE_CAPTURE = 100;
    Uri photoPath;
    String imageFilePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        camera_btn = (Button)findViewById(R.id.camera_btn);
        imageView = (ImageView)findViewById(R.id.imageView);

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //권한 허용시
                Toast.makeText(getApplicationContext(), "권한 허용됨", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                //권한 거부시
                Toast.makeText(getApplicationContext(), "권한 거부됨", Toast.LENGTH_SHORT).show();

            }
        };


                //권한체크
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("권한이 필요합니다")
                .setDeniedMessage("일부 기능을 사용 할 수 없습니다.")
                .setPermissions(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE );


        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager()) != null){
                    File photofile = null;
                    try {
                        photofile = createImageFile();

                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    if(photofile !=null){
                        photoPath = FileProvider.getUriForFile(getApplicationContext(), getPackageName(),photofile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoPath);
                        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);

                    }
                }
            }
        });


    }

    File createImageFile() throws IOException{
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFilename = "TEST_"+ timestamp +"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFilename,".jpg",storageDir);
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode != REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(imageFilePath);
            }catch (IOException e){
                e.printStackTrace();
            }
            int exifOrientation;
            int exifDgree;
            if(exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDgree = exifOrientationToDegree(exifOrientation);
            }
            else{
                exifDgree = 0;
            }

            imageView.setImageBitmap(rotate(bitmap,exifDgree));


        }

        super.onActivityResult(requestCode,resultCode,data);

    }

    int exifOrientationToDegree(int exifOrientation){
        if(exifOrientation== ExifInterface.ORIENTATION_ROTATE_90)
            return 90;
        else if(exifOrientation== ExifInterface.ORIENTATION_ROTATE_180)
            return 180;
        else if(exifOrientation== ExifInterface.ORIENTATION_ROTATE_270)
            return 270;

        return 0;
    }

        Bitmap rotate(Bitmap bitmap, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(), matrix, true);
        }

}