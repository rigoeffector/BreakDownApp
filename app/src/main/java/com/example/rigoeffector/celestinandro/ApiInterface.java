package com.example.rigoeffector.celestinandro;

import com.example.rigoeffector.celestinandro.Models.Emergency;
import com.example.rigoeffector.celestinandro.Models.ListItem;
import com.example.rigoeffector.celestinandro.Models.Subtypes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by rigoeffector on 9/25/20.
 */

public interface ApiInterface {
    @GET("types/readAll.php")
    Call<List<ListItem>> getTyps();

    @POST("subtype/readByType.php")
    Call<List<Subtypes>> getSubType(@Query("br_id") String br_id);

    @GET("emergency/readAll.php")
    Call<List<Emergency>> getEmergencyList();
}
