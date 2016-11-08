package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Commerciale.Offerte;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;

import ir.sohreco.androidfilechooser.ExternalStorageNotAvailableException;
import ir.sohreco.androidfilechooser.FileChooserDialog;
import mcteamgestapp.momo.com.mcteamgestapp.Fragments.DatePickerFragment;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Commessa;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Nominativo;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Commesse.NominativoSpinnerAdapter;
import mcteamgestapp.momo.com.mcteamgestapp.NetworkReq.VolleyRequests;
import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * @author Created by Riccardo Rossi on 15/10/2016.
 */
public class NuovaOffertaActivity extends AppCompatActivity {

    private VolleyRequests mVolleyRequests;
    private ArrayList<Nominativo> mNominativiList;
    private EditText data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuova_offerta);

        Commessa commessa = getIntent().getParcelableExtra("COMMESSA");

        EditText codCommessa = (EditText) findViewById(R.id.dett_off_new_codcomm);
        EditText cliente = (EditText) findViewById(R.id.dett_off_new_cliente);
        Spinner ref1 = (Spinner) findViewById(R.id.dett_off_new_ref1);
        Spinner ref2 = (Spinner) findViewById(R.id.dett_off_new_ref2);
        Spinner ref3 = (Spinner) findViewById(R.id.dett_off_new_ref3);
        data = (EditText) findViewById(R.id.dett_off_new_data);
        EditText oggetto = (EditText) findViewById(R.id.dett_off_new_obj);
        BootstrapButton allegato = (BootstrapButton) findViewById(R.id.dett_off_new_alleg);

        //Codice commessa
        codCommessa.setText(commessa.getCodice_commessa());
        codCommessa.setEnabled(false);

        //Cliente
        cliente.setText(commessa.getCliente().getNomeSocietà());
        cliente.setEnabled(false);

        //Lista nominativi
        mNominativiList = new ArrayList<>();
        NominativoSpinnerAdapter adapter = new NominativoSpinnerAdapter(this, R.layout.nominativo_societa_spinner_row, mNominativiList);

        //Riferenti 1, 2 e 3
        ref1.setAdapter(adapter);
        ref2.setAdapter(adapter);
        ref3.setAdapter(adapter);

        //Data
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSelData();
            }
        });

        //Oggetto
        oggetto.setText(commessa.getNome_commessa());
        oggetto.setEnabled(false);

        //File chooser
        final FileChooserDialog.Builder builder = new FileChooserDialog.Builder(FileChooserDialog.ChooserType.FILE_CHOOSER, new FileChooserDialog.ChooserListener() {
            @Override
            public void onSelect(String path) {
                //Prova commit conflitto
            }
        });

        allegato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    builder.build().show(getSupportFragmentManager(), null);
                } catch (ExternalStorageNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        //Riempita nominativi list
        mVolleyRequests = new VolleyRequests(this, this);
        mVolleyRequests.getNominativiList(mNominativiList, adapter);

    }

    //Apertura date picker dopo click sul campo
    private void onClickSelData() {
        DatePickerFragment dateFragment = new DatePickerFragment(data);
        dateFragment.show(getFragmentManager(), "datePicker");
    }

}
