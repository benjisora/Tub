package com.projects.benjisora.tubapp.data.database;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.projects.benjisora.tubapp.network.NetworkService;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by benjamin_saugues on 03/02/2017.
 */

public class MyApplication extends Application {

    private static Retrofit retrofit;
    private static String url;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        url = SP.getString("url", "https://tub.bourgmapper.fr/api/");

        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    public static Retrofit getRetrofitInstance() {
        if(retrofit == null) {
            Gson gson = new GsonBuilder().create();
            retrofit  = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static NetworkService getNetworkServiceInstance() {
        return getRetrofitInstance().create(NetworkService.class);
    }
}