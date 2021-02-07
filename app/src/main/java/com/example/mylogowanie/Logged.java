package com.example.mylogowanie;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mylogowanie.DTO.DaySummary;
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

public class Logged extends AppCompatActivity implements View.OnClickListener {

    private TextView myDate;
    private TextView myProtein;
    private TextView myCarbo;
    private TextView myFat;

    private  Button btnFood;
    private  Button btnProfile;

    private ProgressBar progresProtein;
    private ProgressBar progresCarbo;
    private ProgressBar progresFat;


    private Gson gson = new Gson();
    private OkHttpClient client = new OkHttpClient();
    private String URL = "http://172.23.186.23:8082/days";

    private UserRepresentation loggedUser;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logged);
        getSupportActionBar().setTitle("Aplikacja");
        CalendarView calendarView = findViewById(R.id.calendarView);

        loggedUser = gson.fromJson(getIntent().getStringExtra("userJson"), UserRepresentation.class);


        // labele
        myDate = findViewById(R.id.myDate);
        myProtein = findViewById(R.id.myProtein);
        myCarbo=findViewById(R.id.myCarbo);
        myFat=findViewById(R.id.myFat);

        // progres barry
        progresProtein=findViewById(R.id.progresProtein);
        progresCarbo=findViewById(R.id.progresCarbo);
        progresFat=findViewById(R.id.progresFat);

        // max progres barrow
        progresProtein.setMax((int)loggedUser.getDemandProtein());
        progresCarbo.setMax((int)loggedUser.getDemandCarbs());
        progresFat.setMax((int)loggedUser.getDemandFat());



        btnFood = findViewById(R.id.buttonFood);
        btnFood.setOnClickListener(this);

        btnProfile = findViewById(R.id.buttonProfile);
        btnProfile.setOnClickListener( this);

        LocalDate today = LocalDate.now();
        String params = "?localDate="+ today + "&login=" + loggedUser.getLogin();

        Request request = new Request.Builder()
                .url(URL+"/details"+params)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    setData(response.body().string(), today.toString());
                }
            }
        });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String date = getDate(year,month,dayOfMonth);
                String params = "?localDate="+ date + "&login=" + loggedUser.getLogin();
                Request request = new Request.Builder()
                        .url(URL+"/details"+params)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.isSuccessful()){
                            setData(response.body().string(), date);
                        }
                    }
                });

            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonProfile)
        {
            String userJson = gson.toJson(loggedUser);
            openProfile(userJson);
        }

        if(v.getId()==R.id.buttonFood)
        {
            String userJson = gson.toJson(loggedUser);
            openFood(userJson);
        }
    }


    public void openProfile(String userJson){

        Intent intent = new Intent(this,Profile.class);
        intent.putExtra("userJson", userJson);

        startActivity(intent);
    }

    public void openFood(String userJson){
        String currentDate = myDate.getText().toString();
        Intent intent = new Intent(this,AddFood.class);
        intent.putExtra("userJson", userJson);
        intent.putExtra("date", currentDate);
        startActivity(intent);
    }

    private void setData(String json, String date) {
        String dailySummary = json;
        DaySummary daySummary = gson.fromJson(dailySummary, DaySummary.class);


        myDate.setText(date);

        String protein = "Białko: "+ daySummary.getProtein() + "/" + (int)loggedUser.getDemandProtein() + "g";
        myProtein.setText(protein);

        String carbo = "Węglowodany: "+ daySummary.getCarbs() + "/" + (int)loggedUser.getDemandCarbs() + "g";
        myCarbo.setText(carbo);

        String fat = "Tłuszcze: "+ daySummary.getFat() + "/" + (int)loggedUser.getDemandFat() + "g";
        myFat.setText(fat);

        progresProtein.setProgress((int)daySummary.getProtein());
        progresCarbo.setProgress((int)daySummary.getCarbs());
        progresFat.setProgress((int)daySummary.getFat());
    }

    private String getDate(int year, int month, int dayOfMonth) {
        String month0;
        String date;
        String day;

        if(dayOfMonth<10){day="0"+dayOfMonth;}
        else day=""+ dayOfMonth;

        if(month<10){
            month0="0"+(month+1);
            date = year +"-"+ month0+"-"+ day;
            return date;
        }
        else {
            date = year +"-"+ (month+1) +"-"+ day;}
        return date;
    }

}