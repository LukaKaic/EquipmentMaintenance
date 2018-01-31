package com.example.lukakaic.equipmentmaintenance.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lukakaic.equipmentmaintenance.R;
import com.example.lukakaic.equipmentmaintenance.rest.ApiClient;
import com.example.lukakaic.equipmentmaintenance.rest.ApiInterface;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_item);

        final EditText reservationStart = (EditText) findViewById(R.id.reservationStartDate);
        final EditText reservationEnd = (EditText) findViewById(R.id.reservationReturnDate);
        final EditText reservationRemark = (EditText) findViewById(R.id.reservationRemark);
        final Button reservationButton = (Button) findViewById(R.id.reservetionButton1);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String token = preferences.getString("key", "defaultValue");
        final String itemValue = getIntent().getStringExtra("EXTRA_SESSION_ID");
        final Integer itemId = getIntent().getIntExtra("ID_VALUE", 1);
        Log.d("token", token);
        Log.d("itemId", itemId.toString());

        final ArrayList<Integer> item_id1 = new ArrayList<>();
        item_id1.add(0, itemId);

        Log.d("item_id", item_id1.toString());

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String reservationStartText = reservationStart.getText().toString();
                String reservationEndText = reservationEnd.getText().toString().trim();
                String reservationRemarkText = reservationRemark.getText().toString().trim();
                Log.d("reservation", reservationStartText);
                Log.d("reservation", reservationEndText);
                Log.d("reservation", reservationRemarkText);
                final Call<ResponseBody> call = apiService.reservation("Bearer " + token, reservationStartText, reservationEndText,  item_id1.toString(), reservationRemarkText);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Context context = getApplicationContext();
                        CharSequence text = "Reservation in progress";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        Intent myIntent = new Intent(ReservationItemActivity.this, MainActivity.class);
                        ReservationItemActivity.this.startActivity(myIntent);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(ReservationItemActivity.this, MainActivity.class);
                ReservationItemActivity.this.startActivity(myIntent);
                finish();
            }
        });
    }
}
