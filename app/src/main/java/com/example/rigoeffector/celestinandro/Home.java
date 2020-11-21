package com.example.rigoeffector.celestinandro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.rigoeffector.celestinandro.Adapter.MyAdapter;
import com.example.rigoeffector.celestinandro.Models.ListItem;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class Home extends AppCompatActivity {

    private SharedPreferences sharedPreferences;


    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    private  ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = (RecyclerView) findViewById(R.id.break_down_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        apiInterface =ApiClient.getApiClient().create(ApiInterface.class);


        Call<List<ListItem>> call = apiInterface.getTyps();
        call.enqueue(new Callback<List<ListItem>>() {
            @Override
            public void onResponse(Call<List<ListItem>> call, Response<List<ListItem>> response) {
              listItems = response.body();
                adapter = new MyAdapter(listItems, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ListItem>> call, Throwable t) {
                    t.printStackTrace();
            }
        });


    }

    public  void gotToSubType(String typeId, String typeName){
      Intent intent = new Intent(getApplicationContext(), getSubType.class);
        intent.putExtra("TypeName", typeName);
        intent.putExtra("TypeId",typeId);
        startActivity(intent);
    }


}
