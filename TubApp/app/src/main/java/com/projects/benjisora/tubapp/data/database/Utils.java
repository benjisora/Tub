package com.projects.benjisora.tubapp.data.database;

import com.projects.benjisora.tubapp.data.model.Favorites;
import com.projects.benjisora.tubapp.data.model.Path;
import com.projects.benjisora.tubapp.data.model.Path_Table;
import com.projects.benjisora.tubapp.data.model.Stop;
import com.projects.benjisora.tubapp.data.model.StopGroups;
import com.projects.benjisora.tubapp.data.model.StopGroups_Table;
import com.projects.benjisora.tubapp.data.model.Stop_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by benjamin_saugues on 10/02/2017.
 */

public class Utils {


    private static Utils instanceUtils;

    public static Utils getinstance() {
        if (instanceUtils == null) {
            instanceUtils = new Utils();
        }
        return instanceUtils;
    }

    public List<Path> getPathAll(){
        return SQLite.select()
                .from(Path.class)
                .queryList();
    }

    public Path getPath(int id){
        return SQLite.select()
                .from(Path.class)
                .where(Path_Table.id.eq(id))
                .querySingle();
    }

    public List<Stop> getStopAll(){
        return SQLite.select()
                .from(Stop.class)
                .queryList();
    }

    public List<Stop> getStopForPath(int id){
        return SQLite.select()
                .from(Stop.class)
                .where(Stop_Table.id
                        .in(
                                SQLite.select()
                                        .from(StopGroups.class)
                                        .where(StopGroups_Table.id_line.eq(id))
                        )
                )
                .queryList();
    }

    public Stop getStop(double latitude, double longitude){
        return SQLite.select()
                .from(Stop.class)
                .where(Stop_Table.latitude.eq(latitude))
                .and(Stop_Table.longitude.eq(longitude))
                .querySingle();
    }

    //MARK:Favorites table query

    public List<Favorites> getFavorites(){
        return SQLite.select()
                .from(Favorites.class)
                .queryList();
    }
}