package com.example.android.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> places= new ArrayList<String>();
    static ArrayList<LatLng> location= new ArrayList<LatLng>();
    static ArrayAdapter<String> arrayAdapter;
    private SharedPreferences msharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list_places= (ListView) findViewById(R.id.list_view);
        msharedPreferences= this.getSharedPreferences("com.example.android.memorableplaces", Context.MODE_PRIVATE);
        ArrayList<String> latitudes= new ArrayList<String>();
        ArrayList<String> longitudes= new ArrayList<String>();

        places.clear();
        latitudes.clear();
        longitudes.clear();
        location.clear();

        try {
            places= (ArrayList<String>) ObjectSerializer.deserialize("places",ObjectSerializer.serialize(new ArrayList<String>()));
            latitudes= (ArrayList<String>) ObjectSerializer.deserialize("latitudes",ObjectSerializer.serialize(new ArrayList<String>()));
            longitudes= (ArrayList<String>) ObjectSerializer.deserialize("longitudes",ObjectSerializer.serialize(new ArrayList<String>()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (places.size()>0 && latitudes.size()>0 && longitudes.size()>0){
            if (places.size()==latitudes.size() && latitudes.size()== longitudes.size()){
                for (int i=0; i< latitudes.size(); i++){
                    location.add(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i))));
                }
            }
        }

        else{
            places.add("Add a new Place");
            location.add(new LatLng(0,0));
        }

        arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places);
        list_places.setAdapter(arrayAdapter);

        list_places.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent= new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("placeNumber",position);
                startActivity(intent);
            }
        });

    }
}
