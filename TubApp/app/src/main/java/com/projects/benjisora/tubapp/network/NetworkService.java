package com.projects.benjisora.tubapp.network;


import com.projects.benjisora.tubapp.data.model.Path;
import com.projects.benjisora.tubapp.data.model.Paths;
import com.projects.benjisora.tubapp.data.model.Stops;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by benjamin_saugues on 03/02/2017.
 */

public interface NetworkService {

    @GET("lines")
    Call<Paths> getAllPaths();

    @GET("stops")
    Call<Stops> getAllStops();

}
