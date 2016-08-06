package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

import mcteamgestapp.momo.com.mcteamgestapp.Utils.HeaderFooterPageEvent;
import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;

/**
 * Created by meddaakouri on 02/12/2015.
 */
public class SistemiUtils {

    public static Font tableTitleFond = new Font(Font.FontFamily.UNDEFINED, Font.BOLD);

    public static void print(UserInfo user, Context context) throws Exception {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GestApp/accessi/pdf");

        File pdf = new File(dir, user.getCognome() + ".pdf");

        tableTitleFond.setColor(BaseColor.WHITE);

        tableTitleFond.setSize(14);

        //creazione tabella
        //make them in case they're not there
        dir.mkdirs();
        Font boldTitle = new Font(Font.FontFamily.HELVETICA, 20f, Font.BOLD);
        Document pdfToPrint = new Document(PageSize.A4.rotate(), 20, 20, 100, 25);


        PdfWriter writer = PdfWriter.getInstance(pdfToPrint, new FileOutputStream(pdf));

        HeaderFooterPageEvent event = new HeaderFooterPageEvent("STAMPA UTENTE");

        event.setImage(context);

        writer.setPageEvent(event);


        pdfToPrint.open();

        //adding meta data
        pdfToPrint.addTitle(user.getCognome() + user.getNome());
        Paragraph titolo = new Paragraph(user.getCognome() + " " + user.getNome(), boldTitle);
        titolo.setAlignment(Element.ALIGN_CENTER);


        pdfToPrint.add(titolo);

        pdfToPrint.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(4);

        PdfPCell c1 = new PdfPCell(new Phrase("Cognome", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Nome", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Username", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Abilitato", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        table.setHeaderRows(1);

        table.addCell(user.getCognome() == null || user.getCognome().equals("") ? " " : user.getCognome());
        table.addCell(user.getNome() == null || user.getNome().equals("") ? " " : user.getNome());
        table.addCell(user.getEmail() == null || user.getEmail().equals("") ? " " : user.getEmail());
        table.addCell(user.isAbilitato() ? "Si" : "No");

        pdfToPrint.add(table);

        table = new PdfPTable(3);
        c1 = new PdfPCell(new Phrase("Telefono", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Data di nascita", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Luogo di nascita", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        table.setHeaderRows(1);

        table.addCell(user.getPhone() == null || TextUtils.isEmpty(user.getPhone()) ? " " : user.getPhone());
        table.addCell(user.getDataNascita() == null || TextUtils.isEmpty(user.getDataNascita()) ? " " : user.getDataNascita());
        table.addCell(user.getLuogoNascita() == null || TextUtils.isEmpty(user.getLuogoNascita()) ? " " : user.getLuogoNascita());

        pdfToPrint.add(table);


        table = new PdfPTable(1);
        c1 = new PdfPCell(new Phrase("Ruoli".toUpperCase(), tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        String tipologiaUser = "";
        tipologiaUser += user.isAmministratore() ? "Amministrazione - " : "";
        tipologiaUser += user.isCommerciale() ? "Commerciale - " : "";
        tipologiaUser += user.isSistemi() ? "Sistemi - " : "";
        tipologiaUser += user.isGestionale() ? "Gestionale - " : "";
        tipologiaUser += user.isProduzione() ? "Produzione - " : "";
        tipologiaUser += user.isCapoProgetto() ? "Capo Progetto - " : "";
        tipologiaUser += user.isConsulente() ? "Consulente - " : "";
        tipologiaUser += user.isDirezione() ? "Direzionale - " : "";
        tipologiaUser += user.isPersonale() ? "Personale - " : "";

        table.setHeaderRows(1);
        table.addCell(tipologiaUser);

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

}
