package com.example.rigoeffector.celestinandro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {
    public EditText username, password, phone, address,fname, lname, vmodel, vtype,platenumber;
    public Button Loginbtn, Registerbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        vmodel = (EditText) findViewById(R.id.vmodel);
        vtype = (EditText) findViewById(R.id.vtype);
        platenumber = (EditText) findViewById(R.id.plateNumber);

        Loginbtn =(Button) findViewById(R.id.login);
        Registerbtn = (Button) findViewById(R.id.register);

        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}
