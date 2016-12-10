package com.mcteam.gestapp.Moduli.Amministrazione.PrimaNotaCassa;

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
import com.mcteam.gestapp.Models.PrimaNota.NotaCassa;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.Functions;
import com.mcteam.gestapp.NetworkReq.VolleyRequests;

/**
 * Created by Riccardo Rossi on 19/05/2016.
 */
public class VisualElimCassaActivity extends AppCompatActivity {

    private NotaCassa notaCassa;
    private VolleyRequests volleyRequest;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_nota_cassa);

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

        TextView cassa = (TextView) findViewById(R.id.tv_cassa);
        TextView dataOperazione = (TextView) findViewById(R.id.tv_data_operazione);
        TextView causaleContabile = (TextView) findViewById(R.id.tv_causale_contabile);
        TextView sottoconto = (TextView) findViewById(R.id.tv_sottoconto);
        TextView descrizione = (TextView) findViewById(R.id.tv_descrizione);
        TextView protocollo = (TextView) findViewById(R.id.tv_protocollo);
        TextView dare = (TextView) findViewById(R.id.tv_dare);
        TextView avere = (TextView) findViewById(R.id.tv_avere);
        TextView totale = (TextView) findViewById(R.id.tv_totale);

        LinearLayout layoutVisual = (LinearLayout) findViewById(R.id.layout_visual);
        LinearLayout layoutElim = (LinearLayout) findViewById(R.id.layout_elim);

        boolean visualizza = getIntent().getBooleanExtra(Constants.VISUAL_ELIM_STAMPA, true);
        notaCassa = getIntent().getParcelableExtra(Constants.NOTA_CASSA);

        String[] cassaArray = getResources().getStringArray(R.array.type);

        cassa.setText(cassaArray[notaCassa.getCassa()]);
        dataOperazione.setText(notaCassa.getDataPagamento());
        causaleContabile.setText(notaCassa.getCausaleContabile());
        sottoconto.setText(notaCassa.getSottoconto());
        descrizione.setText(notaCassa.getDescrizione());
        if (notaCassa.getNumeroProtocollo() != 0)
            protocollo.setText(notaCassa.getNumeroProtocollo() + "");
        dare.setText(Functions.format(notaCassa.getDare()) + " €");
        avere.setText(Functions.format(notaCassa.getAvere()) + " €");
        totale.setText(Functions.format(notaCassa.getTotale()) + " €");

        if (visualizza)
            layoutVisual.setVisibility(View.VISIBLE);
        else {
            setTitle("Elimina nota cassa");
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
        Intent modificaIntent = new Intent(getApplicationContext(), NuovoModifCassaActivity.class);
        modificaIntent.putExtra(Constants.NOTA_CASSA, notaCassa);
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
        volleyRequest.deleteElement("nota-cassa-del/" + notaCassa.getID());
    }

    public void onClickAnnulla(View view) {
        finish();
    }
}
