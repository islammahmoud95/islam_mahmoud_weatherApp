package com.task.weatherapp.ui.activities.main;

import static com.task.weatherapp.common.Utilities.GetErrorResponse;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.task.weatherapp.data.local.helper.DatabaseManager;
import com.task.weatherapp.data.local.table.CityTable;
import com.task.weatherapp.data.local.table.WeatherTable;
import com.task.weatherapp.data.network.model.ErrorsMessage;
import com.task.weatherapp.data.network.model.WeatherResponse;
import com.task.weatherapp.repository.WeatherRepository;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;

public class MainViewModel extends ViewModel {


    MutableLiveData<ArrayList<CityTable>> _cityTable = new MutableLiveData<>();
    public LiveData<ArrayList<CityTable>> cityTable = _cityTable;
    MutableLiveData<ArrayList<WeatherTable>> _weatherTableList = new MutableLiveData<>();
    public LiveData<ArrayList<WeatherTable>> weatherTableList = _weatherTableList;
    MutableLiveData<WeatherResponse> _weatherResponse = new MutableLiveData<>();
    public LiveData<WeatherResponse> weatherResponse = _weatherResponse;
    MutableLiveData<Boolean> _showProgress = new MutableLiveData<>();
    public LiveData<Boolean> showProgress = _showProgress;
    MutableLiveData<String> _errorMsg = new MutableLiveData<>();
    public LiveData<String> errorMsg = _errorMsg;
    MutableLiveData<String> _insertMsg = new MutableLiveData<>();
    public LiveData<String> insertMsg = _insertMsg;
    CompositeDisposable disposables = new CompositeDisposable();
    WeatherRepository repository;
    DatabaseManager databaseManager;
    public WeatherTable weatherTable=new WeatherTable();
    public String name="" ;

    public WeatherTable getWeatherTable(){
        return weatherTable;
    }

    public void setWeatherTable(WeatherTable weatherTable) {
        this.weatherTable = weatherTable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void Init(WeatherRepository weatherRepository, DatabaseManager databaseManager){
        repository=weatherRepository;
        this.databaseManager=databaseManager;
    }

    public void getWeather(String city) {
        disposables.add(repository.getWeather(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        _showProgress.postValue(true);
                    }
                }).subscribe(response -> {
                    _showProgress.postValue(false);
                    _weatherResponse.postValue(response);
                }, throwable -> {
                    _showProgress.postValue(false);
                    _errorMsg.postValue(throwable.getMessage().toString());
                }));

    }

    public void InsertCityList(CityTable cityTable) {
        String succese = databaseManager.InsertCity(cityTable);
        _insertMsg.postValue(succese);

    }
    public void InsertWeatherList(WeatherTable weatherTable) {
        String succese = databaseManager.InsertWeather(weatherTable);
        _insertMsg.postValue(succese);

    }
    public void getCityList() {
        _cityTable.postValue((ArrayList<CityTable>) databaseManager.getAllCityies());
    }
    public void getWeatherTables(int id) {
        _weatherTableList.postValue((ArrayList<WeatherTable>) databaseManager.getAllWeather(id));
    }

}
