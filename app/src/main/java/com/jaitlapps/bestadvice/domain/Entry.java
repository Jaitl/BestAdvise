package com.jaitlapps.bestadvice.domain;

public abstract class Entry {
    protected String id;
    private String title;
    private String pathToImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
