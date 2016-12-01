package com.mcteam.gestapp.Moduli.Commerciale.Offerte;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;
import com.itextpdf.text.pdf.parser.Line;
import com.mcteam.gestapp.Callback.CallbackRequest;
import com.mcteam.gestapp.Callback.CallbackSelection;
import com.mcteam.gestapp.Fragments.DatePickerFragment;
import com.mcteam.gestapp.Models.Commerciale.Offerta;
import com.mcteam.gestapp.Models.Commessa;
import com.mcteam.gestapp.Models.Rubrica.Nominativo;
import com.mcteam.gestapp.Moduli.Gestionale.Allegati.AllegatiUtils;
import com.mcteam.gestapp.Moduli.Gestionale.Commesse.NominativoSpinnerAdapter;
import com.mcteam.gestapp.NetworkReq.VolleyRequests;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.Functions;
import com.nononsenseapps.filepicker.FilePickerActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Created by Riccardo Rossi on 15/10/2016.
 */
public class NuovaModifOffertaActivity extends AppCompatActivity {

    private VolleyRequests mVolleyRequests;
    private ArrayList<Nominativo> mNominativiList;
    private EditText mData;
    private static final int FILE_CODE = 992;
    private File mChoosenFile;
    private DatePickerFragment mDateFragment;
    private EditText mCodCommessa;
    private Commessa mCommessa;
    private EditText mCliente;
    private EditText mOggetto;
    private Spinner mRef1;
    private Spinner mRef2;
    private Spinner mRef3;
    private LinearLayout mAllegatoLayout;
    private ImageView mAllegatoLogo;
    private TextView mAllegatoNome;
    private TextView mAllegatoSize;
    private BootstrapButton mAllegato;
    private CheckBox mPresentata;
    private RadioButton mModificaYes;
    private RadioButton mNewVersion;
    private LinearLayout mPresentataLayout;
    private LinearLayout mVuoiModifLayout;
    private boolean mIsNew;
    private Offerta mOffertaToEdit;
    private RadioGroup mNewVersionLayout;
    static Gson gson = new Gson();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuova_offerta);

        mCommessa = getIntent().getParcelableExtra("COMMESSA");
        mIsNew = getIntent().getBooleanExtra("NUOVO", true);

        mCodCommessa = (EditText) findViewById(R.id.dett_off_new_codcomm);
        mCliente = (EditText) findViewById(R.id.dett_off_new_cliente);
        mRef1 = (Spinner) findViewById(R.id.dett_off_new_ref1);
        mRef2 = (Spinner) findViewById(R.id.dett_off_new_ref2);
        mRef3 = (Spinner) findViewById(R.id.dett_off_new_ref3);
        mData = (EditText) findViewById(R.id.dett_off_new_data);
        mOggetto = (EditText) findViewById(R.id.dett_off_new_obj);
        mAllegato = (BootstrapButton) findViewById(R.id.dett_off_new_alleg);
        mPresentata = (CheckBox) findViewById(R.id.dett_off_checkbox_pres);
        mPresentataLayout = (LinearLayout) findViewById(R.id.dett_off_layout_presentata);
        mVuoiModifLayout = (LinearLayout) findViewById(R.id.dett_off_vuoi_modificare);
        Button creaButton = (Button) findViewById(R.id.bCrea);
        Button modificaButton = (Button) findViewById(R.id.bModifica);
        mModificaYes = (RadioButton) findViewById(R.id.dett_off_yes);
        mNewVersion = (RadioButton) findViewById(R.id.dett_off_new_vers);
        mNewVersionLayout = (RadioGroup) findViewById(R.id.dett_off_new_vers_radiogroup);
        mAllegatoLayout = (LinearLayout) findViewById(R.id.dett_off_alleg_layout);
        mAllegatoLogo = (ImageView) findViewById(R.id.dett_off_alleg_logo);
        mAllegatoNome = (TextView) findViewById(R.id.dett_off_alleg_nome);
        mAllegatoSize = (TextView) findViewById(R.id.dett_off_alleg_size);

        /* Se l'activity è stata chiamata con lo scopo di modificare l'offerta e non di crearla nuova */
        if (!mIsNew) {
            /* Recupero l'offerta da modificare */
            mOffertaToEdit = getIntent().getParcelableExtra("OFFERTA_TO_EDIT");
            /* Carico la data */
            mData.setText(Functions.getFormattedDate(mOffertaToEdit.getDataOfferta()));
            /* Mostro il campo 'presentata' e ne setto il valore */
            mPresentata.setChecked(mOffertaToEdit.getAccettata() == 1);
            mPresentataLayout.setVisibility(View.VISIBLE);
            /* Visualizzazione richiesta modifica dell'offerta */
            mVuoiModifLayout.setVisibility(View.VISIBLE);
            RadioButton radioButtonYes = (RadioButton) findViewById(R.id.dett_off_yes);
            radioButtonYes.setChecked(true);
            /* Visualizzazione richiesta nuova versione o meno */
            mNewVersionLayout.setVisibility(View.VISIBLE);
            /* Bottoni */
            modificaButton.setVisibility(View.VISIBLE);
        } else {
            creaButton.setVisibility(View.VISIBLE);
        }

        //Codice commessa
        mCodCommessa.setText(mCommessa.getCodice_commessa());
        mCodCommessa.setEnabled(false);

        //Cliente
        mCliente.setText(mCommessa.getCliente().getNomeSocietà());
        mCliente.setEnabled(false);

        //Lista nominativi
        mNominativiList = new ArrayList<>();
        final NominativoSpinnerAdapter adapter = new NominativoSpinnerAdapter(this, R.layout.nominativo_societa_spinner_row, mNominativiList);

        //Riferenti 1, 2 e 3
        mRef1.setAdapter(adapter);
        mRef2.setAdapter(adapter);
        mRef3.setAdapter(adapter);

        //Inizializzazione mData
        mDateFragment = new DatePickerFragment(mData);
        mData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSelData();
            }
        });

        //Oggetto
        mOggetto.setText(mCommessa.getNome_commessa());
        mOggetto.setEnabled(false);

        /* File chooser */
        mAllegato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //File chooser
                Intent i = new Intent(getApplicationContext(), FilePickerActivity.class);
                // This works if you defined the intent filter
                // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

                // Set these depending on your use case. These are the defaults.
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
                i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
                // Configure initial directory by specifying a String.
                // You could specify a String like "/storage/emulated/0/", but that can
                // dangerous. Always use Android's API calls to get paths to the SD-card or
                // internal memory.
                i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

                startActivityForResult(i, FILE_CODE);
            }
        });

        //Riempita nominativi list
        mVolleyRequests = new VolleyRequests(this, this);
        mVolleyRequests.getNominativiList(
                mNominativiList,
                adapter,
                new CallbackSelection<Nominativo>() {
                    @Override
                    public void onListLoaded(ArrayList<Nominativo> list) {
                        /* Quando si carica la lista, seleziono i dati relativi alla commessa */
                        mRef1.setSelection(adapter.getPositionById(mCommessa.getOff1()));
                        mRef2.setSelection(adapter.getPositionById(mCommessa.getOff2()));
                        mRef3.setSelection(adapter.getPositionById(mCommessa.getOff3()));
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            mChoosenFile = new File(uri.getPath());
            if (mChoosenFile != null) {
                mAllegatoNome.setText(mChoosenFile.getName());
                Bitmap logo = AllegatiUtils.getAllegatoLogo(getResources(), mChoosenFile.getName());
                mAllegatoLogo.setImageBitmap(logo);
                mAllegatoSize.setText(mChoosenFile.length() + "Bytes");
                mAllegatoSize.setVisibility(View.VISIBLE);
            }
            Log.d("FilePicker", mChoosenFile.toString());
            // Do anything with file
        }
    }

    //Apertura date picker dopo click sul campo

    private void onClickSelData() {
        mDateFragment.withCustomDate(mData.getText().toString());
        mDateFragment.show(getFragmentManager(), "datePicker");
    }

    public void onClickAnnulla(View view) {
        finish();
    }

    public void onClickCrea(View view) throws IOException {
        boolean cancel = false;
        View focusView = null;

        /* Controllo selezione spinner */
        if (mRef1.getSelectedItemPosition() == 0 ||
                mRef2.getSelectedItemPosition() == 0 ||
                mRef3.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Uno dei referenti non è stato selezionato: impossibile continuare", Toast.LENGTH_LONG).show();
            cancel = true;
        }
        /* Controllo se la mData è stata selezionata */
        else if (!mDateFragment.isDataSelected()) {
            Toast.makeText(getApplicationContext(), "La data non è stata selezionata: impossibile continuare", Toast.LENGTH_LONG).show();
            focusView = mData;
            cancel = true;
        }
        /* Controllo allegato scelto */
        /*else if (mChoosenFile == null) {
            Toast.makeText(getApplicationContext(), "File non selezionato: scegliere un file", Toast.LENGTH_LONG).show();
            focusView = mAllegato;
            cancel = true;
        }*/

        if (!cancel) {
            Offerta offertaToEncode = null;
            try {
                offertaToEncode = offertaToEncode();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
                //TODO: Mostra errore, non è stato possibile decodificare i dati in JSON.
            }
            if (offertaToEncode != null)
                mVolleyRequests.addNewElementRequest(gson.toJson(offertaToEncode),
                        "offerta-nuovo/" + mCommessa.getID(),
                        null
                );
        } else {
            if (focusView != null)
                focusView.requestFocus();
        }
    }

    public void onClickModifica(View view) {

        boolean cancel = false;
        View focusView = null;

        /* Controllo selezione spinner */
        if (mRef1.getSelectedItemPosition() == 0 ||
                mRef2.getSelectedItemPosition() == 0 ||
                mRef3.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Uno dei referenti non è stato selezionato: impossibile continuare", Toast.LENGTH_LONG).show();
            cancel = true;
        } else if (!mDateFragment.isDataSelected()) {
            Toast.makeText(getApplicationContext(), "La data non è stata selezionata: impossibile continuare", Toast.LENGTH_LONG).show();
            focusView = mData;
            cancel = true;
        } else if (mModificaYes.isChecked() && mChoosenFile == null) {
            Toast.makeText(getApplicationContext(), "Se vuoi modificare l'offerta, devi selezionare un nuovo allegato", Toast.LENGTH_LONG).show();
            focusView = mAllegato;
            cancel = true;
        }

        if (!cancel) {
            Offerta offertaToEncode = null;
            try {
                offertaToEncode = offertaToEncode();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
                //TODO: Mostra errore, non è stato possibile decodificare i dati in JSON.
            }
            if (offertaToEncode != null)
                mVolleyRequests.addNewElementRequest(gson.toJson(offertaToEncode),
                        "offerta-edit",
                        null
                );
        } else {
            if (focusView != null)
                focusView.requestFocus();
        }
    }

    private Offerta offertaToEncode() throws ParseException, java.text.ParseException {

        Nominativo ref1 = (Nominativo) mRef1.getSelectedItem();
        Nominativo ref2 = (Nominativo) mRef2.getSelectedItem();
        Nominativo ref3 = (Nominativo) mRef3.getSelectedItem();
        int versione = mOffertaToEdit != null ? mOffertaToEdit.getVersione() : 0;
        int presentata = mPresentata.isChecked() ? 1 : 0;
        int modificaOfferta = mModificaYes.isChecked() ? 1 : 0;
        int nuovaVersione = mNewVersion.isChecked() ? 1 : 0;
        String dataOfferta = mData.getText().toString();
        String nomeAllegato = mChoosenFile != null ? mChoosenFile.getName() : "";

        return new Offerta()
                .setIdCommessa(mCommessa.getID())
                .setVersione(versione)
                .setAccettata(presentata)
                .setOff1Comm(ref1.getID())
                .setOff2Comm(ref2.getID())
                .setOff3Comm(ref3.getID())
                .setModificaOfferta(modificaOfferta)
                .setNuovaVersione(nuovaVersione)
                .setDataOfferta(Functions.fromDateToSql(dataOfferta))
                .setAllegato(nomeAllegato);
    }

    /**
     * Metodo richiamato dal click di una delle due radiobutton
     *
     * @param view
     */
    public void onRadioButtonClick(View view) {
        /* Controlla quale radiobutton è stata selezionata */
        switch (view.getId()) {
            case R.id.dett_off_yes:
                mAllegatoLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.dett_off_no:
                mAllegatoLayout.setVisibility(View.GONE);
                break;
        }
    }
}
