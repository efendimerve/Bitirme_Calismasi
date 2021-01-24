package com.example.deneme;

import android.content.Context;
import android.content.Intent;
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


public class SesliAsistan_Activity extends AppCompatActivity {
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_color));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sesliasistan);

        Button button_on = findViewById(R.id.button_on);
        button_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext = getApplicationContext();
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String url = "http://192.168.1.107:8080/on";

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

        Button button_off = findViewById(R.id.button_off);
        button_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext = getApplicationContext();
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String url = "http://192.168.1.107:8080/off";

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

        Button button_open = findViewById(R.id.button_open);
        button_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext = getApplicationContext();
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String url = "http://192.168.1.107:8080/open";

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

        Button button_close = findViewById(R.id.button_close);
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext = getApplicationContext();
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String url = "http://192.168.1.107:8080/close";

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

        Button button_closew = findViewById(R.id.button_closew);
        button_closew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext = getApplicationContext();
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String url = "http://192.168.1.107:8080/close_window";

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

        Button button_openw = findViewById(R.id.button_openw);
        button_openw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext = getApplicationContext();
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String url = "http://192.168.1.107:8080/open_window";

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


        Button button_mod = (Button) findViewById(R.id.MOD);
        button_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SesliAsistan_Activity.this,ModActivity.class);
                startActivity(intent);
            }
        });


        Button button = (Button) findViewById(R.id.button_play);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SesliAsistan_Activity.this,MusicActivity.class);
                startActivity(intent);
            }
        });
    }
}
