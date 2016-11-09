package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Commerciale.Offerte;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.nononsenseapps.filepicker.FilePickerActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import mcteamgestapp.momo.com.mcteamgestapp.Fragments.DatePickerFragment;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Commessa;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Nominativo;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Allegati.AllegatiUtils;
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
    private static final int FILE_CODE = 992;
    private File mChoosenFile;
    ImageView mAllegatoLogo;
    TextView mAllegatoNome;
    TextView mAllegatoSize;

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
        Button creaButton = (Button) findViewById(R.id.bCrea);
        mAllegatoLogo = (ImageView) findViewById(R.id.dett_off_alleg_logo);
        mAllegatoNome = (TextView) findViewById(R.id.dett_off_alleg_nome);
        mAllegatoSize = (TextView) findViewById(R.id.dett_off_alleg_size);


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

        allegato.setOnClickListener(new View.OnClickListener() {
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
        mVolleyRequests.getNominativiList(mNominativiList, adapter);

        creaButton.setVisibility(View.VISIBLE);
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
            }
            Log.d("FilePicker", mChoosenFile.toString());
            // Do anything with file
        }
    }

    //Apertura date picker dopo click sul campo

    private void onClickSelData() {
        DatePickerFragment dateFragment = new DatePickerFragment(data);
        dateFragment.show(getFragmentManager(), "datePicker");
    }

    public void onClickAnnulla(View view) {
        finish();
    }

    public void onClickCrea(View view) throws IOException {

        String nomeAllegatoSelected = mAllegatoNome.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (mChoosenFile == null) {
            Toast.makeText(getApplicationContext(), "File non selezionato: scegliere un file", Toast.LENGTH_LONG).show();
            cancel = true;
        }

        if (!cancel) {
            try {
                mVolleyRequests.uploadFile(mChoosenFile, nomeAllegatoSelected);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            focusView.requestFocus();
        }

    }
}
