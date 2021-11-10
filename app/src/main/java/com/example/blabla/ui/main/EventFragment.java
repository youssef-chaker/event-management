package com.example.blabla.ui.main;

import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blabla.R;
import com.example.blabla.databinding.FragmentEventBinding;
import com.example.blabla.repo.Repository;
import com.example.blabla.viewmodel.EventViewModel;
import com.example.blabla.viewmodel.MainViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class EventFragment extends Fragment {
    private static final String TAG = "EventFragment";
    private MainViewModel mViewModel;
    private EventViewModel eventViewModel;
    private TextView title;
    private TextView description;
    private TextView address;
    private FragmentEventBinding binding;
    private Button subscribeButton;
    private String eventId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_event, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        eventId = EventFragmentArgs.fromBundle(getArguments()).getId();
        binding.setEventViewModel(eventViewModel);
        binding.setLifecycleOwner(this);
        eventViewModel.getEventById(eventId);
        title = binding.title;
        description = binding.description;
        address = binding.address;
        subscribeButton= binding.subscribe;
        subscribeButton.setOnClickListener(v -> {
            mViewModel.subscribeToEvent(eventId, new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()) {
                        Snackbar.make(binding.getRoot(),"successfull", BaseTransientBottomBar.LENGTH_LONG).setAnchorView(R.id.bottomNavigationView).setTextColor(Color.GREEN).show();
                    }else {
                        Snackbar.make(binding.getRoot(),"error", BaseTransientBottomBar.LENGTH_LONG).setAnchorView(R.id.bottomNavigationView).setTextColor(Color.RED).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Snackbar.make(binding.getRoot(),"onFailure", BaseTransientBottomBar.LENGTH_LONG).setAnchorView(R.id.bottomNavigationView).setTextColor(Color.RED).show();
                }
            });
        });
        return binding.getRoot();
    }
}