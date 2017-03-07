package com.projects.benjisora.tubapp.data.model;

import com.projects.benjisora.tubapp.data.database.TubDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Stop class
 */
@Table(database = TubDataBase.class)
public class Stop extends BaseModel {

    @PrimaryKey
    @Column
    private int id;

    @Column
    private String label;

    @Column
    private double latitude;

    @Column
    private double longitude;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
