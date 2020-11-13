package com.example.shopstock.maps;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyMarker {
    private LatLng position;
    private String title;

    public MyMarker(LatLng position, String title) {
        this.position = position;
        this.title = title;
    }

    public LatLng getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public MarkerOptions buildGoogleMarker() {
        return new MarkerOptions()
                .position(this.position)
                .title(this.title)
                .anchor(0.5f, 0.5f);
    }
}