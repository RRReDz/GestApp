package com.mcteam.gestapp.Moduli.Amministrazione.PrimaNotaBanca;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcteam.gestapp.Utils.Constants;
import com.mcteam.gestapp.Models.PrimaNota.NotaBanca;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.Functions;
import com.mcteam.gestapp.NetworkReq.VolleyRequests;

/**
 * @author Created by Riccardo Rossi on 19/05/2016.
 */
public class VisualElimBancaActivity extends AppCompatActivity {

    private NotaBanca notaBanca;
    private VolleyRequests volleyRequest;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_nota_banca);

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

        TextView gruppo = (TextView) findViewById(R.id.banca_show_gruppo);
        TextView dataOperazione = (TextView) findViewById(R.id.banca_show_dataop);
        TextView dataValuta = (TextView) findViewById(R.id.banca_show_dataval);
        TextView descrizione = (TextView) findViewById(R.id.banca_show_descr);
        TextView protocollo = (TextView) findViewById(R.id.banca_show_prot);
        TextView dare = (TextView) findViewById(R.id.banca_show_dare);
        TextView avere = (TextView) findViewById(R.id.banca_show_avere);
        TextView totale = (TextView) findViewById(R.id.banca_show_tot);

        LinearLayout layoutVisual = (LinearLayout) findViewById(R.id.banca_layout_visual);
        LinearLayout layoutElim = (LinearLayout) findViewById(R.id.banca_layout_elim);

        boolean visualizza = getIntent().getBooleanExtra(Constants.VISUAL_ELIMINA, true);
        notaBanca = getIntent().getParcelableExtra(Constants.NOTA_BANCA);

        String[] bancaArray = getResources().getStringArray(R.array.gruppo);

        if (notaBanca.getGruppo() == 0)
            gruppo.setText("");
        else
            gruppo.setText(bancaArray[notaBanca.getGruppo()]);

        dataOperazione.setText(notaBanca.getDataPagamento());
        dataValuta.setText(notaBanca.getDataValuta());
        descrizione.setText(notaBanca.getDescrizione());
        if (notaBanca.getNumeroProtocollo() != 0)
            protocollo.setText(notaBanca.getNumeroProtocollo() + "");
        dare.setText(Functions.format(notaBanca.getDare()) + " €");
        avere.setText(Functions.format(notaBanca.getAvere()) + " €");
        totale.setText(Functions.format(notaBanca.getTotale()) + " €");

        if (visualizza)
            layoutVisual.setVisibility(View.VISIBLE);
        else {
            setTitle("Elimina nota banca");
            layoutElim.setVisibility(View.VISIBLE);
        }

        volleyRequest = new VolleyRequests(this, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return true;
    }

    public void onClickEdit(View view) {
        Intent modificaIntent = new Intent(getApplicationContext(), NuovoModifBancaActivity.class);
        modificaIntent.putExtra(Constants.NOTA_BANCA, notaBanca);
        //modificaIntent.putExtra("actualUser", mUser);
        startActivityForResult(modificaIntent, Constants.NOTA_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.NOTA_EDIT)
            if (resultCode == Activity.RESULT_OK)
                finish();
    }

    public void onClickDelete(View view) {
        volleyRequest.deleteElement("nota-banca-del/" + notaBanca.getID());
    }

    public void onClickAnnulla(View view) {
        finish();
    }
}
