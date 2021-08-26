package com.example.weatherapi_practice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherAPI {

//        public final String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtNcst?serviceKey=lUl2Bgrjso5%2BsJGiY4QTeOsxgfz3lkeG8u8IK8Rn66wQs%2BgKjdcU7FaIT7zpoxVKFWoojIcXc9r1mmSszYQCLw%3D%3D&numOfRows=10&pageNo=1"&base_date=20210620&base_time=0630&nx=55&ny=127";
//        lUl2Bgrjso5%2BsJGiY4QTeOsxgfz3lkeG8u8IK8Rn66wQs%2BgKjdcU7FaIT7zpoxVKFWoojIcXc9r1mmSszYQCLw%3D%3D
    //?serviceKey=lUl2Bgrjso5%2BsJGiY4QTeOsxgfz3lkeG8u8IK8Rn66wQs%2BgKjdcU7FaIT7zpoxVKFWoojIcXc9r1mmSszYQCLw%3D%3D&numOfRows=10&pageNo=1"&base_date=20210620&base_time=0630&nx=55&ny=127
    //

//        @GET("getUltraSrtNcst?")
//        Call<WeatherInfo> getData( @Query("serviceKey") String serviceKey,
//                                   @Query("numOfRows") String numOfRows,
//                                   @Query("dataType") String dataType,
//                                   @Query("pageNo") String pageNo,
//                                   @Query("base_date") String base_date,
//                                   @Query("base_time") String base_time,
//                                   @Query("nx") String nx,
//                                   @Query("ny") String ny);
@GET("weather")
    Call<WeatherInfo> getData(   @Query("lat") String lat,
                                 @Query("lon") String lon,
                                 @Query("APPID") String APPID);
    @GET("weather")
    Call<WeatherInfo>  getSeoulData(  @Query("q") String city_name,
                                      @Query("APPID") String APPID);


    }

