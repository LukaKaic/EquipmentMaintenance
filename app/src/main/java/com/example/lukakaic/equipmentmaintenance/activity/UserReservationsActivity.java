package com.example.lukakaic.equipmentmaintenance.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.lukakaic.equipmentmaintenance.R;
import com.example.lukakaic.equipmentmaintenance.rest.ApiClient;
import com.example.lukakaic.equipmentmaintenance.rest.ApiInterface;

public class UserReservationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservations);

        ListView listView = (ListView) findViewById(R.id.userReservationsList);

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //Call<List<Item>> call = apiService.getItems("Bearer " + token);
    }
}
