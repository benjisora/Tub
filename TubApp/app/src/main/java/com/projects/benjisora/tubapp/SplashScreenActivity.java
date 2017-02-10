package com.projects.benjisora.tubapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.projects.benjisora.tubapp.data.database.MyApplication;
import com.projects.benjisora.tubapp.data.model.Paths;
import com.projects.benjisora.tubapp.network.NetworkService;
import com.projects.benjisora.tubapp.ui.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private Call<Paths> call = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (shouldWeUpdate()) {
            NetworkService networkService = MyApplication.getNetworkServiceInstance();
            call = networkService.getAllPaths();
            call.enqueue(new Callback<Paths>() {
                @Override
                public void onResponse(Call<Paths> call, Response<Paths> response) {
                    updateSucceeded(response);
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

    public void updateSucceeded(Response<Paths> response) {
        /*
        if(response != null){
            Paths p = response.body();
            for(Path path : p.lines){
                path.save();
            }
        }
        */
        startIntentAndFinish(new Intent(SplashScreenActivity.this, MainActivity.class));
    }

    public void updateFailed(Throwable t) {
        Log.e("RetrofitError", getString(R.string.log_error), t);
        startIntentAndFinish(new Intent(SplashScreenActivity.this, MainActivity.class));
    }

    public void startIntentAndFinish(Intent intent) {
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (call != null) {
            call.cancel();
        }
    }
}
