package com.example.deneme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class CameraActivity extends AppCompatActivity {
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_color));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        mContext = getApplicationContext();
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "http://192.168.1.107:8080/camera_on";

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

        VideoView videoView =(VideoView) findViewById(R.id.videoView);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        Uri uri = Uri.parse("http://192.168.1.107:8090");

        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.start();

        Button button_o = findViewById(R.id.button_k_kapa);
        button_o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext = getApplicationContext();
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String url = "http://192.168.1.107:8080/camera_off";

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

                Intent intent=new Intent(CameraActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
