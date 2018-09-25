package com.nguy.richrush;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.UiThreadTest;
import android.widget.FrameLayout;

import com.nguy.richrush.RestaurantsListAdapter.ViewHolder;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DataUtilsInstrumentedTest {
    final static String jsonData =
            "[{\"id\":0, \"name\":\"a\", \"description\":\"a desc\", \"cover_img_url\":\"aaa_url\", \"status\":\"a mins\"}," +
                    "{\"id\":1, \"name\":\"b\", \"description\":\"b desc\", \"cover_img_url\":\"bbb_url\", \"status\":\"b mins\"}," +
                    "{\"id\":2, \"name\":\"c\", \"description\":\"c desc\", \"cover_img_url\":\"ccc_url\", \"status\":\"c mins\"}]";

    @Test
    public void resetRestaurantData() throws Exception {
        DataUtils.resetRestaurantData("");
        assertEquals(0, DataUtils.getRestaurantsCount());

        DataUtils.resetRestaurantData(jsonData);
        assertEquals(3, DataUtils.getRestaurantsCount());

        assertEquals(0, DataUtils.getRestaurantData(0).id);
        assertEquals("a", DataUtils.getRestaurantData(0).name);
        assertEquals("a desc", DataUtils.getRestaurantData(0).description);
        assertEquals("aaa_url", DataUtils.getRestaurantData(0).coverImgUrl);
        assertEquals("a mins", DataUtils.getRestaurantData(0).status);

        assertEquals(1, DataUtils.getRestaurantData(1).id);
        assertEquals("b", DataUtils.getRestaurantData(1).name);

        assertEquals(2, DataUtils.getRestaurantData(2).id);
        assertEquals("c", DataUtils.getRestaurantData(2).name);
    }

    @Test
    @UiThreadTest
    public void adapterBinding() throws Exception {
        final Context context = InstrumentationRegistry.getTargetContext();
        final RestaurantsListAdapter adapter = new RestaurantsListAdapter(context);
        assertEquals(0, adapter.getItemCount());

        DataUtils.resetRestaurantData(jsonData);
        assertEquals(3, adapter.getItemCount());

        final ViewHolder viewHolder = adapter.onCreateViewHolder(new FrameLayout(context), 0);
        adapter.onBindViewHolder(viewHolder, 0);
        assertEquals("a", viewHolder.name.getText());

        adapter.onBindViewHolder(viewHolder, 1);
        assertEquals("b", viewHolder.name.getText());
    }
}
