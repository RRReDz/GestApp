package com.mcteam.gestapp.Moduli.Produzione.Consuntivi;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mcteam.gestapp.Models.Associazione;
import com.mcteam.gestapp.Models.Ferie;
import com.mcteam.gestapp.Models.OrariAttivita;
import com.mcteam.gestapp.Models.UserInfo;
import com.mcteam.gestapp.NetworkReq.CustomRequest;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.Functions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Riccardo Rossi on 09/02/2016.
 */
public class MonthCalendarFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_MONTH_NUMBER = "month_number";
    private static final String ARG_ACTUAL_USER = "actual_user";
    private static final String ARG_ACTUAL_DATE = "actual_date";
    private static final String ARG_MONTH_LIST = "associazioni";
    private static final int MODIFY_ATTIVITA = 664;

    ListView mCalendarList;
    ConsuntiviCalendarioAdapter mCalendarAdapter;

    ArrayList<OrariAttivita> days = new ArrayList<>();

    ArrayList<OrariAttivita> attivitaMensili;

    ArrayList<Associazione> mAssociazioni;

    ArrayList<Ferie> mFerie;

    RequestQueue mRequestQueue;

    Date actualDate;

    Gson gson;

    Calendar today;


    UserInfo mActualUser;

    ProgressBar mProgressBar;


    public MonthCalendarFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MonthCalendarFragment newInstance(int mese, Date date, ArrayList<Associazione> mAssociazioni, UserInfo user) {
        MonthCalendarFragment fragment = new MonthCalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MONTH_NUMBER, mese);
        args.putSerializable(ARG_ACTUAL_DATE, date);
        args.putParcelableArrayList(ARG_MONTH_LIST, mAssociazioni);
        args.putParcelable(ARG_ACTUAL_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_consuntivi_main, container, false);

        // set up ListView per la lista dei giorni
        mCalendarList = (ListView) rootView.findViewById(R.id.calendar_day_list);

        FloatingActionButton stampaButton = (FloatingActionButton) rootView.findViewById(R.id.fab_consuntivo_stampa_tutto);


        // get progressbar
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressbar);

        // inizializzare l'adapeter
        mCalendarAdapter = new ConsuntiviCalendarioAdapter(getActivity(), days);

        //Ottieni la data attuale
        actualDate = (Date) getArguments().getSerializable(ARG_ACTUAL_DATE);

        mAssociazioni = getArguments().getParcelableArrayList(ARG_MONTH_LIST);

        mActualUser = getArguments().getParcelable(ARG_ACTUAL_USER);

        attivitaMensili = new ArrayList<>();
        mFerie = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(getActivity());

        gson = new Gson();

        //On item clicked listener
        //mCalendarList.setOnItemClickListener((ConsuntiviMainActivity)getActivity());
        mCalendarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrariAttivita selectedOrario = (OrariAttivita) parent.getItemAtPosition(position);
                Intent openDayDialog = new Intent(getActivity(), DayConsuntivoDialog.class);
                openDayDialog.putExtra("orarioAttivita", selectedOrario);
                openDayDialog.putExtra("associazioniList", mAssociazioni);
                startActivityForResult(openDayDialog, MODIFY_ATTIVITA);
            }
        });


        today = Calendar.getInstance();

        today.setTime(actualDate);

        //setupDays();
        getFerie(mActualUser.getID());

        stampaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ConsuntivoUtils.printSimple(mAssociazioni, days, getActivity().getApplicationContext(), mActualUser);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

    /**
     * Ottiene la data attuale e ritorna tutti i giorni del mese dalla data attuale
     */
    public void setupDays() {

        //if(days.isEmpty()) {
        days.clear();
        int day_number = today.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MMM");
        today.set(Calendar.DATE, 1);
        for (int i = 1; i <= day_number; i++) {
            OrariAttivita data = new OrariAttivita();
            data.setId_utente(mActualUser.getID());

            //Trasformare in sql date
            java.sql.Date sqldate = new java.sql.Date(today.getTimeInMillis());
            data.setGiorno(sqldate.toString());

            data.setDay(i);
            data.setMonth(today.get(Calendar.MONTH) + 1);

            data.setDay_of_week(today.get(Calendar.DAY_OF_WEEK));
            Date date = today.getTime();
            String day_name = sdf.format(date);
            data.setDay_name(day_name);
            data.setMonth_name(sdfMonth.format(date));

            int indice;

            if (!attivitaMensili.isEmpty()) {
                if ((indice = attivitaMensili.indexOf(data)) > -1) {
                    OrariAttivita temp = attivitaMensili.get(indice);
                    if (temp.getOre_totali() == 0.5) {
                        int occurence = Collections.frequency(attivitaMensili, temp);
                        if (occurence == 2) {
                            for (OrariAttivita other : attivitaMensili) {
                                if (other.equals(temp) && other.getCommessa() != temp.getCommessa()) {
                                    data.setOtherHalf(other);
                                    data.getOtherHalf().setModifica(true);
                                }
                            }

                        }
                    }
                    data.setFromOld(temp);
                    data.setModifica(true);
                }
            }
            isFerie(data);


            //Aggiungi la data alla lista
            days.add(data);

            //Aggiugni un giorno alla data attuale
            today.add(Calendar.DATE, 1);
        }

        today.setTime(actualDate);

        mCalendarAdapter.notifyDataSetChanged();
        mCalendarList.setAdapter(mCalendarAdapter);
        showProgress(false);
    }

    private boolean isFerie(OrariAttivita data) {
        for (Ferie ferie : mFerie) {
            if (data.getGiorno().equals(ferie.getData())) {
                data.setFerie(true);
            }
        }
        return false;
    }


    /**
     * Ottiene tutte i Orari attività dalla tabella [orari_attivita]
     * in cui l'utente attuale è coinvolto
     *
     * @param id
     */
    private void getAttivita(int id, int month, int year) {
        String url = getString(R.string.mobile_url);
        url += "/orari_attivita/" + id + "/" + month + "/" + year;

        attivitaMensili.clear();
        CustomRequest accessiRequest = new CustomRequest(url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                try {

                    for (int i = 0; i < responseArray.length(); i++) {

                        JSONObject response = responseArray.getJSONObject(i);

                        OrariAttivita attivita = gson.fromJson(response.toString(), OrariAttivita.class);

                        setupDate(attivita);

                        attivitaMensili.add(attivita);
                    }
                    setupDays();
                } catch (JSONException e) {

                    e.printStackTrace();
                    Log.d("Err resp getAttivita", e.getMessage());
                    Toast.makeText(getActivity(),
                            "Err resp getAttivita: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Err. orari_attivita", error.getMessage());
                Toast.makeText(getActivity(),
                        "Err. orari_attivita: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        });

        mRequestQueue.add(accessiRequest);
    }

    /**
     * Ottiene tutte le ferie per un determinato utente dalla tabella [orari_attivita]
     * in cui l'utente attuale è coinvolto
     *
     * @param id
     */
    private void getFerie(int id) {
        String url = getString(R.string.mobile_url);
        url += "/ferie/" + id;

        attivitaMensili.clear();
        CustomRequest accessiRequest = new CustomRequest(url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                /*Log.d("Ferie", responseArray.toString());*/
                try {

                    for (int i = 0; i < responseArray.length(); i++) {

                        JSONObject response = responseArray.getJSONObject(i);

                        Ferie ferie = gson.fromJson(response.toString(), Ferie.class);

                        mFerie.add(ferie);
                    }
                    getAttivita(mActualUser.getID(), today.get(Calendar.MONTH) + 1, today.get(Calendar.YEAR));
                } catch (JSONException e) {

                    e.printStackTrace();
                    Log.d("Err resp getFerie", e.getMessage());
                    Toast.makeText(getActivity(),
                            "Err resp getFerie: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Err. req ferie", error.getMessage());
                Toast.makeText(getActivity(),
                        "Err. req ferie: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        });

        mRequestQueue.add(accessiRequest);
    }

    private void setupDate(OrariAttivita attivita) {
        String sqlDate = attivita.getGiorno();

        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Calendar calendar = Calendar.getInstance();

        try {
            Date date = sdf.parse(Functions.getFormattedDate(sqlDate));
            calendar.setTime(date);
            attivita.setDay(calendar.get(Calendar.DATE));
            attivita.setMonth(calendar.get(Calendar.MONTH));
            attivita.setYear(calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void showProgress(boolean show) {
        if (show) {
            mCalendarList.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mCalendarList.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MODIFY_ATTIVITA && resultCode == Activity.RESULT_OK) {
            getAttivita(mActualUser.getID(), today.get(Calendar.MONTH) + 1, today.get(Calendar.YEAR));
        }
    }
}
