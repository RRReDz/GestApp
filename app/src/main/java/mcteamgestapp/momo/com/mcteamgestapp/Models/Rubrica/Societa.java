package mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by meddaakouri on 13/11/2015.
 */
public class Societa implements Parcelable {

    @SerializedName("ID")
    private int mCodiceSocieta;

    @SerializedName("SOCIETA")
    private String mNomeSocietà;

    @SerializedName("INDIRIZZO")
    private String mIndirizzo;

    @SerializedName("CAP")
    private String mCap;

    @SerializedName("PROVINCIA")
    private String mProvincia;

    @SerializedName("CELLULARE")
    private String mCellulare;

    @SerializedName("TIPOLOGIA")
    private String mTipologia;

    @SerializedName("NOTE")
    private String mNote;

    @SerializedName("COD_FISCALE")
    private String mCOD_FISCALE;

    @SerializedName("IVA")
    private String mPartita_iva;

    @SerializedName("STATO")
    private String mStato;

    @SerializedName("WWW")
    private String mSito;

    @SerializedName("CITTA")
    private String mCitta;

    @SerializedName("TELEFONO")
    private String mTelefono;

    @SerializedName("FAX")
    private String mFax;

    @SerializedName("ID_CLIENTE")
    private int ID;

    /*********************************************************************/
    public Societa() {
    }

    public String getSito() {
        return mSito;
    }

    public void setSito(String mSito) {
        this.mSito = mSito;
    }

    public String getStato() {
        return mStato;
    }

    public void setStato(String mStato) {
        this.mStato = mStato;
    }

    public String getPartitaIva() {
        return mPartita_iva;
    }

    public void setPartitaIva(String mPartita_iva) {
        this.mPartita_iva = mPartita_iva;
    }

    public Integer getCodiceSocieta() {
        return mCodiceSocieta;
    }

    public void setCodiceSocieta(Integer mCodiceSocieta) {
        this.mCodiceSocieta = mCodiceSocieta;
    }

    public String getNote() {
        return mNote;
    }

    public String getCOD_FISCALE() {
        return mCOD_FISCALE;
    }

    public void setCOD_FISCALE(String mCOD_FISCALE) {
        this.mCOD_FISCALE = mCOD_FISCALE;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public String getmProvincia() {
        return mProvincia;
    }

    public void setmProvincia(String mProvincia) {
        this.mProvincia = mProvincia;
    }

    public String getmCellulare() {
        return mCellulare;
    }

    public void setmCellulare(String mCellulare) {
        this.mCellulare = mCellulare;
    }

    public String getmTipologia() {
        return mTipologia;
    }

    public void setmTipologia(String mTipologia) {
        this.mTipologia = mTipologia;
    }

    public String getNomeSocietà() {
        return mNomeSocietà;
    }

    public void setNomeSocietà(String mNomeSocietà) {
        this.mNomeSocietà = mNomeSocietà;
    }

    public String getIndirizzo() {
        return mIndirizzo;
    }

    public void setIndirizzo(String mIndirizzo) {
        this.mIndirizzo = mIndirizzo;
    }

    public String getCap() {
        return mCap;
    }

    public void setCap(String mCap) {
        this.mCap = mCap;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getmCitta() {
        return mCitta;
    }

    public void setmCitta(String mCitta) {
        this.mCitta = mCitta;
    }

    public String getmTelefono() {
        return mTelefono;
    }

    public void setmTelefono(String mTelefono) {
        this.mTelefono = mTelefono;
    }

    public String getmFax() {
        return mFax;
    }

    public void setmFax(String mFax) {
        this.mFax = mFax;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {


        dest.writeInt(ID);
        dest.writeInt(mCodiceSocieta);
        dest.writeString(mNomeSocietà);
        dest.writeString(mIndirizzo);
        dest.writeString(mCap);
        dest.writeString(mCitta);
        dest.writeString(mStato);
        dest.writeString(mFax);
        dest.writeString(mTelefono);
        dest.writeString(mCellulare);

        dest.writeString(mNote);
        dest.writeString(mTipologia);
        dest.writeString(mProvincia);
        dest.writeString(mPartita_iva);
        dest.writeString(mCOD_FISCALE);
        dest.writeString(mSito);

    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator<Societa>() {
        public Societa createFromParcel(Parcel in) {
            return new Societa(in);
        }

        @Override
        public Societa[] newArray(int size) {
            return new Societa[size];
        }
    };

    public Societa(Parcel in) {

        ID = in.readInt();
        mCodiceSocieta = in.readInt();
        mNomeSocietà = in.readString();
        mIndirizzo = in.readString();
        mCap = in.readString();
        mCitta = in.readString();
        mStato = in.readString();
        mFax = in.readString();
        mTelefono = in.readString();
        mCellulare = in.readString();

        mNote = in.readString();
        mTipologia = in.readString();
        mProvincia = in.readString();
        mPartita_iva = in.readString();
        mCOD_FISCALE = in.readString();
        mSito = in.readString();
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ID", getCodiceSocieta());
            jsonObject.put("SOCIETA", getNomeSocietà());
            jsonObject.put("INDIRIZZO", getIndirizzo());
            jsonObject.put("CAP", getCap());
            jsonObject.put("WWW", getSito());
            jsonObject.put("PROVINCIA", getmProvincia());
            jsonObject.put("STATO", getStato());
            jsonObject.put("CITTA", getmCitta());
            jsonObject.put("IVA", getPartitaIva());
            jsonObject.put("TELEFONO", getmTelefono());
            jsonObject.put("CELLULARE", getmCellulare());
            jsonObject.put("COD_FISCALE", getCOD_FISCALE());
            jsonObject.put("CELLULARE", getmCellulare());
            jsonObject.put("NOTE", getNote());
            jsonObject.put("TIPOLOGIA", getmTipologia());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }

        final Societa societa = (Societa) other;
        if (this.getID() == societa.getID()) {
            return true;
        }
        return false;
    }
}
