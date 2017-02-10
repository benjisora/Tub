package com.projects.benjisora.tubapp.adapter;

/**
 * Created by benjamin_saugues on 07/02/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


public class SchedulesAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<Path> list;
    private List<Favorites> favs;

    public SchedulesAdapter() {
        list = Utils.getinstance().getAllPaths();
        favs = Utils.getinstance().getFavorites();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedules_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        //TODO : bind tous les champs, et setTag sur like icon pour savoir si liked ou pas
        holder.titleTextView.setText(list.get(position).getLabel());

        if(Utils.getinstance().pathIsFav(position)){
            holder.likeImageView.setLiked(true);
        }else{
            holder.likeImageView.setLiked(false);
        }

        TextDrawable drawable = TextDrawable.builder()
                .buildRect(String.valueOf(list.get(position).getId()), Color.parseColor(list.get(position).getColor()));

        holder.backgroundImageView.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.titleTextView)
    TextView titleTextView;

    @BindView(R.id.backgroundImageView)
    ImageView backgroundImageView;

    @BindView(R.id.likeImageView)
    LikeButton likeImageView;


    MyViewHolder(View v) {
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
                Toast.makeText(likeButton.getContext(), R.string.added_fav, Toast.LENGTH_SHORT).show();

                likeImageView.setLiked(true);
                new Favorites(getAdapterPosition() + 1).insert();

                // TODO: Ajouter aux favs
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Toast.makeText(likeButton.getContext(), R.string.removed_fav, Toast.LENGTH_SHORT).show();

                likeImageView.setLiked(false);

                for (Favorites fav : Utils.getinstance().getFavorites()){
                    if (fav.getId_path() == getAdapterPosition()+1){
                        fav.delete();
                    }
                }

                // TODO: Retirer des favs
            }
        });
/*
        likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int id = (int) likeImageView.getTag();
                if (id == R.drawable.ic_favorite_border_black_24dp) {

                    likeImageView.setTag(R.drawable.ic_favorite_black_24dp);
                    likeImageView.setImageResource(R.drawable.ic_favorite_black_24dp);



                } else {

                    likeImageView.setTag(R.drawable.ic_favorite_border_black_24dp);
                    likeImageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);

                    Toast.makeText(v.getContext(), R.string.removed_fav, Toast.LENGTH_SHORT).show();

                    // TODO: Retirer des favs

                }
            }
        });
        */
    }
}