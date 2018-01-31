package com.example.lukakaic.equipmentmaintenance.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lukakaic.equipmentmaintenance.R;
import com.example.lukakaic.equipmentmaintenance.model.Reservation;
import com.example.lukakaic.equipmentmaintenance.model.ReservationResponse;
import com.example.lukakaic.equipmentmaintenance.rest.ApiClient;
import com.example.lukakaic.equipmentmaintenance.rest.ApiInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryReservationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_reservations);

        ListView listView = (ListView) findViewById(R.id.historyList);
        final TextView editItemText = (TextView) findViewById(R.id.itemName) ;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString("key", "defaultValue");
        final Integer itemId = getIntent().getIntExtra("ID_VALUE", 1);
        Log.d("token", token);

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<ReservationResponse>> call = apiService.getReresrvationsOfItem("Bearer " + token, itemId.intValue());
        final ListView finalListView1 = listView;

        call.enqueue(new Callback<List<ReservationResponse>>() {
            @Override
            public void onResponse(Call<List<ReservationResponse>> call, Response<List<ReservationResponse>> response) {
                Integer code = response.code();
                List<List<ReservationResponse>> reservation = Arrays.asList(response.body());
                List<String> noReservations = Arrays.asList("History is empty");
                Integer size = reservation.size();
                Log.d("code ", code.toString());
                editItemText.setText(reservation.get(0).get(0).getDescription());
                if(reservation.get(0).get(0).getReservations().isEmpty()) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(HistoryReservationsActivity.this,
                            android.R.layout.simple_list_item_1, android.R.id.text1, noReservations);
                    finalListView1.setAdapter(adapter);

                } else{
                    ArrayList<Reservation> itemsSorted = new ArrayList<>();
                    ArrayList<String> reservationsList = new ArrayList<String>();
                    for(int i=0; i < reservation.size(); i++) {
                        for(int j=0; j < reservation.get(i).size(); j++){
                            for(int k=0; k<reservation.get(i).get(j).getReservations().size(); k++){
                                itemsSorted.add(reservation.get(i).get(j).getReservations().get(k));
                            }
                        }

                    }
                    for(int i=0; i < itemsSorted.size(); i++) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(itemsSorted.get(i).getStartDate() + "           " + itemsSorted.get(i).getUser().getFirstName() +  " " + itemsSorted.get(i).getUser().getLastName() + "        " + itemsSorted.get(i).getReturnDate());
                        reservationsList.add(stringBuilder.toString());
                    }
                    Integer size1 = itemsSorted.size();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(HistoryReservationsActivity.this,
                            android.R.layout.simple_list_item_1, android.R.id.text1, reservationsList);
                    finalListView1.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ReservationResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(HistoryReservationsActivity.this, MainActivity.class);
                HistoryReservationsActivity.this.startActivity(myIntent);
                finish();
            }
        });

    }


}
