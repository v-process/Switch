package com.androidbegin.onoffzone;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class NewMapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    Button search_btn;
    EditText search_address;
    String address;
    double country;
    double area;
    Marker marker1;
    GoogleMap gmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_maps);
        setUpMapIfNeeded();

        gmap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        LatLng startingPoint = new LatLng(37.566535, 126.97796919999996);
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(startingPoint, 8));


        search_btn = (Button)findViewById(R.id.search_btn);
        search_address = (EditText)findViewById(R.id.search_address);

    }

    public void searchButton(View view) {
        address = search_address.getText().toString();



        InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        mInputMethodManager.hideSoftInputFromWindow(search_address.getWindowToken(), 0);


        if(marker1 !=null) { marker1.remove();}
        getAddress();
    }
    public void getAddress() {
        try {
            Geocoder geo = new Geocoder(this, Locale.KOREAN );
            List<Address> addr = geo.getFromLocationName(address, 10);
            //geo.getFromLocation(35.184471,129.074935, 10);
            if (addr.size() > 0) {
                country = addr.get(0).getLatitude();
                area = addr.get(0).getLongitude();
                Toast.makeText(this, "위치 : " + country + "/" + area,
                        Toast.LENGTH_SHORT).show();
                marker1 = mMap.addMarker(new MarkerOptions().position(new LatLng(country, area)).title(address));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(country, area), 18));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(country, area)));

            } else {
                Toast toast=Toast.makeText(this, "정확한 장소를 입력해주세요", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP|Gravity.LEFT, 35, 280);
                toast.show();
            }

        } catch (IOException e) {
            Toast toast=Toast.makeText(this, "    Failed to bringing location    ", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP|Gravity.LEFT, 29, 280);
            toast.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(country, area)).title(address));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(country == 0) {
            Log.d(""+country, ""+area);
            Toast.makeText(this, "장소를 검색한 후 완료해주세요", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            Log.d(""+country, ""+area);
            Intent intent = new Intent(this, FunctionActivity.class);
            intent.putExtra("latitude", country);
            intent.putExtra("longitude", area);
            intent.putExtra("address", address);
            setResult(0, intent);
            finish();
        }

        return true;
    }
}
