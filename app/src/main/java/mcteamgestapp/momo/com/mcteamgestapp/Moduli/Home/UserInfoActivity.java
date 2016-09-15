package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Home;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;
import mcteamgestapp.momo.com.mcteamgestapp.Application.MyApp;
import mcteamgestapp.momo.com.mcteamgestapp.R;

public class UserInfoActivity extends AppCompatActivity {


    private TextView mNameView;
    private TextView mSurnameView;
    private TextView mPhoneView;
    private TextView mPlaceView;
    private TextView mEmailView;

    private UserInfo mCurrentUser;

    private LinearLayout mMiniSistemi;
    private LinearLayout mMiniProduzione;
    private LinearLayout mMiniCommerciale;
    private LinearLayout mMiniAmministrazione;
    private LinearLayout mMiniGestionale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        //Permette landscape e portrait solo se è un tablet
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        mCurrentUser = ((MyApp) getApplication()).getCurrentUser();

        if (mCurrentUser == null)
            finish();

        mNameView = (TextView) findViewById(R.id.info_tv_name);
        mSurnameView = (TextView) findViewById(R.id.info_tv_surname);
        mPhoneView = (TextView) findViewById(R.id.info_tv_phone);
        mPlaceView = (TextView) findViewById(R.id.info_tv_luogo);
        mEmailView = (TextView) findViewById(R.id.info_tv_email);

        mNameView.setText(mCurrentUser.getNome().equals("null") ? "" : mCurrentUser.getNome());
        mSurnameView.setText(mCurrentUser.getCognome().equals("null") ? "" : mCurrentUser.getCognome());
        mPhoneView.setText(mCurrentUser.getPhone().equals("null") ? "" : mCurrentUser.getPhone());
        mPlaceView.setText(mCurrentUser.getLuogoNascita().equals("null") ? "" : mCurrentUser.getLuogoNascita());
        mEmailView.setText(mCurrentUser.getEmail().equals("null") ? "" : mCurrentUser.getEmail());

        mMiniSistemi = (LinearLayout) findViewById(R.id.info_mini_accessi);
        mMiniProduzione = (LinearLayout) findViewById(R.id.info_mini_produzione);
        mMiniCommerciale = (LinearLayout) findViewById(R.id.info_mini_commerciale);
        mMiniAmministrazione = (LinearLayout) findViewById(R.id.info_mini_amministrazione);
        mMiniGestionale = (LinearLayout) findViewById(R.id.info_mini_gestionale);

        if (!mCurrentUser.isGestionale()) {
            mMiniGestionale.setVisibility(View.GONE);
        }
        if (!mCurrentUser.isProduzione()) {
            mMiniProduzione.setVisibility(View.GONE);
        }

        if (!mCurrentUser.isCommerciale()) {
            mMiniCommerciale.setVisibility(View.GONE);
        }
        if (!mCurrentUser.isAmministratore()) {
            mMiniAmministrazione.setVisibility(View.GONE);
        }
        if (!mCurrentUser.isSistemi()) {
            mMiniSistemi.setVisibility(View.GONE);
        }
    }
}
