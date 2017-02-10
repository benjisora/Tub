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


public class SchedulesAdapter extends RecyclerView.Adapter<SchedulesAdapter.MySchedulesViewHolder> {

    private List<Path> list;

    public SchedulesAdapter() {
        list = Utils.getinstance().getAllPaths();
    }

    @Override
    public MySchedulesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedules_item, parent, false);
        return new MySchedulesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MySchedulesViewHolder holder, int position) {
        //TODO : bind tous les champs, et setTag sur like icon pour savoir si liked ou pas
        holder.titleTextView.setText(list.get(position).getLabel());

        if (Utils.getinstance().pathIsFav(list.get(position).getId())) {
            holder.likeImageView.setLiked(true);
        } else {
            holder.likeImageView.setLiked(false);
        }

        TextDrawable drawable = TextDrawable.builder()
                .buildRect(String.valueOf(list.get(position).getNumber()), Color.parseColor(list.get(position).getColor()));

        holder.backgroundImageView.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MySchedulesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.titleTextView)
        TextView titleTextView;

        @BindView(R.id.backgroundImageView)
        ImageView backgroundImageView;

        @BindView(R.id.likeImageView)
        LikeButton likeImageView;


        MySchedulesViewHolder(View v) {
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

                    new Favorites(getAdapterPosition() + 1).save();

                }

                @Override
                public void unLiked(LikeButton likeButton) {

                }
            });
        }
    }
}