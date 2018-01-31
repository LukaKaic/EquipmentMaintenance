package com.example.lukakaic.equipmentmaintenance.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lukakaic.equipmentmaintenance.R;
import com.example.lukakaic.equipmentmaintenance.rest.ApiClient;
import com.example.lukakaic.equipmentmaintenance.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final EditText email = (EditText) findViewById(R.id.emailRegister);
        final EditText first_name = (EditText) findViewById(R.id.firstNameRegister);
        final EditText last_name = (EditText) findViewById(R.id.lastNameRegister);
        final EditText password = (EditText) findViewById(R.id.passwordRegister);
        Button register = (Button) findViewById(R.id.register);

        String emailText = email.getText().toString().trim();
        String firstNameText = first_name.getText().toString().trim();
        String lastNameText = last_name.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString().trim();
                String firstNameText = first_name.getText().toString().trim();
                String lastNameText = last_name.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                Log.d("Proba", emailText);
                Call<String> call = apiService.saveUser(emailText, passwordText, firstNameText, lastNameText);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String respond = response.body();
                        Integer code = response.code();
                        Log.d("code ", code.toString());
                        Context context = getApplicationContext();
                        CharSequence text = "Registration complete";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        call.cancel();
                        Context context = getApplicationContext();
                        CharSequence text = "User registred";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        RegisterActivity.this.startActivity(intent);
                    }
                });
            }
        });

    }
}
