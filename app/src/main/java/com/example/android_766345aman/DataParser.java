package com.example.android_766345aman;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {

    public HashMap<String ,String> nearPlace(JSONObject object){

        HashMap<String,String> place = new HashMap<>();

        String nameOfPlace = "-NA-";
        String vicinity =  "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
        if(!object.isNull("name")){

                nameOfPlace = object.getString("name");
            }
        if(!object.isNull("vicinity")){

                nameOfPlace = object.getString("vicinity");
            }

        latitude = object.getJSONObject("geometry").getJSONObject("location").getString("lat");
        longitude = object.getJSONObject("geometry").getJSONObject("location").getString("lng");

        place.put("placeName",nameOfPlace);
        place.put("vicicnity",vicinity);
        place.put("lat",latitude);
        place.put("lng",longitude);
        place.put("reference",reference);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return place;
    }

    private List<HashMap<String ,String>> nearPlaces (JSONArray jsonArray){

        int count = jsonArray.length();

        List<HashMap<String ,String >> listOfPlaces =new  ArrayList<>();

        HashMap<String,String> Plcaes = null;

        for (int i= 0; i<count;i++){

            try {
                Plcaes = nearPlace((JSONObject) jsonArray.get(i));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            listOfPlaces.add(Plcaes);

        }



        return listOfPlaces;
    }

    public  List<HashMap<String,String>> parse(String JsonData){
        JSONArray ja = null;

        try {
            JSONObject jsonObject =new JSONObject(JsonData);
            ja = jsonObject.getJSONArray("results");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nearPlaces(ja);

    }


}
