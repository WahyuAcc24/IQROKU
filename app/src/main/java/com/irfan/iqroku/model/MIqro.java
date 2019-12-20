package com.irfan.iqroku.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MIqro implements Parcelable {

    private int id;
    private String name;
    private String suara;

    private int thumb;

    public MIqro() {
    }

    protected MIqro(Parcel in) {
        id = in.readInt();
        name = in.readString();
        suara = in.readString();
        thumb = in.readInt();
    }

    public static final Creator<MIqro> CREATOR = new Creator<MIqro>() {
        @Override
        public MIqro createFromParcel(Parcel in) {
            return new MIqro(in);
        }

        @Override
        public MIqro[] newArray(int size) {
            return new MIqro[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumb() {
        return thumb;
    }

    public void setThumb(int thumb) {
        this.thumb = thumb;
    }

    public String getSuara() {
        return suara;
    }

    public void setSuara(String suara) {
        this.suara = suara;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(thumb);
        dest.writeString(suara);
    }
}
