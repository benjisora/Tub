package com.projects.benjisora.tubapp.adapter;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter class for the RecyclerView's {@link com.projects.benjisora.tubapp.fragment.SchedulesFragment}
 */
public class SchedulesAdapter extends RecyclerView.Adapter<SchedulesAdapter.MySchedulesViewHolder> {

    private List<Path> list;

    /**
     * Default constructor.
     * Initializes the list with the database
     */
    public SchedulesAdapter() {
        list = Utils.getinstance().getAllPaths();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MySchedulesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedules_item, parent, false);
        return new MySchedulesViewHolder(view);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Binds every ressource to the corresponding holder on the RecyclerView
     */
    @Override
    public void onBindViewHolder(final MySchedulesViewHolder holder, int position) {

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

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Class destined to be held by the RecyclerView, and will represent the list's rows
     */
    class MySchedulesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.titleTextView)
        TextView titleTextView;

        @BindView(R.id.backgroundImageView)
        ImageView backgroundImageView;

        @BindView(R.id.likeImageView)
        LikeButton likeImageView;

        /**
         * Default constructor.
         * Creates the view and sets the click listeners
         */
        MySchedulesViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            /*
              // Commented due to the lack of Schedules on the API server.
              // The DetailsActivity is useless until schedules are set on the server
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, DetailsActivity.class);
                    context.startActivity(intent);
                }
            });
            */

            likeImageView.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    Favorites fav = new Favorites();
                    fav.setId_path(list.get(getAdapterPosition()).getId());
                    fav.save();
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    Utils.getinstance().getFavorite(list.get(getAdapterPosition()).getId()).delete();
                }
            });
        }
    }
}