package com.project.rudy.lekanmovie.model;

import java.util.List;

/**
 * Created by maoyan on 2018/5/4.
 */

public class MovieDataVO {

    private String link;
    private String name;
    private List<MovieData> data;

    public List<MovieData> getData() {
        return data;
    }

    public void setData(List<MovieData> data) {
        this.data = data;
    }

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
}
