package mcteamgestapp.momo.com.mcteamgestapp.Models.PrimaNota;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rrossi on 18/05/2016.
 */
public class NotaCassa implements Parcelable {

    @SerializedName("riga")
    private int ID;

    @SerializedName("cassa")
    private int cassa;

    @SerializedName("data_pagamento")
    private String dataPagamento;

    @SerializedName("cod_dare")
    private String causaleContabile;

    @SerializedName("cod_avere")
    private String sottoconto;

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

    public NotaCassa() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCassa() {
        return cassa;
    }

    public void setCassa(int cassa) {
        this.cassa = cassa;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getCausaleContabile() {
        return causaleContabile;
    }

    public void setCausaleContabile(String causaleContabile) {
        this.causaleContabile = causaleContabile;
    }

    public String getSottoconto() {
        return sottoconto;
    }

    public void setSottoconto(String sottoconto) {
        this.sottoconto = sottoconto;
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

    public void setDareDb(String dareDb) {
        this.dareDb = dareDb;
    }

    public void setAvereDb(String avereDb) {
        this.avereDb = avereDb;
    }

    public float getTotale() {
        return totale;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }

    public NotaCassa(Parcel in) {
        ID = in.readInt();
        cassa = in.readInt();
        dataPagamento = in.readString();
        causaleContabile = in.readString();
        sottoconto = in.readString();
        descrizione = in.readString();
        numeroProtocollo = in.readInt();
        dare = in.readFloat();
        avere = in.readFloat();
        dareDb = in.readString();
        avereDb = in.readString();
        totale = in.readFloat();
    }

    public static final Creator<NotaCassa> CREATOR = new Creator<NotaCassa>() {
        @Override
        public NotaCassa createFromParcel(Parcel in) {
            return new NotaCassa(in);
        }

        @Override
        public NotaCassa[] newArray(int size) {
            return new NotaCassa[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeInt(cassa);
        dest.writeString(dataPagamento);
        dest.writeString(causaleContabile);
        dest.writeString(sottoconto);
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
        return "NotaCassa{" +
                "ID=" + ID +
                ", cassa=" + cassa +
                ", dataPagamento='" + dataPagamento + '\'' +
                ", causaleContabile='" + causaleContabile + '\'' +
                ", sottoconto='" + sottoconto + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", numeroProtocollo=" + numeroProtocollo +
                ", dare=" + dare +
                ", avere=" + avere +
                ", dareDb=" + dareDb +
                ", avereDb=" + avereDb +
                ", totale=" + totale +
                '}';
    }
}
