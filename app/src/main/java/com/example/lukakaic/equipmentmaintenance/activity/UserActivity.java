package com.example.lukakaic.equipmentmaintenance.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lukakaic.equipmentmaintenance.R;
import com.example.lukakaic.equipmentmaintenance.model.User;
import com.example.lukakaic.equipmentmaintenance.rest.ApiClient;
import com.example.lukakaic.equipmentmaintenance.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString("key", "defaultValue");
        Log.d("token", token);


        final TextView userName = (TextView) findViewById(R.id.userName);
        final TextView userEmail = (TextView) findViewById(R.id.userEmail);
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Button reservationsButton = (Button) findViewById(R.id.myReservationsButton);

        Call<User> call = apiService.getUser("bearer" + token);


        Button logout = (Button) findViewById(R.id.logoutButton);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("key", null);
                editor.apply();
                Intent myIntent = new Intent(UserActivity.this, LoginActivity.class);
                UserActivity.this.startActivity(myIntent);
            }
        });


        reservationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(UserActivity.this, ReservationActivity.class);
                UserActivity.this.startActivity(myIntent);
            }
        });


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Integer code = response.code();
                User user = response.body();
                userName.setText(user.getFirstName() + " " + user.getLastName());
                userEmail.setText(user.getEmail());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(UserActivity.this, MainActivity.class);
                UserActivity.this.startActivity(myIntent);
            }
        });

    }
}
