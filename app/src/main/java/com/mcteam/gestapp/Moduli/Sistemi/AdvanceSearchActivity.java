package com.mcteam.gestapp.Moduli.Sistemi;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mcteam.gestapp.Models.UserInfo;
import com.mcteam.gestapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AdvanceSearchActivity extends AppCompatActivity {

    ListView mResulList;
    SistemiListAdapter mListAdapter;
    ArrayList<UserInfo> mAccessiList = null;
    ArrayList<UserInfo> mSearchList;
    EditText mNome;
    EditText mCognome;
    RadioButton mAbilitato;
    RadioButton mNonAbilitato;
    TextView mResultStatus;
    Comparator<UserInfo> surnameSortingComparator;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_search);

        //Permette landscape e portrait solo se è un tablet
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable actionBarBack = getDrawable(R.drawable.actionbar_sistemi);
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
            getSupportActionBar().setIcon(R.drawable.ic_accessibility_white_24dp);
        }

        mResulList = (ListView) findViewById(R.id.sistemi_cerca_result_list);

        surnameSortingComparator = new Comparator<UserInfo>() {
            @Override
            public int compare(UserInfo lhs, UserInfo rhs) {
                return String.CASE_INSENSITIVE_ORDER.compare(lhs.getCognome(), rhs.getCognome());
            }
        };

        Intent intent = getIntent();
        mAccessiList = intent.getParcelableArrayListExtra("listaAccessi");

        if (mAccessiList == null || mAccessiList.isEmpty()) {
            finish();
        }

        mSearchList = new ArrayList<>(mAccessiList);

        mListAdapter = new SistemiListAdapter(this, mAccessiList);

        mNome = (EditText) findViewById(R.id.accessi_cerca_nome);
        mCognome = (EditText) findViewById(R.id.accessi_cerca_cognome);

        mAbilitato = (RadioButton) findViewById(R.id.sistemi_cerca_abilitato_si);
        mNonAbilitato = (RadioButton) findViewById(R.id.sistemi_cerca_abilitato_no);
        mResultStatus = (TextView) findViewById(R.id.sistemi_ricerca_avanzata_status);


        ImageButton cercaButton = (ImageButton) findViewById(R.id.sistemi_cerca_button);

        cercaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerca();
            }
        });

        mResulList.setAdapter(mListAdapter);

        mResulList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserInfo selectedUser;
                selectedUser = (UserInfo) parent.getItemAtPosition(position);
                Intent visualizzaUtente = new Intent(getApplicationContext(), VisualizzaActivity.class);
                visualizzaUtente.putExtra("userToView", selectedUser);
                startActivity(visualizzaUtente);
            }
        });

    }

    private void cerca() {
        String nome = mNome.getText().toString();
        String cognome = mCognome.getText().toString();

        ArrayList<UserInfo> matchingElement = new ArrayList<>();

        boolean abilitato = mAbilitato.isChecked();

        String[] data = {nome, cognome, abilitato ? "abititato" : "disabilitato"};

        if (!TextUtils.isEmpty(nome) && !TextUtils.isEmpty(cognome)) {
            for (UserInfo user : mSearchList) {
                if (abilitato) {
                    if ((user.getCognome().toUpperCase().contains(cognome.toUpperCase()) || user.getNome().toUpperCase().contains(nome.toUpperCase())) && user.isAbilitato()) {
                        matchingElement.add(user);
                    }
                } else {
                    if ((user.getCognome().toUpperCase().contains(cognome.toUpperCase()) || user.getNome().toUpperCase().contains(nome.toUpperCase())) && !user.isAbilitato()) {
                        matchingElement.add(user);
                    }
                }
            }
            updateList(matchingElement, data);
        } else if (!TextUtils.isEmpty(nome)) {
            for (UserInfo user : mSearchList) {
                if (abilitato) {
                    if (user.getNome().toUpperCase().contains(nome.toUpperCase()) && user.isAbilitato()) {
                        matchingElement.add(user);
                    }
                } else {
                    if (user.getNome().toUpperCase().contains(nome.toUpperCase()) && !user.isAbilitato()) {
                        matchingElement.add(user);
                    }
                }
            }
            updateList(matchingElement, data);
        } else if (!TextUtils.isEmpty(cognome)) {
            for (UserInfo user : mSearchList) {
                if (abilitato) {
                    if (user.getCognome().toUpperCase().contains(cognome.toUpperCase()) && user.isAbilitato()) {
                        matchingElement.add(user);
                    }
                } else {
                    if (user.getCognome().toUpperCase().contains(cognome.toUpperCase()) && !user.isAbilitato()) {
                        matchingElement.add(user);
                    }
                }
            }
            updateList(matchingElement, data);
        } else {
            for (UserInfo user : mSearchList) {
                if (abilitato) {
                    if (user.isAbilitato()) {
                        matchingElement.add(user);
                    }
                } else {
                    if (!user.isAbilitato()) {
                        matchingElement.add(user);
                    }
                }
            }
            updateList(matchingElement, data);
        }

    }

    private void updateList(ArrayList<UserInfo> list, String... query) {
        mAccessiList.clear();
        mAccessiList.addAll(list);
        String result = "";
        for (String data : query) {
            if (!TextUtils.isEmpty(data))
                result += "\"" + data + "\"";
        }

        mResultStatus.setText("Risultati per " + result + " : " + list.size());
        Collections.sort(mAccessiList, surnameSortingComparator);
        mListAdapter.cleanAlphabeticIndex();
        mListAdapter.notifyDataSetChanged();
    }
}
