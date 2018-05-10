package com.project.rudy.lekanmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by maoyan on 2018/5/6.
 */

public class PlayDate implements Parcelable {
    private String showname;
    private String data;
    private String data2;

    public String getShowname() {
        return showname;
    }

    public void setShowname(String showname) {
        this.showname = showname;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.showname);
        dest.writeString(this.data);
        dest.writeString(this.data2);
    }

    public PlayDate() {
    }

    protected PlayDate(Parcel in) {
        this.showname = in.readString();
        this.data = in.readString();
        this.data2 = in.readString();
    }

    public static final Parcelable.Creator<PlayDate> CREATOR = new Parcelable.Creator<PlayDate>() {
        @Override
        public PlayDate createFromParcel(Parcel source) {
            return new PlayDate(source);
        }

        @Override
        public PlayDate[] newArray(int size) {
            return new PlayDate[size];
        }
    };
}
