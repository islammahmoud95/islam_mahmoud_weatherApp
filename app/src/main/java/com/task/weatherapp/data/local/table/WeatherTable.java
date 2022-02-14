package com.task.weatherapp.data.local.table;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.text.SimpleDateFormat;

@DatabaseTable(tableName = "weather_info")
public class WeatherTable implements Serializable {

    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(columnName = "description" )
    private String description;
    @DatabaseField(columnName = "temp" )
    private String temp;
    @DatabaseField(columnName = "humidity" )
    private String humidity;
    @DatabaseField(columnName = "speed" )
    private String speed;
    @DatabaseField(columnName = "icon" )
    private String icon;
    @DatabaseField(columnName = "created_at" )
    private String created_at;
    @DatabaseField(columnName = "cityId" )
    private int city;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemp() {
        String te=temp;
        if (temp!=null)
        {
             te=String.valueOf(Math.round((Double.parseDouble(temp)-273.15)* 100.0) / 100.0);
        }
        return te;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
