package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Commerciale.Offerte;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Commerciale.Offerta;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Commessa;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Allegati.AllegatiUtils;
import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * @author Created by Riccardo Rossi on 17/10/2016.
 */
public class VisualOffertaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_offerta);

        Offerta offerta = getIntent().getParcelableExtra("OFFERTA");
        Commessa commessa = getIntent().getParcelableExtra("COMMESSA");

        TextView textOffertaCodComm = (TextView) findViewById(R.id.visual_offerta_codcomm);
        TextView textOffertaClient = (TextView) findViewById(R.id.visual_offerta_cliente);
        TextView textOffertaRef1 = (TextView) findViewById(R.id.visual_offerta_ref1);
        TextView textOffertaRef2 = (TextView) findViewById(R.id.visual_offerta_ref2);
        TextView textOffertaRef3 = (TextView) findViewById(R.id.visual_offerta_ref3);
        TextView textOffertaDataOff = (TextView) findViewById(R.id.visual_offerta_dataoff);
        TextView textOffertaObj = (TextView) findViewById(R.id.visual_offerta_oggetto); //E' il nome della commessa o cosa?
        CheckBox textOffertaPresent = (CheckBox) findViewById(R.id.visual_offerta_present);
        ImageView textOffertaAlleg = (ImageView) findViewById(R.id.visual_offerta_allegato);

        textOffertaCodComm.setText(commessa.getCodice_commessa());
        textOffertaClient.setText(commessa.getCliente().getNomeSocietà());
        textOffertaRef1.setText(commessa.getCommerciale().getNome() + commessa.getCommerciale().getCognome());
        textOffertaRef2.setText(commessa.getReferente1().getNome() + commessa.getReferente1().getCognome());
        textOffertaRef3.setText(commessa.getReferente2().getNome() + commessa.getReferente2().getCognome());
        textOffertaDataOff.setText(offerta.getDataOfferta());
        textOffertaObj.setText(""); //Non so principalmente che cosa settare, dal doc non si capisce
        textOffertaPresent.setChecked(offerta.getAccettata() == 1);
        textOffertaAlleg.setImageBitmap(AllegatiUtils.getAllegatoLogo(getResources(), offerta.getAllegato()));

    }
}
