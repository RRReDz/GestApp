package com.mcteam.gestapp.Models.Rubrica;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Riccardo Rossi on 14/01/2016.
 */
public class Banca implements Parcelable {

    @SerializedName("id_banca")
    private int id_banca;

    @SerializedName("nome")
    private String nome;

    @SerializedName("indirizzo")
    private String indirizzo;

    @SerializedName("iban")
    private String iban;

    @SerializedName("referente")
    private String referente;

    public Banca() {
    }


    public Banca(Parcel in) {
        id_banca = in.readInt();
        nome = in.readString();
        indirizzo = in.readString();
        iban = in.readString();
        referente = in.readString();
    }

    public static final Creator<Banca> CREATOR = new Creator<Banca>() {
        @Override
        public Banca createFromParcel(Parcel in) {
            return new Banca(in);
        }

        @Override
        public Banca[] newArray(int size) {
            return new Banca[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_banca);
        dest.writeString(nome);
        dest.writeString(indirizzo);
        dest.writeString(iban);
        dest.writeString(referente);
    }

    public int getId_banca() {
        return id_banca;
    }

    public void setId_banca(int id_banca) {
        this.id_banca = id_banca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getReferente() {
        return referente;
    }

    public void setReferente(String referente) {
        this.referente = referente;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }

        final Banca banca = (Banca) other;
        if (this.getId_banca() == banca.getId_banca()) {
            return true;
        }
        return false;
    }

    public boolean isValid() {
        return this.getId_banca() > 0;
    }
}
