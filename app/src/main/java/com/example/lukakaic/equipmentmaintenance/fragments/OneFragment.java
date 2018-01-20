package com.example.lukakaic.equipmentmaintenance.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lukakaic.equipmentmaintenance.R;
import com.example.lukakaic.equipmentmaintenance.model.Item;
import com.example.lukakaic.equipmentmaintenance.rest.ApiClient;
import com.example.lukakaic.equipmentmaintenance.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OneFragment extends Fragment {

    public OneFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = (ListView) getActivity().findViewById(R.id.fragmentOneList);

        //Uzimanje tokena
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String token = preferences.getString("key", "defaultValue");
        Log.d("token", token);

        //poziv rute i predaja tokena u headeru
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Item>> call = apiService.getItems("Bearer " + token);
        final ListView finalListView1 = listView;
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                Integer code = response.code();
                Log.d("Code", code.toString());
                List<Item> items = response.body();
                ArrayList<String> itemsSorted = new ArrayList<String>();
                for(int i=0; i < items.size(); i++) {
                    itemsSorted.add(items.get(i).getIdentifier());
                }
                Log.d("sorted", itemsSorted.toString());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, itemsSorted);
                finalListView1.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false);


    }
}
