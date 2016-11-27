package com.mcteam.gestapp.Moduli.Commerciale.Offerte;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mcteam.gestapp.Models.Commessa;
import com.mcteam.gestapp.Models.Rubrica.Nominativo;
import com.mcteam.gestapp.NetworkReq.VolleyRequests;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.ComparatorPool;
import com.mcteam.gestapp.Utils.Constants;
import com.mcteam.gestapp.Utils.GuiUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OfferteActivity extends AppCompatActivity {

    private ArrayList<Commessa> mCommArrList;
    private ArrayList<Commessa> mCommArrListO;
    private RecyclerView mOffRecyclerView;
    private OfferteAdapter mAdapter;
    private VolleyRequests mVolleyRequests;
    private ProgressBar mProgressBar;
    private ArrayList<Nominativo> mNominativiList;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offerte);

        //Permette landscape e portrait solo se è un tablet
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable actionBarBack = getDrawable(R.drawable.commerciale_home_background);
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
        }

        mOffRecyclerView = (RecyclerView) findViewById(R.id.offerte_comm_recycler);
        mOffRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mVolleyRequests = new VolleyRequests(this, this);
        mCommArrList = new ArrayList<>();
        mCommArrListO = new ArrayList<>();
        mNominativiList = new ArrayList<>();
        mAdapter = new OfferteAdapter(mCommArrList, new OfferteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Commessa item) {
                /* Carico nell'item commessa il valore dei nominativi dei tre referenti delle offerte */
                item.setReferente_offerta1(getNominativoById(mNominativiList, item.getOff1()));
                item.setReferente_offerta2(getNominativoById(mNominativiList, item.getOff2()));
                item.setReferente_offerta3(getNominativoById(mNominativiList, item.getOff3()));

                Intent i = new Intent(getApplicationContext(), DettaglioOffertaActivity.class);
                i.putExtra("COMMESSA", item);
                startActivityForResult(i, Constants.SHOW_OFFERTA);
            }
        });
        mProgressBar = (ProgressBar) findViewById(R.id.offerte_progress);

        mOffRecyclerView.setAdapter(mAdapter);

        mVolleyRequests.getNominativiList(mNominativiList, null);
        mVolleyRequests.getCommesseList();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        /* Aggiorno la lista delle commesse quando l'activity torna visibile */
        GuiUtils.showProgressBar(mOffRecyclerView, mProgressBar, true);
        mVolleyRequests.getCommesseList();
    }

    // FromVolleyRequest indica se la richiesta di aggiornamento lista è stata fatta dopo una richiesta al db (Volley)
    public void updateList(ArrayList<Commessa> list, boolean fromVolleyRequest) {
        Collections.sort(list, ComparatorPool.getCommessaComparator());
        /* Rimozione progress bar */
        GuiUtils.showProgressBar(mOffRecyclerView, mProgressBar, false);
        mCommArrList.clear();
        mCommArrList.addAll(list);
        mAdapter.notifyDataSetChanged();
        if (fromVolleyRequest) //Aggiorna solo se richiesta fatta da una volley
            mCommArrListO.addAll(list);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.SHOW_OFFERTA && resultCode == Activity.RESULT_OK)
            Toast.makeText(this, "Ricevuto aggiornamento da NuovaOffertaActivity", Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_societa, menu);

        MenuItem searchItem = menu.findItem(R.id.action_ricerca_semplice);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                simpleSearch(newText);
                return true;
            }
        });

        return true;
    }

    private void simpleSearch(String query) {
        ArrayList<Commessa> matchingElement = new ArrayList<>();

        if (!TextUtils.isEmpty(query)) {
            query = query.toUpperCase();

            for (Commessa commessa : mCommArrListO) {

                String cliente = "";
                if (commessa.getCliente() != null && !TextUtils.isEmpty(commessa.getCliente().getNomeSocietà())) {
                    cliente = commessa.getCliente().getNomeSocietà();
                }

                String nomeRisorsa = "", cognomeRisorsa = "";
                if (commessa.getCommerciale() != null) {
                    cognomeRisorsa = commessa.getCommerciale().getCognome();
                    nomeRisorsa = commessa.getCommerciale().getNome();
                }

                String nomeCommessa = "";
                if (commessa.getNome_commessa() != null && !TextUtils.isEmpty(commessa.getNome_commessa())) {
                    nomeCommessa = commessa.getNome_commessa();
                }

                if (nomeCommessa.toUpperCase().contains(query) || cliente.toUpperCase().contains(query) || nomeRisorsa.toUpperCase().contains(query) || cognomeRisorsa.toUpperCase().contains(query)) {
                    matchingElement.add(commessa);
                }
            }

            updateList(matchingElement, false);
        } else
            updateList(mCommArrListO, false);

    }

    /**
     * Restituisce
     * @param id
     * @return
     */
    public Nominativo getNominativoById(ArrayList<Nominativo> data, int id) {
        for (int i = 0; i < data.size(); i++)
            if (data.get(i).getID() == id)
                return data.get(i);
        return null;
    }

}
