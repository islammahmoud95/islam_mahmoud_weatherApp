package com.task.weatherapp.ui.fragments.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
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
import com.task.weatherapp.databinding.FragmentDashboardBinding;
import com.task.weatherapp.interfaces.ClickedItem;
import com.task.weatherapp.ui.activities.MainActivity;
import com.task.weatherapp.ui.activities.main.MainViewModel;
import com.task.weatherapp.ui.adapters.CitiesAdapter;
import com.task.weatherapp.ui.adapters.HistoricallCitiesAdapter;
import com.task.weatherapp.ui.fragments.home.HomeFragmentDirections;

import java.util.ArrayList;
import java.util.Calendar;


public class DashboardFragment extends Fragment implements ClickedItem {

    private MainViewModel homeViewModel;
    private FragmentDashboardBinding binding;
    private FragmentActivity activity;
    int id =0;
    String name ="";
    public ArrayList<WeatherTable> weatherTables;
    public HistoricallCitiesAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (getActivity()!=null)
            activity=getActivity();
        id=DashboardFragmentArgs.fromBundle(getArguments()).getId();
        name=DashboardFragmentArgs.fromBundle(getArguments()).getName();
        activity.setTitle(name+" "+getResources().getString(R.string.hitoricall));
        homeViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        binding.setViewModel(homeViewModel);
        homeViewModel.Init(((WeatherApp) activity.getApplication()).weatherRepository, DatabaseManager.getInstance(activity));

        weatherTables=new ArrayList<>();
        binding.listItem.setLayoutManager(new LinearLayoutManager(activity));
        adapter=new HistoricallCitiesAdapter(activity,weatherTables,this);
        binding.listItem.setAdapter(adapter);
        DividerItemDecoration decoration= new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL);
        binding.listItem.addItemDecoration(decoration);
        InitObservable();
        return binding.getRoot();
    }
    private void InitObservable(){
        homeViewModel.showProgress.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean it) {
                ((MainActivity)activity).ShowProgress(it);
            }
        });
        homeViewModel.errorMsg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errorsMessage) {
                Toast.makeText(activity, errorsMessage, Toast.LENGTH_LONG).show();
            }
        });
        homeViewModel.insertMsg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String msg) {
                Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
                homeViewModel.getCityList();
            }
        });

        homeViewModel.weatherTableList.observe(getViewLifecycleOwner(), new Observer<ArrayList<WeatherTable>>() {
            @Override
            public void onChanged(ArrayList<WeatherTable> weatherTable) {
                weatherTables.clear();
                weatherTables.addAll(weatherTable);
                adapter.notifyDataSetChanged();
            }
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void Item(int type, int pos, int id) {
        NavController navController = NavHostFragment.findNavController(this);

        navController.navigate(DashboardFragmentDirections.actionNavigationDashboardToNavigationMoreInfo(name,id,weatherTables.get(pos)));

    }

    @Override
    public void onResume() {
        super.onResume();
        homeViewModel.getWeatherTables(id);
    }
}