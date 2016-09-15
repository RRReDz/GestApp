package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;
import mcteamgestapp.momo.com.mcteamgestapp.R;

public class StampaAccessiActivity extends AppCompatActivity {

    private static final String TAG = StampaAccessiActivity.class.getSimpleName();
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

    private UserInfo mUserToView;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stampa_accessi);

        //Permette landscape e portrait solo se è un tablet
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            Drawable actionBarBack = getDrawable(R.drawable.actionbar_sistemi);
            getSupportActionBar().setBackgroundDrawable(actionBarBack);
            getSupportActionBar().setIcon(R.drawable.ic_accessibility_white_24dp);
        }

        mNameView = (TextView) findViewById(R.id.accesso_stampa_nome);
        mSurnameView = (TextView) findViewById(R.id.accesso_stampa_cognome);
        mPhoneView = (TextView) findViewById(R.id.accesso_stampa_telefono);
        mEmailView = (TextView) findViewById(R.id.accesso_stampa_email);
        mDataNascita = (TextView) findViewById(R.id.stampa_accesso_data_nascita);
        mLuogoNascita = (TextView) findViewById(R.id.stampa_nascita_luogo_nascita);

        mCheckProduzione = (CheckBox) findViewById(R.id.check_stampa_produzione);
        mCheckSistemi = (CheckBox) findViewById(R.id.check_stampa_sistemi);
        mCheckAmministrazione = (CheckBox) findViewById(R.id.check_stampa_amministrazione);
        mCheckCommerciale = (CheckBox) findViewById(R.id.check_stampa_commerciale);
        mCheckGestionale = (CheckBox) findViewById(R.id.check_stampa_gestionale);
        mCheckConsulente = (CheckBox) findViewById(R.id.check_stampa_consulente);
        mCheckCapoProgetto = (CheckBox) findViewById(R.id.check_stampa_capo_progetto);
        mAbilitatoTv = (TextView) findViewById(R.id.stampa_abilitato_tv);

        Intent intent = getIntent();

        final UserInfo userToPrint = intent.getParcelableExtra("userToPrint");

        Button stampa = (Button) findViewById(R.id.accesso_stampa_button);
        Button annulla = (Button) findViewById(R.id.accesso_stampa_annulla);

        stampa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SistemiUtils.print(userToPrint, getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setupView(userToPrint);

    }

    private void setupView(UserInfo user) {
        if (!user.getNome().equals("null"))
            mNameView.setText(user.getNome());
        else
            mNameView.setText(" ");

        if (!user.getCognome().equals("null"))
            mSurnameView.setText(user.getCognome());
        else
            mSurnameView.setText(" ");

        if (!user.getPhone().equals("null"))
            mPhoneView.setText(user.getPhone());
        else
            mPhoneView.setText(" ");

        if (!user.getEmail().equals("null"))
            mEmailView.setText(user.getEmail());
        else
            mEmailView.setText("");

        if (!user.getDataNascita().equals("null"))
            mDataNascita.setText(user.getDataNascita());
        else
            mDataNascita.setText(" ");

        if (!user.getLuogoNascita().equals("null"))
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


}
