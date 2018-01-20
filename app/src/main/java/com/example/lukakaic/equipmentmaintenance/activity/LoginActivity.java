package com.example.lukakaic.equipmentmaintenance.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lukakaic.equipmentmaintenance.R;
import com.example.lukakaic.equipmentmaintenance.model.UserResponse;
import com.example.lukakaic.equipmentmaintenance.rest.ApiClient;
import com.example.lukakaic.equipmentmaintenance.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final Button registerButton = (Button) findViewById(R.id.registerButton);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        final EditText email = (EditText) findViewById(R.id.emailLogin);
        final EditText password = (EditText) findViewById(R.id.passwordLogin);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString("key", "defaultValue");
        Log.d("token", token);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                Call<UserResponse> call = apiService.loginUser(emailText, passwordText);


                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if(response.isSuccessful()){
                            String loginToken = response.body().getToken().toString();
                            Integer userId = response.body().getUser().getId();
                            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(myIntent);
                            Context context = getApplicationContext();
                            String url = call.request().url().toString();
                            CharSequence text = "Logged in";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("key", loginToken);
                            editor.putInt("key1", userId);
                            editor.apply();
                        }
                        else if(response.code() == 401){
                            Context context = getApplicationContext();
                            String url = call.request().url().toString();
                            CharSequence text = "Wrong password";
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                        else if(response.code() == 404){
                            Context context = getApplicationContext();
                            String url = call.request().url().toString();
                            CharSequence text = "Wrong email";
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                        else {
                            Context context = getApplicationContext();
                            String url = call.request().url().toString();
                            CharSequence text = "Code :" + response.code();
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.d("Fail", "failed");

                    }
                });
            }
        });
    }


}
