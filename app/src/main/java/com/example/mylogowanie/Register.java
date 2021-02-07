package com.example.mylogowanie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mylogowanie.DTO.AddUserDTO;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private Gson gson = new Gson();
    private OkHttpClient client = new OkHttpClient();
    private String URL = "http://172.23.186.23:8082";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button buttonRegistered = findViewById(R.id.buttonRegistered);
        buttonRegistered.setOnClickListener( this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonRegistered)
        {
            AddUserDTO userToAdd = new AddUserDTO();

            EditText login = (EditText)findViewById(R.id.login);
            EditText password = (EditText)findViewById(R.id.password);
            EditText passwordConfirm = (EditText)findViewById(R.id.passwordConfirm);
            EditText name = (EditText)findViewById(R.id.name);
            EditText surname = (EditText)findViewById(R.id.surname);
            EditText age = (EditText)findViewById(R.id.age);
            EditText height = (EditText)findViewById(R.id.height);
            EditText weight = (EditText)findViewById(R.id.wegiht);

            userToAdd.setLogin(login.getText().toString());
            userToAdd.setPassword(password.getText().toString());
            userToAdd.setFirstName(name.getText().toString());
            userToAdd.setLastName(surname.getText().toString());
            userToAdd.setAge(Integer.parseInt(age.getText().toString()));
            userToAdd.setHeight(Integer.parseInt(height.getText().toString()));
            userToAdd.setWeight(Integer.parseInt(weight.getText().toString()));


            String userJSON = gson.toJson(userToAdd);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), userJSON);

            Request request = new Request.Builder()
                    .url(URL+"/register")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    openProfile();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful()) {
                        String userJson = response.body().string();
                        openLogged(userJson);
                    }

                }
            });



        }
    }

    public void openLogged(String userJson){
        Intent intent = new Intent(this,Logged.class);
        intent.putExtra("userJson", userJson);
        startActivity(intent);
    }

    public void openProfile(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }


}