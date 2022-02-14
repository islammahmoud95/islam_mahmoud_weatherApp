package com.task.weatherapp.ui.fragments.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.task.weatherapp.R;
import com.task.weatherapp.app.WeatherApp;
import com.task.weatherapp.data.local.helper.DatabaseManager;
import com.task.weatherapp.data.local.table.CityTable;
import com.task.weatherapp.data.local.table.WeatherTable;
import com.task.weatherapp.data.network.model.ErrorsMessage;
import com.task.weatherapp.data.network.model.WeatherResponse;
import com.task.weatherapp.databinding.FragmentHomeBinding;
import com.task.weatherapp.databinding.LayoutAddCityBinding;
import com.task.weatherapp.interfaces.ClickedItem;
import com.task.weatherapp.ui.activities.MainActivity;
import com.task.weatherapp.ui.activities.main.MainViewModel;
import com.task.weatherapp.ui.adapters.CitiesAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements ClickedItem {

    private MainViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private FragmentActivity activity;
    private CitiesAdapter citiesAdapter;
    private ArrayList<CityTable> cityTables;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (getActivity()!=null)
            activity=getActivity();
        homeViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setViewModel(homeViewModel);
        homeViewModel.Init(((WeatherApp) activity.getApplication()).weatherRepository,DatabaseManager.getInstance(activity));
        InitObservable();
        cityTables=new ArrayList<>();
        binding.listItem.setLayoutManager(new LinearLayoutManager(activity));
        citiesAdapter=new CitiesAdapter(activity,cityTables,this);
        binding.listItem.setAdapter(citiesAdapter);
        DividerItemDecoration decoration= new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL);
        binding.listItem.addItemDecoration(decoration);
        return binding.getRoot();
    }

    private void InitObservable(){
        homeViewModel.errorMsg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errorsMessage) {
                Toast.makeText(activity, errorsMessage, Toast.LENGTH_LONG).show();
            }
        });
        homeViewModel.showProgress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean it) {
                ((MainActivity)activity).ShowProgress(it);
            }
        });
        homeViewModel.insertMsg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String msg) {
                if (!msg.isEmpty())
                Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
                homeViewModel.getCityList();
            }
        });

        homeViewModel.cityTable.observe(getViewLifecycleOwner(), new Observer<ArrayList<CityTable>>() {
            @Override
            public void onChanged(ArrayList<CityTable> weatherResponse) {
                cityTables.clear();
                cityTables.addAll(weatherResponse);
                citiesAdapter.notifyDataSetChanged();
            }
        });
        homeViewModel.weatherResponse.observe(getViewLifecycleOwner(), new Observer<WeatherResponse>() {
            @Override
            public void onChanged(WeatherResponse weatherResponse) {
               if (weatherResponse.getCod()==200){
                   CityTable cityTable=new CityTable();
                   cityTable.setName(weatherResponse.getName());
                   homeViewModel.InsertCityList(cityTable);
               }
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                AddCityDialog();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void AddCityDialog(){

        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();

        LayoutAddCityBinding layoutAddCityBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.layout_add_city, null,false);
        alertDialog.setView(layoutAddCityBinding.getRoot());
        layoutAddCityBinding.dismiss.setOnClickListener(view -> {
            alertDialog.dismiss();
        });
        layoutAddCityBinding.add.setOnClickListener(view -> {
            if (layoutAddCityBinding.cityName.getText()!=null) {
                homeViewModel.getWeather(layoutAddCityBinding.cityName.getText().toString());
                alertDialog.dismiss();

            }
            else  layoutAddCityBinding.cityName.setError("Please write city name");
        });

        alertDialog.show();
    }


    @Override
    public void onResume() {
        homeViewModel.getCityList();
        super.onResume();
    }

    @Override
    public void Item(int type, int pos, int id) {
        NavController navController = NavHostFragment.findNavController(this);
        switch (type){
            case 1:
                navController.navigate(HomeFragmentDirections.actionNavigationHomeToNavigationMoreInfo(cityTables.get(pos).getName().toString(),id,null));
                break;
            case 2:
                navController.navigate(HomeFragmentDirections.actionNavigationHomeToNavigationDashboard(id,cityTables.get(pos).getName()));
                break;
        }

    }
}