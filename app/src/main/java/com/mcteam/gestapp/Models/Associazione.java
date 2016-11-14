package com.mcteam.gestapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Riccardo Rossi on 27/01/2016.
 */
public class Associazione implements Parcelable {

    @SerializedName("id_commessa")
    int id_commessa;

    @SerializedName("id_utente")
    int id_utente;

    @SerializedName("data_inizio")
    String data_inizio;

    @SerializedName("data_fine")
    String data_fine;

    @SerializedName("commessa")
    Commessa commessa;

    @SerializedName("risorsa")
    UserInfo risorsa;

    public Associazione() {
    }

    protected Associazione(Parcel in) {
        id_commessa = in.readInt();
        id_utente = in.readInt();
        data_inizio = in.readString();
        data_fine = in.readString();
        commessa = in.readParcelable(Commessa.class.getClassLoader());
        risorsa = in.readParcelable(UserInfo.class.getClassLoader());
    }

    public static final Creator<Associazione> CREATOR = new Creator<Associazione>() {
        @Override
        public Associazione createFromParcel(Parcel in) {
            return new Associazione(in);
        }

        @Override
        public Associazione[] newArray(int size) {
            return new Associazione[size];
        }
    };

    public int getId_commessa() {
        return id_commessa;
    }

    public void setId_commessa(int id_commessa) {
        this.id_commessa = id_commessa;
    }

    public int getId_utente() {
        return id_utente;
    }

    public void setId_utente(int id_utente) {
        this.id_utente = id_utente;
    }

    public String getData_inizio() {
        return data_inizio;
    }

    public void setData_inizio(String data_inizio) {
        this.data_inizio = data_inizio;
    }

    public String getData_fine() {
        return data_fine;
    }

    public void setData_fine(String data_fine) {
        this.data_fine = data_fine;
    }

    public Commessa getCommessa() {
        return commessa;
    }

    public void setCommessa(Commessa commessa) {
        this.commessa = commessa;
    }

    public UserInfo getRisorsa() {
        return risorsa;
    }

    public void setRisorsa(UserInfo risorsa) {
        this.risorsa = risorsa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Associazione)) return false;

        Associazione that = (Associazione) o;

        if (id_commessa != that.id_commessa) return false;
        return id_utente == that.id_utente;

    }

    @Override
    public int hashCode() {
        int result = id_commessa;
        result = 31 * result + id_utente;
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_commessa);
        dest.writeInt(id_utente);
        dest.writeString(data_inizio);
        dest.writeString(data_fine);
        dest.writeParcelable(commessa, flags);
        dest.writeParcelable(risorsa, flags);
    }
}
