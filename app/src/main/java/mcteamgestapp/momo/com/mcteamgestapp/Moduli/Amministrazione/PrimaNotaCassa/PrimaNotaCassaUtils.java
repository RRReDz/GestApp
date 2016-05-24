package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Amministrazione.PrimaNotaCassa;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
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
import java.util.ArrayList;

import mcteamgestapp.momo.com.mcteamgestapp.HeaderFooterPageEvent;
import mcteamgestapp.momo.com.mcteamgestapp.Models.PrimaNota.NotaCassa;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Nominativo;

/**
 * Created by Riccardo Rossi on 24/05/2016.
 */
public class PrimaNotaCassaUtils {

    public static Font tableTitleFond = new Font(Font.FontFamily.UNDEFINED, Font.BOLD);

    public static void printAll(ArrayList<NotaCassa> notaCassaList, Context context, String type) throws Exception {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GestApp/nota_cassa/pdf");
        Log.d("PATH DI STAMPA", context.getFilesDir() + "/GestApp/nota_cassa/pdf");
        File pdf = new File(dir, "Prima_Nota_Cassa" + ".pdf");

        dir.mkdirs();
        //make them in case they're not there

        //creazione tabella
        Font boldTitle = new Font(Font.FontFamily.HELVETICA, 20f, Font.BOLD);
        Document pdfToPrint = new Document(PageSize.A4.rotate(), 20, 20, 100, 25);
        FileOutputStream fos = new FileOutputStream(pdf);
        PdfWriter writer = PdfWriter.getInstance(pdfToPrint, fos);
        HeaderFooterPageEvent event = new HeaderFooterPageEvent("PRIMA NOTA CASSA - " + type.toUpperCase());
        event.setImage(context);

        writer.setPageEvent(event);
        pdfToPrint.open();

        //adding meta data

        tableTitleFond.setColor(BaseColor.WHITE);
        tableTitleFond.setSize(15);

        PdfPTable table = new PdfPTable(9);

        PdfPCell c1 = new PdfPCell(new Phrase("Progr.", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Data operazione", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Causale contabile", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Sottoconto", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Descrizione movimenti", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Fatture nr protocollo", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Dare(€)", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);


        c1 = new PdfPCell(new Phrase("Avere(€)", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Saldo(€)", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        table.setHeaderRows(1);

        int i = 1;
        for (NotaCassa notaCassa : notaCassaList) {
            table.addCell(i + "");
            table.addCell(notaCassa.getDataPagamento() == null ? " " : notaCassa.getDataPagamento());
            table.addCell(notaCassa.getCausaleContabile() == null ? " " : notaCassa.getCausaleContabile());
            table.addCell(notaCassa.getSottoconto() == null ? " " : notaCassa.getSottoconto());
            table.addCell(notaCassa.getDescrizione() == null ? " " : notaCassa.getDescrizione());
            table.addCell(notaCassa.getNumeroProtocollo() == 0 ? " " : notaCassa.getNumeroProtocollo() + "");

            Font font = new Font(Font.FontFamily.UNDEFINED);
            font.setColor(BaseColor.BLUE);
            c1 = new PdfPCell(new Phrase(notaCassa.getDare() + "", font));
            table.addCell(c1);

            Font font2 = new Font(Font.FontFamily.UNDEFINED);
            font2.setColor(BaseColor.RED);
            c1 = new PdfPCell(new Phrase(notaCassa.getAvere() + "", font2));
            table.addCell(c1);

            table.addCell(notaCassa.getTotale() + "");
        }

        pdfToPrint.add(table);

        //Write the workbook in file system
        readPdfFile(Uri.fromFile(pdf), context, "pdf");

        pdfToPrint.close();
    }

    public static void readPdfFile(Uri path, Context context, String type) {

        Intent intent = new Intent(Intent.ACTION_VIEW);

        if (type.equals("pdf")) {
            intent.setDataAndType(path, "application/pdf");
        } else if (type.equals("excel")) {
            intent.setDataAndType(path, "application/vnd.ms-excel");
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No Application Available to View this data", Toast.LENGTH_SHORT).show();
        }
    }
}
