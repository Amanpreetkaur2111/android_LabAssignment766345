package com.example.android_766345aman;

public class ClassOfPlaces {

int id ;
 String address,nameoffavrtplace,date;
 Double latitude,longitude;

    public ClassOfPlaces( int id, String nameoffavrtplace,String date,String address,Double longitude,Double latitude) {
        this.nameoffavrtplace = nameoffavrtplace;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.id = id;
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

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getDate() {
        return date;
    }
}
