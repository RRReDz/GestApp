package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Commerciale.Offerte;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Commerciale.Offerte;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Commessa;
import mcteamgestapp.momo.com.mcteamgestapp.NetworkReq.VolleyRequests;
import mcteamgestapp.momo.com.mcteamgestapp.R;
import mcteamgestapp.momo.com.mcteamgestapp.Utils.ComparatorPool;

public class OfferteActivity extends AppCompatActivity {

    private ArrayList<Offerte> mOffArrList;
    private ArrayList<Commessa> mCommArrList;
    private RecyclerView mOffRecyclerView;
    private OfferteListAdapter mAdapter;
    private VolleyRequests mVolleyRequests;

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
            Drawable actionBarBack = getDrawable(R.drawable.actionbar_gestionale);
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
        }

        mOffRecyclerView = (RecyclerView) findViewById(R.id.offerte_recycler);
        mOffRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mVolleyRequests = new VolleyRequests(this, this);
        mCommArrList = new ArrayList<>();
        mOffArrList = new ArrayList<>();
        mAdapter = new OfferteListAdapter(mCommArrList, new OfferteListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Commessa item) {
                Toast.makeText(OfferteActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        mOffRecyclerView.setAdapter(mAdapter);

        mVolleyRequests.getCommesseList(mCommArrList, mAdapter);
    }

    public void updateList(ArrayList<Commessa> list) {
        //showProgress(false);
        Collections.sort(list, ComparatorPool.getCommessaComparator());
        mCommArrList.clear();
        mCommArrList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }
}
