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
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        RegisterActivity.this.startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        call.cancel();
                        Context context = getApplicationContext();
                        CharSequence text = "Code : ";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });
            }
        });








        /*
        call.enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                if(response.isSuccessful()){
                    Integer isTrue = response.code();
                    Context context = getApplicationContext();
                    CharSequence text = "Registred!" + isTrue;
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString().trim();
                String firstNameText = first_name.getText().toString().trim();
                String lastNameText = last_name.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                Context context = getApplicationContext();
                CharSequence text = "Registred!" + emailText + " " + firstNameText + " " + lastNameText + " " + passwordText;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Call<User> call = apiService.saveUser(emailText, firstNameText, lastNameText, passwordText);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Integer isTrue = response.code();
                        Context context = getApplicationContext();
                        CharSequence text = "Registred!" + isTrue;
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });
    }
    public void sendPost(String email, String firstName, String lastName, String passwordText) {
        apiService.savePost(email, firstName, lastName, passwordText).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void showResponse(String response) {
        if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }
    */
    }
}
