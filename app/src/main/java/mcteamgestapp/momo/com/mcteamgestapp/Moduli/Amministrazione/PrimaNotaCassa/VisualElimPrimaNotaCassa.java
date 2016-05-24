package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Amministrazione.PrimaNotaCassa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import mcteamgestapp.momo.com.mcteamgestapp.Constants;
import mcteamgestapp.momo.com.mcteamgestapp.Models.PrimaNota.NotaCassa;
import mcteamgestapp.momo.com.mcteamgestapp.R;
import mcteamgestapp.momo.com.mcteamgestapp.ToolUtils;
import mcteamgestapp.momo.com.mcteamgestapp.VolleyRequests;

/**
 * Created by Rrossi on 19/05/2016.
 */
public class VisualElimPrimaNotaCassa extends AppCompatActivity {

    private NotaCassa notaCassa;
    private VolleyRequests volleyRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_nota_cassa);

        TextView dataOperazione = (TextView) findViewById(R.id.tv_data_operazione);
        TextView causaleContabile = (TextView) findViewById(R.id.tv_causale_contabile);
        TextView sottoconto = (TextView) findViewById(R.id.tv_sottoconto);
        TextView descrizione = (TextView) findViewById(R.id.tv_descrizione);
        TextView protocollo = (TextView) findViewById(R.id.tv_protocollo);
        TextView dare = (TextView) findViewById(R.id.tv_dare);
        TextView avere = (TextView) findViewById(R.id.tv_avere);
        TextView totale = (TextView) findViewById(R.id.tv_totale);
        Button bModificaV = (Button) findViewById(R.id.b_visual_modifica);
        Button bEliminaV = (Button) findViewById(R.id.b_visual_elimina);
        Button bStampaV = (Button) findViewById(R.id.b_visual_stampa);
        Button bStampaDettV = (Button) findViewById(R.id.b_visual_stampadett);
        Button bAnnullaE = (Button) findViewById(R.id.b_elim_annulla);
        Button bElimE = (Button) findViewById(R.id.b_elim_elim);

        LinearLayout layoutVisual = (LinearLayout) findViewById(R.id.layout_visual);
        LinearLayout layoutElim = (LinearLayout) findViewById(R.id.layout_elim);

        boolean visualizza = getIntent().getBooleanExtra(Constants.VISUAL_ELIMINA, true);
        notaCassa = getIntent().getParcelableExtra(Constants.NOTA_CASSA);
        System.out.println(notaCassa);

        dataOperazione.setText(notaCassa.getDataPagamento());
        causaleContabile.setText(notaCassa.getCausaleContabile());
        sottoconto.setText(notaCassa.getSottoconto());
        descrizione.setText(notaCassa.getDescrizione());
        protocollo.setText(notaCassa.getNumeroProtocollo()+"");
        dare.setText(notaCassa.getDare()+"");
        avere.setText(notaCassa.getAvere()+"");
        totale.setText(notaCassa.getTotale()+"");

        if(visualizza)
            layoutVisual.setVisibility(View.VISIBLE);
        else
            layoutElim.setVisibility(View.VISIBLE);

        volleyRequest = new VolleyRequests(this, this);
    }

    public void onClickPrint(View view) {

    }

    public void onClickEdit(View view) {
        Intent modificaIntent = new Intent(getApplicationContext(), NewEditPrimaNotaCassaActivity.class);
        modificaIntent.putExtra(Constants.NOTA_CASSA, notaCassa);
        //modificaIntent.putExtra("actualUser", mUser);
        startActivity(modificaIntent);
    }

    public void onClickDelete(View view) {
        volleyRequest.deleteElement("nota-cassa-del/" + notaCassa.getID());
    }

    public void onClickPrintDett(View view) {
    }

    public void onClickAnnulla(View view) {
        finish();
    }
}
