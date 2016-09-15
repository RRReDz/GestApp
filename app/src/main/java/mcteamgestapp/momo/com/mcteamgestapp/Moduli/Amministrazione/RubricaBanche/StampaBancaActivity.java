package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Amministrazione.RubricaBanche;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Banca;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Home.HomeActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Login.LoginActivity;
import mcteamgestapp.momo.com.mcteamgestapp.R;

public class StampaBancaActivity extends AppCompatActivity {

    TextView mNominativoView;
    TextView mIbanView;
    TextView mIndirizzoView;
    TextView mReferenteView;
    Banca mBancaAttuale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stampa_banca);

        //Permette landscape e portrait solo se è un tablet
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable actionBarBack = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                actionBarBack = getDrawable(R.drawable.actionbar_amministrazione);
            } else {
                actionBarBack = getResources().getDrawable(R.drawable.actionbar_amministrazione);
            }
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
        }


        mBancaAttuale = getIntent().getParcelableExtra("bancaToPrint");

        mNominativoView = (TextView) findViewById(R.id.banca_nome);
        mIbanView = (TextView) findViewById(R.id.banca_iban);
        mIndirizzoView = (TextView) findViewById(R.id.banca_indirizzo);
        mReferenteView = (TextView) findViewById(R.id.banca_referente);

        Button annulla = (Button) findViewById(R.id.annulla_action_button);
        Button conferma = (Button) findViewById(R.id.conferma_action_button);


        conferma.setText("STAMPA");

        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BancaUtils.print(mBancaAttuale, getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        setupView(mBancaAttuale);

    }

    void setupView(Banca banca) {

        mNominativoView.setText(banca.getNome());
        mIbanView.setText(banca.getIban());
        mIndirizzoView.setText(banca.getIndirizzo());
        mReferenteView.setText(banca.getReferente());

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                goHome();
                return true;
            case R.id.action_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        Intent goLogin = new Intent(this, LoginActivity.class);
        goLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(goLogin);
        finish();
    }

    private void goHome() {
        Intent goHome = new Intent(this, HomeActivity.class);
        goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(goHome);
        finish();
    }


}
