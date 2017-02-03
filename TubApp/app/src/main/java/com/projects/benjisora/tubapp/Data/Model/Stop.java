package com.projects.benjisora.tubapp.data.model;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by benjamin_saugues on 03/02/2017.
 */

public class Stop extends BaseModel {

    @PrimaryKey
    @Column
    @SerializedName("id")
    private long id;

    @Column
    @SerializedName("available")
    private boolean available;

    @Column
    @SerializedName("latitude")
    private double latitude;

    @Column
    @SerializedName("longitude")
    private double longitude;

}