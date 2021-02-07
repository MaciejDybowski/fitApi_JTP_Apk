package com.example.mylogowanie;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mylogowanie.DTO.AddMealDTO;
import com.example.mylogowanie.DTO.AddProduct;
import com.example.mylogowanie.DTO.ProductRepresentation;
import com.example.mylogowanie.DTO.UserRepresentation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddFood extends AppCompatActivity implements View.OnClickListener {


    private Gson gson = new Gson();
    private List<String> products = new LinkedList<>();
    private String URL = "http://172.23.186.23:8082";
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dodaj Posiłek");

        AutoCompleteTextView editText = findViewById(R.id.Products);

        Button buttonMeal = findViewById(R.id.addMeal);
        buttonMeal.setOnClickListener(this);

        Button addProduct = findViewById(R.id.addProduct);
        addProduct.setOnClickListener(this);


        Request request = new Request.Builder()
                .url(URL+ "/products")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ProductRepresentation[] productsArray = gson.fromJson(response.body().string(), ProductRepresentation[].class);
                for(ProductRepresentation p : productsArray) {
                    products.add(p.getName());
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, products);
        editText.setAdapter(adapter);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.addProduct) {
            EditText name = (EditText)findViewById(R.id.nameOfProduct);

            String nameOfProduct = name.getText().toString();
            EditText proteinText = (EditText)findViewById(R.id.productProtein);
            EditText carbsText = (EditText)findViewById(R.id.productCarbs);
            EditText fatText = (EditText)findViewById(R.id.productFat);

            Float fat = Float.parseFloat(fatText.getText().toString());
            Float protein = Float.parseFloat(proteinText.getText().toString());
            Float carbs = Float.parseFloat(carbsText.getText().toString());

            AddProduct addProduct = new AddProduct(nameOfProduct, carbs, fat, protein);


            String jsonObject = gson.toJson(addProduct);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject);

            Request request = new Request.Builder()
                    .url(URL+"/products")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    System.out.println("Dodano do bazy");
                }
            });



        }

        if(v.getId()==R.id.addMeal){
            AutoCompleteTextView editText = findViewById(R.id.Products);
            EditText weightProduct = findViewById(R.id.weightOfProduct);


            String date = gson.fromJson(getIntent().getStringExtra("date"), String.class);
            UserRepresentation loggedUser = gson.fromJson(getIntent().getStringExtra("userJson"), UserRepresentation.class);
            String productName = (editText.getText().toString());
            String weight = weightProduct.getText().toString();

            AddMealDTO addMealDTO = new AddMealDTO(Float.parseFloat(weight), date, loggedUser.getLogin(), productName);

            String jsonObject = gson.toJson(addMealDTO);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject);

            Request request = new Request.Builder()
                    .url(URL+"/meals")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    System.out.println("Dodano posiłem dla usera");
                }
            });

        }
    }
}