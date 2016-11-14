package com.mcteam.gestapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Riccardo Rossi on 03/02/2016.
 */
public class OrariAttivita implements Parcelable {

    @SerializedName("GIORNO")
    String giorno;

    @SerializedName("ID_COMMESSA")
    int id_commessa;

    @SerializedName("ID_UTENTE")
    int id_utente;

    @SerializedName("TOTALE_ORE")
    double ore_totali;

    @SerializedName("DESCRIZIONE")
    String descrizione;

    @SerializedName("SEDE")
    String sede;

    @SerializedName("CONSUNTIVATO")
    int consuntivato;

    @SerializedName("commessa")
    Commessa commessa;

    int day_of_week;

    int day;

    int month;

    int year;

    String day_name;

    String month_name;

    boolean isModifica = false;

    boolean isFerie;

    boolean alreadySetup = false;

    OrariAttivita otherHalf = null;

    //********************************************************//
    public OrariAttivita() {
        //EMPTY COSTRUCTOR
    }


    protected OrariAttivita(Parcel in) {
        giorno = in.readString();
        id_commessa = in.readInt();
        id_utente = in.readInt();
        ore_totali = in.readDouble();
        descrizione = in.readString();
        sede = in.readString();
        consuntivato = in.readInt();
        commessa = in.readParcelable(Commessa.class.getClassLoader());
        day_of_week = in.readInt();
        day = in.readInt();
        month = in.readInt();
        year = in.readInt();
        day_name = in.readString();
        month_name = in.readString();
        isModifica = in.readByte() != 0;
        isFerie = in.readByte() != 0;
        alreadySetup = in.readByte() != 0;
        otherHalf = in.readParcelable(OrariAttivita.class.getClassLoader());

    }

    public static final Creator<OrariAttivita> CREATOR = new Creator<OrariAttivita>() {
        @Override
        public OrariAttivita createFromParcel(Parcel in) {
            return new OrariAttivita(in);
        }

        @Override
        public OrariAttivita[] newArray(int size) {
            return new OrariAttivita[size];
        }
    };

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

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

    public double getOre_totali() {
        return ore_totali;
    }

    public void setOre_totali(double ore_totali) {
        this.ore_totali = ore_totali;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public int getConsuntivato() {
        return consuntivato;
    }

    public void setConsuntivato(int consuntivato) {
        this.consuntivato = consuntivato;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public String getMonth_name() {
        return month_name;
    }

    public void setMonth_name(String month_name) {
        this.month_name = month_name;
    }

    public void setDay_of_week(int day_of_week) {
        this.day_of_week = day_of_week;
    }

    public int getDay_of_week() {
        return day_of_week;
    }

    public boolean isFerie() {
        return isFerie;
    }

    public void setFerie(boolean ferie) {
        isFerie = ferie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrariAttivita)) return false;

        OrariAttivita attivita = (OrariAttivita) o;

        return giorno.equals(attivita.giorno);

    }

    @Override
    public int hashCode() {
        return giorno.hashCode();
    }

    public void setFromOld(OrariAttivita fromOld) {
        giorno = fromOld.getGiorno();
        id_commessa = fromOld.getId_commessa();
        id_utente = fromOld.getId_utente();
        ore_totali = fromOld.getOre_totali();
        descrizione = fromOld.getDescrizione();
        sede = fromOld.getDescrizione();
        consuntivato = fromOld.getConsuntivato();
        commessa = fromOld.getCommessa();
    }

    public Commessa getCommessa() {
        return commessa;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(giorno);
        dest.writeInt(id_commessa);
        dest.writeInt(id_utente);
        dest.writeDouble(ore_totali);
        dest.writeString(descrizione);
        dest.writeString(sede);
        dest.writeInt(consuntivato);
        dest.writeParcelable(commessa, flags);
        dest.writeInt(day_of_week);
        dest.writeInt(day);
        dest.writeInt(month);
        dest.writeInt(year);
        dest.writeString(day_name);
        dest.writeString(month_name);
        dest.writeByte((byte) (isModifica ? 1 : 0));
        dest.writeInt((byte) (isFerie ? 1 : 0));
        dest.writeInt((byte) (alreadySetup ? 1 : 0));
        dest.writeParcelable(otherHalf, flags);
    }

    public boolean isModifica() {
        return isModifica;
    }

    public void setModifica(boolean modifica) {
        isModifica = modifica;
    }

    public void setCommessa(Commessa commessa) {
        this.commessa = commessa;
    }

    public boolean isAlreadySetup() {
        return alreadySetup;
    }

    public void setAlreadySetup(boolean alreadySetup) {
        this.alreadySetup = alreadySetup;
    }

    public OrariAttivita getOtherHalf() {
        return otherHalf;
    }

    public void setOtherHalf(OrariAttivita otherHalf) {
        this.otherHalf = otherHalf;
    }
}
