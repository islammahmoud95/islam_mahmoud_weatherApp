package com.task.weatherapp.data.local.helper;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.task.weatherapp.data.local.table.CityTable;
import com.task.weatherapp.data.local.table.WeatherTable;

import java.sql.SQLException;
import java.util.List;

public class DatabaseManager {
    private final String TAG = DatabaseManager.this.getClass().getSimpleName();
    private final Context mContext;
    private static DatabaseManager INSTANCE;
    private DatabaseHelper databaseHelper;

    private Dao<CityTable, Long> cityItemDao;
    private Dao<WeatherTable, Long> weatherItemDao;

    public DatabaseManager(Context mContext) {
        Log.i(TAG, "DatabaseManager");
        this.mContext = mContext;
        databaseHelper = OpenHelperManager.getHelper(mContext, DatabaseHelper.class);

        try {
            cityItemDao = databaseHelper.getCityTables();
            weatherItemDao = databaseHelper.getWeatherTables();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance(Context context){
        if(INSTANCE == null) INSTANCE = new DatabaseManager(context);
        return INSTANCE;
    }
    public void releaseDB(){
        if (databaseHelper != null){
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
            INSTANCE = null;
        }
    }
    public int clearAllData(){
        try {
            if (databaseHelper == null) return -1;
            databaseHelper.clearTables();
            return 0;
        }catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }


    public boolean isCityExisting(String name){
        QueryBuilder queryBuilder = cityItemDao.queryBuilder();
        boolean flag = false;
        try {
            if(queryBuilder.where().eq("name",name).countOf()>0){
                flag = true;
            }else {
                flag = false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return flag;
    }

    public String InsertCity(CityTable cityTable){
        String msg = "";
        try {
            UpdateBuilder updateBuilder = cityItemDao.updateBuilder();
            String name = cityTable.getName() != null ? cityTable.getName(): "";

            if(cityTable == null)
                return "Cant create city";

            if(!isCityExisting(name)) {
                cityItemDao.create(cityTable);
                msg ="City created successfully ";
                return msg;
            }
            else return msg;

        }catch (SQLException e){
            e.printStackTrace();
            return  e.getMessage();
        }
    }

    public int DeleteCity(String name){
        try {
            if(cityItemDao == null) return -1;
            DeleteBuilder deleteBuilder = cityItemDao.deleteBuilder();
            if(name != null || !name.isEmpty()) deleteBuilder.where().eq("name",name);
            deleteBuilder.delete();
            Log.i(TAG,"city deleted");
            return 0;
        }catch (SQLException e){
            e.printStackTrace();
            return  -1;
        }
    }



    public String InsertWeather(WeatherTable weatherTable){
        String msg = "";
        try {
            weatherItemDao.create(weatherTable);
            Log.i(TAG, "this city exist");
            msg ="Record created successfully ";

            return msg;
        }catch (SQLException e){
            e.printStackTrace();
            return  msg;
        }
    }


    public List<CityTable> getAllCityies(){
        try {
            if (cityItemDao == null)return null;
            return cityItemDao.queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public List<WeatherTable> getAllWeather(int id){
        try {
            if (weatherItemDao == null)return null;
            QueryBuilder queryBuilder = weatherItemDao.queryBuilder();

            return queryBuilder.where().eq("cityID",id).query();
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
