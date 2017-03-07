package com.projects.benjisora.tubapp.fragment;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.kml.KmlLayer;
import com.projects.benjisora.tubapp.R;
import com.projects.benjisora.tubapp.UtilsDrawable;
import com.projects.benjisora.tubapp.data.database.Utils;
import com.projects.benjisora.tubapp.data.model.Path;
import com.projects.benjisora.tubapp.data.model.Stop;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MapFragment class
 */
public class MapsFragment extends Fragment {

    @BindView(R.id.mapView)
    MapView mMapView;

    @BindView(R.id.spinner)
    Spinner spinner;

    private ArrayList<KmlLayer> layer;
    private GoogleMap googleMap;

    /**
     * {@inheritDoc}
     * <p>
     * Binds the data and initializes the MapView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, rootView);

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            Log.e(getString(R.string.map_fragment), getString(R.string.log_error), e);
        }
        layer = new ArrayList<>();
        ArrayList<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("All lines");
        for (int i = 0; i < Utils.getinstance().getAllPaths().size(); i++) {
            spinnerArray.add("Line " + Utils.getinstance().getAllPaths().get(i).getNumber());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * {@inheritDoc}
             * Draws the {@link Path}s and the {@link Stop}s if needed
             */
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (layer != null) {
                    for (KmlLayer lay : layer) {
                        lay.removeLayerFromMap();
                    }
                    googleMap.clear();
                    mMapView.invalidate();
                }
                if (position == 0) {
                    List<Path> paths = Utils.getinstance().getAllPaths();
                    for (Path path : paths) {
                        drawKml("path" + path.getId());
                    }
                } else {
                    Path path = Utils.getinstance().getAllPaths().get(position - 1);
                    if (path != null) {
                        List<Stop> stopList = Utils.getinstance().getStopForPath(path.getId());
                        drawKml("path" + path.getId());
                        for (Stop stop : stopList) {
                            LatLng stopCoord = new LatLng(stop.getLatitude(), stop.getLongitude());
                            addMapMarker(stopCoord, stop.getLabel(), path.getColor());
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                LatLng bourg = new LatLng(46.2053457, 5.2260777);
                spinner.setSelection(1);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(bourg).zoom(13).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;
    }

    /**
     * Creates a KmlLayer from a file name
     *
     * @param kmlname the name of the ressource file
     */
    private void drawKml(String kmlname) {
        Context context = getActivity().getApplicationContext();
        Resources res = context.getResources();
        int kmlId = res.getIdentifier(kmlname, "raw", context.getPackageName());
        try {
            layer.add(new KmlLayer(googleMap, kmlId, context));
            layer.get(layer.size() - 1).addLayerToMap();
        } catch (Exception e) {
            Log.e(getString(R.string.map_fragment), getString(R.string.log_error), e);
        }
    }

    /**
     * Adds a marker to the map
     *
     * @param coord The coordinates of the marker
     * @param name  The name of the {@link Stop}
     * @param color The color of the {@link Path}'s line
     */
    private void addMapMarker(LatLng coord, String name, String color) {
        Context context = getActivity().getApplicationContext();
        Resources res = context.getResources();
        Drawable busIcon = res.getDrawable(R.drawable.bus_stop_icon, context.getTheme());
        Bitmap marker = UtilsDrawable.drawableToBitmap(busIcon);
        marker = UtilsDrawable.changeBitmapColor(marker, Color.parseColor(color));
        this.googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(marker))
                .position(coord)
                .title(name)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
