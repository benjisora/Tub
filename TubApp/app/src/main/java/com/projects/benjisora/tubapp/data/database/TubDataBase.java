package com.projects.benjisora.tubapp.data.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Database class
 */
@Database(name = TubDataBase.NAME, version = TubDataBase.VERSION)
public class TubDataBase {
    public static final String NAME = "MyDataBase";
    static final int VERSION = 1;
}
