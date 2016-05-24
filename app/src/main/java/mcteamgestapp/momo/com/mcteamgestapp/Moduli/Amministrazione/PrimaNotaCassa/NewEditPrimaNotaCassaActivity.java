package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Amministrazione.PrimaNotaCassa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.itextpdf.text.pdf.StringUtils;

import org.apache.poi.util.StringUtil;

import java.sql.Date;
import java.text.ParseException;

import mcteamgestapp.momo.com.mcteamgestapp.Constants;
import mcteamgestapp.momo.com.mcteamgestapp.Models.PrimaNota.NotaCassa;
import mcteamgestapp.momo.com.mcteamgestapp.R;
import mcteamgestapp.momo.com.mcteamgestapp.ToolUtils;
import mcteamgestapp.momo.com.mcteamgestapp.VolleyRequests;

/**
 * Created by Rrossi on 17/05/2016.
 */

public class NewEditPrimaNotaCassaActivity extends AppCompatActivity {

    private Spinner mType;
    private EditText mDataOperazione;
    private EditText mCausaleContabile;
    private EditText mSottoconto;
    private EditText mDescrizioneMovimenti;
    private EditText mProtocollo;
    private EditText mDare;
    private EditText mAvere;
    private Button mButtonAnnulla;
    private Button mButtonCrea;
    private Button mButtonModifica;
    private VolleyRequests mVolleyRequest;
    private NotaCassa notaCassaEdit;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_nota_cassa);

        mType = (Spinner) findViewById(R.id.spinner_type_nota_cassa);
        mDataOperazione = (EditText) findViewById(R.id.et_data_operazione);
        mCausaleContabile = (EditText) findViewById(R.id.et_causale_contabile);
        mSottoconto = (EditText) findViewById(R.id.et_sottoconto);
        mDescrizioneMovimenti = (EditText) findViewById(R.id.et_descrizione_movimenti);
        mProtocollo = (EditText) findViewById(R.id.et_protocollo);
        mDare = (EditText) findViewById(R.id.et_dare);
        mAvere = (EditText) findViewById(R.id.et_avere);
        mButtonAnnulla = (Button) findViewById(R.id.bAnnulla);
        mButtonCrea = (Button) findViewById(R.id.bCrea);
        mButtonModifica = (Button) findViewById(R.id.bModifica);
        mVolleyRequest = new VolleyRequests(this, this);

        notaCassaEdit = getIntent().getParcelableExtra(Constants.NOTA_CASSA);
        System.out.println(notaCassaEdit);
        if (notaCassaEdit != null) {
            mType.setSelection(notaCassaEdit.getCassa());
            mDataOperazione.setText(notaCassaEdit.getDataPagamento());
            if (!notaCassaEdit.getCausaleContabile().equals("null"))
                mCausaleContabile.setText(notaCassaEdit.getCausaleContabile());
            if (!notaCassaEdit.getSottoconto().equals("null"))
                mSottoconto.setText(notaCassaEdit.getSottoconto());
            mDescrizioneMovimenti.setText(notaCassaEdit.getDescrizione());
            mProtocollo.setText(notaCassaEdit.getNumeroProtocollo() + "");
            mDare.setText(notaCassaEdit.getDare() + "");
            mAvere.setText(notaCassaEdit.getAvere() + "");
            mButtonModifica.setVisibility(View.VISIBLE);
        } else {
            mButtonCrea.setVisibility(View.VISIBLE);
        }

    }

    public void onClickModifica(View view) {
        if (checkFields())
            try {
                attemptEdit();
            } catch (ParseException e) {
                e.printStackTrace();
            }
    }

    private void attemptEdit() throws ParseException {
        NotaCassa notaCassa = notaCassaToEncode();
        int ID = notaCassaEdit.getID();
        mVolleyRequest.addNewElementRequest(gson.toJson(notaCassa), "nota-cassa-edit/" + ID);
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
        mVolleyRequest.addNewElementRequest(gson.toJson(notaCassa), "nota-cassa-nuovo");
    }

    public void onClickAnnulla(View view) {
        finish();
    }

    private boolean checkFields() {

        String dataOperazione = mDataOperazione.getText().toString();
        String descrizione = mDescrizioneMovimenti.getText().toString();
        String dare = mDare.getText().toString();
        String avere = mAvere.getText().toString();
        String protocollo = mProtocollo.getText().toString();

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
        try {
            Double.parseDouble(dare);
        } catch (NumberFormatException ex) {
            mDare.setError("Inserire un importo valido");
            mDare.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(avere)) {
            mAvere.setError("Inserire un importo");
            mAvere.requestFocus();
            return false;
        }
        try {
            Double.parseDouble(avere);
        } catch (NumberFormatException ex) {
            mAvere.setError("Inserire un importo valido");
            mAvere.requestFocus();
            return false;
        }

        return true;
    }

    private NotaCassa notaCassaToEncode() throws ParseException{
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
        double dare = Double.parseDouble(mDare.getText().toString());
        double avere = Double.parseDouble(mAvere.getText().toString());

        notaCassa.setCassa(type);
        notaCassa.setDataPagamento(ToolUtils.fromDateToSql(dataOperazione));
        notaCassa.setCausaleContabile(causaleContabile);
        notaCassa.setSottoconto(sottoconto);
        notaCassa.setDescrizione(descrizione);
        notaCassa.setDare(dare);
        notaCassa.setAvere(avere);
        notaCassa.setNumeroProtocollo(protocollo);

        return notaCassa;
    }
}
