package com.example.android_766345aman;

public class ClassOfPlaces {

int id ;
 String address,nameoffavrtplace,longitude,latitude,date;

    public ClassOfPlaces(int id, String address, String nameoffavrtplace, String longitude, String latitude, String date) {
        this.id = id;
        this.address = address;
        this.nameoffavrtplace = nameoffavrtplace;
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getNameoffavrtplace() {
        return nameoffavrtplace;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getDate() {
        return date;
    }
}
