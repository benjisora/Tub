package com.projects.benjisora.tubapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.projects.benjisora.tubapp.data.database.MyApplication;
import com.projects.benjisora.tubapp.data.model.Path;
import com.projects.benjisora.tubapp.data.model.Paths;
import com.projects.benjisora.tubapp.network.NetworkService;
import com.projects.benjisora.tubapp.ui.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private Call<Paths> call = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NetworkService networkService = MyApplication.getNetworkServiceInstance();
        call = networkService.getAllPaths();

        call.enqueue(new Callback<Paths>() {
            @Override
            public void onResponse(Call<Paths> call, Response<Paths> response) {
                /*
                if(response != null){

                    Paths p = response.body();
                    for(Path path : p.lines){
                        path.save();
                    }

                }
                */
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Paths> call, Throwable t) {
                Log.e("RetrofitError", getString(R.string.log_error), t);
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(call != null) {
            call.cancel();
        }
    }
}
