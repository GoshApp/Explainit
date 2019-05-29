package com.explainitapp.explainit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{
    private static final int REQUEST_CAMERARESULT = 201;
    static boolean flag;
    // Object Detector
    static {
        if(!(OpenCVLoader.initDebug())){
            //Do Something
        }else {
            //close the application
            flag = true;
        }
    }
    JavaCameraView javaCameraView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        javaCameraView = (JavaCameraView) findViewById(R.id.camera_view);


        javaCameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                        ///method to get Images
                        showCamera();
                    }else{
                        if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                            Toast.makeText(MainActivity.this,"Your Permission is needed to get access the camera", Toast.LENGTH_LONG).show();
                        }
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CAMERARESULT);
                    }
                }else{
                    showCamera();
                }
            }
        });
    }

    public void showCamera(){
        if(flag){
            javaCameraView.setCameraIndex(0);
            javaCameraView.setCvCameraViewListener(this);
            javaCameraView.enableView();
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return inputFrame.gray(); // for
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(javaCameraView.isEnabled()){
            javaCameraView.disableView();
        }
    }
}
