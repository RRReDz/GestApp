package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Associazioni;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Associazione;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Commessa;
import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;
import mcteamgestapp.momo.com.mcteamgestapp.R;
import mcteamgestapp.momo.com.mcteamgestapp.ToolUtils;
import mcteamgestapp.momo.com.mcteamgestapp.VolleyRequests;

public class EliminaAssociazioneActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_elimina_associazione);


        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable actionBarBack = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                actionBarBack = getDrawable(R.drawable.actionbar_gestionale);
            }
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
        }

        mMyRequests = new VolleyRequests(this, this);

        mAssociazioneAttuale = getIntent().getParcelableExtra("associazioneToDelete");

        mNomeCommessaView = (TextView) findViewById(R.id.elimina_associazione_nome_commessa);
        mCapoProgettoView = (TextView) findViewById(R.id.elimina_associazione_capo_progetto);
        mConsulenteView = (TextView) findViewById(R.id.elimina_associazione_consulente);
        mDataInizioView = (TextView) findViewById(R.id.elimina_associazione_data_inizio);
        mDataFineView = (TextView) findViewById(R.id.elimina_associazione_data_fine);


        BootstrapButton elimina = (BootstrapButton) findViewById(R.id.elimina_associazione_conferma_button);
        elimina.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mMyRequests.deleteElement("associazione/" + mAssociazioneAttuale.getId_commessa() + "/" + mAssociazioneAttuale.getId_utente());
            }
        });

        BootstrapButton annulla = (BootstrapButton) findViewById(R.id.elimina_associazione_annulla);
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
        mDataInizioView.setText(ToolUtils.getFormattedDate(associazione.getData_inizio()));
        mDataFineView.setText(ToolUtils.getFormattedDate(associazione.getData_fine()));
    }


}
