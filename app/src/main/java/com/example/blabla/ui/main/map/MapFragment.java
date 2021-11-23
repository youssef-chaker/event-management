package com.example.blabla.ui.main.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blabla.R;
import com.example.blabla.model.Event;
import com.example.blabla.viewmodel.MainViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MapFragment extends Fragment implements OnMapReadyCallback , LocationListener {

    private MapView mapView;
    private ClusterManager<ClusterMarker> clusterManager;
    private LocationManager locationManager;
    private MainViewModel mViewModel;
    private static final String TAG = "MapFragment";

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(mViewModel.location.getValue()!=null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mViewModel.location.getValue(), 12));
        }else {
            mViewModel.location.observe(getViewLifecycleOwner(),latLng -> {
                if(latLng !=null) googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mViewModel.location.getValue(), 12));
            });
        }
        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(36.8665, 10.1647), 12));
        }
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(36.8665, 10.1647), 13));
        clusterManager = new ClusterManager<>(requireContext(),googleMap);
        clusterManager.setRenderer(new ClusterRenderer(requireContext(),googleMap,clusterManager));
        googleMap.setOnCameraIdleListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);
//        clusterManager.addItem(new ClusterMarker(new LatLng(36.8665, 10.1647),"aa","aa"));
//        clusterManager.addItem(new ClusterMarker(new LatLng(36.8665, 10.1647),"bb","bb"));
//        clusterManager.addItem(new ClusterMarker(new LatLng(36.9, 10.18),"bb","bb"));

        mViewModel.eventsLiveData.observe(getViewLifecycleOwner(),events -> {
            Log.i(TAG, "onMapReady: events observer");
            if(events != null && events.size() >= 1) {
                Log.i(TAG, "onMapReady: events observer : inside loop");
                for (Event event : events) {
                    clusterManager.addItem(new ClusterMarker(new LatLng(event.getLongLat().getLatitude(),event.getLongLat().getLongitude()),event.getTitle(),event.getDescription()));
                    clusterManager.cluster();
                }
            }
        });

//        googleMap.setOnMapClickListener(latLng -> {
//            googleMap.clear();
//            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//            googleMap.addMarker(new MarkerOptions().position(latLng));
//        });


    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        if(mViewModel.eventsLiveData.getValue() == null || mViewModel.eventsLiveData.getValue().size() < 1) {
            Log.i(TAG, "onActivityCreated: fetching events");
            if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                mViewModel.getEvents();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},99);
        }
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager)requireActivity().getSystemService(Context.LOCATION_SERVICE);

            if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                new AlertDialog.Builder(requireContext()).setMessage("you must enable your gps")
                        .setNegativeButton("cancel",(dialog, which) -> {
                        })
                        .setPositiveButton("enable",(dialog, which) -> {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,300,this);
                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,300,this);
                        }).setTitle("Alert").show();
            }else {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,300,this);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,300,this);
            }
        }

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(null);
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        mViewModel.location.setValue(new LatLng(location.getLatitude(),location.getLongitude()));
        mViewModel.getEventsNearMe(location.getLongitude(),location.getLatitude());
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        return;
    }
}