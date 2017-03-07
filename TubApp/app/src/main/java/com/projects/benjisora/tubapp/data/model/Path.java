package com.projects.benjisora.tubapp.data.model;

import com.google.gson.annotations.SerializedName;
import com.projects.benjisora.tubapp.data.database.TubDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Path class
 */
@Table(database = TubDataBase.class)
public class Path extends BaseModel {

    @PrimaryKey
    @Column
    @SerializedName("id")
    private int id;

    @Column
    @SerializedName("label")
    private String label;

    @Column
    private int number;

    @Column
    @SerializedName("color")
    private String color;

    @Column
    @SerializedName("order")
    private int order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    int getOrder() {
        return order;
    }

    void setOrder(int order) {
        this.order = order;
    }

    public int getNumber() {
        return number;
    }

    void setNumber(int number) {
        this.number = number;
    }
}