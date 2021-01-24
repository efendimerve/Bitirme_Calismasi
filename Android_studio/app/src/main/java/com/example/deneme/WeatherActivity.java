package com.example.deneme;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class WeatherActivity extends AppCompatActivity {
    FirebaseDatabase db;
    private TextView hum,tem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_color));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weathermain);
        baslangic();
        temperature();
        humidity();
    }
    private void baslangic() {
        db = FirebaseDatabase.getInstance();
        hum=(TextView)findViewById(R.id.textView7);
        tem=(TextView)findViewById(R.id.textView9);
    }
    private void temperature(){
        Query query = FirebaseDatabase.getInstance().getReference().child("sensor").child("dht").child("temperature").limitToLast(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                tem.setText("");
                Iterable<DataSnapshot> keys= dataSnapshot.getChildren();
                for(DataSnapshot key: keys){
                    tem.append(key.getValue().toString()+"°C");
                }
            }
            @Override
            public void onCancelled( DatabaseError error) {
            }
        });
    }
    private void humidity(){
        Query query1 = FirebaseDatabase.getInstance().getReference().child("sensor").child("dht").child("humidity").limitToLast(1);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                hum.setText("");
                Iterable<DataSnapshot> keys= dataSnapshot.getChildren();
                for(DataSnapshot key: keys){
                    hum.append(key.getValue().toString()+"%");
                }
            }
            @Override
            public void onCancelled( DatabaseError error) {
            }
        });
    }
}