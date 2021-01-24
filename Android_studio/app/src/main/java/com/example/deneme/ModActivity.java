package com.example.deneme;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ModActivity extends AppCompatActivity {
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_color));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modlar);
        Button button_funny = findViewById(R.id.button_funny);
        button_funny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext = getApplicationContext();
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String url = "http://192.168.1.107:8080/funny_mode";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("hata");
                    }
                });

                queue.add(stringRequest);

            }
        });

        Button button_sleep = findViewById(R.id.button_sleep);
        button_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext = getApplicationContext();
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String url = "http://192.168.1.107:8080/sleep_mode";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("hata");
                    }
                });

                queue.add(stringRequest);

            }
        });

        Button button_reading = findViewById(R.id.button_reading);
        button_reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext = getApplicationContext();
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String url = "http://192.168.1.107:8080/reading_mode";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("hata");
                    }
                });

                queue.add(stringRequest);

            }
        });

        Button button_rest = findViewById(R.id.button_rest);
        button_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext = getApplicationContext();
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String url = "http://192.168.1.107:8080/rest_mode";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("hata");
                    }
                });

                queue.add(stringRequest);

            }
        });

    }
    }
