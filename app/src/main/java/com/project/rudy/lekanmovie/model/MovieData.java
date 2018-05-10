package com.project.rudy.lekanmovie.model;

import java.util.List;

/**
 * Created by maoyan on 2018/5/3.
 */

public class MovieData {
    private String link;
    private String name;
    private List<Movie> data;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getData() {
        return data;
    }

    public void setData(List<Movie> data) {
        this.data = data;
    }
}
