package com.mcteam.gestapp.Moduli.Commerciale.Offerte;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mcteam.gestapp.Callback.CallbackSelection;
import com.mcteam.gestapp.Fragments.DatePickerFragment;
import com.mcteam.gestapp.Models.Commerciale.Offerta;
import com.mcteam.gestapp.Models.Commessa;
import com.mcteam.gestapp.NetworkReq.VolleyRequests;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.Constants;
import com.mcteam.gestapp.Utils.GuiUtils;

import java.util.ArrayList;

public class DettaglioOffertaActivity extends AppCompatActivity {

    private ArrayList<Offerta> mOffArrayList;
    private ArrayList<Offerta> mOffOriginalList;
    private RecyclerView mOffRecyclerView;
    private DettaglioOffertaAdapter mOffAdapter;
    private Commessa mCommessa;
    private ProgressBar mProgressBar;
    private VolleyRequests mVolleyRequests;
    private FloatingActionsMenu mFabMenu;
    private View mOverlay;
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

        mOffRecyclerView = (RecyclerView) findViewById(R.id.offerte_recycler);
        mOffRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar = (ProgressBar) findViewById(R.id.dett_offerte_progress);
        mFabMenu = (FloatingActionsMenu) findViewById(R.id.fabmenu_offerta);
        mOverlay = findViewById(R.id.dett_off_overlay);

        mOffArrayList = new ArrayList<>();
        mOffOriginalList = new ArrayList<>();
        mOffAdapter = new DettaglioOffertaAdapter(mOffArrayList, mCommessa);
        mVolleyRequests = new VolleyRequests(this, this);
        mCallbackListLoaded = new CallbackSelection<Offerta>() {
            @Override
            public void onListLoaded(ArrayList<Offerta> list) {
                updateList(list);
            }
        };

        mOffRecyclerView.setAdapter(mOffAdapter);

        mFabMenu.setOnFloatingActionsMenuUpdateListener(
                new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
                    @Override
                    public void onMenuExpanded() {
                        mOverlay.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onMenuCollapsed() {
                        mOverlay.setVisibility(View.GONE);
                    }
                }
        );

        mOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOverlay.setVisibility(View.GONE);
                mFabMenu.collapse();
            }
        });

        setupHeaderCommessa(mCommessa);
        setupBodyDettOfferte();
    }

    private void setupBodyDettOfferte() {
        GuiUtils.showProgressBar(mOffRecyclerView, mProgressBar, true);
        mVolleyRequests.getDettOfferteList(mCommessa, mCallbackListLoaded);
    }

    public void updateList(ArrayList<Offerta> newList) {
        GuiUtils.showProgressBar(mOffRecyclerView, mProgressBar, false);
        if (newList.isEmpty())
            emptyMode(true);
        else {
            emptyMode(false);
            mOffArrayList.clear();
            mOffOriginalList.clear();
            mOffArrayList.addAll(newList);
            mOffOriginalList.addAll(newList);
            mOffAdapter.notifyDataSetChanged();
        }
    }

    private void updateListForSearch(ArrayList<Offerta> matchingElement) {
        mOffArrayList.clear();
        mOffArrayList.addAll(matchingElement);
        mOffAdapter.notifyDataSetChanged();
    }

    private void emptyMode(boolean enabled) {
        LinearLayout emptyLayout = (LinearLayout) findViewById(R.id.dettaglio_offerta_empty);
        FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.fabmenu_offerta);
        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fab_offerta_add);
        LinearLayout labelItems = (LinearLayout) findViewById(R.id.dett_off_label_items);

        if (enabled) {
            fabAdd.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            fabMenu.setVisibility(View.VISIBLE);
            labelItems.setVisibility(View.VISIBLE);
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
        } else if ((requestCode == Constants.OFFERTA_DEL || requestCode == Constants.OFFERTA_EDIT) && resultCode == RESULT_OK) {
            Toast.makeText(this, "La lista di offerte è stata aggiornata", Toast.LENGTH_SHORT).show();
            setupBodyDettOfferte();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dett_off, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
            MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .title(R.string.title_activity_societa_advance_search)
                    .customView(R.layout.dett_off_advanced_search_dialog, false)
                    .onPositive(
                            new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull final MaterialDialog dialog, @NonNull DialogAction which) {
                                    View customView = dialog.getCustomView();
                                    EditText versione = (EditText) customView.findViewById(R.id.versione);
                                    EditText dataOfferta = (EditText) customView.findViewById(R.id.data_offerta);
                                    Spinner presentata = (Spinner) customView.findViewById(R.id.presentata);

                                    advancedSearch(
                                            versione.getText().toString(),
                                            dataOfferta.getText().toString(),
                                            presentata.getSelectedItem().toString()
                                    );
                                }
                            })
                    .positiveText("Cerca")
                    .negativeText("Annulla")
                    .show();

            View customView = dialog.getCustomView();
            TextView dataOfferta = (TextView) customView.findViewById(R.id.data_offerta);

            dataOfferta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickSelData(view);
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private void onClickSelData(View dateView) {
        DialogFragment dialogFragment = new DatePickerFragment((TextView) dateView);
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    private void advancedSearch(String versioneString, String dataOfferta, String presentataString) {
        ArrayList<Offerta> matchingElement = new ArrayList<>();
        if (!(versioneString.isEmpty() && dataOfferta.isEmpty() && presentataString.equals("Entrambe"))) {

            int versione;
            try {
                versione = Integer.parseInt(versioneString);
            } catch (NumberFormatException exc) {
                versione = -1;
            }

            int presentata;
            switch (presentataString) {
                case "Si": presentata = 1; break;
                case "No": presentata = 0; break;
                case "Entrambe": presentata = 2; break;
                default: presentata = -1;
            }

            for (Offerta offerta : mOffOriginalList) {
                if ((offerta.getVersione() == versione || versione == -1) &&
                        (offerta.getAccettata() == presentata || presentata == 2) &&
                        (dataOfferta.isEmpty() || offerta.getDataOfferta().equals(dataOfferta))) {
                    matchingElement.add(offerta);
                }
            }

            updateListForSearch(matchingElement);
        }

    }
}
