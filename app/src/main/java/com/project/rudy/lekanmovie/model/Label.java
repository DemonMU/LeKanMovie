package com.project.rudy.lekanmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by maoyan on 2018/5/6.
 */

public class Label implements Parcelable {
    private String link;
    private String name;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.link);
        dest.writeString(this.name);
    }

    public Label() {
    }

    protected Label(Parcel in) {
        this.link = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Label> CREATOR = new Parcelable.Creator<Label>() {
        @Override
        public Label createFromParcel(Parcel source) {
            return new Label(source);
        }

        @Override
        public Label[] newArray(int size) {
            return new Label[size];
        }
    };
}
