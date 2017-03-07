package com.projects.benjisora.tubapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.benjisora.tubapp.R;
import com.projects.benjisora.tubapp.adapter.FavoritesAdapter;
import com.projects.benjisora.tubapp.data.database.Utils;
import com.projects.benjisora.tubapp.data.model.Favorites;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * FavoritesFragment class
 */
public class FavoritesFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView myRecyclerView;

    private List<Favorites> favs;

    /**
     * Default constructor.
     * Initializes the list with the database
     */
    public FavoritesFragment() {
        favs = Utils.getinstance().getFavorites();
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
        if (myRecyclerView != null && Utils.getinstance().getFavorites() != null) {
            myRecyclerView.setAdapter(new FavoritesAdapter(favs));
        }
        myRecyclerView.setLayoutManager(MyLayoutManager);

        return view;
    }

}
