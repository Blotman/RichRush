package com.nguy.richrush.activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nguy.richrush.R;
import com.nguy.richrush.core.DataUtils;
import com.nguy.richrush.core.RestaurantData;

public class RestaurantsListAdapter extends Adapter<RestaurantsListAdapter.ViewHolder> {
    private final Context mContext;
    private final Helper mHelper;

    public interface Helper {
        void onRestaurantClicked(int index, View imageView);
    }

    public RestaurantsListAdapter(Context context, Helper helper) {
        mContext = context;
        mHelper = helper;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewHolder viewHolder = new ViewHolder(parent);
        viewHolder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHelper != null) {
                    mHelper.onRestaurantClicked(viewHolder.getAdapterPosition(), viewHolder.image);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RestaurantData restaurantData = DataUtils.getRestaurantData(position);
        if (restaurantData != null) {
            Glide.clear(holder.image);
            Glide.with(mContext)
                    .load(restaurantData.coverImgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.image);

            holder.name.setText(restaurantData.name);
            holder.description.setText(restaurantData.description);
            holder.status.setText(restaurantData.status);
        }
    }

    @Override
    public int getItemCount() {
        return DataUtils.getRestaurantsCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image;
        public final TextView name;
        public final TextView description;
        public final TextView status;

        public ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.list_item_restaurant, parent, false));
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            status = itemView.findViewById(R.id.status);
        }
    }
}
