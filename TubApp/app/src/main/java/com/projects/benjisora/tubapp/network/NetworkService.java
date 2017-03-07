package com.projects.benjisora.tubapp.network;


import com.projects.benjisora.tubapp.data.model.Paths;
import com.projects.benjisora.tubapp.data.model.StopGroups;
import com.projects.benjisora.tubapp.data.model.Stops;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * The NetworkService interface used to make retrofit calls to the API
 */
public interface NetworkService {

    @GET("lines")
    Call<Paths> getAllPaths();

    @GET("stops")
    Call<Stops> getAllStops();

    @GET("stopgroups")
    Call<StopGroups> getAllStopGroups();

}
