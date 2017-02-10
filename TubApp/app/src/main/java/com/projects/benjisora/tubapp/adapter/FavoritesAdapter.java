package com.projects.benjisora.tubapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.projects.benjisora.tubapp.R;
import com.projects.benjisora.tubapp.data.database.Utils;
import com.projects.benjisora.tubapp.data.model.Favorites;
import com.projects.benjisora.tubapp.data.model.Path;
import com.projects.benjisora.tubapp.ui.DetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iem on 10/02/2017.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<MyFavoritesViewHolder> {

    private List<Favorites> list;
    private List<Path> paths;

    public FavoritesAdapter() {
        list = Utils.getinstance().getFavorites();
        paths = Utils.getinstance().getAllPaths();
    }

    @Override
    public MyFavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedules_item, parent, false);
        return new MyFavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyFavoritesViewHolder holder, int position) {

        if(Utils.getinstance().pathIsFav(Utils.getinstance().getAllPaths().get(position).getId())) {

            holder.titleTextView.setText(Utils.getinstance().getAllPaths().get(position).getLabel());

            holder.likeImageView.setLiked(true);

            TextDrawable drawable = TextDrawable.builder()
                    .buildRect(String.valueOf(Utils.getinstance().getAllPaths().get(position).getNumber()),
                            Color.parseColor(Utils.getinstance().getAllPaths().get(position).getColor()));

            holder.backgroundImageView.setImageDrawable(drawable);
        }

    }

    @Override
    public int getItemCount() {
        return Utils.getinstance().getFavorites().size();
    }
}

class MyFavoritesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.titleTextView)
    TextView titleTextView;

    @BindView(R.id.backgroundImageView)
    ImageView backgroundImageView;

    @BindView(R.id.likeImageView)
    LikeButton likeImageView;


    MyFavoritesViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailsActivity.class);
                context.startActivity(intent);
            }
        });

        likeImageView.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

            }

            @Override
            public void unLiked(LikeButton likeButton) {

                for (Favorites fav : Utils.getinstance().getFavorites()){
                    if (fav.getId_path() == getAdapterPosition()+1){
                        fav.delete();
                    }
                }
            }
        });
    }
}