package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;
import mcteamgestapp.momo.com.mcteamgestapp.PUTRequest;
import mcteamgestapp.momo.com.mcteamgestapp.R;
import mcteamgestapp.momo.com.mcteamgestapp.ToolUtils;

public class NuovoAccessoActivity extends AppCompatActivity {

    private EditText mNameView;
    private EditText mSurnameView;
    private EditText mPhoneView;
    private EditText mPlaceView;
    private EditText mPasswordView;
    private EditText mEmailView;
    private EditText mDataNascita;
    private CheckBox mCheckSistemi;
    private CheckBox mCheckGestionale;
    private CheckBox mCheckProduzione;
    private CheckBox mCheckCommerciale;
    private CheckBox mCheckAmministrazione;
    private CheckBox mCheckCapoProgetto;
    private CheckBox mCheckConsulente;
    private Switch mAbilitato;
    private TextView mAbilitatoTv;

    boolean abilitato = true;

    AlertDialog.Builder dialogBuilder;

    private RequestQueue mRequestQueue;


    private UserInfo mUserToModify;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuovo_accesso);

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable actionBarBack = getDrawable(R.drawable.actionbar_sistemi);
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
            getSupportActionBar().setIcon(R.drawable.ic_accessibility_white_24dp);
        }

        dialogBuilder = new AlertDialog.Builder(this);

        mNameView = (EditText) findViewById(R.id.nuovo_accesso_nome);
        mSurnameView = (EditText) findViewById(R.id.nuovo_accesso_cognome);
        mPhoneView = (EditText) findViewById(R.id.nuovo_accesso_phone);
        mPlaceView = (EditText) findViewById(R.id.nuovo_accesso_luogo_nascita);
        mDataNascita = (EditText) findViewById(R.id.nuovo_accesso_data_nascita);
        mEmailView = (EditText) findViewById(R.id.nuovo_accesso_email);
        mPasswordView = (EditText) findViewById(R.id.nuovo_accesso_password);
        mAbilitato = (Switch) findViewById(R.id.switch_nuovo_accesso_abilitato);
        mAbilitatoTv = (TextView) findViewById(R.id.nuovo_accesso_abilitato_tv);

        mCheckProduzione = (CheckBox) findViewById(R.id.check_nuovo_accesso_produzione);
        mCheckSistemi = (CheckBox) findViewById(R.id.check_nuovo_accesso_sistemi);
        mCheckAmministrazione = (CheckBox) findViewById(R.id.check_nuovo_accesso_amministratore);
        mCheckCommerciale = (CheckBox) findViewById(R.id.check_nuovo_accesso_commerciale);
        mCheckGestionale = (CheckBox) findViewById(R.id.check_nuovo_accesso_commerciale);
        mCheckConsulente = (CheckBox) findViewById(R.id.check_nuovo_accesso_consulente);
        mCheckCapoProgetto = (CheckBox) findViewById(R.id.check_nuovo_accesso_capo_progetto);

        mRequestQueue = Volley.newRequestQueue(this);

        mAbilitato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAbilitato.isChecked()) {
                    mAbilitatoTv.setText(R.string.is_abilitato);
                    abilitato = true;
                } else {
                    mAbilitatoTv.setText(R.string.not_abilitato);
                    abilitato = false;
                }
            }
        });

        Button mModificaButton = (Button) findViewById(R.id.button_nuovo_accesso_conferma);

        mModificaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptCrea();
            }
        });

        Button mAnnullaButton = (Button) findViewById(R.id.button_nuovo_accesso_annulla);

        mAnnullaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void attemptCrea() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        boolean cancel = false;
        View focusView = null;

        String name = mNameView.getText().toString();
        String surname = mSurnameView.getText().toString();
        String phone = mPhoneView.getText().toString();
        String place = mPlaceView.getText().toString();
        String dataNascita = mDataNascita.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean mAmministratore = mCheckAmministrazione.isChecked();
        boolean mProduzione = mCheckProduzione.isChecked();
        boolean mSistemi = mCheckSistemi.isChecked();
        boolean mCommerciale = mCheckCommerciale.isChecked();
        boolean mGestionale = mCheckGestionale.isChecked();

        boolean mCapoProgetto = mCheckCapoProgetto.isChecked();
        boolean mConsulente = mCheckConsulente.isChecked();


        UserInfo userToCreate = new UserInfo();

        userToCreate.setPhone(phone);
        userToCreate.setLuogoNascita(place);

        if (TextUtils.isEmpty(dataNascita) || !ToolUtils.validateDate(dataNascita)) {
            mDataNascita.setError("Data mancante o errata: rispettare il formato 01-01-1900");
            focusView = mDataNascita;
            cancel = true;
        } else {
            userToCreate.setDataNascita(dataNascita);
        }


        if (!TextUtils.isEmpty(email)) {
            //  mUserToModify.setEmail(email);
            userToCreate.setEmail(email);
        } else {
            mEmailView.setError("Inserire un email");
            focusView = mEmailView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(password)) {
            // mUserToModify.setPassword(password);
            userToCreate.setPassword(password);
        } else {
            mPasswordView.setError("Inserire una password valida");
            focusView = mPasswordView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(name)) {
            //mUserToModify.setNome(name);
            userToCreate.setNome(name);
        } else {
            mNameView.setError("Inserire il nome");
            focusView = mNameView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(surname)) {
            //mUserToModify.setCognome(password);
            userToCreate.setCognome(surname);
        } else {
            mSurnameView.setError("Inserire il cognome");
            focusView = mPasswordView;
            cancel = true;
        }

        userToCreate.setAmministratore(mAmministratore ? 1 : 0);
        userToCreate.setProduzione(mProduzione ? 1 : 0);
        userToCreate.setSistemi(mSistemi ? 1 : 0);
        userToCreate.setCommerciale(mCommerciale ? 1 : 0);
        userToCreate.setGestionale(mGestionale ? 1 : 0);
        userToCreate.setConsulente(mConsulente ? 1 : 0);
        userToCreate.setCapoProgetto(mCapoProgetto ? 1 : 0);
        userToCreate.setAbilitato(abilitato);


        if (!cancel) {
            String json = userToCreate.toJson();
            sendRequest(json);
        } else {
            focusView.requestFocus();
        }
    }

    private void sendRequest(final String json) {
        String url = getString(R.string.mobile_url);
        url += "accessi-nuovo";

        HashMap params = new HashMap();
        params.put("data", json);

        dialogBuilder.setTitle("Creazione nuovo utente");


        PUTRequest modifyRequestJson = new PUTRequest(Request.Method.PUT, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean errore = response.getBoolean("error");
                    if (errore) {
                        showError(true);
                    } else {
                        showError(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError(true);
            }
        });

        mRequestQueue.add(modifyRequestJson);
    }

    public void showError(boolean error) {
        if (error) {
            dialogBuilder.setTitle("Errore modifica dati");
            dialogBuilder.setMessage("Si è verificato un errore nella modifica dei dati");
            dialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        } else {
            dialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    return;
                }
            });
            AlertDialog dialog = dialogBuilder.create();
            dialog.setTitle("Successo!");
            dialog.setMessage("utente creato con successo");
            dialog.show();
        }
    }
}
