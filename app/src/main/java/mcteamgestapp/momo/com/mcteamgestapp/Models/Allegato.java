package mcteamgestapp.momo.com.mcteamgestapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by meddaakouri on 18/01/2016.
 */
public class Allegato implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("descrizione")
    private String descrizione;

    @SerializedName("file")
    private String file;

    @SerializedName("upload")
    private String upload;


    //********************* GETTER ************************************//


    protected Allegato(Parcel in) {
        id = in.readInt();
        descrizione = in.readString();
        file = in.readString();
        upload = in.readString();
    }

    public static final Creator<Allegato> CREATOR = new Creator<Allegato>() {
        @Override
        public Allegato createFromParcel(Parcel in) {
            return new Allegato(in);
        }

        @Override
        public Allegato[] newArray(int size) {
            return new Allegato[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getFile() {
        return file;
    }

    public String getUpload() {
        return upload;
    }


    //************************ SETTER ************************************//


    public void setId(int id) {
        this.id = id;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(descrizione);
        dest.writeString(file);
        dest.writeString(upload);
    }
}
