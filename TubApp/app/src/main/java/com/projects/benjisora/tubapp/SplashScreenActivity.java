package com.projects.benjisora.tubapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.projects.benjisora.tubapp.data.database.MyApplication;
import com.projects.benjisora.tubapp.data.model.Path;
import com.projects.benjisora.tubapp.data.model.Paths;
import com.projects.benjisora.tubapp.data.model.Stop;
import com.projects.benjisora.tubapp.data.model.StopGroup;
import com.projects.benjisora.tubapp.data.model.StopGroups;
import com.projects.benjisora.tubapp.data.model.Stops;
import com.projects.benjisora.tubapp.network.NetworkService;
import com.projects.benjisora.tubapp.ui.MainActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private Call<Paths> callAllPaths = null;
    private Call<Stops> callAllStops = null;
    private Call<StopGroups> callAllStopGroups = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (shouldWeUpdate()) {
            final NetworkService networkService = MyApplication.getNetworkServiceInstance();

            callAllPaths = networkService.getAllPaths();
            callAllPaths.enqueue(new Callback<Paths>() {
                @Override
                public void onResponse(Call<Paths> call, Response<Paths> response) {
                    updateSucceededAllPaths(response);
                    callAllStops = networkService.getAllStops();
                    callAllStops.enqueue(new Callback<Stops>() {
                        @Override
                        public void onResponse(Call<Stops> call, Response<Stops> response) {
                            updateSucceededAllStops(response);
                            callAllStopGroups = networkService.getAllStopGroups();
                            callAllStopGroups.enqueue(new Callback<StopGroups>() {
                                @Override
                                public void onResponse(Call<StopGroups> call, Response<StopGroups> response) {
                                    updateSucceededAllStopGroups(response);
                                }
                                @Override
                                public void onFailure(Call<StopGroups> call, Throwable t) {
                                    updateFailed(t);
                                }
                            });
                        }
                        @Override
                        public void onFailure(Call<Stops> call, Throwable t) {
                            updateFailed(t);
                        }
                    });
                }
                @Override
                public void onFailure(Call<Paths> call, Throwable t) {
                    updateFailed(t);
                }
            });



        } else {
            startIntentAndFinish(new Intent(SplashScreenActivity.this, MainActivity.class));
        }

    }

    public boolean shouldWeUpdate() {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        return SP.getBoolean("udpates", true);
    }

    public void updateSucceededAllPaths(Response<Paths> response) {
        if(response.code() == 200){
            Paths p = response.body();
            Pattern pat = Pattern.compile("\\: (.*)");
            for(Path path : p.lines){
                Matcher m = pat.matcher(path.getLabel());
                if(m.find())
                    path.setLabel(m.group(1));
                path.save();
            }
        }
    }

    public void updateSucceededAllStops(Response<Stops> response) {
        if(response.code() == 200){
            Stops p = response.body();
            System.out.println(response.body());
            for(Stop stop : p.stops){
                stop.save();
            }
        }
    }

    public void updateSucceededAllStopGroups(Response<StopGroups> response) {
        if(response.code() == 200){
            StopGroups p = response.body();
            System.out.println(response.body());
            for(StopGroup stopgroup : p.stopgroups){
                stopgroup.save();
            }
        }
        startIntentAndFinish(new Intent(SplashScreenActivity.this, MainActivity.class));
    }


    public void updateFailed(Throwable t) {
        Log.e("RetrofitError", getString(R.string.log_error), t);
        Toast.makeText(this, R.string.error_fetching_data, Toast.LENGTH_SHORT).show();

        startIntentAndFinish(new Intent(SplashScreenActivity.this, MainActivity.class));
    }

    public void startIntentAndFinish(Intent intent) {
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (callAllPaths != null) {
            callAllPaths.cancel();
        }
    }
}
