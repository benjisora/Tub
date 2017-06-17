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

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Utils class, used to store the call functions to the database easily
 */
public class Utils {

    private static Utils instanceUtils;

    /**
     * Gets the class instance
     *
     * @return Util's instance
     */
    public static Utils getinstance() {
        if (instanceUtils == null) {
            instanceUtils = new Utils();
        }
        return instanceUtils;
    }

    /**
     * Database query to get all the paths
     *
     * @return All the stored paths
     */
    public List<Path> getAllPaths() {
        return SQLite.select()
                .from(Path.class)
                .queryList();
    }

    /**
     * Database query to get a path from its id
     *
     * @param id The path id
     * @return The corresponding path
     */
    public Path getPath(int id) {
        return SQLite.select()
                .from(Path.class)
                .where(Path_Table.id.eq(id))
                .querySingle();
    }

    /**
     * Database query to get all the stops
     *
     * @return All the stored stops
     */
    public List<Stop> getAllStops() {
        return SQLite.select()
                .from(Stop.class)
                .queryList();
    }

    /**
     * Database query to get a stop from its coordinates
     *
     * @param latitude  The latitude of the stop
     * @param longitude The longitude of the stop
     * @return The corresponding stop
     */
    /*
    public Stop getStop(double latitude, double longitude) {
        return SQLite.select()
                .from(Stop.class)
                .where(Stop_Table.latitude.eq(latitude))
                .and(Stop_Table.longitude.eq(longitude))
                .querySingle();
    }
    */

    /**
     * Database query to get a stops from their corresponding path id
     *
     * @param id The path id
     * @return The corresponding stops
     */
    public List<Stop> getStopForPath(int id) {
        return SQLite.select()
                .from(Stop.class)
                .where(Stop_Table.id
                        .in(
                                SQLite.select(StopGroup_Table.stop_id)
                                        .from(StopGroup.class)
                                        .where(StopGroup_Table.line_id.eq(id))
                        )
                )
                .queryList();
    }

    public List<Path> getLineforStop(int id){
        return SQLite.select()
                .from(Path.class)
                .where(Path_Table.id
                .in(
                        SQLite.select(StopGroup_Table.line_id)
                        .from(StopGroup.class)
                        .where(StopGroup_Table.stop_id.eq(id))
                ))
                .queryList();
    }

    /**
     * Database query to get all the favorites
     *
     * @return All the stored favorites
     */
    public List<Favorites> getFavorites() {
        return SQLite.select()
                .from(Favorites.class)
                .queryList();
    }

    /**
     * Database query to get a favorite from its id
     *
     * @param id The favorite id
     * @return The corresponding favorite
     */
    public Favorites getFavorite(int id) {
        return SQLite.select()
                .from(Favorites.class)
                .where(Favorites_Table.id_path.eq(id))
                .querySingle();
    }

    /**
     * Database query to get a favorite from its id
     *
     * @param id The path id
     * @return true if the path is favorited, false otherwise
     */
    public Boolean pathIsFav(int id) {
        Favorites fav = SQLite.select()
                .from(Favorites.class)
                .where(Favorites_Table.id_path.eq(id))
                .querySingle();
        return fav != null;
    }
}