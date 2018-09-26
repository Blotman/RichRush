package com.nguy.richrush.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nguy.richrush.R;
import com.nguy.richrush.activities.RestaurantsListAdapter.Helper;
import com.nguy.richrush.core.Constants.Restaurant;
import com.nguy.richrush.core.DataUtils;
import com.nguy.richrush.core.DataUtils.RestaurantDataListener;

public class RestaurantsListActivity extends AppCompatActivity {

    private static final String DEFAULT_LAT = "37.422740";
    private static final String DEFAULT_LNG = "-122.139956";

    private View mProgressBar;
    private RestaurantsListAdapter mAdapter;

    final private RestaurantDataListener mDataListener = new RestaurantDataListener() {
        @Override
        public void onReceived() {
            mProgressBar.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
        }
    };

    final private Helper mAdapterHelper = new Helper() {
        @Override
        public void onRestaurantClicked(int index, View imageView) {
            final Intent intent = new Intent(RestaurantsListActivity.this, RestaurantActivity.class);
            intent.putExtra(Restaurant.INDEX, index);

                final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        RestaurantsListActivity.this,
                        new Pair<>(imageView, "image"));

                ActivityCompat.startActivity(RestaurantsListActivity.this, intent, options.toBundle());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_list);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressBar = findViewById(R.id.progress_bar);
        mAdapter = new RestaurantsListAdapter(this, mAdapterHelper);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataUtils.setRestaurantDataListener(mDataListener);
        DataUtils.executeGetRestaurants(DEFAULT_LAT, DEFAULT_LNG);

        if (DataUtils.getRestaurantsCount() == 0) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        DataUtils.setRestaurantDataListener(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_restaurants_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
