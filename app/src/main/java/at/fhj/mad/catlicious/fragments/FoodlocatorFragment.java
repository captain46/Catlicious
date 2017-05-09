package at.fhj.mad.catlicious.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import at.fhj.mad.catlicious.R;
import at.fhj.mad.catlicious.service.AsyncCallbackInternet;
import at.fhj.mad.catlicious.utils.HttpAsyncTask;


/**
 * @author Thomas Spitzer
 * @since 19.04.2017
 */

public class FoodlocatorFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap map;
    private MarkerOptions options = new MarkerOptions();
    private HashMap<String, LatLng> places = new HashMap<>();
    private LocationManager locManager;

    public FoodlocatorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foodlocator, container, false);
        initListeners(view);
        return view;
    }

    private void initListeners(View view) {
        locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        startLocating();
    }

    private void startLocating() {
        Log.i("LOCATING", "Start location updates. Added location listener");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //permission not granted, request it
            FragmentCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    getActivity().getApplicationContext().getResources().getInteger(R.integer.PERMISSION_REQUEST_GPS));
        } else {
            //permissions granted
            map.setMyLocationEnabled(true);
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14);
                        map.animateCamera(yourLocation);
                        updateMapMarkers(location);
                    }

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                }

                @Override
                public void onProviderEnabled(String s) {
                }

                @Override
                public void onProviderDisabled(String s) {
                }
            });

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == getActivity().getApplicationContext().getResources().getInteger(R.integer.PERMISSION_REQUEST_GPS)) {
            startLocating();
        }
    }


    private void updateMapMarkers(Location location) {
        StringBuffer url = new StringBuffer("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        url.append("location=").append(location.getLatitude()).append(",").append(location.getLongitude());
        url.append("&");
        url.append("radius=").append("1000");
        url.append("&");
        url.append("type=").append("store");
        url.append("&");
        url.append("key=").append(getActivity().getResources().getString(R.string.google_places_nearby_key));

        HttpAsyncTask httpAsyncTask = new HttpAsyncTask();
        httpAsyncTask.setCallback(new AsyncCallbackInternetServiceImpl());
        httpAsyncTask.execute(url.toString());
    }


    private class AsyncCallbackInternetServiceImpl implements AsyncCallbackInternet {
        @Override
        public void handleResponse(String s) {
            try {
                JSONObject jsonObj = new JSONObject(s);
                JSONArray results = jsonObj.getJSONArray("results");
                JSONObject place = null;
                if (results.length() > 0) {
                    for (int i = 0; i < results.length(); i++) {
                        place = results.getJSONObject(i);
                        String locationName = place.getString("name");
                        JSONObject location = place.getJSONObject("geometry").getJSONObject("location");
                        double longitude = location.getDouble("lng");
                        double latitude = location.getDouble("lat");
                        if (place.getJSONArray("types").toString().contains("food")) {
                            places.put(locationName, new LatLng(latitude, longitude));
                            Log.i("INTERNET", "Place with food found!:" + place.toString());
                        }
                        Log.i("INTERNET", "Returned data:" + place.toString());
                    }

                } else {
                    Log.i("INTERNET", "No food places found");
                }
            } catch (JSONException e) {
                Log.e("INTERNET", "Error occurred converting to JSON", e);
            }
            map.clear();
            //Add Markers
            Iterator it = places.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                options.position((LatLng) pair.getValue());
                options.title(pair.getKey().toString());
                //options.snippet("");
                map.addMarker(options);
                it.remove();
            }

        }
    }
}
