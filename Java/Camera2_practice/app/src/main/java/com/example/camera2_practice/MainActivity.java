package com.example.camera2_practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    CameraManager cameraManager;
    TextureView textureView;
    int cameraOrientaton;
    CameraCharacteristics characteristics;
    String cameraId;
    CameraDevice cameraDevice;
    CameraCaptureSession cameraCaptureSession;
    CaptureRequest.Builder captureRequest;

    int previewWidth;
    int previewHeight;
    TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {

        }

        @Override
        public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
        }


        textureView = findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(surfaceTextureListener);


        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        cameraId = getCameraId();


        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cameraDevice != null)
                    cameraDevice.close();
                cameraId = "0";
                openCamera();
            }
        });
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cameraDevice != null)
                    cameraDevice.close();
                cameraId = "1";
                openCamera();
            }
        });
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cameraDevice != null)
                    cameraDevice.close();
                cameraId = "2";
                openCamera();
            }
        });
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cameraDevice != null)
                    cameraDevice.close();
                cameraId = "3";
                openCamera();
            }
        });


    }


    void openCamera(){
        if (cameraId != null && !cameraId.equals("")) {
        try {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                characteristics = cameraManager.getCameraCharacteristics(cameraId);
                StreamConfigurationMap map = characteristics.get(
                        CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                Size[] jpegSizes = null;
                if(map != null){
                    jpegSizes = map.getInputSizes(ImageFormat.JPEG);
                }

                if (jpegSizes != null && 0 < jpegSizes.length) {
                    previewWidth = jpegSizes[0].getWidth();
                    previewHeight= jpegSizes[0].getHeight();
                }

                CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
                    @Override
                    public void onOpened(@NonNull CameraDevice camera) {
                        showToast("StateCallback onOpened");
                        cameraDevice = camera;
                        startPreview();
                    }

                    @Override
                    public void onDisconnected(@NonNull CameraDevice camera) {
                        showToast("StateCallback onDisconnected");
                        cameraDevice.close();
                        cameraDevice = null;
                    }

                    @Override
                    public void onError(@NonNull CameraDevice camera, int error) {
                        showToast("StateCallback onError");
                        cameraDevice.close();
                        cameraDevice = null;
                    }
                };
                cameraManager.openCamera(cameraId, stateCallback, null);

            }
            else{
                showToast("Camera 권한 없음");
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        }
    }


    private void startPreview() {
        showToast("startPreview");
        try {

            SurfaceTexture texture = textureView.getSurfaceTexture();
            texture.setDefaultBufferSize(previewWidth,previewHeight);
            Surface surface = new Surface(texture);


            captureRequest = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequest.addTarget(surface);




            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    cameraCaptureSession = session;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Log.e("error", "MMM onConfigureFailed");
                }
            }, null);

        }
        catch (Exception e){
            e.printStackTrace();
            showToast("startPreview() 오류");
        }
    }

    private void updatePreview()  {
        try {
            cameraCaptureSession.setRepeatingRequest(captureRequest.build(),null,null);
        }
        catch (Exception e){
                showToast("updatePreview()  오류");
                Log.d("error" , e.getMessage());
        }
    }




    private String getCameraId() {
        try {
            String[] cameraIds = cameraManager.getCameraIdList();

            for(String id : cameraIds){
                characteristics = cameraManager.getCameraCharacteristics(id);
                cameraOrientaton = characteristics.get(CameraCharacteristics.LENS_FACING);

               if(cameraOrientaton == CameraCharacteristics.LENS_FACING_BACK){
                  return id;
                }
            }

        }

        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}