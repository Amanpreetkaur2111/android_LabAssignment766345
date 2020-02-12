package com.example.android_766345aman;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class List0fFavtPlaces extends AppCompatActivity {

    DBofFavrtPlaces mDatabase;
    List<ClassOfPlaces> listPlace;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list0f_favt_places);
        listView = findViewById(R.id.favrt_places);
        listPlace = new ArrayList<>();
        mDatabase = new DBofFavrtPlaces(this);
        loadPlaces();
    }



    private void loadPlaces(){

        Cursor cursor = mDatabase.getAllPlaces();
        if(cursor.moveToFirst()){

            do{


                listPlace.add(new ClassOfPlaces(cursor.getInt(0),
                        cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),cursor.getString(5)
                        ));

            }while (cursor.moveToNext());

            cursor.close();
        }


        // Custom Adaptor


    }



}
