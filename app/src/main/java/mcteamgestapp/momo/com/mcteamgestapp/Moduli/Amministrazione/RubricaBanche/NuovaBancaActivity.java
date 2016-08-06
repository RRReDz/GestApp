package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Amministrazione.RubricaBanche;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Banca;
import mcteamgestapp.momo.com.mcteamgestapp.R;
import mcteamgestapp.momo.com.mcteamgestapp.NetworkReq.VolleyRequests;

public class NuovaBancaActivity extends AppCompatActivity {

    EditText mNominativoView;
    EditText mIbanView;
    EditText mIndirizzoView;
    EditText mReferenteView;
    Banca mBancaAttuale;
    Button mCreaButton;
    Button mAnnulla;
    boolean toModify = false;
    Gson gson;
    VolleyRequests mVolleyRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuova_banca);

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable actionBarBack = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                actionBarBack = getDrawable(R.drawable.actionbar_amministrazione);
            } else {
                actionBarBack = getResources().getDrawable(R.drawable.actionbar_amministrazione);
            }
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
        }

        mBancaAttuale = getIntent().getParcelableExtra("bancaToModify");

        gson = new Gson();
        mVolleyRequest = new VolleyRequests(this, this);

        mNominativoView = (EditText) findViewById(R.id.nuovo_banca_nominativo);
        mIbanView = (EditText) findViewById(R.id.nuovo_banca_iban);
        mIndirizzoView = (EditText) findViewById(R.id.nuovo_banca_indirizzo);
        mReferenteView = (EditText) findViewById(R.id.nuovo_banca_referente);

        mCreaButton = (Button) findViewById(R.id.nuova_banca_crea_nuovo);
        mAnnulla = (Button) findViewById(R.id.nuova_banca_annulla);


        mCreaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptCreation();
            }
        });


        if (mBancaAttuale != null && mBancaAttuale.isValid()) {
            toModify = true;
            setupView(mBancaAttuale);
        }

        if (toModify) {
            getSupportActionBar().setTitle("Modifica Banca");
            mCreaButton.setText("Modifica");
        }

    }

    void setupView(Banca banca) {

        mNominativoView.setText(banca.getNome());
        mIbanView.setText(banca.getIban());
        mIndirizzoView.setText(banca.getIndirizzo());
        mReferenteView.setText(banca.getReferente());

    }


    public void attemptCreation() {
        String nome = mNominativoView.getText().toString();
        String indirizzo = mIndirizzoView.getText().toString();
        String iban = mIbanView.getText().toString();
        String referente = mReferenteView.getText().toString();


        boolean cancel = false;
        View focusView = null;

        Banca banca = new Banca();

        if (!TextUtils.isEmpty(nome)) {
            banca.setNome(nome);
        } else {
            mNominativoView.setError("Campo neccessario");
            focusView = mNominativoView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(indirizzo)) {
            banca.setIndirizzo(indirizzo);
        } else {
            mIndirizzoView.setError("Campo neccessario");
            focusView = mIndirizzoView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(iban)) {
            banca.setIban(iban);
        } else {
            mIbanView.setError("Campo neccessario");
            focusView = mIbanView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(referente)) {
            banca.setReferente(referente);
        } else {
            mReferenteView.setError("Campo neccessario");
            focusView = mReferenteView;
            cancel = true;
        }

        if (!cancel) {
            String json = gson.toJson(banca);

            if (!toModify) {
                mVolleyRequest.addNewElementRequest(json, "banca-nuovo");
            } else {
                mVolleyRequest.addNewElementRequest(json, "banca/" + mBancaAttuale.getId_banca());
            }

        } else {
            focusView.requestFocus();
        }
    }


}
