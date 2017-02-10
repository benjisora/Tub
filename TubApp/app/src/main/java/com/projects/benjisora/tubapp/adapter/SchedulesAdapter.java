package com.projects.benjisora.tubapp.adapter;

/**
 * Created by benjamin_saugues on 07/02/2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.projects.benjisora.tubapp.R;
import com.projects.benjisora.tubapp.data.model.Path;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SchedulesAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private ArrayList<Path> list = new ArrayList<>();
    private Context context;

    public SchedulesAdapter(Context context) {
        this.context = context;

        Path path = new Path();
        path.setLabel("St Denis Collège <> Clinique Convert/EREA La Chagne");
        path.setId(4);
        path.setColor("#AAAAAA");

        Path path2 = new Path();
        path2.setLabel("Norelan <> Ainterexpo");
        path2.setId(27);
        path2.setColor("#ADFEBC");

        list.add(path);
        list.add(path2);

        // TODO: recuperer les paths depuis la database
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedules_item, parent, false);
        return new MyViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.titleTextView.setText(list.get(position).getLabel());
        holder.likeImageView.setTag(R.drawable.ic_favorite_border_black_24dp);

        TextDrawable drawable = TextDrawable.builder()
                .buildRect(String.valueOf(list.get(position).getId()), Color.parseColor(list.get(position).getColor()));

        holder.backgroundImageView.setImageDrawable(drawable);

        //TODO : bind tous les champs, et setTag sur like icon pour savoir si liked ou pas
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
    ImageView likeImageView;


    MyViewHolder(final Context context, View v) {
        super(v);
        ButterKnife.bind(this, v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int id = (int)likeImageView.getTag();
                if( id == R.drawable.ic_favorite_border_black_24dp){

                    likeImageView.setTag(R.drawable.ic_favorite_black_24dp);
                    likeImageView.setImageResource(R.drawable.ic_favorite_black_24dp);

                    Toast.makeText(context,R.string.title_activity_main,Toast.LENGTH_SHORT).show();

                    // TODO: Ajouter aux favs

                }else{

                    likeImageView.setTag(R.drawable.ic_favorite_border_black_24dp);
                    likeImageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);

                    Toast.makeText(context,R.string.title_activity_main,Toast.LENGTH_SHORT).show();

                    // TODO: Retirer des favs

                }

            }
        });

    }
}