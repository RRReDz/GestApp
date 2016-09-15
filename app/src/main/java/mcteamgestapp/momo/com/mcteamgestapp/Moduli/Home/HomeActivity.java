package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Amministrazione.PrimaNotaBanca.PrimaNotaBancaActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Amministrazione.PrimaNotaCassa.PrimaNotaCassaActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Amministrazione.RubricaBanche.RubricaBanca;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Allegati.AllegatiActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Associazioni.AssociazioniActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Commesse.CommesseActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Nominativo.RubricaNominativaActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Societa.RubricaSocietaActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Login.LoginActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Produzione.Consuntivi.ConsuntiviMainActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi.SistemiAcitivity;
import mcteamgestapp.momo.com.mcteamgestapp.Application.MyApp;
import mcteamgestapp.momo.com.mcteamgestapp.R;

public class HomeActivity extends AppCompatActivity {

    private UserInfo mCurrentUser;

    /*
     *  I differenti frame della homepage
      *     Gestionale
       ----------------------
            Commerciale
       ----------------------
            Amministrazione
       ----------------------
            Produzione
       ----------------------
               Sistemi
       ----------------------

       Ogni parte è divisa in tre po
      *
     */
    private FrameLayout mSistemiFrame;
    private FrameLayout mGestionaleFrame;
    private FrameLayout mCommercialeFrame;
    private FrameLayout mProduzioneFrame;
    private FrameLayout mAmministrazioneFrame;

    private LinearLayout lastTabOpened = null;

    private TextView mTvNome;
    private TextView mTvCognome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Permette landscape e portrait solo se è un tablet
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        //Non lo usa
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;

        //get Global object
        //mCurrentUser = getIntent().getParcelableExtra(Constants.CURRENT_USER);

        //if(mCurrentUser == null)
        mCurrentUser = ((MyApp) this.getApplication()).getCurrentUser(); //Get current user

        mSistemiFrame = (FrameLayout) findViewById(R.id.frame_sistemi);
        mGestionaleFrame = (FrameLayout) findViewById(R.id.frame_gestionale);
        mCommercialeFrame = (FrameLayout) findViewById(R.id.frame_commerciale);
        mProduzioneFrame = (FrameLayout) findViewById(R.id.frame_produzione);
        mAmministrazioneFrame = (FrameLayout) findViewById(R.id.frame_amministrazione);

        mTvCognome = (TextView) findViewById(R.id.tv_cognome);
        mTvNome = (TextView) findViewById(R.id.tv_nome);

        if (!TextUtils.isEmpty(mCurrentUser.getCognome()))
            mTvCognome.setText(mCurrentUser.getCognome());
        if (!TextUtils.isEmpty(mCurrentUser.getNome()))
            mTvNome.setText(mCurrentUser.getNome());

        ImageButton moreUserInfo = (ImageButton) findViewById(R.id.more_user_info);

        moreUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMore = new Intent(getApplicationContext(), UserInfoActivity.class);
                startActivity(openMore);
            }
        });

        //Check if user has permission to show certain tabs

        if (!mCurrentUser.isSistemi()) {
            mSistemiFrame.setVisibility(View.GONE);
        }

        if (!mCurrentUser.isGestionale()) {
            mGestionaleFrame.setVisibility(View.GONE);
        }

        if (!mCurrentUser.isCommerciale()) {
            mCommercialeFrame.setVisibility(View.GONE);
        }

        if (!mCurrentUser.isProduzione()) {
            mProduzioneFrame.setVisibility(View.GONE);
        }

        if (!mCurrentUser.isAmministratore()) {
            mAmministrazioneFrame.setVisibility(View.GONE);
        }

        //**************************************************************************
        //Inizializzazione dei button
        //**************************************************************************

        Button accessiButton = (Button) findViewById(R.id.button_accessi);

        accessiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sistemiIntent = new Intent(getContext(), SistemiAcitivity.class);
                startActivity(sistemiIntent);
            }
        });

        Button rubricaSocieta = (Button) findViewById(R.id.home_rubrica_societa);

        rubricaSocieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rubricaSocietaIntent = new Intent(getContext(), RubricaSocietaActivity.class);
                startActivity(rubricaSocietaIntent);
            }
        });

        Button rubricaNominativi = (Button) findViewById(R.id.home_rubrica_nominativi);

        rubricaNominativi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent apriRubricaNominativi = new Intent(getContext(), RubricaNominativaActivity.class);
                apriRubricaNominativi.putExtra("actualUser", mCurrentUser);
                startActivity(apriRubricaNominativi);
            }
        });

        Button commesse = (Button) findViewById(R.id.home_commesse);

        commesse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commesseIntent = new Intent(getContext(), CommesseActivity.class);
                startActivity(commesseIntent);
            }
        });

        Button rubricaBanche = (Button) findViewById(R.id.home_rubrica_banca);

        rubricaBanche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent apriRubricaBanca = new Intent(getContext(), RubricaBanca.class);
                startActivity(apriRubricaBanca);
            }
        });


        ImageButton allegati = (ImageButton) findViewById(R.id.home_allegati);
        allegati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent apriAllegati = new Intent(getContext(), AllegatiActivity.class);
                startActivity(apriAllegati);
            }
        });

        Button associazioni = (Button) findViewById(R.id.home_associazioni);
        associazioni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent apriAssociazioni = new Intent(getApplicationContext(), AssociazioniActivity.class);
                startActivity(apriAssociazioni);
            }
        });

        Button consuntivi = (Button) findViewById(R.id.home_consuntivi);
        consuntivi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consuntivi = new Intent(getApplicationContext(), ConsuntiviMainActivity.class);
                consuntivi.putExtra("actualUser", mCurrentUser);
                startActivity(consuntivi);
            }
        });

        Button primaNotaCassa = (Button) findViewById(R.id.home_prima_nota_cassa);
        primaNotaCassa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent primaNotaCassaIntent = new Intent(getApplicationContext(), PrimaNotaCassaActivity.class);
                primaNotaCassaIntent.putExtra("actualUser", mCurrentUser);
                startActivity(primaNotaCassaIntent);
            }
        });

        Button primaNotaBanca = (Button) findViewById(R.id.home_prima_nota_banca);
        primaNotaBanca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent primaNotaBancaIntent = new Intent(getApplicationContext(), PrimaNotaBancaActivity.class);
                primaNotaBancaIntent.putExtra("actualUser", mCurrentUser);
                startActivity(primaNotaBancaIntent);
            }
        });
    }

    /*
    *  Set visibile a clicked tab and hide other tabs
    */
    public void openTab(View view) {

        RelativeLayout layoutClicked = (RelativeLayout) view;
        LinearLayout layoutToShowHide = null;
        if (layoutClicked.getId() == R.id.gestionale_menu_view) {
            layoutToShowHide = (LinearLayout) findViewById(R.id.gestionale_menu);
        } else if (layoutClicked.getId() == R.id.commerciale_menu_view) {
            layoutToShowHide = (LinearLayout) findViewById(R.id.commerciale_menu);
        } else if (layoutClicked.getId() == R.id.amministrazione_menu_view) {
            layoutToShowHide = (LinearLayout) findViewById(R.id.amministrazione_menu);
        } else if (layoutClicked.getId() == R.id.produzione_menu_view) {
            layoutToShowHide = (LinearLayout) findViewById(R.id.produzione_menu);
        } else if (layoutClicked.getId() == R.id.sistemi_menu_view) {
            layoutToShowHide = (LinearLayout) findViewById(R.id.sistemi_menu);
        }

        if (layoutToShowHide.getVisibility() == View.GONE) {

            if (lastTabOpened != null)
                lastTabOpened.setVisibility(View.GONE);

            layoutToShowHide.setVisibility(View.VISIBLE);
            lastTabOpened = layoutToShowHide;

        } else {
            layoutToShowHide.setVisibility(View.GONE);
            lastTabOpened = null;
        }

    }

    public Context getContext() {
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        ((MyApp) this.getApplication()).setCurrentUser(null); //Remove user in MyApp
        Intent goLogin = new Intent(this, LoginActivity.class);
        goLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(goLogin);
        finish();
    }
}
