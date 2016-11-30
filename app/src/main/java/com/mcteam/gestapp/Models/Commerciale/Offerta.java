package com.mcteam.gestapp.Models.Commerciale;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mcteam.gestapp.Models.Rubrica.Nominativo;

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

    @SerializedName("off1_comm")
    private int off1Comm;
    private Nominativo off1CommNom;

    @SerializedName("off2_comm")
    private int off2Comm;
    private Nominativo off2CommNom;

    @SerializedName("off3_comm")
    private int off3Comm;
    private Nominativo off3CommNom;

    @SerializedName("edit_offerta")
    private int editOfferta;

    @SerializedName("new_version")
    private int newVersion;

    public Offerta() {

    }

    protected Offerta(Parcel in) {
        idCommessa = in.readInt();
        versione = in.readInt();
        dataOfferta = in.readString();
        accettata = in.readInt();
        offerta = in.readString();
        allegato = in.readString();
        off1Comm = in.readInt();
        off2Comm = in.readInt();
        off3Comm = in.readInt();
        editOfferta = in.readInt();
        newVersion = in.readInt();
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
        dest.writeInt(off1Comm);
        dest.writeInt(off2Comm);
        dest.writeInt(off3Comm);
        dest.writeInt(editOfferta);
        dest.writeInt(newVersion);
    }

    public int getOff1Comm() {
        return off1Comm;
    }

    public Offerta setOff1Comm(int off1Comm) {
        this.off1Comm = off1Comm;
        return this;
    }

    public int getOff2Comm() {
        return off2Comm;
    }

    public Offerta setOff2Comm(int off2Comm) {
        this.off2Comm = off2Comm;
        return this;
    }

    public int getOff3Comm() {
        return off3Comm;
    }

    public Offerta setOff3Comm(int off3Comm) {
        this.off3Comm = off3Comm;
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

    public int getEditOfferta() {
        return editOfferta;
    }

    public void setEditOfferta(int editOfferta) {
        this.editOfferta = editOfferta;
    }

    public int getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(int newVersion) {
        this.newVersion = newVersion;
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
