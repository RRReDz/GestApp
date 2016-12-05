package com.mcteam.gestapp.Moduli.Commerciale.Offerte;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcteam.gestapp.Models.Commerciale.Offerta;
import com.mcteam.gestapp.Models.Commessa;
import com.mcteam.gestapp.Moduli.Gestionale.Allegati.AllegatiUtils;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.Constants;

/**
 * @author Created by Riccardo Rossi on 17/10/2016.
 */
public class VisualElimOffertaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_offerta);

        Commessa commessa = getIntent().getParcelableExtra("COMMESSA");
        Offerta offerta = getIntent().getParcelableExtra("OFFERTA");

        TextView textOffertaCodComm = (TextView) findViewById(R.id.visual_offerta_codcomm);
        TextView textOffertaClient = (TextView) findViewById(R.id.visual_offerta_cliente);
        TextView textOffertaRef1 = (TextView) findViewById(R.id.visual_offerta_ref1);
        TextView textOffertaRef2 = (TextView) findViewById(R.id.visual_offerta_ref2);
        TextView textOffertaRef3 = (TextView) findViewById(R.id.visual_offerta_ref3);
        TextView textOffertaDataOff = (TextView) findViewById(R.id.visual_offerta_dataoff);
        TextView textOffertaObj = (TextView) findViewById(R.id.visual_offerta_oggetto);
        CheckBox textOffertaPresent = (CheckBox) findViewById(R.id.visual_offerta_present);
        ImageView textOffertaAlleg = (ImageView) findViewById(R.id.visual_offerta_allegato);

        LinearLayout layoutVisual = (LinearLayout) findViewById(R.id.layout_visual);
        LinearLayout layoutElim = (LinearLayout) findViewById(R.id.layout_elim);

        textOffertaCodComm.setText(commessa.getCodice_commessa());
        textOffertaClient.setText(commessa.getCliente().getNomeSocietà());
        if(commessa.getReferente_offerta1() != null)
            textOffertaRef1.setText(commessa.getReferente_offerta1().getNome() + commessa.getReferente_offerta1().getCognome());

        if(commessa.getReferente_offerta2() != null)
            textOffertaRef2.setText(commessa.getReferente_offerta2().getNome() + commessa.getReferente_offerta2().getCognome());

        if(commessa.getReferente_offerta3() != null)
            textOffertaRef3.setText(commessa.getReferente_offerta3().getNome() + commessa.getReferente_offerta3().getCognome());

        textOffertaDataOff.setText(offerta.getDataOfferta());
        textOffertaObj.setText(commessa.getNome_commessa());
        textOffertaPresent.setChecked(offerta.getAccettata() == 1);
        textOffertaAlleg.setImageBitmap(AllegatiUtils.getAllegatoLogo(getResources(), offerta.getAllegato()));

        if(getIntent().getStringExtra(Constants.VISUAL_ELIMINA).equals("VISUAL")) {
            layoutVisual.setVisibility(View.VISIBLE);
        } else if(getIntent().getStringExtra(Constants.VISUAL_ELIMINA).equals("ELIM")){
            layoutElim.setVisibility(View.VISIBLE);
        }

    }
}
