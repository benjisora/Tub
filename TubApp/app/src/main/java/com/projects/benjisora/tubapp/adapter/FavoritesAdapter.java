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
 * Adapter class for the RecyclerView's {@link com.projects.benjisora.tubapp.fragment.FavoritesFragment}
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyFavoritesViewHolder> {

    private List<Favorites> favs;

    /**
     * Default constructor.
     * Initializes the list with the database
     */
    public FavoritesAdapter(List<Favorites> favs) {
        this.favs = favs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MyFavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedules_item, parent, false);
        return new MyFavoritesViewHolder(view);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Binds every ressource to the corresponding holder on the RecyclerView
     */
    @Override
    public void onBindViewHolder(final MyFavoritesViewHolder holder, int position) {
        if (favs != null && !favs.isEmpty()) {
            Path path = Utils.getinstance().getPath(favs.get(position).getId_path());
            holder.titleTextView.setText(path.getLabel());
            holder.likeImageView.setLiked(true);
            TextDrawable drawable = TextDrawable.builder()
                    .buildRect(String.valueOf(path.getNumber()),
                            Color.parseColor(path.getColor()));
            holder.backgroundImageView.setImageDrawable(drawable);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemCount() {
        return Utils.getinstance().getFavorites().size();
    }


    /**
     * Class destined to be held by the RecyclerView, and will represent the list's rows
     */
    class MyFavoritesViewHolder extends RecyclerView.ViewHolder {

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
        MyFavoritesViewHolder(View v) {
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
                    //context.startActivity(intent);
                }
            });
            */

            likeImageView.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    removeAt(getAdapterPosition());
                }
            });
        }
    }

    /**
     * Removes the item at the given position
     *
     * @param position The position of the item in the view
     */
    private void removeAt(int position) {
        favs.get(position).delete();
        favs.remove(position);
        notifyItemRemoved(position);
    }
}

