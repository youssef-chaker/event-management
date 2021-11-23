package com.example.blabla.ui.main;

import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blabla.R;
import com.example.blabla.databinding.FragmentAddEventBinding;
import com.example.blabla.model.Event;
import com.example.blabla.model.Location;
import com.example.blabla.model.LongLat;
import com.example.blabla.viewmodel.EventViewModel;
import com.example.blabla.viewmodel.MainViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class AddEventFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private MainViewModel mViewModel;
    private EventViewModel eventViewModel;
    private Button locationButton;
    private Button addButton;
    private TextView location;
    private TextView lnglat;
    private double longtitude;
    private double latitude;
    private EditText title;
    private EditText description;
    private FloatingActionButton fab;
    private FragmentAddEventBinding binding;
    private static final String TAG = "AddEventFragment";

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.setOnMapClickListener(latLng -> {
            googleMap.clear();
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.addMarker(new MarkerOptions().position(latLng));
            fab.setEnabled(true);
            lnglat.setText(latLng.longitude+ " : "+latLng.latitude);
            longtitude = latLng.longitude;
            latitude = latLng.latitude;
            eventViewModel.longtitude.setValue(latLng.longitude);
            eventViewModel.latitude.setValue(latLng.latitude);
            try {
                String addr = new Geocoder(requireContext()).getFromLocation(latLng.latitude,latLng.longitude,1).get(0).getAddressLine(0);
                location.setText(addr);
                eventViewModel.address.setValue(addr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static AddEventFragment newInstance() {
        return new AddEventFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        Log.i(TAG, "onCreateView: ");
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_event,container,false);
        binding.setLifecycleOwner(this);

        mapView = binding.mapView;
        fab = binding.confirm;
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        binding.setEventViewModel(eventViewModel);
        title = binding.title;
        description = binding.description;
        location = binding.location;
        lnglat= binding.lnglat;
        mapView.onCreate(null);
        mapView.getMapAsync(this);
        locationButton = binding.locationButton;
        addButton = binding.add;
        locationButton.setOnClickListener(v -> {
            ((MotionLayout)binding.motionLayout).transitionToEnd();
        });
        fab.setOnClickListener(v -> {
            ((MotionLayout)binding.motionLayout).transitionToStart();
        });

        addButton.setOnClickListener(v -> {
            Event event = new Event(title.getText().toString(),description.getText().toString(), location.getText().toString(),new LongLat(longtitude,latitude));
            mViewModel.postEvent(event, new Callback<Event>() {
                @Override
                public void onResponse(Call<Event> call, Response<Event> response) {
                    Log.i(TAG, "onResponse: code "+response.code());
                    Log.i(TAG, "onResponse: successful "+response.isSuccessful());
                    if(!response.isSuccessful()) {
                        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show();
                    }else {
                        Log.i(TAG, "onResponse: navigating");
                        Navigation.findNavController(requireView()).navigate(AddEventFragmentDirections.actionAddEventFragmentToEventFragment(response.body().getId()));
                    }
                }

                @Override
                public void onFailure(Call<Event> call, Throwable t) {
                    Log.i(TAG, "onFailure: ");
                    Toast.makeText(requireContext(), "on failure", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        });

        return binding.getRoot();
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
        Log.i(TAG, "onResume: ");
        Log.i(TAG, "onResume: "+eventViewModel.address.getValue());
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
}