package mcteamgestapp.momo.com.mcteamgestapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Parcel;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.apache.poi.util.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Allegato;
import mcteamgestapp.momo.com.mcteamgestapp.Models.PrimaNota.NotaBanca;
import mcteamgestapp.momo.com.mcteamgestapp.Models.PrimaNota.NotaCassa;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Nominativo;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Societa;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Amministrazione.PrimaNotaBanca.PrimaNotaBancaActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Amministrazione.PrimaNotaBanca.PrimaNotaBancaRecyclerAdapter;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Amministrazione.PrimaNotaCassa.PrimaNotaCassaActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Amministrazione.PrimaNotaCassa.PrimaNotaCassaRecyclerAdapter;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Allegati.AllegatiListAdapter;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Nominativo.RubricaNominativaListAdapter;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Nominativo.SocietaSpinnerAdapter;


/**
 * Created by meddaakouri on 18/12/2015.
 */
public class VolleyRequests {

    Context mContext;
    RequestQueue mRequestQueue;
    Gson gson;
    AlertDialog.Builder dialogBuilder;
    Activity mActivity;
    boolean isShowing = false;

    public VolleyRequests(Context context, Activity activity) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(context);
        gson = new Gson();
        dialogBuilder = new AlertDialog.Builder(context);
        mActivity = activity;
    }

    public void getNominativiList(final ArrayList<Nominativo> list, final NominativoSpinnerAdapter adapter) {
        String url = mContext.getString(R.string.mobile_url);
        url += "rubrica-nominativi";

        CustomRequest accessiRequest = new CustomRequest(url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                try {
                    Log.i("SistemiActivity.class", " " + responseArray.length());
                    // Parsing json object response
                    // response will be a json object
                    ArrayList<Nominativo> societas = new ArrayList<>();
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject response = responseArray.getJSONObject(i);
                        System.out.println(response);
                        Nominativo nominativo = gson.fromJson(response.toString(), Nominativo.class);
                        societas.add(nominativo);
                    }
                    list.addAll(societas);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {

                    e.printStackTrace();
                    Log.i("LoginResponse", e.getMessage());

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Errore richiesta Volley", "Error: " + error.getMessage());
                // hide the progress dialog
            }
        });

        mRequestQueue.add(accessiRequest);
    }

    public void getSocietaList(final ArrayList<Societa> list, final SocietaSpinnerAdapter adapter) {
        String url = mContext.getString(R.string.mobile_url);
        url += "rubrica-societa";

        CustomRequest accessiRequest = new CustomRequest(url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                try {
                    Log.i("SistemiActivity.class", " " + responseArray.length());
                    // Parsing json object response
                    // response will be a json object
                    ArrayList<Societa> societas = new ArrayList<>();
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject response = responseArray.getJSONObject(i);
                        Societa societa = gson.fromJson(response.toString(), Societa.class);
                        societas.add(societa);
                    }
                    list.addAll(societas);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {

                    e.printStackTrace();
                    Log.i("LoginResponse", e.getMessage());

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Errore richiesta Volley", "Error: " + error.getMessage());
                // hide the progress dialog
            }
        });

        mRequestQueue.add(accessiRequest);
    }

    public void addNewElementRequest(String json, String route) {
        String url = mContext.getString(R.string.mobile_url);
        url += route;

        System.out.println("The url -> " + url);

        HashMap params = new HashMap();
        params.put("data", json);

        System.out.println(json);

        dialogBuilder.setTitle("Inserimento nuovo valore");

        PUTRequest modifyRequestJson = new PUTRequest(Request.Method.PUT, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean errore = response.getBoolean("error");
                    if (errore) {
                        try {
                            String error_type = response.getString("error_type");
                            showError(true, error_type);

                        } catch (JSONException e) {
                            showError(true);
                        }
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

        modifyRequestJson.setShouldCache(false);
        mRequestQueue.add(modifyRequestJson);

    }

    public void addNewElementRequestNoAlert(String json, String route) {
        String url = mContext.getString(R.string.mobile_url);
        url += route;

        HashMap params = new HashMap();
        params.put("data", json);

        System.out.println(json);

        dialogBuilder.setTitle("Modifica nominativo");

        PUTRequest modifyRequestJson = new PUTRequest(Request.Method.PUT, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("ADD NEW ELEMENT", response.toString());
                /*
                try {
                    boolean errore = response.getBoolean("error");
                    if(errore) {
                        try {
                            String error_type = response.getString("error_type");
                            showError(true,error_type);

                        } catch (JSONException e) {
                            showError(true);
                        }
                    }else{
                        showError(false);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError(true);
            }
        });


        mRequestQueue.add(modifyRequestJson);

    }

    private void showError(boolean error) {
        dialogBuilder = new AlertDialog.Builder(mContext);
        if (error) {
            dialogBuilder.setTitle("Errore");
            dialogBuilder.setMessage("Operazione fallita");
            dialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isShowing = false;
                    return;
                }
            });
            AlertDialog dialog = dialogBuilder.create();
            if (!isShowing) {
                dialog.show();
                isShowing = true;
            } else
                dialog.dismiss();
        } else {
            dialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent data = new Intent();
                    if (mActivity.getParent() == null) {
                        mActivity.setResult(Activity.RESULT_OK, data);
                    } else {
                        mActivity.getParent().setResult(Activity.RESULT_OK, data);
                    }
                    mActivity.finish();
                    return;
                }
            });
            AlertDialog dialog = dialogBuilder.create();
            if (!isShowing) {
                dialog.setTitle("Successo!");
                dialog.setMessage("Operazione avvenuto con successo");
                dialog.show();
                isShowing = true;
            } else
                dialog.dismiss();
        }
    }

    private void showError(boolean error, String type) {
        dialogBuilder = new AlertDialog.Builder(mContext);
        if (error) {
            dialogBuilder.setTitle("Errore");
            dialogBuilder.setMessage(type);
            dialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isShowing = false;
                    return;
                }
            });
            AlertDialog dialog = dialogBuilder.create();
            if (!isShowing) {
                dialog.show();
                isShowing = true;
            } else {
                dialog.dismiss();
            }
        } else {
            dialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent data = new Intent();
                    if (mActivity.getParent() == null) {
                        mActivity.setResult(Activity.RESULT_OK, data);
                    } else {
                        mActivity.getParent().setResult(Activity.RESULT_OK, data);
                    }
                    mActivity.finish();
                    return;
                }
            });
            AlertDialog dialog = dialogBuilder.create();
            dialog.setTitle("Successo!");
            dialog.setMessage("Operazione avvenuto con successo");
            if (!isShowing) {
                dialog.show();
                isShowing = true;
            }
        }
    }

    public void deleteElement(final String path) {
        dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setTitle("Sicuro di voler eliminare?");
        dialogBuilder.setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                attemptDelete(path);
            }
        });

        dialogBuilder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        AlertDialog mDeleteDialog = dialogBuilder.create();
        mDeleteDialog.show();
    }

    private void attemptDelete(String path) {
        String url = mContext.getString(R.string.mobile_url);
        url += path;

        PUTRequest deleteRequestJson = new PUTRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean errore = response.getBoolean("error");
                    if (errore) {
                        try {
                            String error_type = response.getString("error_type");
                            showError(true, error_type);

                        } catch (JSONException e) {
                            showError(true);
                        }
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

    public void getAllegatiList(final ArrayList<Allegato> list, final AllegatiListAdapter adapter) {
        String url = mContext.getString(R.string.mobile_url);
        url += "allegati";

        CustomRequest accessiRequest = new CustomRequest(url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                try {
                    // response will be a json object
                    ArrayList<Allegato> allegati = new ArrayList<>();
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject response = responseArray.getJSONObject(i);
                        System.out.println(response.toString());
                        Allegato allegato = gson.fromJson(response.toString(), Allegato.class);
                        allegati.add(allegato);
                    }
                    list.addAll(allegati);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {

                    e.printStackTrace();
                    Log.i("LoginResponse", e.getMessage());

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Errore richiesta Volley", "Error: " + error.getMessage());
                // hide the progress dialog
            }
        });

        mRequestQueue.add(accessiRequest);
    }

    String twoHyphens = "--";
    String lineEnd = "\r\n";
    String boundary = "apiclient-" + System.currentTimeMillis();
    String mimeType = "multipart/form-data;boundary=" + boundary;
    byte[] multipartBody;

    public void uploadFile(File file, String description) throws IOException {

        System.out.println("inside upladFile");

        String url = mContext.getString(R.string.mobile_url);
        url += "allegato/upload";

        byte[] bDescription = description.getBytes();

        byte[] bfile = fullyReadFileToBytes(file);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        buildFilePart(dos, bfile, file.getName());

        buildTextPart(dos, bDescription);

        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

        multipartBody = baos.toByteArray();

        MultipartRequest multipartRequest = new MultipartRequest(url, null, mimeType, multipartBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(response.toString());

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
                System.out.println(error.toString());
            }
        });

        mRequestQueue.add(multipartRequest);
    }

    public void getPrimaNotaCassaList(final ArrayList<NotaCassa> mNotaCassa, final PrimaNotaCassaRecyclerAdapter adapter, int month, int year, int opType) {

        String url = mActivity.getString(R.string.mobile_url);
        url += "prima-nota-cassa/" + (month + 1) + "/" + year + "/" + opType;

        mNotaCassa.clear(); //Pulisco l'arraylist

        System.out.println("the url -> " + url);

        final CustomRequest accessiRequest = new CustomRequest(url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        PrimaNotaCassaActivity activityPrimaNota = (PrimaNotaCassaActivity) mContext;
                        try {
                            if (jsonArray.length() == 0) {
                                activityPrimaNota.showEmptyString(true);
                            } else {
                                activityPrimaNota.showEmptyString(false);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject response = jsonArray.getJSONObject(i);
                                    System.out.println(response.toString());
                                    //NotaCassa notaCassa = gson.fromJson(response.toString(), NotaCassa.class);

                                    NotaCassa notaCassa = new NotaCassa();
                                    notaCassa.setID(response.getInt("riga"));
                                    notaCassa.setCassa(response.getInt("cassa"));
                                    //notaCassa.setDataPagamento(response.getString("data_pagamento"));
                                    String dataString = response.getString("data_pagamento");
                                    notaCassa.setDataPagamento(ToolUtils.validateReverseDate(dataString) ? ToolUtils.getFormattedDate(dataString) : "");


                                    if (response.get("cod_dare").equals("") || response.get("cod_dare") == null)
                                        notaCassa.setCausaleContabile(""); //Se nullo o vuoto nel database -> il campo resta vuoto
                                    else
                                        notaCassa.setCausaleContabile(response.getString("cod_dare"));

                                    if (response.get("cod_avere").equals("") || response.get("cod_avere") == null)
                                        notaCassa.setSottoconto(""); //Se nullo o vuoto nel database -> il campo resta vuoto
                                    else
                                        notaCassa.setSottoconto(response.getString("cod_avere"));

                                    notaCassa.setDescrizione(response.getString("descrizione"));

                                    if (response.get("nr_protocollo").equals(null))
                                        notaCassa.setNumeroProtocollo(0); //Non presente
                                    else
                                        notaCassa.setNumeroProtocollo(response.getInt("nr_protocollo"));

                                    //Check se campo dare è vuoto -> problema decoding
                                    if (response.get("dare").equals("") || response.get("dare").equals(null))
                                        notaCassa.setDare(0);
                                    else
                                        notaCassa.setDare(ToolUtils.parse(response.getString("dare")));


                                    if (response.get("avere").equals("") || response.get("avere").equals(null))
                                        notaCassa.setAvere(0);
                                    else
                                        notaCassa.setAvere(ToolUtils.parse(response.getString("avere")));

                                    mNotaCassa.add(notaCassa);
                                }
                                adapter.notifyDataSetChanged();
                            }

                            //Recupero le view per la progressbar
                            PrimaNotaCassaActivity activity = (PrimaNotaCassaActivity) mActivity;
                            //activity.iconRefresh(true);
                            ProgressBar progressBar = (ProgressBar) mActivity.findViewById(R.id.prima_nota_cassa_progress);
                            RecyclerView recyclerView = (RecyclerView) mActivity.findViewById(R.id.simpleRecyclerView);
                            ToolUtils.showProgress(recyclerView, progressBar, false);


                        } catch (JSONException jsonEx) {
                            jsonEx.printStackTrace();
                        }
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // As of f605da3 the following should work
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                System.out.println(res);
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            }
                        }
                    }
                });

        accessiRequest.setShouldCache(false);
        mRequestQueue.add(accessiRequest);
    }

    public void getPrimaNotaBancaList(final ArrayList<NotaBanca> mNotaBanca, final PrimaNotaBancaRecyclerAdapter adapter, int month, int year) {
        String url = mActivity.getString(R.string.mobile_url);
        url += "prima-nota-banca/" + (month + 1) + "/" + year;

        mNotaBanca.clear(); //Pulisco l'arraylist

        System.out.println("the url -> " + url);

        final CustomRequest accessiRequest = new CustomRequest(url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        PrimaNotaBancaActivity activityPrimaNota = (PrimaNotaBancaActivity) mContext;
                        try {
                            if (jsonArray.length() == 0) {
                                activityPrimaNota.showEmptyString(true);
                            } else {
                                activityPrimaNota.showEmptyString(false);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject response = jsonArray.getJSONObject(i);
                                    System.out.println(response.toString());
                                    //NotaCassa notaCassa = gson.fromJson(response.toString(), NotaCassa.class);

                                    NotaBanca notaBanca = new NotaBanca();
                                    notaBanca.setID(response.getInt("riga"));
                                    notaBanca.setGruppo(response.getInt("gruppo"));

                                    String dataOpString = response.getString("data_pagamento");
                                    notaBanca.setDataPagamento(ToolUtils.validateReverseDate(dataOpString) ? ToolUtils.getFormattedDate(dataOpString) : "");

                                    String dataValString = response.getString("data_valuta");
                                    notaBanca.setDataValuta(ToolUtils.validateReverseDate(dataValString) ? ToolUtils.getFormattedDate(dataValString) : "");

                                    notaBanca.setDescrizione(response.getString("descrizione"));

                                    if (response.get("nr_protocollo").equals("") || response.get("nr_protocollo").equals(" ") || response.get("nr_protocollo").equals(null))
                                        notaBanca.setNumeroProtocollo(0); //Non presente
                                    else
                                        notaBanca.setNumeroProtocollo(response.getInt("nr_protocollo"));

                                    //Check se campo dare è vuoto -> problema decoding
                                    if (response.get("dare").equals("") || response.get("dare").equals(null))
                                        notaBanca.setDare(0);
                                    else
                                        notaBanca.setDare(ToolUtils.parse(response.getString("dare")));


                                    if (response.get("avere").equals("") || response.get("avere").equals(null))
                                        notaBanca.setAvere(0);
                                    else
                                        notaBanca.setAvere(ToolUtils.parse(response.getString("avere")));

                                    mNotaBanca.add(notaBanca);
                                }
                                adapter.notifyDataSetChanged();
                            }

                            //Recupero le view per la progressbar
                            //PrimaNotaCassaActivity activity = (PrimaNotaCassaActivity) mActivity;
                            //activity.iconRefresh(true);
                            ProgressBar progressBar = (ProgressBar) mActivity.findViewById(R.id.prima_nota_banca_progress);
                            RecyclerView recyclerView = (RecyclerView) mActivity.findViewById(R.id.recyclerview_banca);
                            ToolUtils.showProgress(recyclerView, progressBar, false);


                        } catch (JSONException jsonEx) {
                            jsonEx.printStackTrace();
                        }
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // As of f605da3 the following should work
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                System.out.println(res);
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            }
                        }
                    }
                });

        mRequestQueue.add(accessiRequest);
    }

    public void AddNewNotaCassa(String json) {
        String url = mActivity.getString(R.string.mobile_url);
        url += "nota-cassa-nuovo";

        HashMap params = new HashMap();
        params.put("data", json);

        System.out.println(json);

        dialogBuilder.setTitle("Inserisci nuova cassa");

        PUTRequest modifyRequestJson = new PUTRequest(Request.Method.PUT, url, params, new Response.Listener<JSONObject>() {
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

        mRequestQueue.add(modifyRequestJson);
    }

    public void buildFilePart(DataOutputStream dos, byte[] fileData, String filename) throws IOException {
        dos.writeBytes(twoHyphens + boundary + lineEnd);
        dos.writeBytes("Content-Disposition: from-data; name=\"uploaded_file\"; filename=\"" + filename + "\"" + lineEnd);
        dos.writeBytes(lineEnd);

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(fileData);
        int bytesAvailable = fileInputStream.available();

        int maxBufferSize = 1024 * 1024;

        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dos.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        dos.writeBytes(lineEnd);
    }

    public void buildTextPart(DataOutputStream dos, byte[] fileData) throws IOException {
        dos.writeBytes(twoHyphens + boundary + lineEnd);
        dos.writeBytes("Content-Disposition: from-data; name=\"file_description\";" + lineEnd);
        dos.writeBytes(lineEnd);

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(fileData);
        int bytesAvailable = fileInputStream.available();

        int maxBufferSize = 1024 * 1024;

        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dos.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        dos.writeBytes(lineEnd);
    }


    byte[] fullyReadFileToBytes(File f) throws IOException {
        int size = (int) f.length();
        byte bytes[] = new byte[size];
        byte tmpBuff[] = new byte[size];
        FileInputStream fis = new FileInputStream(f);
        ;
        try {

            int read = fis.read(bytes, 0, size);
            if (read < size) {
                int remain = size - read;
                while (remain > 0) {
                    read = fis.read(tmpBuff, 0, remain);
                    System.arraycopy(tmpBuff, 0, bytes, size - remain, read);
                    remain -= read;
                }
            }
        } catch (IOException e) {
            throw e;
        } finally {
            fis.close();
        }

        return bytes;
    }

}
