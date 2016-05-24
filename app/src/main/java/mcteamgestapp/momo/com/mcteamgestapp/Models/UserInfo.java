package mcteamgestapp.momo.com.mcteamgestapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.toolbox.StringRequest;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by YassIne on 09/11/2015.
 */
public class UserInfo implements Parcelable {
    /**************************
     * This class containt all the info about the user
     * for more info see the Database table
     **************************/

    @SerializedName("ID_UTENTE")
    private int UserID;

    @SerializedName("NOME")
    private String mNome = "";

    @SerializedName("COGNOME")
    private String mCognome;

    @SerializedName("EMAIL")
    private String mEmail;

    @SerializedName("PWD")
    private String mPassword;

    private String mUsername;

    @SerializedName("telefono")
    private String mPhone;

    @SerializedName("COMMERCIALE")
    private int mCommerciale;

    @SerializedName("GESTIONALE")
    private int mGestionale;

    @SerializedName("CAPO_PROGETTO")
    private int mCapoProgetto;

    @SerializedName("CONSULENTE")
    private int mConsulente;

    @SerializedName("AMMINISTRATORE")
    private int mAmministratore;

    @SerializedName("PRODUZIONE")
    private int mProduzione;

    @SerializedName("DIREZIONE")
    private int mDirezione;

    @SerializedName("PERSONALE")
    private int mPersonale;

    @SerializedName("SISTEMI")
    private int mSistemi;

    @SerializedName("abilitato")
    private int mAbilitato;

    @SerializedName("luogo_nascita")
    private String mLuogoNascita;

    @SerializedName("data_nascita")
    private String dataNascita;

    //-----------------------------------
    //      COSTRUCTOR
    //-----------------------------------

    public UserInfo() {
        UserID = -1;
        mNome = null;
        mCognome = null;
        mEmail = null;
        mPassword = null;
        mUsername = null;
        mPhone = null;
    }

    //-----------------------------------
    //      SETTER
    //-----------------------------------


