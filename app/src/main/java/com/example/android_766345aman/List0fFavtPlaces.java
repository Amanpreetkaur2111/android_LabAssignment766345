package com.example.android_766345aman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

public class List0fFavtPlaces extends AppCompatActivity {

    DBofFavrtPlaces mDatabase;
    List<ClassOfPlaces> listPlace;
    SwipeMenuListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list0f_favt_places);
        listView = (SwipeMenuListView) findViewById(R.id.favrt_places);
        listPlace = new ArrayList<>();
        mDatabase = new DBofFavrtPlaces(this);
        loadPlaces();


        final PlacesAdaptor placesAdaptor = new PlacesAdaptor(this,R.layout.list_layout_favrtplaces,listPlace,mDatabase);
        listView.setAdapter(placesAdaptor);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());

                openItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0x66,
                        0xff)));

                openItem.setWidth(300);

                openItem.setTitle("Update");

                openItem.setTitleSize(18);


                openItem.setTitleColor(Color.WHITE);

                menu.addMenuItem(openItem);


                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setTitle("Delete");

                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));

                deleteItem.setWidth(300);
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.BLACK);

                menu.addMenuItem(deleteItem);
            }
        };


        listView.setMenuCreator(creator);


        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                       Intent intent = new Intent(List0fFavtPlaces.this,MainActivity.class);
                       intent.putExtra("id",listPlace.get(position).id);
                        intent.putExtra("lat",listPlace.get(position).latitude);
                        intent.putExtra("lng",listPlace.get(position).longitude);
                       intent.putExtra("edit",true);
                       startActivity(intent);

                        break;
                    case 1:

                        //Toast.makeText(List0fFavtPlaces.this, "SEE:"+listPlace.get(position).id, Toast.LENGTH_SHORT).show();

                        if(mDatabase.deletePlaces(listPlace.get(position).id)){

                            listPlace.remove(position);
                            loadPlaces();
                            listView.setAdapter(placesAdaptor);

                            Toast.makeText(List0fFavtPlaces.this, "delete", Toast.LENGTH_SHORT).show();
                            loadPlaces();


                        }else {

                            Toast.makeText(List0fFavtPlaces.this, "not deleted", Toast.LENGTH_SHORT).show();


                        }
                        loadPlaces();


                        break;
                }

                return false;
            }
        });


    }





    private void loadPlaces(){

        Cursor cursor = mDatabase.getAllPlaces();
        if(cursor.moveToFirst()){

            do{


                listPlace.add(new ClassOfPlaces(cursor.getInt(0),cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),cursor.getDouble(4),cursor.getDouble(5)
                        ));
                System.out.println("id"+cursor.getString(0));

            }while (cursor.moveToNext());

            cursor.close();
        }


        // Custom Adaptor
//        PlacesAdaptor placesAdaptor = new PlacesAdaptor(this,R.layout.list_layout_favrtplaces,listPlace,mDatabase);
//        listView.setAdapter(placesAdaptor);

    }

}
