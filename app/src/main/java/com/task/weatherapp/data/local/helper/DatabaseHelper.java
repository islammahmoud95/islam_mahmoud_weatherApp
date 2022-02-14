package com.task.weatherapp.data.local.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.task.weatherapp.data.local.table.CityTable;
import com.task.weatherapp.data.local.table.WeatherTable;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper  {

    private static final String DATABASE_NAME = "weather";
    private static final int DATABASE_VERSION = 1;
    private Dao<CityTable, Long> cityTables;
    private Dao<WeatherTable, Long> weatherTables;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, CityTable.class);
            TableUtils.createTable(connectionSource, WeatherTable.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            if(checkTableExist(database,"city"))
                TableUtils.dropTable(connectionSource,CityTable.class,false);
            if(checkTableExist(database,"weather_table"))
                TableUtils.dropTable(connectionSource,WeatherTable.class,false);

            onCreate(database,connectionSource);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private boolean checkTableExist(SQLiteDatabase database, String tableName){
        Cursor c = null;
        boolean tableExist = false;
        try {
            c = database.query(tableName, null,null,null,null,null,null);
            tableExist = true;
        }catch (Exception e){

        }
        return tableExist;
    }

    public Dao<CityTable, Long> getCityTables() throws SQLException{
        if(cityTables == null){
            cityTables = getDao(CityTable.class);
        }
        return cityTables;
    }

    public Dao<WeatherTable, Long> getWeatherTables() throws SQLException{
        if(weatherTables == null){
            weatherTables = getDao(WeatherTable.class);
        }
        return weatherTables;
    }

    @Override
    public void close() {
        weatherTables = null;
        cityTables = null;
        super.close();
    }

    public void clearTables() throws SQLException{
        TableUtils.clearTable(getConnectionSource(),CityTable.class);
        TableUtils.clearTable(getConnectionSource(),WeatherTable.class);
    }



}
