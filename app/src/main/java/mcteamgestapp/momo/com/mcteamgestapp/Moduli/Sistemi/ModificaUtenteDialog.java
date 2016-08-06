package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi;

import android.annotation.TargetApi;
import android.app.Activity;
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

import java.util.HashMap;

import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;
import mcteamgestapp.momo.com.mcteamgestapp.NetworkReq.PUTRequest;
import mcteamgestapp.momo.com.mcteamgestapp.R;
import mcteamgestapp.momo.com.mcteamgestapp.Utils.Functions;

public class ModificaUtenteDialog extends AppCompatActivity {

    private EditText mNameView;
    private EditText mSurnameView;
    private EditText mPhoneView;
    private EditText mPlaceView;
    private EditText mBirthdayView;
    private EditText mEmailView;
    private EditText mPasswordView;
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
        setContentView(R.layout.activity_modifica_utente_dialog);

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable actionBarBack = getDrawable(R.drawable.actionbar_sistemi);
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
            getSupportActionBar().setIcon(R.drawable.ic_accessibility_white_24dp);
        }

        dialogBuilder = new AlertDialog.Builder(this);

        mNameView = (EditText) findViewById(R.id.modifica_nome);
        mSurnameView = (EditText) findViewById(R.id.modifica_cognome);
        mPhoneView = (EditText) findViewById(R.id.modifica_telefono);
        mPlaceView = (EditText) findViewById(R.id.modifica_luogo_nascita);
        mBirthdayView = (EditText) findViewById(R.id.modifica_data_nascita);
        mPasswordView = (EditText) findViewById(R.id.modifica_password);
        mEmailView = (EditText) findViewById(R.id.modifica_email);
        mAbilitato = (Switch) findViewById(R.id.modifica_abilitato);
        mAbilitatoTv = (TextView) findViewById(R.id.abilitato_status_view);

        mCheckProduzione = (CheckBox) findViewById(R.id.check_produzione);
        mCheckSistemi = (CheckBox) findViewById(R.id.check_sistemi);
        mCheckAmministrazione = (CheckBox) findViewById(R.id.check_amministrazione);
        mCheckCommerciale = (CheckBox) findViewById(R.id.check_commerciale);
        mCheckGestionale = (CheckBox) findViewById(R.id.check_gestionale);
        mCheckConsulente = (CheckBox) findViewById(R.id.check_consulente);
        mCheckCapoProgetto = (CheckBox) findViewById(R.id.check_capo_progetto);

        mRequestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();

        mUserToModify = intent.getParcelableExtra("userToModify");

        if (mUserToModify != null) {
            setupUserInfo();
        } else {
            Toast.makeText(getApplicationContext(), "i dati sono nulli", Toast.LENGTH_LONG).show();
        }

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

        Button mModificaButton = (Button) findViewById(R.id.modifica_confirm);

        mModificaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptModifica();
            }
        });

        Button mAnnullaButton = (Button) findViewById(R.id.modifica_annulla);

        mAnnullaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupUserInfo() {

        mPasswordView.setText(mUserToModify.getPassword());

        System.out.println(mUserToModify.toJson());

        if (!mUserToModify.getNome().equals("null"))
            mNameView.setText(mUserToModify.getNome());
        else
            mNameView.setText("");

        if (!mUserToModify.getCognome().equals("null"))
            mSurnameView.setText(mUserToModify.getCognome());
        else
            mSurnameView.setText("");

        if (mUserToModify.getPhone() != null && !mUserToModify.getPhone().equals("null"))
            mPhoneView.setText(mUserToModify.getPhone());
        else
            mPhoneView.setText("");

        if (mUserToModify.getEmail() != null && !mUserToModify.getEmail().equals("null"))
            mEmailView.setText(mUserToModify.getEmail());
        else
            mEmailView.setText("");

        if (mUserToModify.getDataNascita() != null && !mUserToModify.getDataNascita().equals("null"))
            mBirthdayView.setText(mUserToModify.getDataNascita());
        else
            mBirthdayView.setText("");

        if (mUserToModify.getLuogoNascita() != null && !mUserToModify.getLuogoNascita().equals("null"))
            mPlaceView.setText(mUserToModify.getLuogoNascita());
        else
            mPlaceView.setText("");


        if (this.mUserToModify.isAmministratore())
            mCheckAmministrazione.setChecked(true);
        if (this.mUserToModify.isSistemi())
            mCheckSistemi.setChecked(true);
        if (this.mUserToModify.isCommerciale())
            mCheckCommerciale.setChecked(true);
        if (this.mUserToModify.isGestionale())
            mCheckGestionale.setChecked(true);
        if (this.mUserToModify.isProduzione())
            mCheckProduzione.setChecked(true);
        if (this.mUserToModify.isCapoProgetto())
            mCheckCapoProgetto.setChecked(true);
        if (this.mUserToModify.isConsulente())
            mCheckConsulente.setChecked(true);


        if (this.mUserToModify.isAbilitato()) {
            mAbilitato.setChecked(true);
            mAbilitatoTv.setText(R.string.is_abilitato);
            abilitato = true;
        } else {
            mAbilitato.setChecked(false);
            mAbilitatoTv.setText(R.string.not_abilitato);
            abilitato = false;
        }
    }

    public void attemptModifica() {

        mEmailView.setError(null);
        mPasswordView.setError(null);
        mNameView.setError(null);
        mSurnameView.setError(null);

        boolean cancel = false;
        View focusView = null;

        String name = mNameView.getText().toString();
        String surname = mSurnameView.getText().toString();
        String phone = mPhoneView.getText().toString();
        String place = mPlaceView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String data_nascita = mBirthdayView.getText().toString();

        boolean mAmministratore = mCheckAmministrazione.isChecked();
        boolean mProduzione = mCheckProduzione.isChecked();
        boolean mSistemi = mCheckSistemi.isChecked();
        boolean mCommerciale = mCheckCommerciale.isChecked();
        boolean mGestionale = mCheckGestionale.isChecked();

        boolean mCapoProgetto = mCheckCapoProgetto.isChecked();
        boolean mConsulente = mCheckConsulente.isChecked();

        UserInfo userToModify = mUserToModify;

        if (!TextUtils.isEmpty(email)) {
            userToModify.setEmail(email);
        } else {
            mEmailView.setError("Inserire un email");
            focusView = mEmailView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(password)) {
            userToModify.setPassword(password);
        } else {
            mPasswordView.setError("Inserire una password valida");
            focusView = mPasswordView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(name)) {
            userToModify.setNome(name);
        } else {
            mNameView.setError("Inserire il nome");
            focusView = mNameView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(surname)) {
            userToModify.setCognome(surname);
        } else {
            mSurnameView.setError("Inserire il cognome");
            focusView = mPasswordView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(data_nascita)) {
            System.out.println("is not empty" + data_nascita);
            if (!Functions.validateDate(data_nascita)) {
                System.out.println("date is not valid");
                mBirthdayView.setError("Data mancante o errata: rispettare il formato 01-01-1900");
                focusView = mBirthdayView;
                cancel = true;
            }
        } else {
            userToModify.setDataNascita(data_nascita);
        }

        userToModify.setPhone(phone);
        userToModify.setLuogoNascita(place);

        userToModify.setAmministratore(mAmministratore ? 1 : 0);
        userToModify.setProduzione(mProduzione ? 1 : 0);
        userToModify.setSistemi(mSistemi ? 1 : 0);
        userToModify.setCommerciale(mCommerciale ? 1 : 0);
        userToModify.setGestionale(mGestionale ? 1 : 0);
        userToModify.setConsulente(mConsulente ? 1 : 0);
        userToModify.setCapoProgetto(mCapoProgetto ? 1 : 0);

        userToModify.setAbilitato(mAbilitato.isChecked());

        if (!cancel) {
            String json = userToModify.toJson();
            mUserToModify = userToModify;
            sendRequest(json);
        } else {
            focusView.requestFocus();
        }
    }

    private void sendRequest(final String json) {
        String url = getString(R.string.mobile_url);
        url += "accessi/" + mUserToModify.getID();

        System.out.println(json);

        HashMap params = new HashMap();
        params.put("data", json);

        dialogBuilder.setTitle("Stato modifica");


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

                    Intent data = new Intent();
                    data.putExtra("modifiedUser", mUserToModify);

                    if (getParent() == null) {
                        setResult(Activity.RESULT_OK, data);
                    } else {
                        getParent().setResult(Activity.RESULT_OK, data);
                    }
                    finish();
                    return;
                }
            });
            AlertDialog dialog = dialogBuilder.create();
            dialog.setTitle("Successo!");
            dialog.setMessage("Modifica dati avvenuta con successo");
            dialog.show();
        }
    }
}

