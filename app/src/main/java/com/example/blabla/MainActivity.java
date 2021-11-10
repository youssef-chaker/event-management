package com.example.blabla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.blabla.model.Token;
import com.example.blabla.viewmodel.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mViewModel =new ViewModelProvider(this).get(MainViewModel.class);

        setSupportActionBar(findViewById(R.id.toolbar));
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        mViewModel.token.observe(this,token -> {
            Log.i(TAG, "token observer in mainactivity");
            if(token.getToken()!=null && !token.getToken().equals("")) {
                bottomNavigationView.getMenu().findItem(R.id.loginFragment).setVisible(false);
                MenuItem logoutItem = bottomNavigationView.getMenu().findItem(R.id.logoutItem);
                logoutItem.setVisible(true);
                logoutItem.setOnMenuItemClickListener(item -> {
                    mViewModel.logout();
                    mViewModel.token.postValue(new Token());
                    mViewModel.avatar.postValue(null);
                    Snackbar.make(findViewById(R.id.toolbar),"Logged Out", BaseTransientBottomBar.LENGTH_LONG).setAnchorView(R.id.bottomNavigationView).setTextColor(Color.GREEN).show();
                    return true;
                });
            } else {
                bottomNavigationView.getMenu().findItem(R.id.loginFragment).setVisible(true);
                bottomNavigationView.getMenu().findItem(R.id.logoutItem).setVisible(false);
            }
        });

        NavController navController = Navigation.findNavController(findViewById(R.id.navigationFragment));
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }


    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ((MenuBuilder)menu).setOptionalIconsVisible(true);
        getMenuInflater().inflate(R.menu.top_app_bar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}