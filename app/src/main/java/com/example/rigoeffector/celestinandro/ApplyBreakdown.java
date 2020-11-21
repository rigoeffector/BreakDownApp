package com.example.rigoeffector.celestinandro;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rigoeffector.celestinandro.Models.Emergency;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyBreakdown extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener,View.OnClickListener {

    private GoogleMap mMap;
    private Spinner spinner;
    private  ApiInterface apiInterface;
    RequestQueue requestQueue;
    private List<String> emergenciesList = new ArrayList<String>();
    private  ArrayAdapter<String> emergecnyAdapater;
    private TextView spinner_text_id;



    private ImageView imgView;
    private Button UploadBn, ChooseBn;
    private final int  IMG_REQUEST = 1;
    private Bitmap bitmap;
    private String subtypeIdString,typeIdString;
    private EditText report_description,image_name;
    private  File fileImages;


    private String URL_POST =   "http://192.168.43.47/celestinProject/api/reports/create.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_breakdown);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ChooseBn = (Button)findViewById(R.id.chooseBn);
        ChooseBn.setOnClickListener(this);
        UploadBn = (Button) findViewById(R.id.uploadBn);
        UploadBn.setOnClickListener(this);
        imgView=(ImageView) findViewById(R.id.imageView);
        report_description=(EditText) findViewById(R.id.report_description);
        image_name=(EditText) findViewById(R.id.image_name) ;


        spinner_text_id = (TextView) findViewById(R.id.spinner_selected_id);
        requestQueue = Volley.newRequestQueue(this);
        spinner = (Spinner)findViewById(R.id.spinner);



        VolleyMultiPartRequest multipartRequest = new VolleyMultiPartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                // parse success output
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
                params.put("name", "Angga");
                params.put("location", "Indonesia");
                params.put("about", "UI/UX Designer");
                params.put("contact", "angga@email.com");
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
              //  params.put("avatar", new DataPart("file_avatar.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), mAvatarImage.getDrawable()), "image/jpeg"));
                //params.put("cover", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), mCoverImage.getDrawable()), "image/jpeg"));

                return params;
            }
        };

      //  VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);







        String URL = "http://192.168.43.47/celestinProject/api/emergency/readAll.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                URL, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("emergency");
                    for(int  i=0 ; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String emergencyName = jsonObject.optString("em_name");
                        String emergencyId = jsonObject.optString("id");
                        spinner_text_id.setText(emergencyId);
                        emergenciesList.add(emergencyName);
                        emergecnyAdapater = new ArrayAdapter<String>(ApplyBreakdown.this,android.R.layout.simple_spinner_item,emergenciesList);
                        emergecnyAdapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(emergecnyAdapater);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(jsonObjectRequest);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);


        if(getIntent().getExtras() != null){
            subtypeIdString = getIntent().getExtras().getString("subtypeId");
            typeIdString = getIntent().getExtras().getString("brId");

        }

    }




    private  void  uploadImageAndData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject =  new JSONObject(response);
                    String Response =  jsonObject.getString("response");
                    Toast.makeText(getApplicationContext(),Response,Toast.LENGTH_SHORT).show();
                    imgView.setImageResource(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>  params = new HashMap<>();
                params.put("userId", "4" );
                params.put("emergency_id","1");
                params.put("image", String.valueOf(fileImages));
                params.put("br_subtype",subtypeIdString);
                params.put("br_id",typeIdString);
                params.put("report_location","kigali");
                params.put("name", image_name.getText().toString().trim());
                params.put("report_description",report_description.getText().toString().trim());
                return params;
            }
        };


        Log.d("Images", imageToString(bitmap));
        MySingleton.getmInstance(ApplyBreakdown.this).addToRequestQue(stringRequest);

    }


//    convert image into a string


    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);

    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
        String item = adapterView.getItemAtPosition(i).toString();
        String selectedItem = String.valueOf(adapterView.getSelectedItemId()+1);

        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "Selected: " + selectedItem, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.chooseBn:
                selectImage();
                break;

            case  R.id.uploadBn:
                uploadImageAndData();
                break;
        }
    }

    private void  selectImage(){
        Log.d("typeId", typeIdString);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode ==IMG_REQUEST && resultCode == RESULT_OK && data != null){
//            Uri path = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
//                imgView.setImageBitmap(bitmap);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }


        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {



            //getting the image Uri

            Uri imageUri = data.getData();

            try {

                //getting bitmap object from uri

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);



                //displaying selected image to imageview

                imgView.setImageBitmap(bitmap);



                //calling the method uploadBitmap to upload image

//                loading.setVisibility(View.VISIBLE);

                ///uploadBitmap(bitmap);



                File file = new File(getRealPathFromUri(this, imageUri));
                fileImages = file;





            } catch (IOException e) {

                e.printStackTrace();

            }

        }
    }
    public static String getRealPathFromUri(Context context, Uri contentUri) {

        Cursor cursor = null;

        try {

            String[] proj = { MediaStore.Images.Media.DATA };

            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);

            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            cursor.moveToFirst();

            return cursor.getString(column_index);

        } finally {

            if (cursor != null) {

                cursor.close();

            }

        }

    }


}
