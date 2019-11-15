package com.example.kefeng.withyou;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    double latitude,longitude;
    private DatabaseReference databaseMapUsers;
    private Activity context;
    String[] userMessages;
    String strAdd = "";
    //DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        final Intent i = new Intent(this,MapUserListActivity.class);
        //myDB = new DatabaseHelper(this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        //databaseMapUsers = FirebaseDatabase.getInstance().getReference("users");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the latitude


                    latitude = location.getLatitude();
                    //get the longitude
                    longitude = location.getLongitude();

                    Bundle userData = getIntent().getExtras();
                    userMessages =  userData.getStringArray("transitionMessage") ;
                    String email = userMessages[0].replace(".","");
                    String userName = userMessages[1];
                    //boolean isInserted = myDB.insertData(email,userName);
                    /*if(isInserted = true){
                        Toast.makeText(MapsActivity.this,"Datain")
                    }*/
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email",email);
                    editor.putString("userName",userName);
                    editor.apply();


                    //addUsers();


                    // instantiate the LatLng class
                    LatLng latLng = new LatLng(latitude,longitude);
                    // instantiate the Geocoder class
                    Geocoder geocoder = new Geocoder(getApplicationContext());


                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        if (addresses != null) {
                            Address returnedAddress = addresses.get(0);
                            StringBuilder strReturnedAddress = new StringBuilder("");

                            for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                            }
                            strAdd = strReturnedAddress.toString();
                            //mMap.addMarker(new MarkerOptions().position(latLng).title(strAdd).snippet("\n" + "\n" +"\n" ));

                            mMap.addMarker(new MarkerOptions().position(latLng).title(strAdd).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            //mMap.addMarker(new MarkerOptions().position(latLng).title(strAdd));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
                            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){

                                @Override
                                public View getInfoWindow(Marker marker) {
                                    return null;
                                }

                                @Override
                                public View getInfoContents(Marker marker) {
                                    View v = getLayoutInflater().inflate(R.layout.info_window,null);
                                    TextView tvAddress = (TextView) v.findViewById(R.id.tv_address);

                                    LatLng ll = marker.getPosition();
                                    tvAddress.setText(marker.getTitle());

                                    return v;
                                }
                            });


                        } else {
                            Log.d("MapsActivity","not correct");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("MapsActivity","not correct");
                    }
                    /*try {
                        List<Address> addressList = geocoder.getFromLocation(latitude,longitude,1);
                        String str = addressList.get(0).getLocality() + ",";
                        str += addressList.get(0).getCountryName();

                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }*/


                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });


        }
        else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the latitude
                    double latitude = location.getLatitude();
                    //get the longitude
                    double longitude = location.getLongitude();

                    Bundle userData = getIntent().getExtras();
                    userMessages =  userData.getStringArray("transitionMessage") ;
                    System.out.println("before call");
                    String email = userMessages[0].replace(".","");
                    String userName = userMessages[1];
                    //boolean isInserted = myDB.insertData(email,userName);
                    /*if(isInserted = true){
                        Toast.makeText(MapsActivity.this,"Datain")
                    }*/
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email",email);
                    editor.putString("userName",userName);
                    editor.apply();

                    //addUsers();
                    // instantiate the LatLng class
                    LatLng latLng = new LatLng(latitude,longitude);
                    // instantiate the Geocoder class
                    Geocoder geocoder = new Geocoder(getApplicationContext());

                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        if (addresses != null) {
                            Address returnedAddress = addresses.get(0);
                            StringBuilder strReturnedAddress = new StringBuilder("");

                            for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                            }
                            strAdd = strReturnedAddress.toString();
                            //mMap.addMarker(new MarkerOptions().position(latLng).title(strAdd).snippet("\n" + "\n" +"\n" ));
                            mMap.addMarker(new MarkerOptions().position(latLng).title(strAdd).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
                            //mMap.addMarker(new MarkerOptions().position(latLng).title(strAdd));
                            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){


                                @Override
                                public View getInfoWindow(Marker marker) {
                                    return null;
                                }

                                @Override
                                public View getInfoContents(Marker marker) {
                                    View v = getLayoutInflater().inflate(R.layout.info_window,null);
                                     TextView tvAddress = (TextView) v.findViewById(R.id.tv_address);

                                    LatLng ll = marker.getPosition();
                                    tvAddress.setText(marker.getTitle());

                                    return v;

                                }
                            });

                        } else {
                            Log.d("MapsActivity","not correct");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("MapsActivity","not correct");
                    }
                    /*try {
                        List<Address> addressList = geocoder.getFromLocation(latitude,longitude,1);
                        String str = addressList.get(0).getLocality() + ",";
                        str += addressList.get(0).getCountryName();

                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }*/

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });

        }



       startMethod();


    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       System.out.println("before start the menu");
        getMenuInflater().inflate(R.menu.map_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.usersMenu:
                Intent intent = new Intent(MapsActivity.this,MapUserListActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
    /*public void addUsers(){
        String email = userMessages[0].replace(".","");
        String userName = userMessages[1];
        String currentTime = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());

        System.out.println("the user information is " + email + userName + currentTime);
        MapUser mapUser = new MapUser(userName,email,latitude,longitude,currentTime);

        databaseMapUsers.child(email).setValue(mapUser);

    }*/


public void startMethod(){

    Intent i = new Intent(this,Serv.class);
    startService(i);

    System.out.println("start the service");
}



}
