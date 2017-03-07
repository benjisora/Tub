package com.projects.benjisora.tubapp.data.database;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.projects.benjisora.tubapp.network.NetworkService;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Application class used to create the retrofit instance
 */
public class MyApplication extends Application {

    private static Retrofit retrofit;
    private static String url;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        url = SP.getString("url", "https://tub.bourgmapper.fr/api/");
        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    /**
     * Creates the retrofit instance
     *
     * @return The retrofit instance
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    /**
     * Creates a NetworkService instance with the retrofit instance
     *
     * @return The NetworkService instance
     */
    public static NetworkService getNetworkServiceInstance() {
        return getRetrofitInstance().create(NetworkService.class);
    }
}