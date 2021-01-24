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

public class GirisActivity extends AppCompatActivity {
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_color));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.giris);

        Button buttons_active2 = findViewById(R.id.buttons_active2);
        buttons_active2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext = getApplicationContext();
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String url = "http://192.168.1.107:8080/giris";

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

        Button buttons_close2 = findViewById(R.id.buttons_close2);
        buttons_close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext = getApplicationContext();
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String url = "http://192.168.1.107:8080/k_kapa";

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
