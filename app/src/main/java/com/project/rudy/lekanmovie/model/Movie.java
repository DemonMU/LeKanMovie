package com.project.rudy.lekanmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maoyan on 2018/5/2.
 */

public class Movie implements Parcelable {

    @SerializedName("tvTitle")
    private String name;

    private String grade;

    @SerializedName("iconaddress")
    private String poster;

    @SerializedName("iconlinkUrl")
    private String webUrl;

    @SerializedName("subHead")
    private String cinemaNum;

    private PlayDate playDate;

    private LabelType director;

    private LabelType star;

    @SerializedName("type")
    private LabelType movieType;

    @SerializedName("story")
    private Story story;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getCinemaNum() {
        return cinemaNum;
    }

    public void setCinemaNum(String cinemaNum) {
        this.cinemaNum = cinemaNum;
    }

    public PlayDate getPlayDate() {
        return playDate;
    }

    public void setPlayDate(PlayDate playDate) {
        this.playDate = playDate;
    }

    public LabelType getDirector() {
        return director;
    }

    public void setDirector(LabelType director) {
        this.director = director;
    }

    public LabelType getStar() {
        return star;
    }

    public void setStar(LabelType star) {
        this.star = star;
    }

    public LabelType getMovieType() {
        return movieType;
    }

    public void setMovieType(LabelType movieType) {
        this.movieType = movieType;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public String getReleaseDateString() {
        if (getPlayDate() == null) {
            return null;
        }

        return getPlayDate().getData();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.grade);
        dest.writeString(this.poster);
        dest.writeString(this.webUrl);
        dest.writeString(this.cinemaNum);
        dest.writeParcelable(this.playDate, flags);
        dest.writeParcelable(this.director, flags);
        dest.writeParcelable(this.star, flags);
        dest.writeParcelable(this.movieType, flags);
        dest.writeParcelable(this.story, flags);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.name = in.readString();
        this.grade = in.readString();
        this.poster = in.readString();
        this.webUrl = in.readString();
        this.cinemaNum = in.readString();
        this.playDate = in.readParcelable(PlayDate.class.getClassLoader());
        this.director = in.readParcelable(LabelType.class.getClassLoader());
        this.star = in.readParcelable(LabelType.class.getClassLoader());
        this.movieType = in.readParcelable(LabelType.class.getClassLoader());
        this.story = in.readParcelable(Story.class.getClassLoader());
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public List<String> getMovieTypeList() {
        List<String> movieTypeList = null;
        LabelType movieType = getMovieType();
        if (movieType.getData() != null) {
            movieTypeList = new ArrayList<>();
            if (movieType.getData().getLabel1() != null) {
                movieTypeList.add(movieType.getData().getLabel1().getName());
            }
            if (movieType.getData().getLabel2() != null) {
                movieTypeList.add(movieType.getData().getLabel2().getName());
            }
            if (movieType.getData().getLabel3() != null) {
                movieTypeList.add(movieType.getData().getLabel3().getName());
            }
            if (movieType.getData().getLabel4() != null) {
                movieTypeList.add(movieType.getData().getLabel4().getName());
            }
        }
        return movieTypeList;
    }

    public String getMovieStars() {
        StringBuilder builder = null;
        LabelType movieStar = getStar();
        if (movieStar.getData() != null) {
            builder = new StringBuilder();
            if (movieStar.getData().getLabel1() != null) {
                builder.append(movieStar.getData().getLabel1().getName()).append(",");
            }
            if (movieStar.getData().getLabel2() != null) {
                builder.append(movieStar.getData().getLabel2().getName()).append(",");
            }
            if (movieStar.getData().getLabel3() != null) {
                builder.append(movieStar.getData().getLabel3().getName()).append(",");
            }
            if (movieStar.getData().getLabel4() != null) {
                builder.append(movieStar.getData().getLabel4().getName()).append(",");
            }
            if(builder.toString().endsWith(",")){
                builder.deleteCharAt(builder.lastIndexOf(","));
            }
        }
        return builder.toString();
    }


    public String getMovieTypeString() {
        return extractLabelTypeString(getMovieType());
    }

    private String extractLabelTypeString(LabelType labelType) {
        if (labelType == null) return "";

        StringBuilder builder = new StringBuilder();
        if (labelType.getData() != null) {
            if (labelType.getData().getLabel1() != null) {
                builder.append(labelType.getData().getLabel1().getName()).append("，");
            }
            if (labelType.getData().getLabel2() != null) {
                builder.append(labelType.getData().getLabel2().getName()).append("，");
            }
            if (labelType.getData().getLabel3() != null) {
                builder.append(labelType.getData().getLabel3().getName()).append("，");
            }
            if (labelType.getData().getLabel4() != null) {
                builder.append(labelType.getData().getLabel4().getName()).append("，");
            }
            if (builder.toString().endsWith("，")) {
                builder.deleteCharAt(builder.lastIndexOf("，"));
            }
        }

        return builder.toString();
    }
}
