package com.projects.benjisora.tubapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.benjisora.tubapp.R;
import com.projects.benjisora.tubapp.adapter.SchedulesAdapter;
import com.projects.benjisora.tubapp.data.model.Path;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SchedulesFragment extends Fragment {

    RecyclerView myRecyclerView;

    public SchedulesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedules, container, false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //TODO: check if there're paths in the database
        if (myRecyclerView != null) {
            myRecyclerView.setAdapter(new SchedulesAdapter(getActivity()));
        }
        myRecyclerView.setLayoutManager(MyLayoutManager);

        return view;
    }

}
