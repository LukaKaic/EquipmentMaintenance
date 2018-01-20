package com.example.lukakaic.equipmentmaintenance.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lukakaic.equipmentmaintenance.R;
import com.example.lukakaic.equipmentmaintenance.model.Item;
import com.example.lukakaic.equipmentmaintenance.rest.ApiClient;
import com.example.lukakaic.equipmentmaintenance.rest.ApiInterface;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        final TextView identifierText = (TextView) findViewById(R.id.itemIdentifierText);
        final TextView descriptionText = (TextView) findViewById(R.id.itemDescriptionText);
        final TextView typeText = (TextView) findViewById(R.id.deviceTypeItemText);
        final TextView kitTypeText = (TextView) findViewById(R.id.kitTypeText);
        final TextView reservationText = (TextView) findViewById(R.id.reservationText);
        Button reservationHistoryButton = (Button) findViewById(R.id.historyReservationsButton);
        final Button reservationItemButton = (Button) findViewById(R.id.reservetionButton);


        final String itemValue = getIntent().getStringExtra("EXTRA_SESSION_ID");
        final Integer itemId = getIntent().getIntExtra("ID_VALUE", 1);
        Log.d("itemValue", itemValue);
        Log.d("itemId", itemId.toString());
        final String[] itemsId = new String[]{itemId.toString()};
        Log.d("itemsId", itemsId[0].toString());

        reservationHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemActivity.this, HistoryReservationsActivity.class);
                intent.putExtra("ID_VALUE", itemId);
                ItemActivity.this.startActivity(intent);
            }
        });
        reservationItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemActivity.this, ReservationItemActivity.class);
                intent.putExtra("ID_VALUE", itemId);
                ItemActivity.this.startActivity(intent);
            }
        });


        //Uzimanje tokena
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString("key", "defaultValue");
        Log.d("token", token);

        //poziv rute i predaja tokena u headeru
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Item>> call = apiService.getItems("Bearer " + token);
        final Call<ResponseBody> callOne = apiService.getItemReservationInfo("Bearer " + token, itemId);


        callOne.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String reservation = null;
                try {
                    reservation = response.body().string();
                    if(reservation.equals("true")){
                        reservationText.setText("Slobodan");
                    } else{
                        reservationText.setText("Zauzet");
                        reservationText.setTextColor(Color.RED);
                        reservationItemButton.setClickable(false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                Integer code = response.code();
                Log.d("Code", code.toString());
                List<Item> items = response.body();
                Log.d("item", items.get(0).getIdentifier());
                for(int i=0; i < items.size(); i++) {
                    if (items.get(i).getIdentifier().equals(itemValue)) {
                        Log.d("usao", items.get(i).getIdentifier());
                        identifierText.setText(items.get(i).getIdentifier());
                        descriptionText.setText(items.get(i).getDescription());
                        typeText.setText((CharSequence) items.get(i).getType().getDescription());
                        kitTypeText.setText(items.get(i).getKit().getName());
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        /*
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callTwo.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Integer code = response.code();
                        Log.d("reservation", code.toString());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(ItemActivity.this, MainActivity.class);
                ItemActivity.this.startActivity(myIntent);
            }
        });
    }



}
