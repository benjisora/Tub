package com.projects.benjisora.tubapp.data.model;

import com.projects.benjisora.tubapp.data.database.TubDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by benjamin_saugues on 10/02/2017.
 */

@Table(database = TubDataBase.class)
public class Favorites extends BaseModel {

    @PrimaryKey
    @Column
    private int id;

    @Column
    private int id_path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_path() {
        return id_path;
    }

    public void setId_path(int id_path) {
        this.id_path = id_path;
    }
}
