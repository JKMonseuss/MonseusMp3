package com.example.mediaplayer;

public class Song {
    private String name;
    private String artist;
    private int thumb;
    private int File;

    public Song(String name, String artist, int thumb, int file) {
        this.name = name;
        this.artist = artist;
        this.thumb = thumb;
        File = file;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getThumb() {
        return thumb;
    }

    public void setThumb(int thumb) {
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFile() {
        return File;
    }

    public void setFile(int file) {
        File = file;
    }
}
