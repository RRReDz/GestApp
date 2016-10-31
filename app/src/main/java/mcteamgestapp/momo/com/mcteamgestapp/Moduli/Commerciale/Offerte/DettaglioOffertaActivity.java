package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Commerciale.Offerte;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Commerciale.Offerta;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Commessa;
import mcteamgestapp.momo.com.mcteamgestapp.R;

public class DettaglioOffertaActivity extends AppCompatActivity {

    private ArrayList<Offerta> mOffArrayList;
    private RecyclerView mOffRecyclerView;
    private DettaglioOffertaAdapter mOffAdapter;


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

        Commessa commessa = getIntent().getParcelableExtra("COMMESSA");

        final Gson gson = new Gson();
        mOffArrayList = new ArrayList<>();
        mOffAdapter = new DettaglioOffertaAdapter(mOffArrayList, commessa);
        mOffRecyclerView = (RecyclerView) findViewById(R.id.offerte_recycler);
        mOffRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOffRecyclerView.setAdapter(mOffAdapter);

        String url = getString(R.string.mobile_url) + "offerte-list/" + commessa.getID();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Offerta> newList = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Offerta offerta = gson.fromJson(obj.toString(), Offerta.class);
                                //System.out.println(offerta);
                                newList.add(offerta);
                            } catch (JSONException e) {
                                System.out.println("Something went wrong during deserialization!");
                                e.printStackTrace();
                            }
                        }
                        updateList(newList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Something went wrong!");
                        error.printStackTrace();
                    }
                }
        );

        Volley.newRequestQueue(this).add(jsonObjectRequest);

        setupHeaderCommessa(commessa);
    }

    public void updateList(ArrayList<Offerta> newList) {
        if (newList.isEmpty()) {
            LinearLayout emptyLayout = (LinearLayout) findViewById(R.id.dettaglio_offerta_empty);
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            LinearLayout fieldsLayout = (LinearLayout) findViewById(R.id.dettaglio_offerta_fields);
            fieldsLayout.setVisibility(View.VISIBLE);

            mOffArrayList.addAll(newList);
            mOffAdapter.notifyDataSetChanged();
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

}
