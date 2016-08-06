package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Societa;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import mcteamgestapp.momo.com.mcteamgestapp.Utils.HeaderFooterPageEvent;
import mcteamgestapp.momo.com.mcteamgestapp.NetworkReq.JSONObjectRequest;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Societa;
import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * Created by meddaakouri on 04/12/2015.
 */
public class SocietaUtils {

    public static Font tableTitleFond = new Font(Font.FontFamily.UNDEFINED, Font.BOLD);

    public static void print(Societa societa, Context context) throws Exception {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GestApp/societa/pdf");

        File pdf = new File(dir, societa.getNomeSocietà() + ".pdf");

        tableTitleFond.setColor(BaseColor.WHITE);

        tableTitleFond.setSize(14);

        //creazione tabella
        //make them in case they're not there
        dir.mkdirs();
        Font boldTitle = new Font(Font.FontFamily.HELVETICA, 20f, Font.BOLD);
        Document pdfToPrint = new Document(PageSize.A4.rotate(), 20, 20, 100, 25);
        FileOutputStream fos = new FileOutputStream(pdf);
        PdfWriter writer = PdfWriter.getInstance(pdfToPrint, fos);
        HeaderFooterPageEvent event = new HeaderFooterPageEvent("STAMPA SOCIETA");
        event.setImage(context);


        writer.setPageEvent(event);
        pdfToPrint.open();

        //adding meta data
        pdfToPrint.addTitle(societa.getNomeSocietà());
        Paragraph titolo = new Paragraph(societa.getNomeSocietà(), boldTitle);
        titolo.setAlignment(Element.ALIGN_CENTER);

        pdfToPrint.add(titolo);

        pdfToPrint.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(4);

        PdfPCell c1 = new PdfPCell(new Phrase("Tipoliga", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Società", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Indirizzo", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("CAP", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        table.setHeaderRows(1);

        String tipologia = societa.getmTipologia();
        String resultTiplogia = "";

        if (tipologia.equals("F")) {
            resultTiplogia = "Fornitore";
        } else if (tipologia.equals("C")) {
            resultTiplogia = "Cliente";
        } else {
            resultTiplogia = "Presonale";
        }
        table.addCell(resultTiplogia);
        table.addCell(societa.getNomeSocietà().equals("") ? " " : societa.getNomeSocietà().equals("") ? " " : societa.getNomeSocietà());
        table.addCell(societa.getIndirizzo().equals("") ? " " : societa.getIndirizzo().equals("") ? " " : societa.getIndirizzo());
        table.addCell(societa.getCap().equals("") ? " " : societa.getCap().equals("") ? " " : societa.getCap());

        pdfToPrint.add(table);

        table = new PdfPTable(4);
        c1 = new PdfPCell(new Phrase("Provincia", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Citta", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Stato", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Telefono", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        table.setHeaderRows(1);

        table.addCell(societa.getmProvincia() == null ? " " : societa.getmProvincia().equals("") ? " " : societa.getmProvincia());
        table.addCell(societa.getmCitta() == null ? " " : societa.getmCitta().equals("") ? " " : societa.getmCitta());
        table.addCell(societa.getStato() == null ? " " : societa.getStato().equals("") ? " " : societa.getStato());
        table.addCell(societa.getmTelefono() == null ? " " : societa.getmTelefono().equals("") ? " " : societa.getmTelefono());

        pdfToPrint.add(table);

        table = new PdfPTable(4);
        c1 = new PdfPCell(new Phrase("Fax", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Cellulare", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("WWW", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Partita IVA", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        table.setHeaderRows(1);

        table.addCell(societa.getmFax() == null ? " " : societa.getmFax().equals("") ? " " : societa.getmFax());
        table.addCell(societa.getmCellulare() == null ? " " : societa.getmCellulare().equals("") ? " " : societa.getmCellulare());
        table.addCell(societa.getSito() == null ? " " : societa.getSito().equals("") ? " " : societa.getSito());
        table.addCell(societa.getPartitaIva() == null ? " " : societa.getPartitaIva().equals("") ? " " : societa.getPartitaIva());

        pdfToPrint.add(table);


        table = new PdfPTable(2);
        c1 = new PdfPCell(new Phrase("Codice Fiscale", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Note", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        table.setHeaderRows(1);

        table.addCell(societa.getCOD_FISCALE().equals("") ? " " : societa.getCOD_FISCALE().equals("") ? " " : societa.getCOD_FISCALE());
        table.addCell(societa.getNote().equals("") ? " " : societa.getNote().equals("") ? " " : societa.getNote());

        pdfToPrint.add(table);
        //create a standard java.io.File object for the Workbook to use

        readPdfFile(Uri.fromFile(pdf), context);

        pdfToPrint.close();


    }

    public static void readPdfFile(Uri path, Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(path, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No Application Available to View Excel", Toast.LENGTH_SHORT).show();
        }
    }


    public static void printAll(ArrayList<Societa> rubricaSocieta, Context context) throws DocumentException, FileNotFoundException {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/accessiStampa");

        File pdf = new File(dir, "accessi.pdf");

        //creazione tabella
        //make them in case they're not there
        dir.mkdirs();
        Font boldTitle = new Font(Font.FontFamily.HELVETICA, 15f, Font.BOLD);
        Document pdfToPrint = new Document(PageSize.A4.rotate(), 20, 20, 100, 25);
        FileOutputStream fos = new FileOutputStream(pdf);
        PdfWriter writer = PdfWriter.getInstance(pdfToPrint, fos);
        HeaderFooterPageEvent event = new HeaderFooterPageEvent("STAMPA TUTTO SOCIETA");
        event.setImage(context);

        writer.setPageEvent(event);
        pdfToPrint.open();

        //adding meta data
        pdfToPrint.addTitle("STAMPA TUTTO SOCIETA");

        tableTitleFond.setColor(BaseColor.WHITE);
        tableTitleFond.setSize(14);

        PdfPTable table = new PdfPTable(11);

        PdfPCell c1 = new PdfPCell(new Phrase("SOCIETA", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("T", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("INDIRIZZO", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("CAP", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("CITTA", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("PV", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("TELEFONO", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);


        c1 = new PdfPCell(new Phrase("FAX", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("P.IVA", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("WWW", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("NOTE", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (Societa societa : rubricaSocieta) {
            table.addCell(societa.getNomeSocietà() == null ? " " : societa.getNomeSocietà());
            table.addCell(societa.getmTipologia() == null ? " " : societa.getmTipologia());
            table.addCell(societa.getIndirizzo() == null ? " " : societa.getIndirizzo());
            table.addCell(societa.getCap() == null ? " " : societa.getCap());
            table.addCell(societa.getmCitta() == null ? " " : societa.getmCitta());
            table.addCell(societa.getmProvincia() == null ? " " : societa.getmProvincia());
            table.addCell(societa.getmTelefono() == null ? " " : societa.getmTelefono());
            table.addCell(societa.getmFax() == null ? " " : societa.getmFax());
            table.addCell(societa.getPartitaIva() == null ? " " : societa.getPartitaIva());
            table.addCell(societa.getSito() == null ? " " : societa.getSito());
            table.addCell(societa.getNote() == null ? " " : societa.getNote());
        }

        pdfToPrint.add(table);

        //Write the workbook in file system
        readPdfFile(Uri.fromFile(pdf), context);

        pdfToPrint.close();

    }

    public static Societa getSocietaFromId(int ID, final Context context) {

        String url = context.getString(R.string.mobile_url);
        url += "societa/" + ID;

        final Societa[] result = new Societa[1];

        JSONObjectRequest getSocietaFormDatabaseRequest = new JSONObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Societa societa = new Societa();
                    societa.setID(response.getInt("ID_CLIENTE"));
                    societa.setCodiceSocieta(response.getInt("ID"));
                    societa.setNomeSocietà(response.getString("SOCIETA"));
                    societa.setIndirizzo(response.getString("INDIRIZZO"));
                    societa.setCap(response.getString("CAP"));
                    societa.setSito(response.getString("WWW"));
                    societa.setmProvincia(response.getString("PROVINCIA"));
                    societa.setStato(response.getString("STATO"));
                    societa.setmCitta(response.getString("CITTA"));
                    societa.setPartitaIva(response.getString("IVA"));
                    societa.setmTelefono(response.getString("TELEFONO"));
                    societa.setCOD_FISCALE(response.getString("COD_FISCALE"));
                    societa.setmCellulare(response.getString("CELLULARE"));
                    societa.setNote(response.getString("NOTE"));
                    societa.setmTipologia(response.getString("TIPOLOGIA"));

                    result[0] = societa;

                } catch (JSONException e) {

                    result[0] = null;
                    e.printStackTrace();
                    Log.i("LoginResponse", e.getMessage());

                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Errore richiesta Volley", "Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                result[0] = null;
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(getSocietaFormDatabaseRequest);

        return result[0];
    }


}
