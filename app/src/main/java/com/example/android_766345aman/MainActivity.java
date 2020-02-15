package com.example.android_766345aman;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;

    private final int REQUEST_CODE = 1;

    DBofFavrtPlaces mDatabase;
    Geocoder  gcode;
    String address;
//    Location location;
    List<Address> addresses;
    List<Location> points;
    Spinner maptype;
    boolean onMarkerClick = false;
double  dest_lat,dest_lng,lat,lng;
 boolean isClicked = false;


    // get user location
    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest;


    // latitude, longitude
//    double latitude, longitude;
    final int RADIUS = 1500;

    LatLng customMarker;
    LatLng currentLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMap();
        getUserLocation();

      //Intent intent = getIntent();

        points = new ArrayList<>();
        maptype = findViewById(R.id.choosethemap);

        maptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){

                    case 0:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        Toast.makeText(MainActivity.this, "Normal Map Selected", Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        Toast.makeText(MainActivity.this, "Hybrid Map Selected", Toast.LENGTH_SHORT).show();
                       break;

                    case 2:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        Toast.makeText(MainActivity.this, "Normal  Map Selected", Toast.LENGTH_SHORT).show();
                        break;

                        case 3:
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        Toast.makeText(MainActivity.this, "Terrain Map Selected", Toast.LENGTH_SHORT).show();
                    break;

                    case 4:
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    Toast.makeText(MainActivity.this, "Satellite Map Selected", Toast.LENGTH_SHORT).show();
                    break;

                    default:

                      break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!checkPermission()) {
            requestPermission();
        }
        else {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }

        mDatabase = new DBofFavrtPlaces(this);
    }




    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void getUserLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10);
        setHomeMarker();
    }

    private void setHomeMarker() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                    currentLocation = userLocation;

                    CameraPosition cameraPosition = CameraPosition.builder()
                            .target(userLocation)
                            .zoom(15)
                            .bearing(0)
                            .tilt(45)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    mMap.addMarker(new MarkerOptions().position(userLocation)
                            .title("your location"));
                  // mDatabase.addFavrtPlaces()
                    // .icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.icon_loc)));
                }
            }
        };
    }



    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private boolean checkPermission() {
        int permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setHomeMarker();
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }
        }
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

  mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
    @Override
    public boolean onMarkerClick(final Marker marker) {


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("You want this marker as Source or Destination?");
        builder.setCancelable(true);


        builder.setPositiveButton("SOURCE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                currentLocation = marker.getPosition();
                onMarkerClick = true;

                //addressOfFavouritePlaces(customMarker);


            }
        });
        builder.setNegativeButton("DESTINATION", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.cancel();
                customMarker = marker.getPosition();
                onMarkerClick = false;
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return true;
    }
  });

//
//        return false;
//    }
//});

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

//                if(points.size() == 2){
//                    mMap.clear();
//                    points.clear();
//                }
              Location location = new Location("your destination");
                location.setLatitude(latLng.latitude);
                location.setLongitude(latLng.longitude);



                customMarker = latLng;
                dest_lat = customMarker.latitude;
                dest_lng = customMarker.longitude;
                points.add(location);
                setMarker(latLng);


                addressOfPlaces(customMarker);
            }
        });

    }







    private void setMarker(LatLng latLng){
   // LatLng userLatlng = new LatLng(location.getLatitude(),location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(address)
                .snippet("you are going there").draggable(false).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mMap.addMarker(markerOptions);


    }


    private void addressOfPlaces(LatLng latLng){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String addDate = simpleDateFormat.format(calendar.getTime());
        gcode = new Geocoder(this, Locale.getDefault());
        try{
               addresses = gcode.getFromLocation(latLng.latitude, latLng.longitude,1);
               if(!addresses.isEmpty()){
                   address = addresses.get(0).getLocality() + " " + addresses.get(0).getAddressLine(0);
                   System.out.println(addresses.get(0).getAddressLine(0));


                   if (mDatabase.addFavrtPlaces(addresses.get(0).getLocality(),addDate,addresses.get(0).getAddressLine(0),latLng.latitude,latLng.longitude)){

                       Toast.makeText(MainActivity.this, "places:"+addresses.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                   }else {

                       Toast.makeText(MainActivity.this, "places NOT FOUND:", Toast.LENGTH_SHORT).show();

                   }
               }




        }catch (IOException e){
            e.printStackTrace();

        }

    }



    public void btnClick(View view) {


        Object[] dataTransfer = new Object[2];;
        String url;
        NearPlaces getNearbyPlaceData = new NearPlaces();

        switch (view.getId()) {
            case R.id.btn_restaurant:
                // get the url from place api
                url = getUrl(currentLocation.latitude, currentLocation.longitude,"restaurant");

                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlaceData.execute(dataTransfer);
                Toast.makeText(this, "Restaurants", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_museum:
                url = getUrl(currentLocation.latitude, currentLocation.longitude, "museum");

                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlaceData.execute(dataTransfer);
                Toast.makeText(this, "Museum", Toast.LENGTH_SHORT).show();
                break;



            case R.id.btn_direction:
                dataTransfer = new Object[4];

                url = getDirectionUrl();
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                dataTransfer[2] = customMarker;
                dataTransfer[3] = new LatLng(currentLocation.latitude,currentLocation.longitude);
                FetchDirectionData getDirectionsData = new FetchDirectionData(this);
                // execute asynchronously
                getDirectionsData.execute(dataTransfer);
                break;



                case R.id.btn_cafe:
                url = getUrl(currentLocation.latitude, currentLocation.longitude, "cafe");
                dataTransfer = new Object[2];
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlaceData.execute(dataTransfer);
                Toast.makeText(this, "Cafe", Toast.LENGTH_SHORT).show();


                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            break;

            case R.id.btn_clear:
                mMap.clear();
                break;


            case R.id.btn_addfavrt:
                Intent intent2 = new Intent(this,AddActivity.class);
                startActivity(intent2);
                break;



        }
    }


    private String getDirectionUrl() {
        StringBuilder googleDirectionUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionUrl.append("origin="+currentLocation.latitude+","+currentLocation.longitude);
        googleDirectionUrl.append("&destination="+customMarker.latitude+","+customMarker.longitude);
        googleDirectionUrl.append("&key=AIzaSyBQOhueB792SnXFS0ZvfrKg6U5Nyd8Jd_0");
        Log.d("", "getDirectionUrl: "+googleDirectionUrl);
        return googleDirectionUrl.toString();
    }


    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder placeUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        placeUrl.append("location="+latitude+","+longitude);
        placeUrl.append("&radius="+RADIUS);
        placeUrl.append("&type="+nearbyPlace);
        placeUrl.append("&key=AIzaSyBQOhueB792SnXFS0ZvfrKg6U5Nyd8Jd_0");
        System.out.println(placeUrl.toString());
        return placeUrl.toString();
    }
}

