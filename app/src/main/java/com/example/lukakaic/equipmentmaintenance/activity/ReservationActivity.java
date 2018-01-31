package com.example.lukakaic.equipmentmaintenance.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lukakaic.equipmentmaintenance.R;
import com.example.lukakaic.equipmentmaintenance.model.Item;
import com.example.lukakaic.equipmentmaintenance.model.UserReservationsResponse;
import com.example.lukakaic.equipmentmaintenance.rest.ApiClient;
import com.example.lukakaic.equipmentmaintenance.rest.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservations);

        ListView listView = (ListView) findViewById(R.id.userReservationsList);
        final String reservationId;
        final HashMap<Integer, Integer> reservationMap = new HashMap<Integer, Integer>();
        final HashMap<Integer, Integer> statusMap = new HashMap<Integer, Integer>();

        final Integer[] flag = {0};

        //Dohvacanje tokena
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString("key", "defaultValue");
        Integer userId = preferences.getInt("key1", 1);
        Log.d("token", token);
        Log.d("token", userId.toString());

        //poziv rute i predaja tokena u headeru
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Item>> call = apiService.getReservations("Bearer " + token);
        Call<List<UserReservationsResponse>> callTwo = apiService.getReservationsOfUser("Bearer " + token, userId);
        Log.d("userId", userId.toString());
        final ListView finalListView1 = listView;

        callTwo.enqueue(new Callback<List<UserReservationsResponse>>() {
            @Override
            public void onResponse(Call<List<UserReservationsResponse>> call, Response<List<UserReservationsResponse>> response) {
                Integer code = response.code();
                Log.d("code", code.toString());
                ArrayList<String> sbArray = new ArrayList<String>();
                List<UserReservationsResponse> items = response.body();
                ArrayList<String> itemsSorted = new ArrayList<String>();
                String[] empty = new String[]{"No active reservations!"};
                int counter = 1;
                for(int i=0; i < items.size(); i++) {
                    for(int j=0; j<items.get(i).getItems().size(); j++){
                        String status;
                        status = items.get(i).getStatus().getName();
                        StringBuilder sb = new StringBuilder();
                        if(status.equals("Zahtijev poslan")){
                            sb.append(items.get(i).getStartDate() + "      " + items.get(i).getReturnDate() + "        " + items.get(i).getItems().get(j).getItem().getIdentifier() + "        " + "Pending");
                            sbArray.add(0, sb.toString());
                            statusMap.put(counter, 1);
                        } else if(status.equals("Odobrena")) {
                            sb.append(items.get(i).getStartDate() + "      " + items.get(i).getReturnDate() + "        " + items.get(i).getItems().get(j).getItem().getIdentifier() + "        " + "Granted" );
                            sbArray.add(0, sb.toString());
                            statusMap.put(counter, 1);
                        } else if(status.equals("Otkazana")){
                            sb.append(items.get(i).getStartDate() + "      " + items.get(i).getReturnDate() + "        " + items.get(i).getItems().get(j).getItem().getIdentifier() + "        " + "Canceled");
                            sbArray.add(0, sb.toString());
                            statusMap.put(counter, 0);
                        } else {
                            sb.append(items.get(i).getStartDate() + "      " + items.get(i).getReturnDate() + "        " + items.get(i).getItems().get(j).getItem().getIdentifier() + "        " + "Rejected");
                            sbArray.add(0, sb.toString());
                            statusMap.put(counter, 0);
                        }
                        reservationMap.put(counter, items.get(i).getItems().get(j).getReservationId());
                        counter++;
                    }
                }
                Log.d("reservations", itemsSorted.toString());

                if(items.isEmpty()){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReservationActivity.this,
                            android.R.layout.simple_list_item_1, android.R.id.text1, empty);
                    finalListView1.setAdapter(adapter);
                } else{
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReservationActivity.this,
                            android.R.layout.simple_list_item_1, android.R.id.text1, sbArray);
                    finalListView1.setAdapter(adapter);
                }



            }

            @Override
            public void onFailure(Call<List<UserReservationsResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });



        final ListView finalListView = listView;
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                    Integer itemPosition = position + 1;
                    Integer itemPositionReverse = statusMap.size() - position;

                    // ListView Clicked item value
                    String  itemValue = (String) finalListView.getItemAtPosition(position);


                    Log.d("List", statusMap.get(itemPosition).toString());
                    Log.d("Position", itemPosition.toString());
                    if(statusMap.get(itemPositionReverse) == 1) {
                        Intent intent = new Intent(ReservationActivity.this, ExtendReservationActivity.class);
                        intent.putExtra("EXTRA_SESSION_ID", itemValue);
                        intent.putExtra("RESERVATION_ID", reservationMap.get(itemPosition));
                        ReservationActivity.this.startActivity(intent);
                    }

                }

            });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(ReservationActivity.this, MainActivity.class);
                ReservationActivity.this.startActivity(myIntent);
                finish();
            }
        });
    }


}
