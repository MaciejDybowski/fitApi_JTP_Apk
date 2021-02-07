package com.example.mylogowanie;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.jetbrains.annotations.NotNull;;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Login extends AppCompatActivity implements View.OnClickListener {

    OkHttpClient client = new OkHttpClient();
    String URL = "http://172.23.186.23:8082";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener( this);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener( this);

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.buttonRegister)
        {
            openRegister();
        }

        if(v.getId()==R.id.buttonLogin)
        {
            EditText loginInput = (EditText)findViewById(R.id.editTextTextPersonName) ;
            EditText passwordInput = (EditText)findViewById(R.id.editTextTextPassword) ;

            String login = loginInput.getText().toString();
            String password = passwordInput.getText().toString();

            String params = "?login="+ login + "&password=" + password;
            Request request = new Request.Builder()
                    .url(URL+"/login"+params)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful()){
                        String userJson = response.body().string();
                        openLogged(userJson);
                    }
                }
            });
        }

    }

    public void openRegister(){
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }

    public void openLogged(String userJson){
        Intent intent = new Intent(this,Logged.class);
        intent.putExtra("userJson", userJson);
        startActivity(intent);
    }


}