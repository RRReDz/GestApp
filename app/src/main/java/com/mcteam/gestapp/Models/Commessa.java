package com.mcteam.gestapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mcteam.gestapp.Models.Rubrica.Nominativo;
import com.mcteam.gestapp.Models.Rubrica.Societa;

/**
 * Created by Riccardo Rossi on 16/12/2015.
 */
public class Commessa implements Parcelable {

    @SerializedName("ID_COMMESSA")
    private int ID;

    @SerializedName("COD_COMMESSA")
    private String codice_commessa;

    @SerializedName("ID_CLIENTE")
    private int id_cliente;

    @SerializedName("CLIENTE")
    private Societa cliente;

    @SerializedName("NOME_COMMESSA")
    private String nome_commessa;

    @SerializedName("ID_COMMERCIALE")
    private int id_commerciale;

    @SerializedName("COMMERCIALE")
    private Nominativo commerciale;

    @SerializedName("DATA")
    private String data;

    @SerializedName("AVANZAMENTO")
    private String avanzamento;

    @SerializedName("ID_REFERENTE1")
    private int id_referente_1;

    @SerializedName("REFERENTE1")
    private Nominativo referente1;

    @SerializedName("ID_REFERENTE2")
    private int id_referente_2;

    @SerializedName("REFERENTE2")
    private Nominativo referente2;

    @SerializedName("off1")
    private int off1;

    @SerializedName("ref_off1")
    private Nominativo referente_offerta1 = null;

    @SerializedName("off2")
    private int off2;

    @SerializedName("ref_off2")
    private Nominativo referente_offerta2 = null;

    @SerializedName("off3")
    private int off3;

    @SerializedName("ref_off3")
    private Nominativo referente_offerta3 = null;

    @SerializedName("NOTE")
    private String note;

    @SerializedName("id_capo_progetto")
    private int id_capo_progetto;

    @SerializedName("capo_progetto")
    private UserInfo capo_progetto = null;

    public Commessa() {
        cliente = null;
        commerciale = null;
        referente1 = null;
        referente2 = null;
    }

