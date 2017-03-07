package com.projects.benjisora.tubapp.data.model;

import com.projects.benjisora.tubapp.data.database.TubDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Favorites class
 */
@Table(database = TubDataBase.class)
public class Favorites extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @Column
    private int id;

    @Column
    @Unique
    private int id_path;

    public Favorites() {

    }

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
