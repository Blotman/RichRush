package com.nguy.richrush;

import com.nguy.richrush.core.DataUtils;
import com.nguy.richrush.core.DataUtils.RestaurantDataListener;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataUtilsTest {
    @Test
    public void restaurantListener() throws Exception {
        final TestRestaurantDataListener listener = new TestRestaurantDataListener();
        assertEquals(0, listener.encounter);

        DataUtils.notifyRestaurantDataReceived();
        assertEquals(0, listener.encounter);

        DataUtils.setRestaurantDataListener(listener);
        assertEquals(0, listener.encounter);

        DataUtils.notifyRestaurantDataReceived();
        assertEquals(1, listener.encounter);

        DataUtils.notifyRestaurantDataReceived();
        assertEquals(2, listener.encounter);

        DataUtils.setRestaurantDataListener(null);
        DataUtils.notifyRestaurantDataReceived();
        assertEquals(2, listener.encounter);
    }

    private class TestRestaurantDataListener implements RestaurantDataListener {
        private int encounter = 0;

        @Override
        public void onReceived() {
            encounter++;
        }
    }
}
