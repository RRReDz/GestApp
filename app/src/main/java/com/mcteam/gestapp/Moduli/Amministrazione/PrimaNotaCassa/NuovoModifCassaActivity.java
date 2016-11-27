package com.mcteam.gestapp.Moduli.Amministrazione.PrimaNotaCassa;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.ParseException;

import com.mcteam.gestapp.Utils.Constants;
import com.mcteam.gestapp.Fragments.DatePickerFragment;
import com.mcteam.gestapp.Models.PrimaNota.NotaCassa;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.Functions;
import com.mcteam.gestapp.NetworkReq.VolleyRequests;

/**
 * @author Created by Riccardo Rossi on 17/05/2016.
 */

public class NuovoModifCassaActivity extends AppCompatActivity {

    private Spinner mType;
    private TextView mDataOperazione;
    private EditText mCausaleContabile;
    private EditText mSottoconto;
    private EditText mDescrizioneMovimenti;
    private EditText mProtocollo;
    private EditText mDare;
    private EditText mAvere;
    private Button mButtonCrea;
    private Button mButtonModifica;
    private VolleyRequests mVolleyRequest;
    private NotaCassa notaCassaEdit;
    private Gson gson = new Gson();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_nota_cassa);

        //Permette landscape e portrait solo se è un tablet
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        //Set colore action bar
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable actionBarBack = getDrawable(R.drawable.actionbar_amministrazione);
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
            getSupportActionBar().setIcon(R.drawable.ic_accessibility_white_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mType = (Spinner) findViewById(R.id.spinner_type_nota_cassa);
        mDataOperazione = (TextView) findViewById(R.id.et_data_operazione);
        mCausaleContabile = (EditText) findViewById(R.id.et_causale_contabile);
        mSottoconto = (EditText) findViewById(R.id.et_sottoconto);
        mDescrizioneMovimenti = (EditText) findViewById(R.id.et_descrizione_movimenti);
        mProtocollo = (EditText) findViewById(R.id.et_protocollo);
        mDare = (EditText) findViewById(R.id.et_dare);
        mAvere = (EditText) findViewById(R.id.et_avere);
        mButtonCrea = (Button) findViewById(R.id.bCrea);
        mButtonModifica = (Button) findViewById(R.id.bModifica);
        mVolleyRequest = new VolleyRequests(this, this);

        notaCassaEdit = getIntent().getParcelableExtra(Constants.NOTA_CASSA);
        System.out.println(notaCassaEdit);
        if (notaCassaEdit != null) {
            DecimalFormat df = new DecimalFormat();
            df.setMinimumFractionDigits(2);

            //Non mostra la tastiera in apertura
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            setTitle("Modifica nota cassa");
            mType.setSelection(notaCassaEdit.getCassa());
            mDataOperazione.setText(notaCassaEdit.getDataPagamento());
            if (!notaCassaEdit.getCausaleContabile().equals("null"))
                mCausaleContabile.setText(notaCassaEdit.getCausaleContabile());
            if (!notaCassaEdit.getSottoconto().equals("null"))
                mSottoconto.setText(notaCassaEdit.getSottoconto());
            mDescrizioneMovimenti.setText(notaCassaEdit.getDescrizione());
            if (notaCassaEdit.getNumeroProtocollo() != 0)
                mProtocollo.setText(notaCassaEdit.getNumeroProtocollo() + "");
            mDare.setText(notaCassaEdit.getDare() + "");
            mAvere.setText(notaCassaEdit.getAvere() + "");
            mButtonModifica.setVisibility(View.VISIBLE);
        } else {
            mButtonCrea.setVisibility(View.VISIBLE);
            //Set data del mese selezionato in caso di nota nuova
            DecimalFormat mFormat = new DecimalFormat("00");

            int month = getIntent().getIntExtra("MONTH", 1);
            String year = getIntent().getStringExtra("YEAR");
            mDataOperazione.setText("01-" + mFormat.format(month + 1) + "-" + year);
        }

        mDataOperazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSelData();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return true;
    }

    public void onClickModifica(View view) {
        if (checkFields())
            try {
                attemptEdit();
            } catch (ParseException e) {
                e.printStackTrace();
            }
    }

    public void onClickSelData() {
        DialogFragment dialogFragment = new DatePickerFragment(mDataOperazione);
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    private void attemptEdit() throws ParseException {
        NotaCassa notaCassa = notaCassaToEncode();
        int ID = notaCassaEdit.getID();
        mVolleyRequest.addNewElementRequest(gson.toJson(notaCassa), "nota-cassa-edit/" + ID, null);
    }

    public void onClickCrea(View view) {
        if (checkFields())
            try {
                attemptCreate();
            } catch (ParseException e) {
                e.printStackTrace();
            }
    }

    private void attemptCreate() throws ParseException {
        NotaCassa notaCassa = notaCassaToEncode();
        mVolleyRequest.addNewElementRequest(gson.toJson(notaCassa), "nota-cassa-nuovo", null);
    }

    public void onClickAnnulla(View view) {
        finish();
    }

    private boolean checkFields() {

        String dataOperazione = mDataOperazione.getText().toString();
        String descrizione = mDescrizioneMovimenti.getText().toString();
        String dare = mDare.getText().toString();
        String avere = mAvere.getText().toString();

        if (TextUtils.isEmpty(dataOperazione) || !Functions.validateDate(dataOperazione)) {
            mDataOperazione.setError("Data mancante o errata: rispettare il formato gg-mm-aaaa");
            mDataOperazione.requestFocus();
            return false;
        } else {
            try {
                Functions.fromDateToSql(dataOperazione);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (TextUtils.isEmpty(descrizione)) {
            mDescrizioneMovimenti.setError("Inserire una descrizione");
            mDescrizioneMovimenti.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(dare) && TextUtils.isEmpty(avere)) {
            mDare.setError("Inserire almeno un importo tra dare e avere");
            mDare.requestFocus();
            return false;
        }

        return true;
    }

    private NotaCassa notaCassaToEncode() throws ParseException {

        NotaCassa notaCassa = new NotaCassa();

        int type = mType.getSelectedItemPosition();
        String dataOperazione = mDataOperazione.getText().toString();
        String causaleContabile = mCausaleContabile.getText().toString();
        String sottoconto = mSottoconto.getText().toString();
        String descrizione = mDescrizioneMovimenti.getText().toString();
        Integer protocollo;
        try {
            protocollo = Integer.parseInt(mProtocollo.getText().toString()); //protocollo puo essere lasciato vuoto
        } catch (NumberFormatException ex) {
            protocollo = 0; //Se vuoto viene settato a 0 per convenzione
        }

        float dare;
        try {
            dare = Float.parseFloat((mDare.getText().toString()));
        } catch (NumberFormatException exception) {
            dare = 0;
        }

        float avere;
        try {
            avere = Float.parseFloat((mAvere.getText().toString()));
        } catch (NumberFormatException exception) {
            avere = 0;
        }

        notaCassa.setCassa(type);
        notaCassa.setDataPagamento(Functions.fromDateToSql(dataOperazione));
        notaCassa.setCausaleContabile(causaleContabile);
        notaCassa.setSottoconto(sottoconto);
        notaCassa.setDescrizione(descrizione);
        notaCassa.setDareDb(Functions.format(dare));
        notaCassa.setAvereDb(Functions.format(avere));
        notaCassa.setNumeroProtocollo(protocollo);

        return notaCassa;
    }


}
