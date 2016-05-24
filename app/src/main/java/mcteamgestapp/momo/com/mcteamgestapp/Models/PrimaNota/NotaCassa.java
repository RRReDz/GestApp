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

    @SerializedName("dare")
    private double dare;

    @SerializedName("avere")
    private double avere;

    private double totale;

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

    public double getDare() {
        return dare;
    }

    public void setDare(double dare) {
        this.dare = dare;
    }

    public double getAvere() {
        return avere;
    }

    public void setAvere(double avere) {
        this.avere = avere;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
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
        dare = in.readDouble();
        avere = in.readDouble();
        totale = in.readDouble();
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
        dest.writeDouble(dare);
        dest.writeDouble(avere);
        dest.writeDouble(totale);
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
                ", totale=" + totale +
                '}';
    }
}
