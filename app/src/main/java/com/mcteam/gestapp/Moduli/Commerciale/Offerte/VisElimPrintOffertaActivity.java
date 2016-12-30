package com.mcteam.gestapp.Moduli.Commerciale.Offerte;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcteam.gestapp.Models.Commerciale.Offerta;
import com.mcteam.gestapp.Models.Commessa;
import com.mcteam.gestapp.Moduli.Gestionale.Allegati.AllegatiUtils;
import com.mcteam.gestapp.NetworkReq.VolleyRequests;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.Constants;

/**
 * @author Created by Riccardo Rossi on 17/10/2016.
 */
public class VisElimPrintOffertaActivity extends AppCompatActivity {

    private VolleyRequests mVolleyRequests;
    private Offerta mOfferta;
    private Commessa mCommessa;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_offerta);

        //Permette landscape e portrait solo se è un tablet
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable actionBarBack = getDrawable(R.drawable.commerciale_home_background);
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
        }

        mCommessa = getIntent().getParcelableExtra("COMMESSA");
        mOfferta = getIntent().getParcelableExtra("OFFERTA");

        TextView textOffertaCodComm = (TextView) findViewById(R.id.visual_offerta_codcomm);
        TextView textOffertaClient = (TextView) findViewById(R.id.visual_offerta_cliente);
        TextView textOffertaRef1 = (TextView) findViewById(R.id.visual_offerta_ref1);
        TextView textOffertaRef2 = (TextView) findViewById(R.id.visual_offerta_ref2);
        TextView textOffertaRef3 = (TextView) findViewById(R.id.visual_offerta_ref3);
        TextView textOffertaDataOff = (TextView) findViewById(R.id.visual_offerta_dataoff);
        TextView textOffertaObj = (TextView) findViewById(R.id.visual_offerta_oggetto);
        CheckBox textOffertaPresent = (CheckBox) findViewById(R.id.visual_offerta_present);
        ImageView textOffertaAlleg = (ImageView) findViewById(R.id.visual_offerta_allegato);
        mVolleyRequests = new VolleyRequests(this, this);

        LinearLayout layoutVisual = (LinearLayout) findViewById(R.id.layout_visual);
        LinearLayout layoutElim = (LinearLayout) findViewById(R.id.layout_elim);
        LinearLayout layoutStampa = (LinearLayout) findViewById(R.id.layout_stampa);

        textOffertaCodComm.setText(mCommessa.getCodice_commessa());
        textOffertaClient.setText(mCommessa.getCliente().getNomeSocietà());
        if(mCommessa.getReferente_offerta1() != null)
            textOffertaRef1.setText(mCommessa.getReferente_offerta1().getNome() + mCommessa.getReferente_offerta1().getCognome());

        if(mCommessa.getReferente_offerta2() != null)
            textOffertaRef2.setText(mCommessa.getReferente_offerta2().getNome() + mCommessa.getReferente_offerta2().getCognome());

        if(mCommessa.getReferente_offerta3() != null)
            textOffertaRef3.setText(mCommessa.getReferente_offerta3().getNome() + mCommessa.getReferente_offerta3().getCognome());

        textOffertaDataOff.setText(mOfferta.getDataOfferta());
        textOffertaObj.setText(mCommessa.getNome_commessa());
        textOffertaPresent.setChecked(mOfferta.getAccettata() == 1);
        textOffertaAlleg.setImageBitmap(AllegatiUtils.getAllegatoLogo(getResources(), mOfferta.getAllegato()));

        if(getIntent().getStringExtra(Constants.VISUAL_ELIM_STAMPA).equals("VISUAL")) {
            layoutVisual.setVisibility(View.VISIBLE);
        } else if(getIntent().getStringExtra(Constants.VISUAL_ELIM_STAMPA).equals("ELIM")){
            setTitle("Elimina Offerta");
            layoutElim.setVisibility(View.VISIBLE);
        } else if(getIntent().getStringExtra(Constants.VISUAL_ELIM_STAMPA).equals("STAMPA")) {
            setTitle("Stampa Offerta");
            layoutStampa.setVisibility(View.VISIBLE);
        }

    }

    public void onClickDelete(View view) {
        mVolleyRequests.deleteElement("offerta-delete/" + mOfferta.getIdCommessa() + "/" + mOfferta.getVersione());
    }

    public void onClickEdit(View view) {
        Intent modificaIntent = new Intent(this, NuovaModifOffertaActivity.class);
        modificaIntent.putExtra("OFFERTA_TO_EDIT", mOfferta);
        modificaIntent.putExtra("COMMESSA", mCommessa);
        modificaIntent.putExtra("NUOVO", false);
        /* Invia il risultato al chiamante di questa activity */
        modificaIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        startActivity(modificaIntent);
        finish();
    }

    public void onClickPrint(View view) {
        Intent stampaIntent = new Intent(this, VisElimPrintOffertaActivity.class);
        stampaIntent.putExtra("OFFERTA", mOfferta);
        stampaIntent.putExtra("COMMESSA", mCommessa);
        stampaIntent.putExtra(Constants.VISUAL_ELIM_STAMPA, "STAMPA");
        this.startActivity(stampaIntent);
    }
}
