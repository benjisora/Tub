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
import com.projects.benjisora.tubapp.data.database.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * FavoritesFragment class
 */
public class SchedulesFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView myRecyclerView;

    /**
     * Default constructor
     */
    public SchedulesFragment() {
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

        myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (myRecyclerView != null && Utils.getinstance().getAllPaths() != null) {
            myRecyclerView.setAdapter(new SchedulesAdapter());
        }
        myRecyclerView.setLayoutManager(MyLayoutManager);

        return view;
    }

}
