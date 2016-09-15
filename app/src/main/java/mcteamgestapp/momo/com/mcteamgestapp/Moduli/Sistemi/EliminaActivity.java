package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;
import mcteamgestapp.momo.com.mcteamgestapp.NetworkReq.PUTRequest;
import mcteamgestapp.momo.com.mcteamgestapp.R;

public class EliminaActivity extends AppCompatActivity {

    private TextView mNameView;
    private TextView mSurnameView;
    private TextView mEmailView;
    private TextView mAbilitatoTv;
    private TextView mDataNascitaTv;
    private TextView mLuogoNascita;
    private TextView mPhoneView;

    private CheckBox mCheckSistemi;
    private CheckBox mCheckGestionale;
    private CheckBox mCheckProduzione;
    private CheckBox mCheckCommerciale;
    private CheckBox mCheckAmministrazione;
    private CheckBox mCheckCapoProgetto;
    private CheckBox mCheckConsulente;
    AlertDialog.Builder deleteDialogBuilder;

    AlertDialog mDeleteDialog;


    private RequestQueue mRequestQueue;


    private UserInfo mUserToDelete;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elimina);

        //Permette landscape e portrait solo se è un tablet
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable actionBarBack = getDrawable(R.drawable.actionbar_sistemi);
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
            getSupportActionBar().setIcon(R.drawable.ic_accessibility_white_24dp);
        }

        mNameView = (TextView) findViewById(R.id.elimina_activity_name);
        mSurnameView = (TextView) findViewById(R.id.elimina_cognome);
        mEmailView = (TextView) findViewById(R.id.elimina_email);
        mAbilitatoTv = (TextView) findViewById(R.id.elimina_abilitato);
        mDataNascitaTv = (TextView) findViewById(R.id.elimina_data_nascita);
        mLuogoNascita = (TextView) findViewById(R.id.elimina_luogo_nascita);
        mPhoneView = (TextView) findViewById(R.id.elimina_phone);

        mCheckProduzione = (CheckBox) findViewById(R.id.elimina_check_produzione);

        mCheckSistemi = (CheckBox) findViewById(R.id.elimina_check_sistemi);
        mCheckAmministrazione = (CheckBox) findViewById(R.id.elimina_check_amministrazione);
        mCheckCommerciale = (CheckBox) findViewById(R.id.elimina_check_commerciale);
        mCheckGestionale = (CheckBox) findViewById(R.id.elimina_check_gestionale);
        mCheckConsulente = (CheckBox) findViewById(R.id.elimina_check_consulente);
        mCheckCapoProgetto = (CheckBox) findViewById(R.id.elimina_check_capo_progetto);

        deleteDialogBuilder = new AlertDialog.Builder(this);


        mRequestQueue = Volley.newRequestQueue(this);

        mUserToDelete = getIntent().getParcelableExtra("userToDelete");

        if (mUserToDelete != null) {
            setupUserInfo();
        } else {
            Toast.makeText(getApplicationContext(), "i dati sono nulli", Toast.LENGTH_LONG).show();
        }

        Button mModificaButton = (Button) findViewById(R.id.elimina_button_elimina);

        mModificaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAlert();
            }
        });

        Button mAnnullaButton = (Button) findViewById(R.id.elimina_button_annulla);

        mAnnullaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showDeleteAlert() {
        deleteDialogBuilder.setTitle("Sicuro di voler eliminare?");
        deleteDialogBuilder.setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                attemptDelete();
            }
        });

        deleteDialogBuilder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        mDeleteDialog = deleteDialogBuilder.create();
        mDeleteDialog.show();
    }

    private void setupUserInfo() {

        if (!mUserToDelete.getNome().equals("null"))
            mNameView.setText(mUserToDelete.getNome());
        else
            mNameView.setText(" ");

        if (!mUserToDelete.getCognome().equals("null"))
            mSurnameView.setText(mUserToDelete.getCognome());
        else
            mSurnameView.setText(" ");

        if (!mUserToDelete.getPhone().equals("null"))
            mPhoneView.setText(mUserToDelete.getPhone());
        else
            mPhoneView.setText(" ");

        if (!mUserToDelete.getEmail().equals("null"))
            mEmailView.setText(mUserToDelete.getEmail());
        else
            mEmailView.setText("");

        if (!mUserToDelete.getDataNascita().equals("null"))
            mDataNascitaTv.setText(mUserToDelete.getDataNascita());
        else
            mDataNascitaTv.setText(" ");

        if (!mUserToDelete.getLuogoNascita().equals("null"))
            mLuogoNascita.setText(mUserToDelete.getLuogoNascita());
        else
            mLuogoNascita.setText(" ");


        if (this.mUserToDelete.isAmministratore())
            mCheckAmministrazione.setChecked(true);
        if (this.mUserToDelete.isSistemi())
            mCheckSistemi.setChecked(true);
        if (this.mUserToDelete.isCommerciale())
            mCheckCommerciale.setChecked(true);
        if (this.mUserToDelete.isGestionale())
            mCheckGestionale.setChecked(true);
        if (this.mUserToDelete.isProduzione())
            mCheckProduzione.setChecked(true);
        if (this.mUserToDelete.isCapoProgetto())
            mCheckCapoProgetto.setChecked(true);
        if (this.mUserToDelete.isConsulente())
            mCheckConsulente.setChecked(true);

        if (this.mUserToDelete.isAbilitato()) {
            mAbilitatoTv.setText(R.string.is_abilitato);
        } else {
            mAbilitatoTv.setText(R.string.not_abilitato);
        }
    }

    private void attemptDelete() {
        String url = getString(R.string.mobile_url);
        url += "accessi/" + mUserToDelete.getID();

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
            dialog.setMessage("Modifica dati avvenuta con successo");
            dialog.show();
        }
    }
}
