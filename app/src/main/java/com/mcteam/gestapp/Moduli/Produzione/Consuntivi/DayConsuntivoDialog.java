package com.mcteam.gestapp.Moduli.Produzione.Consuntivi;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;
import com.mcteam.gestapp.Models.Associazione;
import com.mcteam.gestapp.Models.Commessa;
import com.mcteam.gestapp.Models.OrariAttivita;
import com.mcteam.gestapp.Moduli.Gestionale.Associazioni.CommessaSpinnerAdapter;
import com.mcteam.gestapp.NetworkReq.PUTRequest;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.Functions;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class DayConsuntivoDialog extends AppCompatActivity {

    ArrayList<Associazione> myAssociazioni;
    ArrayList<Commessa> myCommesse;
    Spinner mCommesseSpinner;
    Spinner mCommesseHalfDaySpinner;
    CommessaSpinnerAdapter commessaSpinnerAdapter;
    CommessaSpinnerAdapter commessaHalfSpinnerAdapter;
    EditText mDescrizioneView;
    EditText mHalfDayDescrizioneView;
    TextView mConsuntivoCommessaError;
    TextView mHalfConsuntivoCommessaError;
    RadioButton mFerie;
    RadioButton mHalfDay;
    RadioButton mFullDay;
    LinearLayout mOtherHarfDay;
    OrariAttivita attivitaAttuale;
    Commessa selectedCommessa = null;
    RequestQueue mRequestQueue;
    Commessa halfCommessaSelected = null;
    ArrayList<Commessa> halfCommesse;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_day_consuntivo);

        //Permette landscape e portrait solo se è un tablet
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        attivitaAttuale = getIntent().getParcelableExtra("orarioAttivita");
        myAssociazioni = getIntent().getParcelableArrayListExtra("associazioniList");

        mRequestQueue = Volley.newRequestQueue(this);

        myCommesse = new ArrayList<>();
        halfCommesse = new ArrayList<>();

        try {
            setupAssociazioni();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //***********************************************************
        // SETUP VIEW ELEMENTS
        //***********************************************************
        mCommesseSpinner = (Spinner) findViewById(R.id.day_consuntivo_commesse_spinner);
        mCommesseHalfDaySpinner = (Spinner) findViewById(R.id.day_consuntivo_commesse_half_day_spinner);

        //editText
        mDescrizioneView = (EditText) findViewById(R.id.day_consuntivo_descrizione);
        mHalfDayDescrizioneView = (EditText) findViewById(R.id.day_consuntivo_commesse_half_day_descrizione);

        //textView
        mConsuntivoCommessaError = (TextView) findViewById(R.id.day_consuntivo_sede_error);
        mHalfConsuntivoCommessaError = (TextView) findViewById(R.id.day_consuntivo_commesse_half_error);

        //Radio button
        mFerie = (RadioButton) findViewById(R.id.day_consuntivo_ferie);
        mHalfDay = (RadioButton) findViewById(R.id.day_consuntivo_mezza_giornata);
        mFullDay = (RadioButton) findViewById(R.id.day_consuntivo_giornata_intera);

        //LinearLayout
        mOtherHarfDay = (LinearLayout) findViewById(R.id.day_consuntivo_other_half_day);

        // bootstrap button
        BootstrapButton annulla = (BootstrapButton) findViewById(R.id.day_consuntivo_annulla);
        BootstrapButton conferma = (BootstrapButton) findViewById(R.id.day_consuntivo_conferma);


        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSalva();
            }
        });

        //Gson per la serializzazione dei JSON
        gson = new Gson();

        //set up adapter for spinner
        commessaSpinnerAdapter = new CommessaSpinnerAdapter(this, R.layout.nominativo_societa_spinner_row, myCommesse);
        mCommesseSpinner.setAdapter(commessaSpinnerAdapter);


        commessaHalfSpinnerAdapter = new CommessaSpinnerAdapter(this, R.layout.nominativo_societa_spinner_row, halfCommesse);
        mCommesseHalfDaySpinner.setAdapter(commessaHalfSpinnerAdapter);

        mCommesseHalfDaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    halfCommessaSelected = (Commessa) parent.getItemAtPosition(position);
                } else {
                    halfCommessaSelected = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //SetOnItemSelectedListener
        mCommesseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedCommessa = (Commessa) parent.getItemAtPosition(position);
                    setupOtherHalfDaySpinner();
                } else {
                    selectedCommessa = null;
                    setupOtherHalfDaySpinner();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCommessa = null;
            }
        });

        mHalfDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mOtherHarfDay.setVisibility(View.VISIBLE);
                    setupOtherHalfDaySpinner();
                } else {
                    mOtherHarfDay.setVisibility(View.GONE);
                }
            }
        });

        String titolo = attivitaAttuale.getDay_name() + " " + attivitaAttuale.getDay() + "/" + attivitaAttuale.getMonth();
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(titolo);
        else
            setTitle(titolo);

        setupView();
    }

    private void setupAssociazioni() throws ParseException {
        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        java.util.Date attivitaDate = sdf.parse(Functions.getFormattedDate(attivitaAttuale.getGiorno()));

        if (myAssociazioni.size() > 0) {
            myCommesse.add(0, new Commessa());
            java.util.Date tempInizio;
            java.util.Date tempFine;

            for (Associazione associazione : myAssociazioni) {
                try {
                    tempInizio = sdf.parse(Functions.getFormattedDate(associazione.getData_inizio()));
                    tempFine = sdf.parse(Functions.getFormattedDate(associazione.getData_fine()));
                    if (attivitaDate.after(tempInizio) && attivitaDate.before(tempFine)) {
                        myCommesse.add(associazione.getCommessa());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    private void setupOtherHalfDaySpinner() {
        halfCommesse.clear();
        if (selectedCommessa != null) {
            if (!myCommesse.isEmpty()) {
                for (Commessa commessa : myCommesse) {
                    if (!commessa.equals(selectedCommessa))
                        halfCommesse.add(commessa);
                }
            }
        } else {
            halfCommesse.addAll(myCommesse);
        }
        commessaHalfSpinnerAdapter.notifyDataSetChanged();
    }

    void setupView() {
        mDescrizioneView.setText(attivitaAttuale.getDescrizione());
        System.out.println(" ore totali " + attivitaAttuale.getOre_totali());

        if (attivitaAttuale.getOre_totali() == 0.0)
            mFerie.setChecked(true);
        else if (attivitaAttuale.getOre_totali() == 0.5) {
            mHalfDay.setChecked(true);
            setupOtherHalfDaySpinner();
        } else if (attivitaAttuale.getOre_totali() == 1.0)
            mFullDay.setChecked(true);

        int indexCommessa = myCommesse.indexOf(attivitaAttuale.getCommessa());
        mCommesseSpinner.setSelection(indexCommessa);
        if (attivitaAttuale.getOtherHalf() != null) {
            int otheIndex = myCommesse.indexOf(attivitaAttuale.getOtherHalf().getCommessa());
            mCommesseHalfDaySpinner.setSelection(otheIndex);
            mHalfDayDescrizioneView.setText(attivitaAttuale.getOtherHalf().getDescrizione());
        }
    }


    void attemptSalva() {

        int oldCommessaId = 0;

        OrariAttivita otherHalf = null;

        boolean cancel = false;
        View focusView = null;
        int oldHalfCommessaId = -1;

        String descrizione = mDescrizioneView.getText().toString();
        mConsuntivoCommessaError.setError(null);
        mHalfConsuntivoCommessaError.setError(null);

        if (mFerie.isChecked())
            attivitaAttuale.setOre_totali(0.0);
        else if (mHalfDay.isChecked()) {
            attivitaAttuale.setOre_totali(0.5);
            String halfDescrizione = mHalfDayDescrizioneView.getText().toString();

            if (halfCommessaSelected != null) {
                otherHalf = new OrariAttivita();
                if (attivitaAttuale.getOtherHalf() != null) {
                    otherHalf.setFromOld(attivitaAttuale.getOtherHalf());
                    oldHalfCommessaId = otherHalf.getId_commessa();
                    otherHalf.setId_commessa(halfCommessaSelected.getID());
                    otherHalf.setDescrizione(halfDescrizione);
                    otherHalf.setModifica(true);
                } else {
                    oldHalfCommessaId = halfCommessaSelected.getID();
                    otherHalf.setGiorno(attivitaAttuale.getGiorno());
                    otherHalf.setDescrizione(halfDescrizione);
                    otherHalf.setOre_totali(0.5);
                    otherHalf.setId_utente(attivitaAttuale.getId_utente());
                    otherHalf.setId_commessa(halfCommessaSelected.getID());
                }
            } else if (!TextUtils.isEmpty(halfDescrizione)) {
                mHalfConsuntivoCommessaError.setError("Commessa non selezionata");
                focusView = mHalfConsuntivoCommessaError;
                cancel = true;
            }
        } else if (mFullDay.isChecked())
            attivitaAttuale.setOre_totali(1);

        attivitaAttuale.setDescrizione(descrizione);

        if (selectedCommessa == null) {
            mConsuntivoCommessaError.setError("Selezionare una commessa");
            focusView = mConsuntivoCommessaError;
            cancel = true;
        } else {
            if (attivitaAttuale.isModifica())
                oldCommessaId = attivitaAttuale.getId_commessa();
            else
                oldCommessaId = selectedCommessa.getID();
            attivitaAttuale.setCommessa(selectedCommessa);
            attivitaAttuale.setId_commessa(selectedCommessa.getID());
        }
        if (!cancel) {
            sendItNow(attivitaAttuale, oldCommessaId);
            if (otherHalf != null) {
                sendItNow(otherHalf, oldHalfCommessaId);
            }
            /*
            String jsonAttivita = gson.toJson(attivitaAttuale);
            if(!attivitaAttuale.isModifica()){
                sendRequest(jsonAttivita, "attivita-nuovo");
            }
            else{
                String url = "attivita-update/"+attivitaAttuale.getGiorno() + "/" + oldCommessaId + "/" + attivitaAttuale.getId_utente();
                sendRequest(jsonAttivita, url);
                System.out.println(jsonAttivita);
            }*/

        } else {
            focusView.requestFocus();
        }
    }

    public void sendItNow(OrariAttivita attivita, int oldCommessaId) {
        String jsonAttivita = gson.toJson(attivita);
        if (!attivita.isModifica()) {
            sendRequest(jsonAttivita, "attivita-nuovo");
        } else {
            String url = "attivita-update/" + attivita.getGiorno() + "/" + oldCommessaId + "/" + attivita.getId_utente();
            sendRequest(jsonAttivita, url);
        }
    }

    public void sendRequest(String json, String path) {
        String url = getString(R.string.mobile_url);
        url += path;

        HashMap params = new HashMap();
        params.put("data", json);

        PUTRequest modifyRequestJson = new PUTRequest(Request.Method.PUT, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("ADD NEW ELEMENT", response.toString());
                try {
                    boolean errore = response.getBoolean("error");
                    if (errore) {

                    } else {
                        Intent data = new Intent();
                        if (getParent() == null) {
                            setResult(Activity.RESULT_OK, data);
                        } else {
                            getParent().setResult(Activity.RESULT_OK, data);
                        }
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //showError(true);
            }
        });

        mRequestQueue.add(modifyRequestJson);

    }

}


