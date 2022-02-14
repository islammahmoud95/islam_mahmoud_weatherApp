package com.task.weatherapp.ui.activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.task.weatherapp.R;
import com.task.weatherapp.data.local.helper.DatabaseManager;
import com.task.weatherapp.data.local.table.CityTable;
import com.task.weatherapp.databinding.ActivityMainBinding;
import com.task.weatherapp.databinding.LayoutAddCityBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    protected Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolBar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_more_info)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.toolBar, navController, appBarConfiguration);
        setTitle(navController.getCurrentDestination().getLabel());

    }




    public void ShowProgress (Boolean it){
        if (it)
            showProgressDialog();
        else hideProgressDialog();

    }
    public void  showProgressDialog() {
        hideProgressDialog();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this).setCancelable(false);
        alertDialogBuilder.setView(R.layout.progress_dialog_loader);
        progressDialog = alertDialogBuilder.create();
        progressDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        progressDialog.show();
    }

    public void  hideProgressDialog() {
        if (progressDialog!=null)
         progressDialog.cancel();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}