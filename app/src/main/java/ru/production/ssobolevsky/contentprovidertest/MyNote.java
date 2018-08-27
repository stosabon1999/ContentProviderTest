package ru.production.ssobolevsky.contentprovidertest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pro on 07.07.2018.
 */

class MyNote implements Parcelable {
    public static final String PARCELABLE_DATA = "PARCELABLEDATA";
    public static final String NOTE_TABLE = "NOTE";
    public static final String TITLE = "title";
    public static final String SUBTITLE = "subtitle";
    public static final String ID = "id";

    private String mTitle;
    private String mSubtitle;
    private int mId;

    public MyNote(String title, String subtitle) {
        mTitle = title;
        mSubtitle = subtitle;
    }

    public MyNote(int id, String title, String subtitle) {
        mTitle = title;
        mSubtitle = subtitle;
        mId = id;
    }

    public MyNote(Parcel in) {
        String[] data = new String[2];
        in.readStringArray(data);
        mId = in.readInt();
        mTitle = data[0];
        mSubtitle = data[1];
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String subtitle) {
        mSubtitle = subtitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeStringArray(new String[]{mTitle, mSubtitle});
    }

    public static final Parcelable.Creator<MyNote> CREATOR = new Parcelable.Creator<MyNote>() {
        public MyNote createFromParcel(Parcel in) {
            return new MyNote(in);
        }

        public MyNote[] newArray(int size) {
            return new MyNote[size];
        }
    };
}
