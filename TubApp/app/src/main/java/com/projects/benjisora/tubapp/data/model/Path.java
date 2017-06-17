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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Path path = (Path) o;

        if (id != path.id) return false;
        if (number != path.number) return false;
        if (order != path.order) return false;
        if (label != null ? !label.equals(path.label) : path.label != null) return false;
        return color != null ? color.equals(path.color) : path.color == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + number;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + order;
        return result;
    }

    void setNumber(int number) {
        this.number = number;
    }
}