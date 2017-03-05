package com.projects.benjisora.tubapp.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.kml.KmlLayer;
import com.projects.benjisora.tubapp.R;
import com.projects.benjisora.tubapp.UtilsDrawable;
import com.projects.benjisora.tubapp.data.database.Utils;
import com.projects.benjisora.tubapp.data.model.Path;
import com.projects.benjisora.tubapp.data.model.Stop;
import com.projects.benjisora.tubapp.ui.MainActivity;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by benjamin_saugues on 02/11/2016.
 */

public class MapsFragment extends Fragment {

    @BindView(R.id.mapView)
    MapView mMapView;

    @BindView(R.id.spinner)
    Spinner spinner;

    private KmlLayer layer;
    private GoogleMap googleMap;
    int id_path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, rootView);

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        id_path = SP.getInt("idToShow", 1);

        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("All lines");
        for(int i = 1; i < Utils.getinstance().getAllPaths().size(); i++){
            spinnerArray.add("Line " + i);
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setSelection(1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(layer != null){
                    layer.removeLayerFromMap();
                    mMapView.invalidate();
                }
                if(position==0){
                } else {
                    Log.d("MAP", String.valueOf(position));
                    SP.edit().putInt("idToShow", position).apply();
                    Path path = Utils.getinstance().getPath(id_path);
                    if (path != null) {
                        Log.d("MAP", "path valide");
                        List<Stop> stopList = Utils.getinstance().getStopForPath(id_path);
                        drawKml("path" + id_path);
                        for (Stop stop : stopList) {
                            LatLng stopCoord = new LatLng(stop.getLatitude(), stop.getLongitude());
                            addMapMarker(stopCoord, stop.getLabel(), path.getColor());
                        }
                    } else {
                        Log.d("MAP","probleme de path");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                spinner.setSelection(1);
            }
        });

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                LatLng bourg = new LatLng(46.2053457, 5.2260777);

                CameraPosition cameraPosition = new CameraPosition.Builder().target(bourg).zoom(13).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;
    }

    private void drawKml(String kmlname) {
        Context context = getActivity().getApplicationContext();
        Resources res = context.getResources();
        int kmlId = res.getIdentifier(kmlname, "raw", context.getPackageName());
        try {
            layer = new KmlLayer(googleMap, kmlId, context);
            layer.addLayerToMap();
        } catch (IOException | XmlPullParserException e) {
            Log.d("MapsFragment", "drawKml: UNABLE TO LOAD KML");
        }
    }

    private void addMapMarker(LatLng coord, String name, String color) {
        Context context = getActivity().getApplicationContext();
        Resources res = context.getResources();
        Drawable busIcon = res.getDrawable(R.drawable.bus_stop_icon, context.getTheme());
        //busIcon.setColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
        Bitmap marker = UtilsDrawable.drawableToBitmap(busIcon);
        marker = UtilsDrawable.changeBitmapColor(marker, Color.parseColor(color));
        this.googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(marker))
                .position(coord)
                .title(name)
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
