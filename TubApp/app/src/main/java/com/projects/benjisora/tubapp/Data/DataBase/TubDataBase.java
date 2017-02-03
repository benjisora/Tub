package com.projects.benjisora.tubapp.data.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by iem on 03/02/2017.
 */

@Database(name = TubDataBase.NAME, version = TubDataBase.VERSION)
public class TubDataBase {
    public static final String NAME = "MyDataBase";
    public static final int VERSION = 1;
}
