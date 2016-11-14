package com.mcteam.gestapp.Moduli.Gestionale.Associazioni;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;
import com.mcteam.gestapp.Fragments.DatePickerFragment;
import com.mcteam.gestapp.Models.Associazione;
import com.mcteam.gestapp.Models.Commessa;
import com.mcteam.gestapp.Models.UserInfo;
import com.mcteam.gestapp.NetworkReq.CustomRequest;
import com.mcteam.gestapp.NetworkReq.VolleyRequests;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.AndroidUtils;
import com.mcteam.gestapp.Utils.Functions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NuovaAssociazioneActivity extends AppCompatActivity implements MultiChoiceSpinner.MultiSpinnerListener {

    private static final int CONSULENTE_REQUEST = 1231;
    EditText mDataInizioView;
    EditText mDataFineView;

    ListView mSelectedConsulentiListView;

    Spinner mCommessaSpinner;
    Spinner mCapoProgettoSpinner;

    Commessa selectedCommessa = null;
    UserInfo selectedCapoProgetto = null;

    TextView mCommessaError;
    TextView mCapoProgettoError;
    TextView mConsulenteError;

    ArrayList<UserInfo> mUtentiList;
    ArrayList<Commessa> mCommesseList;
    ArrayList<UserInfo> mConsulentiList;

    ArrayList<UserInfo> selectedConsulenti;
    ArrayList<UserInfo> mOldConsulentiList;

    CommessaSpinnerAdapter mCommesseAdapter;
    UtentiSpinnerAdapter mUtentiAdapter;
    AssociazioneConsulentiAdapter mConsulenteListAdapter;

    VolleyRequests mVolleyRequests;

    Associazione mAssociazioneAttuale;

    boolean isModifica = false;

    RequestQueue mRequestQueue = null;
    int idOldRisorsa;
    int idOldCommessa;

    Gson gson;
    Comparator<UserInfo> surnameSortingComparator = new Comparator<UserInfo>() {
        @Override
        public int compare(UserInfo lhs, UserInfo rhs) {
            return String.CASE_INSENSITIVE_ORDER.compare(lhs.getCognome(), rhs.getCognome());
        }
    };

    Comparator<Commessa> commessaComparator = new Comparator<Commessa>() {
        @Override
        public int compare(Commessa lhs, Commessa rhs) {
            String lhsNomeSocieta = null;
            String rhsNomeSocieta = null;

            if (lhs.getCliente() != null && lhs.getCliente().getNomeSocietà() != null)
                lhsNomeSocieta = lhs.getCliente().getNomeSocietà();
            if (rhs.getCliente() != null && rhs.getCliente().getNomeSocietà() != null)
                rhsNomeSocieta = rhs.getCliente().getNomeSocietà();

            if (rhsNomeSocieta != null && lhsNomeSocieta != null)
                return String.CASE_INSENSITIVE_ORDER.compare(lhsNomeSocieta, rhsNomeSocieta);
            if (lhsNomeSocieta != null && rhsNomeSocieta == null) {
                return -1;
            } else
                return 2;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuova_associazione);

        //Permette landscape e portrait solo se è un tablet
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable actionBarBack = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                actionBarBack = getDrawable(R.drawable.actionbar_gestionale);
            }
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
        }

        //************************************************************
        //                          GET VIEW
        //************************************************************

        mDataInizioView = (EditText) findViewById(R.id.nuova_associazione_inizio);
        mDataFineView = (EditText) findViewById(R.id.nuova_associazione_fine);

        mCommessaError = (TextView) findViewById(R.id.associazione_commessa_error);
        mCapoProgettoError = (TextView) findViewById(R.id.associazione_capo_progetto_error);
        mConsulenteError = (TextView) findViewById(R.id.associazione_consulente_error);

        mSelectedConsulentiListView = (ListView) findViewById(R.id.associazione_nuovo_lista_selezionati);

        //********************** BootstrapButton ***********************************

        BootstrapButton annulla = (BootstrapButton) findViewById(R.id.associazione_annulla);
        BootstrapButton creaButton = (BootstrapButton) findViewById(R.id.associazione_nuovo);
        BootstrapButton aggiungiConsulente = (BootstrapButton) findViewById(R.id.associazione_nuovo_aggiunti_consulente);


        // ******************* SPINNER ********************************************
        mCommessaSpinner = (Spinner) findViewById(R.id.nuova_associazione_commessa);
        mCapoProgettoSpinner = (Spinner) findViewById(R.id.nuova_associazione_capo_progetto);

        //************************ ArrayList init ***************************************

        mUtentiList = new ArrayList<>();
        mCommesseList = new ArrayList<>();
        mConsulentiList = new ArrayList<>();
        selectedConsulenti = new ArrayList<>();
        mOldConsulentiList = new ArrayList<>();

        //************************ Adapter init ***************************************
        mCommesseAdapter = new CommessaSpinnerAdapter(getApplicationContext(), R.layout.nominativo_societa_spinner_row, mCommesseList);
        mUtentiAdapter = new UtentiSpinnerAdapter(getApplicationContext(), R.layout.nominativo_societa_spinner_row, mUtentiList);
        mConsulenteListAdapter = new AssociazioneConsulentiAdapter(this, mConsulentiList);

        mCommessaSpinner.setAdapter(mCommesseAdapter);
        mCapoProgettoSpinner.setAdapter(mUtentiAdapter);
        mSelectedConsulentiListView.setAdapter(mConsulenteListAdapter);
        AndroidUtils.getListViewSize(mSelectedConsulentiListView);

        //******************************** Other shit ***************************************
        mRequestQueue = Volley.newRequestQueue(this);
        gson = new Gson();
        mVolleyRequests = new VolleyRequests(this, this);

        //********************************* TextWatcher ***************************************

        //mDataInizioView.addTextChangedListener(new DateWatcher(mDataInizioView));
        //mDataFineView.addTextChangedListener(new DateWatcher(mDataFineView));

        mDataInizioView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSelData(mDataInizioView);
            }
        });

        mDataFineView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSelData(mDataFineView);
            }
        });


        //******************************** On element select control ***************************
        mCommessaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedCommessa = (Commessa) parent.getItemAtPosition(position);
                    setupCapoProgetto(selectedCommessa.getCapo_progetto());
                } else {
                    selectedCommessa = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCommessa = null;
            }
        });


        mCapoProgettoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedCapoProgetto = (UserInfo) parent.getItemAtPosition(position);
                } else
                    selectedCapoProgetto = null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCapoProgetto = null;
            }
        });

        mCommesseAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (isModifica) {
                    setupCommesse(mAssociazioneAttuale.getCommessa());
                }
            }
        });

        mUtentiAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (isModifica) {
                    setupCapoProgetto(mAssociazioneAttuale.getCommessa().getCapo_progetto());
                }
            }
        });

        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        creaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptNuovo();
            }
        });
        aggiungiConsulente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent multipleChooser = new Intent(getApplicationContext(), ConsulenteMultiSelectionDialog.class);
                multipleChooser.putParcelableArrayListExtra("consulentiList", mUtentiList);
                multipleChooser.putParcelableArrayListExtra("selectedConsulenti", selectedConsulenti);
                startActivityForResult(multipleChooser, CONSULENTE_REQUEST);
            }
        });

        //**************************************************************************
        // IS MODIFICA
        //*************************************************************************
        isModifica = getIntent().getBooleanExtra("isModifica", false);

        if (isModifica) {
            mAssociazioneAttuale = getIntent().getParcelableExtra("associazioneToModify");
            setupView(mAssociazioneAttuale);
            idOldRisorsa = mAssociazioneAttuale.getId_utente();
            idOldCommessa = mAssociazioneAttuale.getId_commessa();
            creaButton.setText("Modifica");
            getSupportActionBar().setTitle("Modifica associazione");
            aggiungiConsulente.setVisibility(View.GONE);
            ArrayList<UserInfo> consulente = new ArrayList<>();
            consulente.add(mAssociazioneAttuale.getRisorsa());
            updateConsulentiScelti(consulente);
        }

        getAllList();
    }

    private void setupCommesse(Commessa commessa) {
        int indexCommessa;
        indexCommessa = mCommesseList.indexOf(commessa);
        mCommessaSpinner.setSelection(indexCommessa);
        mCommessaSpinner.setEnabled(false);
    }

    public void setupView(Associazione associazione) {
        mDataInizioView.setText(Functions.getFormattedDate(associazione.getData_inizio()));
        mDataFineView.setText(Functions.getFormattedDate(associazione.getData_fine()));
    }


    /**
     * Effettua la chiesta al servere per ottenere tutti i dati relativi alle associazioni
     * La richiesta viene fatta utilizzando Volley <a>http://developer.android.com/intl/pt-br/training/volley/index.html</a>
     * e viene messa nella requestQueue la risposta viene gestita da un Response.Listener
     */
    public void getAllList() {
        String accessiUrl = getString(R.string.mobile_url);
        accessiUrl += "accessi";

        CustomRequest accessiRequest = new CustomRequest(accessiUrl, null, new AccessiResponse(), new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Errore richiesta Volley", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        });

        String commesseUrl = getString(R.string.mobile_url);
        commesseUrl += "commesse";

        CustomRequest commesseRequest = new CustomRequest(commesseUrl, null, new CommesseResponse(), new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Errore richiesta Volley", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        });

        mRequestQueue.add(accessiRequest);
        mRequestQueue.add(commesseRequest);
    }

    @Override
    public void onItemsSelected(boolean[] selected) {
    }

    /**
     * Implementazione di Response.Listener che gestisce le risposte del server
     * Ottiene un array di Json che verra parsificato con la libreria GSON
     * <a>https://github.com/google/gson</a>
     */
    public class AccessiResponse implements Response.Listener<JSONArray> {

        @Override
        public void onResponse(JSONArray responseArray) {
            try {
                ArrayList<UserInfo> userInfos = new ArrayList<>();
                Log.i("SistemiActivity.class", " " + responseArray.length());
                // Parsing json object response
                // response will be a json object
                for (int i = 0; i < responseArray.length(); i++) {

                    JSONObject response = responseArray.getJSONObject(i);

                    UserInfo user = gson.fromJson(response.toString(), UserInfo.class);

                    userInfos.add(user);
                }
                updateUtentiList(userInfos);
                //mSearchList = userInfos;
            } catch (JSONException e) {

                e.printStackTrace();
                Log.i("LoginResponse", e.getMessage());

                Toast.makeText(getApplicationContext(),
                        "Error: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateUtentiList(ArrayList<UserInfo> list) {
        mUtentiList.clear();
        Collections.sort(list, surnameSortingComparator);
        mUtentiList.addAll(list);
        mUtentiAdapter.notifyDataSetChanged();
    }

    /**
     * Implementazione di Response.Listener che gestisce le risposte del server
     * Ottiene un array di Json che verra parsificato con la libreria GSON
     * <a>https://github.com/google/gson</a>
     */
    public class CommesseResponse implements Response.Listener<JSONArray> {

        Gson gson = new Gson();

        @Override
        public void onResponse(JSONArray responseArray) {
            try {
                ArrayList<Commessa> commesse = new ArrayList<>();

                Log.i("Commesse.class", " " + responseArray.length());

                for (int i = 0; i < responseArray.length(); i++) {

                    JSONObject response = responseArray.getJSONObject(i);

                    Commessa commessa = gson.fromJson(response.toString(), Commessa.class);

                    commesse.add(commessa);
                }
                updateCommesseList(commesse);
                //mOriginalList = commesse;
            } catch (JSONException e) {

                e.printStackTrace();
                Log.i("LoginResponse", e.getMessage());

                Toast.makeText(getApplicationContext(),
                        "Error: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateCommesseList(ArrayList<Commessa> list) {
        mCommesseList.clear();
        Collections.sort(list, commessaComparator);
        mCommesseList.addAll(list);
        mCommesseAdapter.notifyDataSetChanged();
    }

    public void attemptNuovo() {

        Associazione associazione;

        if (isModifica) {
            associazione = mAssociazioneAttuale;
        } else {
            associazione = new Associazione();
        }


        String datainizio = "";
        String datafine = "";

        datainizio = mDataInizioView.getText().toString();
        datafine = mDataFineView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        mDataInizioView.setError(null);
        mDataFineView.setError(null);
        mCommessaError.setError(null);
        mCapoProgettoError.setError(null);
        mConsulenteError.setError(null);

        //******************** DATA INIZIO ***********************
        if (!TextUtils.isEmpty(datainizio)) {
            if (Functions.validateNormalDate(datainizio)) {
                try {
                    datainizio = Functions.fromDateToSql(datainizio);
                    associazione.setData_inizio(datainizio);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            } else {
                mDataInizioView.setError("Campo neccessario");
                focusView = mDataInizioView;
                cancel = true;
            }
        } else {
            mDataInizioView.setError("Campo neccessario");
            focusView = mDataInizioView;
            cancel = true;
        }

        //*************************** DATA FINE ************************
        if (!TextUtils.isEmpty(datafine)) {
            if (Functions.validateNormalDate(datafine)) {
                try {
                    datafine = Functions.fromDateToSql(datafine);
                    associazione.setData_fine(datafine);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            } else {
                mDataFineView.setError("Campo neccessario");
                focusView = mDataFineView;
                cancel = true;
            }
        } else {
            mDataFineView.setError("Campo neccessario");
            focusView = mDataFineView;
            cancel = true;
        }
        //**************************************************************

        if (selectedCommessa != null) {
            associazione.setCommessa(selectedCommessa);
            associazione.setId_commessa(selectedCommessa.getID());
        } else {
            mCommessaError.setError("Campo neccessario");
            focusView = mCommessaError;
            cancel = true;
        }

        if (selectedCapoProgetto != null) {

        } else {
            mCapoProgettoError.setError("Campo obbligatorio");
            focusView = mCapoProgettoError;
            cancel = true;
        }

        if (!cancel) {
            if (selectedConsulenti.size() > 0) {
                for (UserInfo consulente : selectedConsulenti) {
                    associazione.setRisorsa(consulente);
                    associazione.setId_utente(consulente.getID());
                    String associazioneJson = gson.toJson(associazione);


                    if (!isModifica) {
                        mVolleyRequests.addNewElementRequest(associazioneJson, "associazione-nuovo");
                    } else {
                        mVolleyRequests.addNewElementRequest(associazioneJson, "associazione/" + idOldCommessa + "/" + consulente.getID());
                        System.out.println("associazione/" + idOldCommessa + "/" + consulente.getID());
                        System.out.println("NuovaAssociazioneActivity -> " + associazioneJson);

                    }
                }
            } else {
                mConsulenteError.setError("Campo obbligatorio");
                focusView = mConsulenteError;
                cancel = true;
            }

            if (cancel) {
                focusView.requestFocus();
            } else {
                if (selectedCapoProgetto != null) {
                    if (selectedCommessa != null) {
                        selectedCommessa.setId_capo_progetto(selectedCapoProgetto.getID());
                        String commessaJson = gson.toJson(selectedCommessa);
                        mVolleyRequests.addNewElementRequestNoAlert(commessaJson, "commessa/" + selectedCommessa.getID());
                    }
                }
            }
        } else {
            focusView.requestFocus();
        }
    }

    void setupCapoProgetto(UserInfo utente) {
        if (utente != null) {
            int indexCliente;
            indexCliente = mUtentiList.indexOf(utente);
            mCapoProgettoSpinner.setSelection(indexCliente);
            mCapoProgettoSpinner.setEnabled(false);
            selectedCapoProgetto = utente;
        } else {
            mCapoProgettoSpinner.setEnabled(true);
            mCapoProgettoSpinner.setSelection(0);
            selectedCapoProgetto = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllList();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONSULENTE_REQUEST && resultCode == RESULT_OK) {
            selectedConsulenti = data.getParcelableArrayListExtra("selectedItems");
            System.out.println("Numero consulenti scelti -> " + selectedConsulenti.size());
            updateConsulentiScelti(selectedConsulenti);
        }
    }

    public void onClickSelData(TextView dataView) {
        DialogFragment dialogFragment = new DatePickerFragment(dataView);
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    /**
     * Aggiorna la ListView dei consulenti scelti
     *
     * @param list
     */
    void updateConsulentiScelti(ArrayList<UserInfo> list) {
        mConsulentiList.clear();
        mConsulentiList.addAll(list);
        AndroidUtils.getListViewSize(mSelectedConsulentiListView);
        mConsulenteListAdapter.notifyDataSetChanged();
        selectedConsulenti = list;
    }

}
