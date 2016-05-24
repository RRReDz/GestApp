package mcteamgestapp.momo.com.mcteamgestapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by meddaakouri on 11/02/2016.
 */
public class Ferie implements Parcelable {

    @SerializedName("data")
    String data;

    @SerializedName("user")
    int user;

    protected Ferie(Parcel in) {
        data = in.readString();
        user = in.readInt();
    }

    public static final Creator<Ferie> CREATOR = new Creator<Ferie>() {
        @Override
        public Ferie createFromParcel(Parcel in) {
            return new Ferie(in);
        }

        @Override
        public Ferie[] newArray(int size) {
            return new Ferie[size];
        }
    };

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
        dest.writeInt(user);
    }
}
