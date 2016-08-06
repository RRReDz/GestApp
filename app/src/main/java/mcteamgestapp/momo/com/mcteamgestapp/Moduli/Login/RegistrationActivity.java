package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import mcteamgestapp.momo.com.mcteamgestapp.R;
import mcteamgestapp.momo.com.mcteamgestapp.Utils.Functions;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mNameView;
    private EditText mSurnameView;
    private EditText mPhoneView;
    private EditText mPlaceView;
    private EditText mBirthdayView;
    private EditText mEmailView;

    private CheckBox mCheckSistemi;
    private CheckBox mCheckGestionale;
    private CheckBox mCheckProduzione;
    private CheckBox mCheckCommerciale;
    private CheckBox mCheckAmministrazione;
    private CheckBox mCheckCapoProgetto;
    private CheckBox mCheckConsulente;
    private CheckBox mCheckPersonale;

    private LinearLayout mRegistrationView; //Non usa?
    private RelativeLayout mRegistrationSuccess; //Non usa?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Don't show up keyboard in onLoad
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mNameView = (EditText) findViewById(R.id.register_name);
        mSurnameView = (EditText) findViewById(R.id.registrer_username);
        mPhoneView = (EditText) findViewById(R.id.register_phone);
        mPlaceView = (EditText) findViewById(R.id.register_birthday_place);
        mBirthdayView = (EditText) findViewById(R.id.register_birthday);
        mEmailView = (EditText) findViewById(R.id.register_email);
        mRegistrationView = (LinearLayout) findViewById(R.id.main_register_view);
        mRegistrationSuccess = (RelativeLayout) findViewById(R.id.registration_complete);


        mCheckProduzione = (CheckBox) findViewById(R.id.check_registrazione_produzione);
        mCheckSistemi = (CheckBox) findViewById(R.id.check_registrazione_sistemi);
        mCheckAmministrazione = (CheckBox) findViewById(R.id.check_registrazione_amministrazion);
        mCheckCommerciale = (CheckBox) findViewById(R.id.check_registrazione_commerciale);
        mCheckGestionale = (CheckBox) findViewById(R.id.check_registrazione_gestionale);
        mCheckConsulente = (CheckBox) findViewById(R.id.check_registrazione_consulente);
        mCheckCapoProgetto = (CheckBox) findViewById(R.id.check_registrazione_capo_progetto);

        Button inviaRegistrazione = (Button) findViewById(R.id.register_send);

        inviaRegistrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegistration();
            }
        });

        Button annullaRegistrazione = (Button) findViewById(R.id.register_abort);

        annullaRegistrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(goBack);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    public void attemptRegistration() {

        //reset errors
        mNameView.setError(null);
        mSurnameView.setError(null);
        mPhoneView.setError(null);
        mPlaceView.setError(null);
        mBirthdayView.setError(null);
        mEmailView.setError(null);


        //get the text inserted by the user
        String name = mNameView.getText().toString();
        String surname = mSurnameView.getText().toString();
        String phone = mPhoneView.getText().toString();
        String place = mPlaceView.getText().toString();
        String birthday = mBirthdayView.getText().toString();
        String email = mEmailView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(name)) {
            mNameView.setError("Nome mancante");
            focusView = mNameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(surname)) {
            mSurnameView.setError("Cognome mancante");
            focusView = mSurnameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError("Telefono mancante");
            focusView = mNameView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;

        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(place)) {
            mPlaceView.setError("Luogo nascita mancante");
            focusView = mPlaceView;
            cancel = true;
        }

        if (TextUtils.isEmpty(birthday) || !Functions.validateDate(birthday)) {
            mBirthdayView.setError("Data mancante o errata: rispettare il formato 01-01-1900");
            focusView = mBirthdayView;
            cancel = true;
        }

        String checkSistemi = (mCheckSistemi.isChecked()) ? "Sistemi" : "";
        String checkGestionale = (mCheckGestionale.isChecked()) ? "Gestionale" : "";
        String checkProduzione = (mCheckProduzione.isChecked()) ? "Produzione" : "";
        String checkCommerciale = (mCheckCommerciale.isChecked()) ? "Commerciale" : "";
        String checkAmministrazione = (mCheckAmministrazione.isChecked()) ? "Amministrazione" : "";
        String checkCapoProgetto = (mCheckCapoProgetto.isChecked()) ? "Capo Progetto" : "";
        String checkConsulente = (mCheckConsulente.isChecked()) ? "Consulente" : "";


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();

        } else {
            StringBuilder emailToSend = new StringBuilder();
            emailToSend.append("l'utente " + name.toUpperCase() + " " + surname.toUpperCase() + "\n" +
                    "recapito telefonico :" + phone + "\n" +
                    "indirizzo email :" + email + "\n" +
                    "nato a :" + place.toUpperCase() + "\n" +
                    "il :" + birthday + "\n" +
                    "Tipo Accesso :" + checkSistemi + "\n"
                    + checkGestionale + "\n"
                    + checkProduzione + "\n"
                    + checkCommerciale + "\n"
                    + checkAmministrazione + "\n"
                    + checkCapoProgetto + "\n"
                    + checkConsulente + "\n"
            );

            completeRegistration(emailToSend.toString());
        }
    }

    private void completeRegistration(String emailBody) {

        /***************************************************
         * THIS METHOD SEND THE EMAIL FOR REGISTRATION
         ****************************************************/

        Intent sendEmail = new Intent(Intent.ACTION_SEND);
        sendEmail.setType("message/rfc822");
        sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.email_address)});
        sendEmail.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.registration_request));
        sendEmail.putExtra(Intent.EXTRA_TEXT, emailBody);

        try {
            startActivity(Intent.createChooser(sendEmail, "Invio email.."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "nessun email client installato.", Toast.LENGTH_LONG);
        }

    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
}
