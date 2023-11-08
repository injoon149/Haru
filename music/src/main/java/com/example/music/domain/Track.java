package com.example.music.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class Track {
    private List<Artist> artists;
    private String name;
    private int popularity;

    public List<Artist> getArtists() {
        return artists;
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }
}

