package com.projects.benjisora.tubapp.data.model;

import com.projects.benjisora.tubapp.data.database.TubDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * StopGroup class, linking Paths and Stops
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
    private int stop_id;

    @Column
    private int line_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getWay() {
        return way;
    }

    void setWay(String way) {
        this.way = way;
    }

    int getOrder() {
        return order;
    }

    void setOrder(int order) {
        this.order = order;
    }

    int getStop_id() {
        return stop_id;
    }

    void setStop_id(int stop_id) {
        this.stop_id = stop_id;
    }

    int getLine_id() {
        return line_id;
    }

    void setLine_id(int line_id) {
        this.line_id = line_id;
    }
}
