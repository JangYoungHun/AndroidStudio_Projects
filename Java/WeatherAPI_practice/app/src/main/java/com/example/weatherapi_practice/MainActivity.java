package com.example.weatherapi_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView dateTxt = (TextView)findViewById(R.id.dateTxt);
        TextView timeTxt = (TextView)findViewById(R.id.timeTxt);
        TextView valueTxt = (TextView)findViewById(R.id.valueTxt);
        TextView pointTxt = (TextView)findViewById(R.id.pointTxt);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);

        Call<WeatherInfo> call = weatherAPI.getSeoulData(
                "Seoul-teukbyeolsi",
                "eabb8ee088781443cfebff1ea63f4d63"
               );

        call.enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                Toast.makeText(MainActivity.this, "성공", Toast.LENGTH_SHORT).show();
                if(!response.isSuccessful()){   //200 : OK
                    Toast.makeText(MainActivity.this, "Check the connection", Toast.LENGTH_SHORT).show();
                    return;
                }
                  dateTxt.setText(String.valueOf(response.body().getSys().country));
//                timeTxt.setText(response.body().getBaseTime());
//                pointTxt.setText(response.body().getNx()+ "  "+response.body().getNy());
//                valueTxt.setText(response.body().getObsrValue());
            }

            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                dateTxt.setText(t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }






}

