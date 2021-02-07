package com.example.mylogowanie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mylogowanie.DTO.UserRepresentation;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Profile extends AppCompatActivity {


    Gson gson = new Gson();
    private UserRepresentation loggedUser;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profil");

        loggedUser = gson.fromJson(getIntent().getStringExtra("userJson"), UserRepresentation.class);

        TextView name = findViewById(R.id.profileName);
        name.setText(loggedUser.getFirstName());

        TextView surname = findViewById(R.id.profileSurname);
        surname.setText(loggedUser.getLastName());

        TextView age = findViewById(R.id.profileAge);
        age.setText(""+ loggedUser.getAge());

        TextView height = findViewById(R.id.profileHeight);
        height.setText(loggedUser.getHeight()+"");

        TextView weight = findViewById(R.id.profileWeight);
        weight.setText(loggedUser.getWeight()+"");

        TextView protein = findViewById(R.id.profileProtein);
        protein.setText(loggedUser.getDemandProtein()+"");

        TextView fat = findViewById(R.id.profileFat);
        fat.setText(loggedUser.getDemandFat()+"");

        TextView carbo = findViewById(R.id.profileCarbo);
        carbo.setText(loggedUser.getDemandCarbs()+"");



    }
}




