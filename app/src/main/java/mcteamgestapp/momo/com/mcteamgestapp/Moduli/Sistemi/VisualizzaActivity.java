package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;

import org.json.JSONException;
import org.json.JSONObject;

import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;
import mcteamgestapp.momo.com.mcteamgestapp.NetworkReq.PUTRequest;
import mcteamgestapp.momo.com.mcteamgestapp.R;

public class VisualizzaActivity extends AppCompatActivity {

    private static final int REQUEST_MODIFY = 9090;
    private TextView mNameView;
    private TextView mSurnameView;
    private TextView mPhoneView;
    private TextView mEmailView;
    private TextView mDataNascita;
    private TextView mLuogoNascita;
    private CheckBox mCheckSistemi;
    private CheckBox mCheckGestionale;
    private CheckBox mCheckProduzione;
    private CheckBox mCheckCommerciale;
    private CheckBox mCheckAmministrazione;
    private CheckBox mCheckCapoProgetto;
    private CheckBox mCheckConsulente;
    private TextView mAbilitatoTv;

    AlertDialog mDeleteDialog;

    private UserInfo mUserToView;

    private RequestQueue mRequestQueue;

    AlertDialog.Builder deleteDialogBuilder;

    AlertDialog.Builder firstDeleteDialogBuilder;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza);

        //Permette landscape e portrait solo se è un tablet
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable actionBarBack = getDrawable(R.drawable.actionbar_sistemi);
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
            getSupportActionBar().setIcon(R.drawable.ic_accessibility_white_24dp);
        }


        mNameView = (TextView) findViewById(R.id.visualizza_nome);
        mSurnameView = (TextView) findViewById(R.id.visualizza_cognome);
        mPhoneView = (TextView) findViewById(R.id.visualizza_phone);
        mEmailView = (TextView) findViewById(R.id.visualizza_email);
        mLuogoNascita = (TextView) findViewById(R.id.visualizza_luogo_nascita);
        mDataNascita = (TextView) findViewById(R.id.visualizza_data_nascita);

        mCheckProduzione = (CheckBox) findViewById(R.id.visualizza_check_produzione);
        mCheckSistemi = (CheckBox) findViewById(R.id.visualizza_check_sistemi);
        mCheckAmministrazione = (CheckBox) findViewById(R.id.visualizza_check_amministrazione);
        mCheckCommerciale = (CheckBox) findViewById(R.id.visualizza_check_commerciale);
        mCheckGestionale = (CheckBox) findViewById(R.id.visualizza_check_gestionale);
        mCheckConsulente = (CheckBox) findViewById(R.id.visualizza_check_consulente);
        mCheckCapoProgetto = (CheckBox) findViewById(R.id.visualizza_check_capo_progett);
        mAbilitatoTv = (TextView) findViewById(R.id.visualizza_abilitato);


        Intent intent = getIntent();

        final UserInfo userToView = intent.getParcelableExtra("userToView");

        if (userToView != null) {
            mUserToView = userToView;
            setupUserInfo(userToView);
        }


        BootstrapButton eliminaButton = (BootstrapButton) findViewById(R.id.visualizza_button_elimina);
        BootstrapButton modificaButton = (BootstrapButton) findViewById(R.id.visualizza_button_modifica);
        BootstrapButton stampaButton = (BootstrapButton) findViewById(R.id.visualizza_button_stampa);

        eliminaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAlert();
            }
        });

        modificaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modificaIntent = new Intent(getApplicationContext(), ModificaUtenteDialog.class);
                modificaIntent.putExtra("userToModify", userToView);
                startActivityForResult(modificaIntent, REQUEST_MODIFY);
            }
        });

        stampaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SistemiUtils.print(userToView, getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        deleteDialogBuilder = new AlertDialog.Builder(this);

        firstDeleteDialogBuilder = new AlertDialog.Builder(this);

        mRequestQueue = Volley.newRequestQueue(this);


        System.out.println(userToView.toJson());

    }


    private void setupUserInfo(UserInfo user) {
        if (!user.getNome().equals("null"))
            mNameView.setText(user.getNome());
        else
            mNameView.setText(" ");

        if (!user.getCognome().equals("null"))
            mSurnameView.setText(user.getCognome());
        else
            mSurnameView.setText(" ");


        if (user.getPhone() != null)
            mPhoneView.setText(user.getPhone());


        if (user.getEmail() != null && !user.getEmail().equals("null"))
            mEmailView.setText(user.getEmail());
        else
            mEmailView.setText("");

        if (user.getDataNascita() != null && !user.getDataNascita().equals("null"))
            mDataNascita.setText(user.getDataNascita());
        else
            mDataNascita.setText(" ");

        if (user.getLuogoNascita() != null && !user.getLuogoNascita().equals("null"))
            mLuogoNascita.setText(user.getLuogoNascita());
        else
            mLuogoNascita.setText(" ");

        if (user.isAmministratore())
            mCheckAmministrazione.setChecked(true);
        if (user.isSistemi())
            mCheckSistemi.setChecked(true);
        if (user.isCommerciale())
            mCheckCommerciale.setChecked(true);
        if (user.isGestionale())
            mCheckGestionale.setChecked(true);
        if (user.isProduzione())
            mCheckProduzione.setChecked(true);
        if (user.isCapoProgetto())
            mCheckCapoProgetto.setChecked(true);
        if (user.isConsulente())
            mCheckConsulente.setChecked(true);
        if (user.isAbilitato()) {
            mAbilitatoTv.setText(R.string.is_abilitato);
        } else {
            mAbilitatoTv.setText(R.string.not_abilitato);
        }

    }

    private void attemptDelete() {
        String url = getString(R.string.mobile_url);
        url += "accessi/" + mUserToView.getID();

        PUTRequest deleteRequestJson = new PUTRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean errore = response.getBoolean("error");
                    if (errore) {
                        showError(true);
                    } else {
                        showError(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError(true);
            }
        });

        mRequestQueue.add(deleteRequestJson);
    }

    public void showError(boolean error) {
        if (error) {
            deleteDialogBuilder.setTitle("Errore modifica dati");
            deleteDialogBuilder.setMessage("Si è verificato un errore nella modifica dei dati");
            deleteDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    return;
                }
            });
            AlertDialog dialog = deleteDialogBuilder.create();
            dialog.show();
        } else {
            deleteDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    return;
                }
            });
            AlertDialog dialog = deleteDialogBuilder.create();
            dialog.setTitle("Successo!");
            dialog.setMessage("Eliminazione avvenuta con successo");
            dialog.show();
        }
    }

    private void showDeleteAlert() {
        firstDeleteDialogBuilder.setTitle("Sicuro di voler eliminare?");
        firstDeleteDialogBuilder.setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                attemptDelete();
            }
        });

        firstDeleteDialogBuilder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        mDeleteDialog = firstDeleteDialogBuilder.create();
        mDeleteDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MODIFY) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }
}




