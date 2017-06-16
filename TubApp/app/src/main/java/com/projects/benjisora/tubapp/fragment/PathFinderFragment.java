package com.projects.benjisora.tubapp.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.projects.benjisora.tubapp.PathFinder;
import com.projects.benjisora.tubapp.R;
import com.projects.benjisora.tubapp.adapter.SchedulesAdapter;
import com.projects.benjisora.tubapp.data.database.Utils;
import com.projects.benjisora.tubapp.data.model.Stop;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Yann on 16/06/2017.
 */

public class PathFinderFragment extends Fragment {

    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.startSpinner)
    Spinner mStartSpinner;

    @BindView(R.id.endSpinner)
    Spinner mEndSpinner;

    final List<Stop> stopList = Utils.getinstance().getAllStops();


    @OnClick(R.id.getpath)
    public void onClick(){
        if (mStartSpinner.getSelectedItem() == mEndSpinner.getSelectedItem()) {
            Toast.makeText(getContext(), "You need to select two different stops", Toast.LENGTH_SHORT).show();
        } else {
            Stop first = stopList.get(mStartSpinner.getSelectedItemPosition());
            Stop last = stopList.get(mEndSpinner.getSelectedItemPosition());

            List<Stop> stopsToTake = PathFinder.getDirectionsOnSameLineBetween(first, last);
            String directions = "";
            for (Stop stop : stopsToTake) {
                directions += stop.toString() + " - ";
            }
            Toast.makeText(getContext(), directions, Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Default constructor
     */
    public PathFinderFragment() {
    }

    /**
     * {@inheritDoc}
     * <p>
     * Binds the data and initializes the RecyclerView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedules, container, false);
        ButterKnife.bind(this, view);



        ArrayAdapter<Stop> adapter =
                new ArrayAdapter<Stop>(getContext(), android.R.layout.simple_spinner_dropdown_item, stopList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mStartSpinner.setAdapter(adapter);
        mEndSpinner.setAdapter(adapter);

        return view;
    }
}
