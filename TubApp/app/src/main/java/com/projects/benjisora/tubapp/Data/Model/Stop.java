package com.projects.benjisora.tubapp.data.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by iem on 03/02/2017.
 */

public class Stop extends BaseModel {

    @PrimaryKey
    @Column
    private long id;

    @Column
    private boolean available;

    @Column
    private double latitude;

    @Column
    private double longitude;

}