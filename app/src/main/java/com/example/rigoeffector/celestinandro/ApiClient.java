package com.example.rigoeffector.celestinandro;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.PUT;

/**
 * Created by rigoeffector on 9/25/20.
 */

public class ApiClient {
    public static final String  BASE_URL = "http://192.168.43.47:80/celestinProject/api/";
    public  static Retrofit retrofit = null;

    public  static  Retrofit getApiClient(){
        if(retrofit == null){

            //        FOR DEBUGGING ONLY REMEMEBER TO REMOVE ON PROD

            HttpLoggingInterceptor httpLoggingInterceptor =  new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .retryOnConnectionFailure(true)
                    .build();

            //        FOR DEBUGGING ONLY REMEMEBER TO REMOVE ON PROD
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).
                    build();
        }
        return  retrofit;
    }
}
