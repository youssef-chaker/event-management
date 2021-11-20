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
import android.widget.TextView;

import com.example.blabla.R;
import com.example.blabla.room.entity.User;
import com.example.blabla.viewmodel.MainViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpFragment extends Fragment {

    private MainViewModel mViewModel;
    private Button registerButton;
    private EditText email;
    private EditText username;
    private EditText password;
    private Button loginButton;

    private static final String TAG = "SignUpFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        registerButton = view.findViewById(R.id.registerButton);
        loginButton = view.findViewById(R.id.loginButton);
        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);

        loginButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
        });

        registerButton.setOnClickListener(v -> {
            mViewModel.signUp(username.getText().toString(),email.getText().toString(), password.getText().toString());

            //todo fix getting called twice everytime !
            mViewModel.message.observe(getViewLifecycleOwner(), msg -> {
                int color = msg.success ? Color.GREEN : Color.rgb(255, 148, 148);
                Log.i(TAG, "oooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
                Snackbar.make(view, msg.msg, BaseTransientBottomBar.LENGTH_LONG).setAnchorView(R.id.bottomNavigationView).setTextColor(color).show();
            });

        });


//        mViewModel.token.observe(getViewLifecycleOwner(), token -> {
//            if (token.getToken().length() > 2 && mViewModel.avatar.getValue() == null) {
//                mViewModel.getAvatar();
//                Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_homePageFragment);
//            }
//        });
       return view;
    }


}