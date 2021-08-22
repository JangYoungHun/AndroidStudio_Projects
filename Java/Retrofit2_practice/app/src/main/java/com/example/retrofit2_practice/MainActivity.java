package com.example.retrofit2_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    static final String API_KEY = "eabb8ee088781443cfebff1ea63f4d63";

    Retrofit retrofit;
    WeatherInterface weatherInterface;
    EditText editText;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherInterface = retrofit.create(WeatherInterface.class);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");

                Call<WeatherInfo> call = weatherInterface.getWeatherInfo(editText.getText().toString(),API_KEY);
                call.enqueue(new Callback<WeatherInfo>() {
                    @Override
                    public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                        showToast("날씨 정보 응답");
                        println("날씨 정보 응답");

                        if(response.isSuccessful()){
                            WeatherInfo info = response.body();
                            println("weather main : " +info.getWeather().get(0).getMain());
                            println("weather description : " +info.getWeather().get(0).getDescription());
                            println("main temp : " +info.getMain().getTemp());
                            println("main feels_like : " +info.getMain().getFeels_like());
                            println("main temp_min : " +info.getMain().getTemp_min());
                            println("main temp_max : " +info.getMain().getTemp_max());
                            println("main pressure : " +info.getMain().getPressure());
                            println("main humidity : " +info.getMain().getHumidity());
                        }

                        else {
                            showToast("날씨 정보 불러오기 실패");
                            println("날씨 정보 불러오기 실패");
                        }

                    }

                    @Override
                    public void onFailure(Call<WeatherInfo> call, Throwable t) {
                        showToast("날씨 정보 응답 없음");
                        println("날씨 정보 응답 없음" + t.getMessage());
                    }
                });
            }
        });



    }

    void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    void println(String text){
        textView.append(text + "\n");
    }
}