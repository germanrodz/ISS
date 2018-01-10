package com.blovvme.iss.datasource;

import com.blovvme.iss.model.ResultClass;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Blovvme on 1/9/18.
 */

public class RetrofitHelper {
    public static final String BASE_URL =  "http://api.open-notify.org/";

    public static Retrofit create(){
        return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static Call<ResultClass> getResults(String Lat, String Long)
    {
        Retrofit retrofit = create();
        APIService apiService = retrofit.create(APIService.class);
        return apiService.getResults(Lat,Long);
    }

    interface APIService{
        @GET("iss-pass.json")
        Call<ResultClass> getResults(@Query("lat") String Lat, @Query("lon") String Long);
    }
}
