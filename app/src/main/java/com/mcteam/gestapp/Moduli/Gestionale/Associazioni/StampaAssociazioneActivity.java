package com.mcteam.gestapp.Moduli.Gestionale.Associazioni;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.mcteam.gestapp.Models.Associazione;
import com.mcteam.gestapp.Models.Commessa;
import com.mcteam.gestapp.Models.UserInfo;
import com.mcteam.gestapp.NetworkReq.VolleyRequests;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.Functions;

public class StampaAssociazioneActivity extends AppCompatActivity {


    TextView mNomeCommessaView;
    TextView mCapoProgettoView;
    TextView mConsulenteView;
    TextView mDataInizioView;
    TextView mDataFineView;

    VolleyRequests mMyRequests;
    Associazione mAssociazioneAttuale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stampa_associazione);

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

        mMyRequests = new VolleyRequests(this, this);

        mAssociazioneAttuale = getIntent().getParcelableExtra("associazioneToPrint");

        mNomeCommessaView = (TextView) findViewById(R.id.stampa_associazione_nome_commessa);
        mCapoProgettoView = (TextView) findViewById(R.id.stampa_associazione_capo_progetto);
        mConsulenteView = (TextView) findViewById(R.id.stmapa_associazione_consulente);
        mDataInizioView = (TextView) findViewById(R.id.stampa_associazione_data_inizio);
        mDataFineView = (TextView) findViewById(R.id.stampa_associazione_data_fine);


        BootstrapButton elimina = (BootstrapButton) findViewById(R.id.stampa_associazione_conferma);

        elimina.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    AssociazioniTools.print(mAssociazioneAttuale, getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        BootstrapButton annulla = (BootstrapButton) findViewById(R.id.stampa_associazione_annulla);
        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setUpView(mAssociazioneAttuale);
    }

    private void setUpView(Associazione associazione) {
        String nomeCommessa = "";
        if (associazione.getCommessa() != null) {
            Commessa commessa = associazione.getCommessa();
            nomeCommessa += commessa.getCodice_commessa() + " - ";
            if (commessa != null && commessa.getCliente() != null && commessa.getCliente().getNomeSocietà() != null) {
                nomeCommessa += commessa.getCliente().getNomeSocietà() + " - ";
            }
            nomeCommessa += commessa.getNome_commessa();
        }

        UserInfo capoProgetto = associazione.getCommessa().getCapo_progetto() != null ? associazione.getCommessa().getCapo_progetto() : null;

        mNomeCommessaView.setText(nomeCommessa);
        mCapoProgettoView.setText(capoProgetto != null ? capoProgetto.getCognome() + " " + capoProgetto.getNome() : "");
        mConsulenteView.setText(associazione.getRisorsa() != null ? associazione.getRisorsa().getCognome() + " " + associazione.getRisorsa().getNome() : "");
        mDataInizioView.setText(Functions.getFormattedDate(associazione.getData_inizio()));
        mDataFineView.setText(Functions.getFormattedDate(associazione.getData_fine()));
    }


}
