package com.example.rigoeffector.celestinandro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;


import com.example.rigoeffector.celestinandro.Adapter.SubTypeAdapter;
import com.example.rigoeffector.celestinandro.Models.Subtypes;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class getSubType extends AppCompatActivity {
  private RecyclerView recyclerView;
  private List<Subtypes>subtypesList;
  private RecyclerView.Adapter adapter;
private TextView typeName;
private  ApiInterface apiInterface;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sub_type);

        typeName =(TextView) findViewById(R.id.typeNames);


        recyclerView = (RecyclerView) findViewById(R.id.subtype_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        if(getIntent().getExtras() != null){
            String typeIdString = getIntent().getExtras().getString("TypeId");
            FetchSubTypes(typeIdString);
        }

    }



    public void FetchSubTypes( String typeId){
          apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Subtypes>> call = apiInterface.getSubType(typeId);
        call.enqueue(new Callback<List<Subtypes>>() {
            @Override
            public void onResponse(Call<List<Subtypes>> call, Response<List<Subtypes>> response) {
                subtypesList = response.body();
                adapter= new SubTypeAdapter(subtypesList, getApplicationContext());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Subtypes>> call, Throwable t) {

            }
        });
    }


}
