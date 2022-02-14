package com.task.weatherapp.data.local.table;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;


@DatabaseTable(tableName = "city")
public class CityTable {

    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(columnName = "name")
    private String name;


    public CityTable(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
