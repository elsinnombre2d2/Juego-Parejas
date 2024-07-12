package com.example.backend.model;

import java.util.List;

public class PixabayResponse {
    private List<PixabayImage> hits;

    public List<PixabayImage> getHits() {
        return hits;
    }

    public void setHits(List<PixabayImage> hits) {
        this.hits = hits;
    }
}