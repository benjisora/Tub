package com.projects.benjisora.tubapp.data.model;

import com.google.gson.annotations.SerializedName;
import com.projects.benjisora.tubapp.data.database.TubDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by benjamin_saugues on 10/02/2017.
 */

@Table(database = TubDataBase.class)
public class StopGroup extends BaseModel {

    @PrimaryKey
    @Column
    private int id;

    @Column
    private String way;

    @Column
    private int order;

    @Column
    @SerializedName("stop_id")
    private int id_stop;

    @Column
    @SerializedName("line_id")
    private int id_line;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getId_stop() {
        return id_stop;
    }

    public void setId_stop(int id_stop) {
        this.id_stop = id_stop;
    }

    public int getId_line() {
        return id_line;
    }

    public void setId_line(int id_line) {
        this.id_line = id_line;
    }
}
