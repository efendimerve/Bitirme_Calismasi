package com.example.deneme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {


    private Context mContext;
    FirebaseDatabase db;
    private TextView deprem,su,alev,gaz,deprem1,su1,alev1,gaz1,hirsizz,textView_hirsiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_color));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button_asist);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SesliAsistan_Activity.class);
                startActivity(intent);
            }
        });

        Button button_w = (Button) findViewById(R.id.button_w);
        button_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,WeatherActivity.class);
                startActivity(intent);
            }
        });

        Button button_cmr = (Button) findViewById(R.id.button_cmr);
        button_cmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,GirisActivity.class);
                startActivity(intent);
            }
        });

        Button button_t = (Button) findViewById(R.id.button_t);
        button_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext = getApplicationContext();
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String url = "http://192.168.1.107:8080/talking";

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

        Button button_safe = (Button) findViewById(R.id.button_safe);
        button_safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SafeActivity.class);
                startActivity(intent);
            }
        });

        Button button_camera = (Button) findViewById(R.id.Camera);
        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,CameraActivity.class);
                startActivity(intent);
            }
        });
        baslangic();
        su();
        alev();
        deprem();
        gaz();
        hirsiz();
    }

    private void baslangic() {
        db = FirebaseDatabase.getInstance();
        deprem=(TextView)findViewById(R.id.deprem);
        su=(TextView)findViewById(R.id.su);
        alev=(TextView)findViewById(R.id.alev);
        gaz=(TextView)findViewById(R.id.gaz);
        deprem1=(TextView)findViewById(R.id.textView_deprem);
        su1=(TextView)findViewById(R.id.textView_su);
        alev1=(TextView)findViewById(R.id.textView_alev);
        gaz1=(TextView)findViewById(R.id.textView_gaz);
        hirsizz=(TextView)findViewById(R.id.hirsizz);
        textView_hirsiz=(TextView)findViewById(R.id.textView_hirsiz);

    }
    private void su(){
        DatabaseReference okuma=db.getReference("sensor/su");
        okuma.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                su.setText("");
                su1.setText("");
                Iterable<DataSnapshot> keys= dataSnapshot.getChildren();
                for(DataSnapshot key: keys){
                    su.append("Flood Detected!"+"\n");
                    su1.append("The House is Flooded! Turn Off Charts!");
                    Intent intent = new Intent(getApplicationContext(), ResultReceiver.class);
                    intent.setAction(ResultReceiver.ACTION_CLICK);
                    NotificationUtil.with(getApplicationContext()).showNotification(
                            "WATER!",
                            "The House is Flooded! Turn Off Charts!",
                            R.mipmap.ic_launcher,
                            intent
                    );
                }
            }
            @Override
            public void onCancelled( DatabaseError error) {
            }
        });
    }
    private void alev(){
        DatabaseReference okuma=db.getReference("sensor/yangın");
        okuma.addValueEventListener(new ValueEventListener() {
            Button button_yan_x = (Button) findViewById(R.id.button_yan_x);
            Button button_yangin = (Button) findViewById(R.id.button_yangin);
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                alev.setText("");
                alev1.setText("");
                Iterable<DataSnapshot> keys= dataSnapshot.getChildren();
                for(DataSnapshot key: keys){
                    alev.append("Fire!"+"\n");
                    alev1.append("There was a fire! Call the fire department!");
                    Intent intent = new Intent(getApplicationContext(), ResultReceiver.class);
                    intent.setAction(ResultReceiver.ACTION_CLICK);
                    NotificationUtil.with(getApplicationContext()).showNotification(
                            "FIRE!",
                            "There was a fire! Call the fire department!",
                            R.mipmap.ic_launcher,
                            intent
                    );
                    button_yangin.setVisibility(View.VISIBLE);
                    button_yangin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent acil_ara=new Intent(Intent.ACTION_DIAL);
                            acil_ara.setData(Uri.parse("tel: 0534"));
                            startActivity(acil_ara);
                        }
                    });
                    button_yan_x.setVisibility(View.VISIBLE);
                    button_yan_x.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button_yangin.setVisibility(View.INVISIBLE);
                            button_yan_x.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
            @Override
            public void onCancelled( DatabaseError error) {
            }
        });

    }
    private void hirsiz(){
        DatabaseReference okuma=db.getReference("sensor/hirsiz");
        okuma.addValueEventListener(new ValueEventListener() {
            Button button_hirsiz_x = (Button) findViewById(R.id.button_hirsiz_x);
            Button button_hirsiz = (Button) findViewById(R.id.button_hirsiz);
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                hirsizz.setText("");
                textView_hirsiz.setText("");
                Iterable<DataSnapshot> keys= dataSnapshot.getChildren();
                for(DataSnapshot key: keys){
                    hirsizz.append("Thief!"+"\n");
                    textView_hirsiz.append("Thief! Call the police!");
                    Intent intent = new Intent(getApplicationContext(), ResultReceiver.class);
                    intent.setAction(ResultReceiver.ACTION_CLICK);
                    NotificationUtil.with(getApplicationContext()).showNotification(
                            "THIEF!",
                            "Thief! Call the police!",
                            R.mipmap.ic_launcher,
                            intent
                    );
                    button_hirsiz.setVisibility(View.VISIBLE);
                    button_hirsiz.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent acil_ara=new Intent(Intent.ACTION_DIAL);
                            acil_ara.setData(Uri.parse("tel: 0534"));
                            startActivity(acil_ara);
                        }
                    });
                    button_hirsiz_x.setVisibility(View.VISIBLE);
                    button_hirsiz_x.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button_hirsiz.setVisibility(View.INVISIBLE);
                            button_hirsiz_x.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
            @Override
            public void onCancelled( DatabaseError error) {
            }
        });

    }
    private void deprem(){
        DatabaseReference okuma=db.getReference("sensor/deprem");
        okuma.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                deprem.setText("");
                deprem1.setText("");
                Iterable<DataSnapshot> keys= dataSnapshot.getChildren();
                for(DataSnapshot key: keys){
                    deprem.append("Earthquake!"+"\n");
                    deprem1.append("Attention! Earthquake moment!");
                    Intent intent = new Intent(getApplicationContext(), ResultReceiver.class);
                    intent.setAction(ResultReceiver.ACTION_CLICK);
                    NotificationUtil.with(getApplicationContext()).showNotification(
                            "EARTHQUAKE!",
                            "Attention! Earthquake moment!",
                            R.mipmap.ic_launcher,
                            intent
                    );
                }
            }
            @Override
            public void onCancelled( DatabaseError error) {
            }
        });
    }
    private void gaz(){
        DatabaseReference okuma=db.getReference("sensor/gaz");
        okuma.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                gaz.setText("");
                gaz1.setText("");
                Iterable<DataSnapshot> keys= dataSnapshot.getChildren();
                for(DataSnapshot key: keys){
                    gaz.append("Gas!"+"\n");
                    gaz1.append("Gas leak detected! Turn off the chartel and the gas!");
                    Intent intent = new Intent(getApplicationContext(), ResultReceiver.class);
                    intent.setAction(ResultReceiver.ACTION_CLICK);
                    NotificationUtil.with(getApplicationContext()).showNotification(
                            "GAS!",
                            "Gas leak detected! Turn off the chartel and the gas!",
                            R.mipmap.ic_launcher,
                            intent
                    );
                }
            }
            @Override
            public void onCancelled( DatabaseError error) {
            }
        });
    }
}
