package com.nguy.richrush;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class RestaurantsListAdapter extends Adapter<RestaurantsListAdapter.ViewHolder> {
    final Context mContext;

    public RestaurantsListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
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