    protected Commessa(Parcel in) {
        ID = in.readInt();
        codice_commessa = in.readString();
        id_cliente = in.readInt();
        cliente = in.readParcelable(Societa.class.getClassLoader());
        nome_commessa = in.readString();
        id_commerciale = in.readInt();
        commerciale = in.readParcelable(Nominativo.class.getClassLoader());
        data = in.readString();
        avanzamento = in.readString();
        id_referente_1 = in.readInt();
        referente1 = in.readParcelable(Nominativo.class.getClassLoader());
        id_referente_2 = in.readInt();
        referente2 = in.readParcelable(Nominativo.class.getClassLoader());
        off1 = in.readInt();
        referente_offerta1 = in.readParcelable(Nominativo.class.getClassLoader());
        off2 = in.readInt();
        referente_offerta2 = in.readParcelable(Nominativo.class.getClassLoader());
        off3 = in.readInt();
        referente_offerta3 = in.readParcelable(Nominativo.class.getClassLoader());
        note = in.readString();
        id_capo_progetto = in.readInt();
        capo_progetto = in.readParcelable(UserInfo.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(codice_commessa);
        dest.writeInt(id_cliente);
        dest.writeParcelable(cliente, flags);
        dest.writeString(nome_commessa);
        dest.writeInt(id_commerciale);
        dest.writeParcelable(commerciale, flags);
        dest.writeString(data);
        dest.writeString(avanzamento);
        dest.writeInt(id_referente_1);
        dest.writeParcelable(referente1, flags);
        dest.writeInt(id_referente_2);
        dest.writeParcelable(referente2, flags);
        dest.writeInt(off1);
        dest.writeParcelable(referente_offerta1, flags);
        dest.writeInt(off2);
        dest.writeParcelable(referente_offerta2, flags);
        dest.writeInt(off3);
        dest.writeParcelable(referente_offerta3, flags);
        dest.writeString(note);
        dest.writeInt(id_capo_progetto);
        dest.writeParcelable(capo_progetto, flags);
    }

    public static final Creator<Commessa> CREATOR = new Creator<Commessa>() {
        @Override
        public Commessa createFromParcel(Parcel in) {
            return new Commessa(in);
        }

        @Override
        public Commessa[] newArray(int size) {
            return new Commessa[size];
        }
    };

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCodice_commessa() {
        return codice_commessa;
    }

    public void setCodice_commessa(String codice_commessa) {
        this.codice_commessa = codice_commessa;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Societa getCliente() {
        return cliente;
    }

    public void setCliente(Societa cliente) {
        this.cliente = cliente;
    }

    public String getNome_commessa() {
        return nome_commessa;
    }

    public void setNome_commessa(String nome_commessa) {
        this.nome_commessa = nome_commessa;
    }

    public int getId_commerciale() {
        return id_commerciale;
    }

    public void setId_commerciale(int id_commerciale) {
        this.id_commerciale = id_commerciale;
    }

    public Nominativo getCommerciale() {
        return commerciale;
    }

    public void setCommerciale(Nominativo commerciale) {
        this.commerciale = commerciale;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAvanzamento() {
        return avanzamento;
    }

    public void setAvanzamento(String avanzamento) {
        this.avanzamento = avanzamento;
    }

    public int getId_referente_1() {
        return id_referente_1;
    }

    public void setId_referente_1(int id_referente_1) {
        this.id_referente_1 = id_referente_1;
    }

    public Nominativo getReferente1() {
        return referente1;
    }

    public void setReferente1(Nominativo referente1) {
        this.referente1 = referente1;
    }

    public int getId_referente_2() {
        return id_referente_2;
    }

    public void setId_referente_2(int id_referente_2) {
        this.id_referente_2 = id_referente_2;
    }

    public Nominativo getReferente2() {
        return referente2;
    }

    public void setReferente2(Nominativo referente2) {
        this.referente2 = referente2;
    }

    public int getOff1() {
        return off1;
    }

    public void setOff1(int off1) {
        this.off1 = off1;
    }

    public int getOff2() {
        return off2;
    }

    public void setOff2(int off2) {
        this.off2 = off2;
    }

    public int getOff3() {
        return off3;
    }

    public void setOff3(int off3) {
        this.off3 = off3;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId_capo_progetto() {
        return id_capo_progetto;
    }

    public void setId_capo_progetto(int id_capo_progetto) {
        this.id_capo_progetto = id_capo_progetto;
    }

    public UserInfo getCapo_progetto() {
        return capo_progetto;
    }

    public void setCapo_progetto(UserInfo capo_progetto) {
        this.capo_progetto = capo_progetto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Nominativo getReferente_offerta3() {
        return referente_offerta3;
    }

    public void setReferente_offerta3(Nominativo referente_offerta3) {
        this.referente_offerta3 = referente_offerta3;
    }

    public Nominativo getReferente_offerta1() {
        return referente_offerta1;
    }

    public void setReferente_offerta1(Nominativo referente_offerta1) {
        this.referente_offerta1 = referente_offerta1;
    }

    public Nominativo getReferente_offerta2() {
        return referente_offerta2;
    }

    public void setReferente_offerta2(Nominativo referente_offerta2) {
        this.referente_offerta2 = referente_offerta2;
    }

    public boolean isValid() {
        return this.getID() > 0;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }

        final Commessa commessa = (Commessa) other;
        if (this.getID() == commessa.getID()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Commessa{" +
                "ID=" + ID +
                ", codice_commessa='" + codice_commessa + '\'' +
                ", id_cliente=" + id_cliente +
                ", cliente=" + cliente +
                ", nome_commessa='" + nome_commessa + '\'' +
                ", id_commerciale=" + id_commerciale +
                ", commerciale=" + commerciale +
                ", data='" + data + '\'' +
                ", avanzamento='" + avanzamento + '\'' +
                ", id_referente_1=" + id_referente_1 +
                ", referente1=" + referente1 +
                ", id_referente_2=" + id_referente_2 +
                ", referente2=" + referente2 +
                ", off1=" + off1 +
                ", off2=" + off2 +
                ", off3=" + off3 +
                ", referente_offerta1=" + referente_offerta1 +
                ", referente_offerta2=" + referente_offerta2 +
                ", referente_offerta3=" + referente_offerta3 +
                ", note='" + note + '\'' +
                ", id_capo_progetto=" + id_capo_progetto +
                ", capo_progetto=" + capo_progetto +
                '}';
    }
}
