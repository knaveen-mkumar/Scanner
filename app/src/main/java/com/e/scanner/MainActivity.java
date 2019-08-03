package com.e.scanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler , ActivityCompat.OnRequestPermissionsResultCallback{
    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    1);
        }
        else{
            mScannerView = new ZXingScannerView(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mScannerView != null){
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
            setContentView(mScannerView);
        }
    }

    @Override
    public void handleResult(Result result) {
        String id = result.getText();
        Log.e("ScanActivity","ID "+id);
        int product_id = Integer.parseInt(id.split(":")[0].trim());
        Toast.makeText(this, "data : "+product_id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mScannerView = new ZXingScannerView(this);

        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        setContentView(mScannerView);
    }
}
