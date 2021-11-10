package com.example.blabla.ui.main.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterMarker implements ClusterItem {

    private String title;
    private String snippet;
    private LatLng location;

    public ClusterMarker(LatLng location,String title, String snippet) {
        this.title = title;
        this.snippet = snippet;
        this.location = location;
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return location;
    }

    @Nullable
    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return snippet;
    }
}
