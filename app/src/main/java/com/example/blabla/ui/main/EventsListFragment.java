package com.example.blabla.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

import com.example.blabla.R;
import com.example.blabla.databinding.FragmentEventsListBinding;
import com.example.blabla.model.Event;
import com.example.blabla.viewmodel.MainViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EventsListFragment extends Fragment implements LocationListener {

    private static final String TAG = "EventsListFragment";
    int counter = 0;

    private MainViewModel mViewModel;
    private FragmentEventsListBinding binding;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private LocationManager locationManager;
    private FloatingActionButton fab;

    private MutableLiveData<List<Event>> eventsList;

    public static EventsListFragment newInstance() {
        return new EventsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_events_list,container,false);
        binding.setLifecycleOwner(this);

        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        fab =binding.fab;
//
//        mViewModel.message.observe(getViewLifecycleOwner(),msg -> {
//            Snackbar.make(getView(),msg.msg, BaseTransientBottomBar.LENGTH_LONG).setAnchorView(R.id.bottomNavigationView).setTextColor(Color.WHITE).show();
//        });

        eventsList = mViewModel.eventsLiveData;

        recyclerView = binding.eventsRecyclerView;
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //todo fix this shit , a lot of problems with it
        eventsList.observe(getViewLifecycleOwner(),events -> {
            if(counter++ == 0 ) return;
            Log.i(TAG, "onActivityCreated: events list observer");
            if(events.size() > 0){
//                if(mViewModel.location.getValue() != null) {
//                    Log.i(TAG, ": latitude : "+mViewModel.location.getValue().latitude);
//                    Log.i(TAG, ": longtitude : "+mViewModel.location.getValue().longitude);
//                    Log.i(TAG, "onCreateView: inside if location");
//                    adapter = new EventsAdapter(eventsList.getValue(),mViewModel.location.getValue());
//                }else {
//                    adapter = new EventsAdapter(eventsList.getValue());
//                }
                if(adapter == null) {
                    Log.i(TAG, "onCreateView: adapter null");
                }
                //todo this line was inside the if statement : figure out why it only works inside the if when location is not enabled
                adapter = new EventsAdapter(eventsList.getValue());
                recyclerView.setAdapter(adapter);
            }
        });


        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},99);
        }

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Navigation.findNavController(getView()).navigate(R.id.action_eventsListFragment_to_homePageFragment);
        }

        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            new AlertDialog.Builder(requireContext()).setMessage("you must enable your gps")
                    .setNegativeButton("cancel",(dialog, which) -> {
                        mViewModel.getEvents();
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

        fab.setOnClickListener(v -> {
            Navigation.findNavController(getView()).navigate(R.id.action_eventsListFragment_to_addEventFragment);
        });


        return binding.getRoot();
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.i(TAG, "onLocationChanged: ");
        Log.i(TAG, "onLocationChanged: latitude : "+location.getLatitude());
        Log.i(TAG, "onLocationChanged: longtitude : "+location.getLongitude());
        mViewModel.location.setValue(new LatLng(location.getAltitude(),location.getLongitude()));
        mViewModel.getEventsNearMe(location.getLongitude(),location.getLatitude());
        adapter = new EventsAdapter(eventsList.getValue(),new LatLng(location.getLatitude(),location.getLongitude()));
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Log.i(TAG, "onProviderEnabled: ");
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Log.i(TAG, "onProviderDisabled: ");
//        Navigation.findNavController(requireView()).navigate(R.id.action_eventsListFragment_to_homePageFragment);
    }
}