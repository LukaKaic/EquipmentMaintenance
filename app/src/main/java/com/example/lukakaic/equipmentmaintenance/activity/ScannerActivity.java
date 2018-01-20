package com.example.lukakaic.equipmentmaintenance.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.example.lukakaic.equipmentmaintenance.R;
import com.example.lukakaic.equipmentmaintenance.model.Item;
import com.example.lukakaic.equipmentmaintenance.rest.ApiClient;
import com.example.lukakaic.equipmentmaintenance.rest.ApiInterface;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerActivity extends AppCompatActivity {

    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);


        cameraView = (SurfaceView) findViewById(R.id.surfafce_view);
        textView = (TextView) findViewById(R.id.text_view);

        final TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        final ArrayList<String> itemsSorted = new ArrayList<String>();
        final int flag = 0;
        final HashMap<String, Integer> itemMap = new HashMap<String, Integer>();


        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString("key", "defaultValue");
        Log.d("token", token);
        Call<List<Item>> call = apiService.getItems("Bearer " + token);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                Integer code = response.code();
                Log.d("Code", code.toString());
                List<Item> items = response.body();

                for(int i=0; i < items.size(); i++) {
                    itemsSorted.add(items.get(i).getIdentifier());
                }
                Log.d("sorted", itemsSorted.toString());

                for (int i = 0 ; i < itemsSorted.size(); i++) {
                    itemMap.put(itemsSorted.get(i), i + 1);
                }
                Log.d("proba", itemMap.toString());
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detector dependecies are not yet available");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ScannerActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });
        }

        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                final SparseArray<TextBlock> items = detections.getDetectedItems();
                if (items.size() != 0) {
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            StringBuilder stringBuilder = new StringBuilder();
                            StringBuffer stringBuffer = new StringBuffer();
                            for (int i = 0; i < items.size(); i++) {
                                TextBlock item = items.valueAt(i);
                                stringBuilder.append(item.getValue());
                                stringBuffer.append(item.getValue());
                            }
                            checkIdentifier(stringBuffer, itemMap);
                            textView.setText(stringBuilder.toString());
                        }
                    });
                }
            }
        });
    }

    public void checkIdentifier(StringBuffer stringBuffer, HashMap<String, Integer> itemMap) {
        int flag = 1;
        if(itemMap.containsKey(stringBuffer.toString())){
            Intent intent = new Intent(ScannerActivity.this, ItemActivity.class);
            intent.putExtra("EXTRA_SESSION_ID",  stringBuffer.toString());
            intent.putExtra("ID_VALUE", itemMap.get(stringBuffer.toString()));
            ScannerActivity.this.startActivity(intent);
            finish();
        }
    }
}
