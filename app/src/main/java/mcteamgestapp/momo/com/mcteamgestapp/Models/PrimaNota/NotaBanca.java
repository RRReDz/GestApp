package mcteamgestapp.momo.com.mcteamgestapp.Models.PrimaNota;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rrossi on 18/05/2016.
 */
public class NotaBanca implements Parcelable {

    @SerializedName("riga")
    private int ID;

    @SerializedName("gruppo")
    private int gruppo;

    @SerializedName("data_pagamento")
    private String dataPagamento;

    @SerializedName("data_valuta")
    private String dataValuta;

    @SerializedName("descrizione")
    private String descrizione;

    @SerializedName("nr_protocollo")
    private int numeroProtocollo;

    @SerializedName("dare_db")
    private String dareDb; //String value for db

    @SerializedName("avere_db")
    private String avereDb; //String value for db

    private float dare;

    private float avere;

    private float totale;

    public NotaBanca() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getGruppo() {
        return gruppo;
    }

    public void setGruppo(int gruppo) {
        this.gruppo = gruppo;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getDataValuta() {
        return dataValuta;
    }

    public void setDataValuta(String dataValuta) {
        this.dataValuta = dataValuta;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getNumeroProtocollo() {
        return numeroProtocollo;
    }

    public void setNumeroProtocollo(int numeroProtocollo) {
        this.numeroProtocollo = numeroProtocollo;
    }

    public String getDareDb() {
        return dareDb;
    }

    public void setDareDb(String dareDb) {
        this.dareDb = dareDb;
    }

    public String getAvereDb() {
        return avereDb;
    }

    public void setAvereDb(String avereDb) {
        this.avereDb = avereDb;
    }

    public float getDare() {
        return dare;
    }

    public void setDare(float dare) {
        this.dare = dare;
    }

    public float getAvere() {
        return avere;
    }

    public void setAvere(float avere) {
        this.avere = avere;
    }

    public float getTotale() {
        return totale;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }

    public NotaBanca(Parcel in) {
        ID = in.readInt();
        gruppo = in.readInt();
        dataPagamento = in.readString();
        descrizione = in.readString();
        numeroProtocollo = in.readInt();
        dare = in.readFloat();
        avere = in.readFloat();
        dareDb = in.readString();
        avereDb = in.readString();
        totale = in.readFloat();
    }

    public static final Creator<NotaBanca> CREATOR = new Creator<NotaBanca>() {
        @Override
        public NotaBanca createFromParcel(Parcel in) {
            return new NotaBanca(in);
        }

        @Override
        public NotaBanca[] newArray(int size) {
            return new NotaBanca[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeInt(gruppo);
        dest.writeString(dataPagamento);
        dest.writeString(descrizione);
        dest.writeInt(numeroProtocollo);
        dest.writeFloat(dare);
        dest.writeFloat(avere);
        dest.writeString(dareDb);
        dest.writeString(avereDb);
        dest.writeFloat(totale);
    }

    @Override
    public String toString() {
        return "NotaBanca{" +
                "ID=" + ID +
                ", gruppo=" + gruppo +
                ", dataPagamento='" + dataPagamento + '\'' +
                ", dataValuta='" + dataValuta + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", numeroProtocollo=" + numeroProtocollo +
                ", dareDb='" + dareDb + '\'' +
                ", avereDb='" + avereDb + '\'' +
                ", dare=" + dare +
                ", avere=" + avere +
                ", totale=" + totale +
                '}';
    }
}
