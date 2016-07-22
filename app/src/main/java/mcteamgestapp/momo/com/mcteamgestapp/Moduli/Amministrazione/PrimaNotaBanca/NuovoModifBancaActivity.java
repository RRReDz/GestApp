package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Amministrazione.PrimaNotaBanca;

import android.annotation.TargetApi;
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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import mcteamgestapp.momo.com.mcteamgestapp.Constants;
import mcteamgestapp.momo.com.mcteamgestapp.DatePickerFragment;
import mcteamgestapp.momo.com.mcteamgestapp.Models.PrimaNota.NotaBanca;
import mcteamgestapp.momo.com.mcteamgestapp.R;
import mcteamgestapp.momo.com.mcteamgestapp.ToolUtils;
import mcteamgestapp.momo.com.mcteamgestapp.VolleyRequests;

/**
 * Created by Rrossi on 17/05/2016.
 */

public class NuovoModifBancaActivity extends AppCompatActivity {

    private Spinner mGruppo;
    private TextView mDataOperazione;
    private TextView mDataValuta;
    private EditText mDescrizioneMovimenti;
    private EditText mProtocollo;
    private EditText mDare;
    private EditText mAvere;
    private Button mButtonCrea;
    private Button mButtonModifica;
    private VolleyRequests mVolleyRequest;
    private NotaBanca notaBancaEdit;
    private Gson gson = new Gson();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_nota_banca);

        //Set colore action bar
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable actionBarBack = getDrawable(R.drawable.actionbar_amministrazione);
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
            getSupportActionBar().setIcon(R.drawable.ic_accessibility_white_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mGruppo = (Spinner) findViewById(R.id.spinner_gruppo_banca);
        mDataOperazione = (TextView) findViewById(R.id.banca_dataop);
        mDataValuta = (TextView) findViewById(R.id.banca_dataval);
        mDescrizioneMovimenti = (EditText) findViewById(R.id.banca_descr);
        mProtocollo = (EditText) findViewById(R.id.banca_prot);
        mDare = (EditText) findViewById(R.id.banca_dare);
        mAvere = (EditText) findViewById(R.id.banca_avere);
        mButtonCrea = (Button) findViewById(R.id.banca_b_crea);
        mButtonModifica = (Button) findViewById(R.id.banca_b_modifica);
        mVolleyRequest = new VolleyRequests(this, this);

        notaBancaEdit = getIntent().getParcelableExtra(Constants.NOTA_BANCA);
        System.out.println(notaBancaEdit);
        if (notaBancaEdit != null) {
            DecimalFormat df = new DecimalFormat();
            df.setMinimumFractionDigits(2);

            //Non mostra la tastiera in apertura
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            setTitle("Modifica nota banca");
            mGruppo.setSelection(notaBancaEdit.getGruppo());
            mDataOperazione.setText(notaBancaEdit.getDataPagamento());
            mDataValuta.setText(notaBancaEdit.getDataPagamento());
            mDescrizioneMovimenti.setText(notaBancaEdit.getDescrizione());
            if (notaBancaEdit.getNumeroProtocollo() != 0)
                mProtocollo.setText(notaBancaEdit.getNumeroProtocollo() + "");
            mDare.setText(notaBancaEdit.getDare() + "");
            mAvere.setText(notaBancaEdit.getAvere() + "");
            mButtonModifica.setVisibility(View.VISIBLE);
        } else {
            mButtonCrea.setVisibility(View.VISIBLE);
            //Set data di oggi se nuova nota
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cal = Calendar.getInstance();
            mDataOperazione.setText(dateFormat.format(cal.getTime()));
            mDataValuta.setText(dateFormat.format(cal.getTime()));
        }

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

    public void onClickSelData(View view) {
        DatePickerFragment dpf;
        if (view.getId() == R.id.banca_dataop_but)
            dpf = new DatePickerFragment(mDataOperazione);
        else
            dpf = new DatePickerFragment(mDataValuta);
        dpf.show(getFragmentManager(), "datePicker");
    }

    private void attemptEdit() throws ParseException {
        NotaBanca notaBanca = notaBancaToEncode();
        int ID = notaBancaEdit.getID();
        mVolleyRequest.addNewElementRequest(gson.toJson(notaBanca), "nota-banca-edit/" + ID);
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
        NotaBanca notaBanca = notaBancaToEncode();
        mVolleyRequest.addNewElementRequest(gson.toJson(notaBanca), "nota-banca-nuovo");
    }

    public void onClickAnnulla(View view) {
        finish();
    }

    private boolean checkFields() {

        String dataOperazione = mDataOperazione.getText().toString();
        String dataValuta = mDataValuta.getText().toString();
        String descrizione = mDescrizioneMovimenti.getText().toString();
        String dare = mDare.getText().toString();
        String avere = mAvere.getText().toString();

        if (TextUtils.isEmpty(dataOperazione) || !ToolUtils.validateDate(dataOperazione)) {
            mDataOperazione.setError("Data mancante o errata: rispettare il formato gg-mm-aaaa");
            mDataOperazione.requestFocus();
            return false;
        } else {
            try {
                ToolUtils.fromDateToSql(dataOperazione);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (TextUtils.isEmpty(dataValuta) || !ToolUtils.validateDate(dataOperazione) || !ToolUtils.strDateGreaterThan(dataOperazione, dataValuta)) {
            mDataValuta.setError("Data errata: rispettare il formato gg-mm-aaaa e DATA VALUTA maggiore di DATA OPERAZIONE");
            mDataValuta.requestFocus();
            return false;
        } else {
            try {
                ToolUtils.fromDateToSql(dataValuta);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (TextUtils.isEmpty(descrizione)) {
            mDescrizioneMovimenti.setError("Inserire una descrizione");
            mDescrizioneMovimenti.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(dare)) {
            mDare.setError("Inserire un importo");
            mDare.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(avere)) {
            mAvere.setError("Inserire un importo");
            mAvere.requestFocus();
            return false;
        }

        return true;
    }

    private NotaBanca notaBancaToEncode() throws ParseException {

        NotaBanca notaBanca = new NotaBanca();

        int gruppo = mGruppo.getSelectedItemPosition();
        String dataOperazione = mDataOperazione.getText().toString();
        String dataValuta = mDataValuta.getText().toString();
        String descrizione = mDescrizioneMovimenti.getText().toString();
        Integer protocollo;
        try {
            protocollo = Integer.parseInt(mProtocollo.getText().toString()); //protocollo puo essere lasciato vuoto
        } catch (NumberFormatException ex) {
            protocollo = 0; //Se vuoto viene settato a 0 per convenzione
        }

        float dare = Float.parseFloat((mDare.getText().toString()));
        float avere = Float.parseFloat((mAvere.getText().toString()));

        notaBanca.setGruppo(gruppo);
        notaBanca.setDataPagamento(ToolUtils.fromDateToSql(dataOperazione));
        notaBanca.setDataValuta(ToolUtils.fromDateToSql(dataValuta));
        notaBanca.setDescrizione(descrizione);
        notaBanca.setNumeroProtocollo(protocollo);
        notaBanca.setDareDb(ToolUtils.format(dare));
        notaBanca.setAvereDb(ToolUtils.format(avere));

        return notaBanca;
    }
}
