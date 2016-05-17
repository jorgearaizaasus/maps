package com.example.garaiza.mapas.cluster;

import android.content.Context;

import com.example.garaiza.mapas.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by garaiza on 5/4/16.
 */
public class MyClusterRenderer extends DefaultClusterRenderer<MyItem> {
    public MyClusterRenderer(Context context, GoogleMap map,
                             ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);

        markerOptions.title(item.getmTitle());
        markerOptions.position(item.getPosition()).title(item.getmTitle()).snippet(item.getmTitle());
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_32));
    }

    @Override
    protected void onClusterItemRendered(MyItem clusterItem, Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);

        //here you have access to the marker itself
    }
}
