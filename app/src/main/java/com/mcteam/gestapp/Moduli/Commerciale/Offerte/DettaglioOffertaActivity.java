package com.mcteam.gestapp.Moduli.Commerciale.Offerte;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mcteam.gestapp.Callback.CallbackSelection;
import com.mcteam.gestapp.Models.Commerciale.Offerta;
import com.mcteam.gestapp.Models.Commessa;
import com.mcteam.gestapp.NetworkReq.VolleyRequests;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.Constants;
import com.mcteam.gestapp.Utils.GuiUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DettaglioOffertaActivity extends AppCompatActivity {

    private ArrayList<Offerta> mOffArrayList;
    private RecyclerView mOffRecyclerView;
    private DettaglioOffertaAdapter mOffAdapter;
    private Commessa mCommessa;
    private ProgressBar mProgressBar;
    private VolleyRequests mVolleyRequests;
    private CallbackSelection<Offerta> mCallbackListLoaded;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_offerta);

        //Permette landscape e portrait solo se è un tablet
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable actionBarBack = getDrawable(R.drawable.commerciale_home_background);
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
        }

        mCommessa = getIntent().getParcelableExtra("COMMESSA");

        final Gson gson = new Gson();
        mOffArrayList = new ArrayList<>();
        mOffAdapter = new DettaglioOffertaAdapter(mOffArrayList, mCommessa);
        mOffRecyclerView = (RecyclerView) findViewById(R.id.offerte_recycler);
        mOffRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = (ProgressBar) findViewById(R.id.dett_offerte_progress);
        mOffRecyclerView.setAdapter(mOffAdapter);
        mVolleyRequests = new VolleyRequests(this, this);
        mCallbackListLoaded = new CallbackSelection<Offerta>() {
            @Override
            public void onListLoaded(ArrayList<Offerta> list) {
                updateList(list);
            }
        };

        setupHeaderCommessa(mCommessa);
        setupBodyDettOfferte();
    }

    private void setupBodyDettOfferte() {
        mVolleyRequests.getDettOfferteList(mCommessa, mCallbackListLoaded);
    }

    public void updateList(ArrayList<Offerta> newList) {
        GuiUtils.showProgressBar(mOffRecyclerView, mProgressBar, false);
        if (newList.isEmpty())
            emptyMode(true);
        else {
            emptyMode(false);
            mOffArrayList.clear();
            mOffArrayList.addAll(newList);
            mOffAdapter.notifyDataSetChanged();
        }
    }

    private void emptyMode(boolean enabled) {
        LinearLayout emptyLayout = (LinearLayout) findViewById(R.id.dettaglio_offerta_empty);
        FloatingActionButton fabSearch = (FloatingActionButton) findViewById(R.id.fab_offerta_search);
        FloatingActionButton fabPrint = (FloatingActionButton) findViewById(R.id.fab_offerta_print);
        FloatingActionButton fabExcel = (FloatingActionButton) findViewById(R.id.fab_offerta_excel);
        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fab_offerta_add);
        LinearLayout fieldsLayout = (LinearLayout) findViewById(R.id.dettaglio_offerta_fields);

        if (enabled) {
            fabAdd.setVisibility(View.VISIBLE);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
            fabSearch.setVisibility(View.VISIBLE);
            fabPrint.setVisibility(View.VISIBLE);
            fabExcel.setVisibility(View.VISIBLE);
        }

    }

    private void setupHeaderCommessa(Commessa commessa) {
        TextView codCommessa = (TextView) findViewById(R.id.dett_off_head_codcomm);
        TextView nomeComm = (TextView) findViewById(R.id.dett_off_head_nomecomm);
        TextView cliente = (TextView) findViewById(R.id.dett_off_head_cliente);
        TextView refCommessa = (TextView) findViewById(R.id.dett_off_head_refcomm);
        TextView consulente = (TextView) findViewById(R.id.dett_off_head_consul);

        codCommessa.setText(commessa.getCodice_commessa());
        nomeComm.setText(commessa.getNome_commessa());
        cliente.setText(commessa.getCliente().getNomeSocietà());

        String nomeReferente = commessa.getReferente1() == null ? "" : commessa.getReferente1().getNome();
        String cognomeReferente = commessa.getReferente1() == null ? "" : commessa.getReferente1().getCognome();
        refCommessa.setText(nomeReferente + " " + cognomeReferente);

        String nomeConsulente = commessa.getCommerciale() == null ? "" : commessa.getCommerciale().getNome();
        String cognomeConsulente = commessa.getCommerciale() == null ? "" : commessa.getCommerciale().getCognome();
        consulente.setText(nomeConsulente + " " + cognomeConsulente);
    }

    public void onClickAddOfferta(View view) {
        Intent intent = new Intent(getApplicationContext(), NuovaModifOffertaActivity.class);
        intent.putExtra("COMMESSA", mCommessa);
        intent.putExtra("NUOVO", true);
        startActivityForResult(intent, Constants.OFFERTA_ADD, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* Risposta di ok ricevuta */
        if (requestCode == Constants.OFFERTA_ADD && resultCode == RESULT_OK) {
            /* Debug */
            //Toast.makeText(this, "Ricevuto messaggio di risposta da volley request", Toast.LENGTH_SHORT).show();

            /* Se il "parent" di questa activity non è null (OfferteActivity), allora setto il risultato per la callback */
            //if(getParent() != null)
            //    getParent().setResult(Activity.RESULT_OK);
            /* Altrimenti rilancio l'activity nuovamente */
            //else
            //    startActivity(new Intent(getApplicationContext(), OfferteActivity.class), null);
            finish();
        } else if (requestCode == Constants.OFFERTA_EDIT && resultCode == RESULT_OK) {

            setupBodyDettOfferte();
        }
    }
}
