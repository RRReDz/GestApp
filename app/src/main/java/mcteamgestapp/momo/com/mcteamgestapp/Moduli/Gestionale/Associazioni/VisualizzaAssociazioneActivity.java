package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Associazioni;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Associazione;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Commessa;
import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;
import mcteamgestapp.momo.com.mcteamgestapp.R;
import mcteamgestapp.momo.com.mcteamgestapp.Utils.Functions;
import mcteamgestapp.momo.com.mcteamgestapp.NetworkReq.VolleyRequests;

public class VisualizzaAssociazioneActivity extends AppCompatActivity {

    TextView mNomeCommessaView;
    TextView mCapoProgettoView;
    TextView mConsulenteView;
    TextView mDataInizioView;
    TextView mDataFineView;
    static final int MODIFY_REQUEST = 4433;


    Associazione mAssociazioneAttuale;

    VolleyRequests mMyRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_associazione);


        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable actionBarBack = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                actionBarBack = getDrawable(R.drawable.actionbar_gestionale);
            }
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
        }

        mAssociazioneAttuale = getIntent().getParcelableExtra("associazioneToView");

        mNomeCommessaView = (TextView) findViewById(R.id.visualizza_associazione_nome_commessa);
        mCapoProgettoView = (TextView) findViewById(R.id.visualizza_associazione_capo_progetto);
        mConsulenteView = (TextView) findViewById(R.id.visualizza_associazione_consulente);
        mDataInizioView = (TextView) findViewById(R.id.visualizza_associazione_data_inizio);
        mDataFineView = (TextView) findViewById(R.id.visualizza_associazione_data_fine);

        mMyRequests = new VolleyRequests(this, this);

        BootstrapButton modifica = (BootstrapButton) findViewById(R.id.visualizza_associazione_modifica);
        modifica.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent modificaIntent = new Intent(getApplicationContext(), NuovaAssociazioneActivity.class);
                modificaIntent.putExtra("associazioneToModify", mAssociazioneAttuale);
                modificaIntent.putExtra("isModifica", true);
                startActivityForResult(modificaIntent, MODIFY_REQUEST);
            }
        });

        BootstrapButton stampa = (BootstrapButton) findViewById(R.id.visualizza_associazione_stampa);
        stampa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AssociazioniTools.print(mAssociazioneAttuale, getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        BootstrapButton elimina = (BootstrapButton) findViewById(R.id.visualizza_associazione_elimina);
        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyRequests.deleteElement("associazione/" + mAssociazioneAttuale.getId_commessa() + "/" + mAssociazioneAttuale.getId_utente());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MODIFY_REQUEST && resultCode == RESULT_OK) {
            finish();
        }
    }
}
