package com.projects.benjisora.tubapp.data.database;

import com.projects.benjisora.tubapp.data.model.Favorites;
import com.projects.benjisora.tubapp.data.model.Favorites_Table;
import com.projects.benjisora.tubapp.data.model.Path;
import com.projects.benjisora.tubapp.data.model.Path_Table;
import com.projects.benjisora.tubapp.data.model.Stop;
import com.projects.benjisora.tubapp.data.model.StopGroup;
import com.projects.benjisora.tubapp.data.model.StopGroup_Table;
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

    //QUERY: Path
    public List<Path> getAllPaths(){
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

    //QUERY: Stop
    public List<Stop> getAllStops(){
        return SQLite.select()
                .from(Stop.class)
                .queryList();
    }

    public List<Stop> getStopForPath(int id){
        return SQLite.select()
                .from(Stop.class)
                .where(Stop_Table.id
                        .in(
                                SQLite.select(StopGroup_Table.id)
                                        .from(StopGroup.class)
                                        .where(StopGroup_Table.id_line.eq(id))
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

    //QUERY Favorite
    public List<Favorites> getFavorites(){
        return SQLite.select()
                .from(Favorites.class)
                .queryList();
    }

    public Boolean pathIsFav(int id){
        Favorites fav = SQLite.select()
                .from(Favorites.class)
                .where(Favorites_Table.id_path.eq(id))
                .querySingle();
        if(fav != null){
            return true;
        }
        return false;
    }
}