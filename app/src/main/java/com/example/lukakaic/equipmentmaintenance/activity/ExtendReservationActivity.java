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

public class ExtendReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_reservation);

        Button extendReservationButton = (Button) findViewById(R.id.extendReservationButton);
        Button cancelReservationButton = (Button) findViewById(R.id.extendCancelButton);

        final EditText extendReturnDate = (EditText) findViewById(R.id.extendReturnDate);
        final EditText extendReason = (EditText) findViewById(R.id.extendReason);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String token = preferences.getString("key", "defaultValue");
        Integer userId = preferences.getInt("key1", 1);
        Log.d("token", token);
        Log.d("userId", userId.toString());

        final Integer reservationId = getIntent().getIntExtra("RESERVATION_ID", 1);
        Log.d("reservationId", reservationId.toString());

        final ArrayList<Integer> reservation_id1 = new ArrayList<>();
        reservation_id1.add(0, reservationId);

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        extendReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String extendReturnDateText = extendReturnDate.getText().toString();
                String extendReasonText = extendReason.getText().toString();

                Log.d("text", extendReturnDateText);
                Log.d("text", extendReasonText);
                Call<ResponseBody> call = apiService.extendReservation("Bearer " + token, extendReturnDateText, reservation_id1.toString(), extendReasonText);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Integer code = response.code();
                        Log.d("response", code.toString());
                        Context context = getApplicationContext();
                        String url = call.request().url().toString();
                        CharSequence text = "Extened request send";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        Intent intent = new Intent(ExtendReservationActivity.this, MainActivity.class);
                        ExtendReservationActivity.this.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });

        cancelReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseBody> callOne = apiService.cancelReservation("Bearer " + token, reservationId);

                callOne.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Integer code = response.code();
                        Log.d("response", code.toString());
                        Context context = getApplicationContext();
                        String url = call.request().url().toString();
                        CharSequence text = "Cancel request send";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        Intent intent = new Intent(ExtendReservationActivity.this, MainActivity.class);
                        ExtendReservationActivity.this.startActivity(intent);
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
                Intent myIntent = new Intent(ExtendReservationActivity.this, MainActivity.class);
                ExtendReservationActivity.this.startActivity(myIntent);
                finish();
            }
        });
    }
}
