package com.example.volley_practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    RecyclerView recyclerView;
    MyAdapter adapter;

    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);

        adapter = new MyAdapter();

        recyclerView.setAdapter(adapter);

        editText = findViewById(R.id.editText);


        Button button = findViewById(R.id.button);

        if(requestQueue==null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlStr = editText.getText().toString();
                request(urlStr);
            }
        });



    }

    private void request(String urlStr) {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    urlStr,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                         // println("응답 : "+response);
                            Toast.makeText(getApplicationContext(),"응답 받음", Toast.LENGTH_SHORT).show();
                            processResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                           // println("에러 : "+error.toString());
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String,String>();
                    return params;
                }
            };

            request.setShouldCache(false);
            requestQueue.add(request);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void processResponse(String response) {
        Gson gson = new Gson();
        MovieList movieList = gson.fromJson(response,MovieList.class);
        int mvNum = movieList.boxOfficeResult.dailyBoxOfficeList.size();

        for(int i = 0; i<mvNum; i++){
            Movie movie =  movieList.boxOfficeResult.dailyBoxOfficeList.get(i);
            adapter.addMovie(movie.movieNm, movie.rank, movie.showCnt);
        }
        adapter.notifyDataSetChanged();

   /*     println("영화정보의 수 : " + mvNum );
        for(int i =0; i<mvNum ; i++) {
            println("영화 이름 : " + movieList.boxOfficeResult.dailyBoxOfficeList.get(i).movieNm);
        }
   */


    }

 /*   public void println(String data){
        textView.append(data+"\n");
    }
*/

}