    public void setLuogoNascita(String mLuogoNascita) {
        this.mLuogoNascita = mLuogoNascita;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public void setAbilitato(Boolean abilitato) {
        this.mAbilitato = abilitato ? 1 : 0;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public void setNome(String mNome) {
        this.mNome = mNome;
    }

    public void setCognome(String mCognome) {
        this.mCognome = mCognome;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void setCommerciale(int mCommerciale) {
        this.mCommerciale = mCommerciale;
    }

    public void setGestionale(int mGestionale) {
        this.mGestionale = mGestionale;
    }

    public void setCapoProgetto(int mCapoProgetto) {
        this.mCapoProgetto = mCapoProgetto;
    }

    public void setConsulente(int mConsulente) {
        this.mConsulente = mConsulente;
    }

    public void setAmministratore(int mAmministratore) {
        this.mAmministratore = mAmministratore;
    }

    public void setProduzione(int mProduzione) {
        this.mProduzione = mProduzione;
    }

    public void setDirezione(int mDirezione) {
        this.mDirezione = mDirezione;
    }

    public void setPersonale(int mPersonale) {
        this.mPersonale = mPersonale;
    }

    public void setSistemi(int mSistemi) {
        this.mSistemi = mSistemi;
    }

    public void setID(int userID) {
        UserID = userID;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    //-----------------------------------------
    //          GETTER
    //-----------------------------------------


    public String getDataNascita() {
        return dataNascita;
    }

    public String getPhone() {
        return mPhone;
    }

    public int getID() {
        return UserID;
    }

    public Boolean isAbilitato() {
        return mAbilitato == 1;
    }

    public String getNome() {
        return mNome;
    }

    public String getCognome() {
        return mCognome;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public Boolean isCommerciale() {
        return mCommerciale == 1;
    }

    public Boolean isGestionale() {
        return mGestionale == 1;
    }

    public Boolean isCapoProgetto() {
        return mCapoProgetto == 1;
    }

    public Boolean isConsulente() {
        return mConsulente == 1;
    }

    public Boolean isAmministratore() {
        return mAmministratore == 1;
    }

    public Boolean isProduzione() {
        return mProduzione == 1;
    }

    public Boolean isDirezione() {
        return mDirezione == 1;
    }

    public Boolean isPersonale() {
        return mPersonale == 1;
    }

    public Boolean isSistemi() {
        return mSistemi == 1;
    }

    public String getUsername() {
        return mUsername;
    }

    //------------------------------------------------
    // TODO: Metodi da implementare tramite connessione al database
    //------------------------------------------------

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(UserID);
        dest.writeString(mNome);
        dest.writeString(mCognome);
        dest.writeString(mEmail);
        dest.writeString(mPassword);
        dest.writeString(mUsername);
        dest.writeString(mPhone);
        dest.writeString(mLuogoNascita);
        dest.writeString(dataNascita);

        dest.writeInt(mCommerciale);     //if myBoolean == true, byte == 1
        dest.writeInt(mGestionale);
        dest.writeInt(mCapoProgetto);     //if myBoolean == true, byte == 1
        dest.writeInt(mConsulente);     //if myBoolean == true, byte == 1
        dest.writeInt(mAmministratore);     //if myBoolean == true, byte == 1
        dest.writeInt(mProduzione);     //if myBoolean == true, byte == 1
        dest.writeInt(mDirezione);     //if myBoolean == true, byte == 1
        dest.writeInt(mPersonale);
        dest.writeInt(mSistemi);
        dest.writeInt(mAbilitato);
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator<UserInfo>() {
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };


    public UserInfo(Parcel in) {


        UserID = in.readInt();
        mNome = in.readString();
        mCognome = in.readString();
        mEmail = in.readString();
        mPassword = in.readString();
        mUsername = in.readString();
        mPhone = in.readString();
        mLuogoNascita = in.readString();
        dataNascita = in.readString();


        mCommerciale = in.readInt();
        mGestionale = in.readInt();
        mCapoProgetto = in.readInt();
        mConsulente = in.readInt();
        mAmministratore = in.readInt();
        mProduzione = in.readInt();
        mDirezione = in.readInt();
        mPersonale = in.readInt();
        mSistemi = in.readInt();
        mAbilitato = in.readInt();
    }


    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ID_UTENTE", getID());
            jsonObject.put("NOME", getNome());
            jsonObject.put("COGNOME", getCognome());
            jsonObject.put("EMAIL", getEmail());
            jsonObject.put("PWD", getPassword());
            jsonObject.put("COMMERCIALE", isCommerciale() ? 1 : 0);
            jsonObject.put("GESTIONALE", isGestionale() ? 1 : 0);
            jsonObject.put("CONSULENTE", isConsulente() ? 1 : 0);
            jsonObject.put("AMMINISTRAZIONE", isAmministratore() ? 1 : 0);
            jsonObject.put("PRODUZIONE", isProduzione() ? 1 : 0);
            jsonObject.put("SISTEMI", isSistemi() ? 1 : 0);
            jsonObject.put("PERSONALE", isPersonale() ? 1 : 0);
            jsonObject.put("DIREZIONE", isDirezione() ? 1 : 0);
            jsonObject.put("CAPO_PROGETTO", isCapoProgetto() ? 1 : 0);
            jsonObject.put("telefono", getPhone());
            jsonObject.put("luogo_nascita", getLuogoNascita());
            jsonObject.put("data_nascita", getDataNascita());
            jsonObject.put("abilitato", mAbilitato);

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfo)) return false;

        UserInfo userInfo = (UserInfo) o;

        if (UserID != userInfo.UserID) return false;
        if (!mNome.equals(userInfo.mNome)) return false;
        return mCognome.equals(userInfo.mCognome);

    }

    @Override
    public int hashCode() {
        int result = UserID;
        result = 31 * result + mNome.hashCode();
        result = 31 * result + mCognome.hashCode();
        return result;
    }

    public String getLuogoNascita() {
        return mLuogoNascita;
    }

    public boolean isEmpty() {
        return mEmail != null && mPassword != null;
    }
}