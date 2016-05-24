package mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica;

import com.google.gson.annotations.SerializedName;

/**
 * Created by meddaakouri on 13/11/2015.
 */
public class RubricaElement {


    @SerializedName("CITTA")
    private String mCitta;

    @SerializedName("TELEFONO")
    private String mTelefono;


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

}
