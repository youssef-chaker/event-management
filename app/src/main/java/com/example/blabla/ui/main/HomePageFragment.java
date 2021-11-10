package com.example.blabla.ui.main;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.blabla.R;
import com.example.blabla.databinding.HomePageFragmentBinding;
import com.example.blabla.viewmodel.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomePageFragment extends Fragment {

    private MainViewModel mViewModel;
    private HomePageFragmentBinding binding;

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_page_fragment,container,false);
        binding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding.setMainViewModel(mViewModel);
        binding.Login.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_homePageFragment_to_loginFragment);
        });
        return binding.getRoot();
    }


}