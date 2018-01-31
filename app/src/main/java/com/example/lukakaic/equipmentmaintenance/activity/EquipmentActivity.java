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
import com.example.lukakaic.equipmentmaintenance.rest.ApiClient;
import com.example.lukakaic.equipmentmaintenance.rest.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquipmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);

        ListView listView = (ListView) findViewById(R.id.listView);

        //Dohvacanje tokena
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString("key", "defaultValue");
        Log.d("token", token);

        final HashMap<Integer, String> equipmentMap = new HashMap<Integer, String>();

        //poziv rute i predaja tokena u headeru
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Item>> call = apiService.getItems("Bearer " + token);
        final ListView finalListView1 = listView;
        final ListView finalListView2 = listView;

        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                Integer code = response.code();
                Log.d("Code", code.toString());
                List<Item> items = response.body();
                ArrayList<String> equipmentList = new ArrayList<String>();
                ArrayList<String> itemsSorted = new ArrayList<String>();
                for(int i=0; i < items.size(); i++) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(items.get(i).getIdentifier() + " \n"+ items.get(i).getDescription());
                    equipmentList.add(stringBuilder.toString());
                    itemsSorted.add(items.get(i).getIdentifier());
                    equipmentMap.put(i + 1, items.get(i).getIdentifier());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(EquipmentActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, equipmentList);
                finalListView1.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                t.printStackTrace();
            }
        });


        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.listView);

        // ListView Item Click Listener
        final ListView finalListView = listView;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                Integer itemPosition = position + 1;

                // ListView Clicked item value
                String  itemValue = (String) equipmentMap.get(position + 1);

                // Show Alert
                //Toast.makeText(getApplicationContext(),
                 //       "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                  //      .show();
                Intent intent = new Intent(EquipmentActivity.this, ItemActivity.class);
                intent.putExtra("EXTRA_SESSION_ID",  itemValue);
                intent.putExtra("ID_VALUE", itemPosition + 1);
                EquipmentActivity.this.startActivity(intent);

            }

        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(EquipmentActivity.this, MainActivity.class);
                EquipmentActivity.this.startActivity(myIntent);
                finish();
            }
        });

    }

}
