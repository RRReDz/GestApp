package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Commerciale.Offerte;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
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

public class DettaglioOffertaActivity extends Activity {

    private ArrayList<Offerta> mOffArrayList;
    private RecyclerView mOffRecyclerView;
    private DettaglioOffertaAdapter mOffAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_offerta);

        Commessa commessa = getIntent().getParcelableExtra("COMMESSA");


        final Gson gson = new Gson();
        mOffArrayList = new ArrayList<>();
        mOffAdapter = new DettaglioOffertaAdapter(mOffArrayList, new DettaglioOffertaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Offerta item) {
                Toast.makeText(DettaglioOffertaActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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

}
