package com.mcteam.gestapp.Models.Commerciale;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author Created by Riccardo Rossi on 02/10/2016.
 */

public class Offerta implements Parcelable{
    @SerializedName("id_commessa")
    private int idCommessa;

    @SerializedName("versione")
    private int versione;

    @SerializedName("data_offerta")
    private String dataOfferta;

    @SerializedName("accettata")
    private int accettata;

    @SerializedName("offerta")
    private String offerta;

    @SerializedName("allegato")
    private String allegato;

    @SerializedName("off1")
    private int off1;
    @SerializedName("off2")
    private int off2;
    @SerializedName("off3")
    private int off3;

    public Offerta() {

    }

    protected Offerta(Parcel in) {
        idCommessa = in.readInt();
        versione = in.readInt();
        dataOfferta = in.readString();
        accettata = in.readInt();
        offerta = in.readString();
        allegato = in.readString();
    }

    public static final Creator<Offerta> CREATOR = new Creator<Offerta>() {
        @Override
        public Offerta createFromParcel(Parcel in) {
            return new Offerta(in);
        }

        @Override
        public Offerta[] newArray(int size) {
            return new Offerta[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idCommessa);
        dest.writeInt(versione);
        dest.writeString(dataOfferta);
        dest.writeInt(accettata);
        dest.writeString(offerta);
        dest.writeString(allegato);
        dest.writeInt(off1);
        dest.writeInt(off2);
        dest.writeInt(off3);
    }

    public int getOff1() {
        return off1;
    }

    public Offerta setOff1(int off1) {
        this.off1 = off1;
        return this;
    }

    public int getOff2() {
        return off2;
    }

    public Offerta setOff2(int off2) {
        this.off2 = off2;
        return this;
    }

    public int getOff3() {
        return off3;
    }

    public Offerta setOff3(int off3) {
        this.off3 = off3;
        return this;
    }

    public int getIdCommessa() {
        return idCommessa;
    }

    public Offerta setIdCommessa(int idCommessa) {
        this.idCommessa = idCommessa;
        return this;
    }

    public int getVersione() {
        return versione;
    }

    public Offerta setVersione(int versione) {
        this.versione = versione;
        return this;
    }

    public String getDataOfferta() {
        return dataOfferta;
    }

    public Offerta setDataOfferta(String dataOfferta) {
        this.dataOfferta = dataOfferta;
        return this;
    }

    public int getAccettata() {
        return accettata;
    }

    public Offerta setAccettata(int accettata) {
        this.accettata = accettata;
        return this;
    }

    public String getOfferta() {
        return offerta;
    }

    public Offerta setOfferta(String offerta) {
        this.offerta = offerta;
        return this;
    }

    public String getAllegato() {
        return allegato;
    }

    public Offerta setAllegato(String allegato) {
        this.allegato = allegato;
        return this;
    }

    @Override
    public String toString() {
        return "Offerta{" +
                "idCommessa=" + idCommessa +
                ", versione=" + versione +
                ", dataOfferta='" + dataOfferta + '\'' +
                ", accettata=" + accettata +
                ", offerta='" + offerta + '\'' +
                ", allegato='" + allegato + '\'' +
                '}';
    }
}
