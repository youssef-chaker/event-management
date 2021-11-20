package com.example.blabla.ui.main;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blabla.R;
import com.example.blabla.viewmodel.MainViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {

    private MainViewModel mViewModel;
    private Button loginButton;
    private EditText email;
    private EditText password;
    private Button registerButton;
    private static final String TAG = "LoginFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        loginButton = view.findViewById(R.id.loginButton);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        registerButton = view.findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
        });

        loginButton.setOnClickListener(v -> {
            mViewModel.login(email.getText().toString(),password.getText().toString());

            //todo fix getting called twice everytime !
            mViewModel.message.observe(getViewLifecycleOwner(), msg -> {
                int color = msg.success ? Color.GREEN : Color.rgb(255,148,148) ;
                Log.i(TAG, "oooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
                Snackbar.make(view,msg.msg, BaseTransientBottomBar.LENGTH_LONG).setAnchorView(R.id.bottomNavigationView).setTextColor(color).show();
            });

        });


        mViewModel.token.observe(getViewLifecycleOwner(),token -> {
            ((TextView)view.findViewById(R.id.loggedIn)).setText(token.getAccess_token());
            Log.i(TAG, "onActivityCreated:  token changed");
//            if(token.getAccess_token().length() > 2 && mViewModel.avatar.getValue() == null) {
//                mViewModel.getAvatar();
//                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homePageFragment);
//            }
        });
        return view;
    }



}