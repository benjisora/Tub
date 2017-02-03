package com.projects.benjisora.tubapp.data.model;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by benjamin_saugues on 03/02/2017.
 */

public class Path extends BaseModel {

    @PrimaryKey
    @Column
    @SerializedName("id")
    private long id;

    @Column
    @SerializedName("label")
    private String label;

    @Column
    @SerializedName("color")
    private String color;

    @Column
    @SerializedName("order")
    private int order;

    public long getId() {
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}