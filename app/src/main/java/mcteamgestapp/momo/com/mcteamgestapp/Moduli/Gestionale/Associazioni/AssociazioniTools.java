package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Associazioni;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
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
import java.util.ArrayList;

import mcteamgestapp.momo.com.mcteamgestapp.Utils.HeaderFooterPageEvent;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Associazione;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Commessa;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Nominativo;
import mcteamgestapp.momo.com.mcteamgestapp.Utils.Functions;

/**
 * Created by meddaakouri on 29/01/2016.
 */
public class AssociazioniTools {
    public static Font tableTitleFond = new Font(Font.FontFamily.UNDEFINED, Font.BOLD);

    public static void print(Associazione associazione, Context context) throws Exception {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GestApp/Associazioni/pdf");

        File pdf = new File(dir, "ASSOCIAZIONI-STAMPA.pdf");

        tableTitleFond.setColor(BaseColor.WHITE);

        tableTitleFond.setSize(14);

        //creazione tabella
        //make them in case they're not there
        dir.mkdirs();
        Font boldTitle = new Font(Font.FontFamily.HELVETICA, 20f, Font.BOLD);
        Document pdfToPrint = new Document(PageSize.A4.rotate(), 20, 20, 100, 25);
        FileOutputStream fos = new FileOutputStream(pdf);
        PdfWriter writer = PdfWriter.getInstance(pdfToPrint, fos);
        HeaderFooterPageEvent event = new HeaderFooterPageEvent("STAMPA ASSOCIAZIONE");
        event.setImage(context);


        writer.setPageEvent(event);
        pdfToPrint.open();

        //adding meta data
        pdfToPrint.addTitle("STAMPA ASSOCIAZIONE");
        Paragraph titolo = new Paragraph("STAMPA ASSOCIAZIONE", boldTitle);
        titolo.setAlignment(Element.ALIGN_CENTER);

        pdfToPrint.add(titolo);

        pdfToPrint.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Commessa", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        table.setHeaderRows(1);

        if (associazione.getCommessa() != null) {
            Commessa commessaAttuale = associazione.getCommessa();
            String nomeCommessa = "";
            nomeCommessa += commessaAttuale.getCodice_commessa() + " - ";
            if (commessaAttuale != null && commessaAttuale.getCliente() != null && commessaAttuale.getCliente().getNomeSocietà() != null) {
                nomeCommessa += commessaAttuale.getCliente().getNomeSocietà() + " - ";
            }
            nomeCommessa += commessaAttuale.getNome_commessa();

            table.addCell(nomeCommessa);
        } else {
            table.addCell(" ");
        }

        pdfToPrint.add(table);

        table = new PdfPTable(2);
        c1 = new PdfPCell(new Phrase("Capo progetto", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);


        c1 = new PdfPCell(new Phrase("Consulente", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        table.setHeaderRows(1);

        if (associazione.getCommessa() != null && associazione.getCommessa().getCapo_progetto() != null) {
            table.addCell(associazione.getCommessa().getCapo_progetto().getCognome() + " " + associazione.getCommessa().getCapo_progetto().getNome());
        } else {
            table.addCell(" ");
        }

        table.addCell(associazione.getRisorsa().getCognome() + " " + associazione.getRisorsa().getNome());

        pdfToPrint.add(table);

        table = new PdfPTable(2);

        c1 = new PdfPCell(new Phrase("Inizio", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Fine", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        table.setHeaderRows(1);

        table.addCell(Functions.getFormattedDate(associazione.getData_inizio()));
        table.addCell(Functions.getFormattedDate(associazione.getData_fine()));

        pdfToPrint.add(table);

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

    public static void printAll(ArrayList<Associazione> associazioni, Context context) throws Exception {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GestApp/Associazioni/pdf");

        File pdf = new File(dir, "ASSOCIAZIONI-STAMPA.pdf");

        tableTitleFond.setColor(BaseColor.WHITE);

        tableTitleFond.setSize(14);

        //creazione tabella
        //make them in case they're not there
        dir.mkdirs();
        Font boldTitle = new Font(Font.FontFamily.HELVETICA, 20f, Font.BOLD);
        Document pdfToPrint = new Document(PageSize.A4.rotate(), 20, 20, 100, 25);
        FileOutputStream fos = new FileOutputStream(pdf);
        PdfWriter writer = PdfWriter.getInstance(pdfToPrint, fos);
        HeaderFooterPageEvent event = new HeaderFooterPageEvent("STAMPA TUTTO ASSOCIAZIONE");
        event.setImage(context);


        writer.setPageEvent(event);
        pdfToPrint.open();

        PdfPTable table = new PdfPTable(5);
        table.setWidths(new float[]{2.5f, 1.4f, 1.4f, 0.8f, 0.8f});

        PdfPCell c1 = new PdfPCell(new Phrase("NOME COMMESSA", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("REFERENTE 1", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);


        c1 = new PdfPCell(new Phrase("CONSULENTE", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("INIZIO", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("FINE", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (Associazione associazione : associazioni) {

            if (associazione.getCommessa() != null) {
                Commessa commessaAttuale = associazione.getCommessa();
                String nomeCommessa = "";
                nomeCommessa += commessaAttuale.getCodice_commessa() + " - ";
                if (commessaAttuale != null && commessaAttuale.getCliente() != null && commessaAttuale.getCliente().getNomeSocietà() != null) {
                    nomeCommessa += commessaAttuale.getCliente().getNomeSocietà() + " - ";
                }
                nomeCommessa += commessaAttuale.getNome_commessa();

                table.addCell(nomeCommessa);
            } else {
                table.addCell(" ");
            }


            if (associazione.getCommessa().getReferente1() != null) {
                Nominativo referente = associazione.getCommessa().getReferente1();
                String referenteString = "";
                if (referente.getCognome() != null)
                    referenteString += referente.getCognome();
                if (referente.getNome() != null)
                    referenteString += referente.getNome();

                table.addCell(referenteString);
            } else {
                table.addCell(" ");
            }

            table.addCell(associazione.getRisorsa().getCognome() + " " + associazione.getRisorsa().getNome());
            table.addCell(Functions.getFormattedDate(associazione.getData_inizio()));
            table.addCell(Functions.getFormattedDate(associazione.getData_fine()));

        }

        pdfToPrint.add(table);

        readPdfFile(Uri.fromFile(pdf), context);

        pdfToPrint.close();

    }

    public static void esportaExcel(ArrayList<Associazione> listaAssociazione, Context context) throws Exception {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GestApp/nominativi/excel");

        //make them in case they're not there
        dir.mkdirs();
        //create a standard java.io.File object for the Workbook to use
        File wbfile = new File(dir, "Associazioni.xlsx");

        Workbook wb = new HSSFWorkbook();

        Cell cella = null;

        CellStyle cs = wb.createCellStyle();

        cs.setFillForegroundColor(HSSFColor.RED.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        Sheet foglio1 = null;
        foglio1 = wb.createSheet("Associazioni");

        Row row = foglio1.createRow(0);

        cella = row.createCell(0);
        cella.setCellValue("Cliente");
        cella.setCellStyle(cs);

        cella = row.createCell(1);
        cella.setCellValue("Cod.");
        cella.setCellStyle(cs);

        cella = row.createCell(2);
        cella.setCellValue("Nome Commessa");
        cella.setCellStyle(cs);

        cella = row.createCell(3);
        cella.setCellValue("I Referente");
        cella.setCellStyle(cs);

        cella = row.createCell(4);
        cella.setCellValue("Consulente");
        cella.setCellStyle(cs);

        cella = row.createCell(5);
        cella.setCellValue("Inizio");
        cella.setCellStyle(cs);

        cella = row.createCell(6);
        cella.setCellValue("Fine");
        cella.setCellStyle(cs);


        int nRow = 1;

        for (Associazione associazione : listaAssociazione) {

            row = foglio1.createRow(nRow++);

            cella = row.createCell(0);
            if (associazione.getCommessa().getCliente() != null)
                cella.setCellValue(associazione.getCommessa().getCliente().getNomeSocietà());

            cella = row.createCell(1);
            cella.setCellValue(associazione.getCommessa().getCodice_commessa());

            cella = row.createCell(2);
            cella.setCellValue(associazione.getCommessa().getNome_commessa());

            cella = row.createCell(3);
            cella.setCellValue(associazione.getCommessa().getReferente1() != null ? associazione.getCommessa().getReferente1().getCognome() + " " + associazione.getCommessa().getReferente1().getNome() : " ");

            cella = row.createCell(4);
            cella.setCellValue(associazione.getRisorsa() != null ? (associazione.getRisorsa().getNome() + " " + associazione.getRisorsa().getCognome()) : "");

            cella = row.createCell(5);
            cella.setCellValue(Functions.getFormattedDate(associazione.getData_inizio()));

            cella = row.createCell(6);
            cella.setCellValue(Functions.getFormattedDate(associazione.getData_fine()));

        }

        foglio1.setColumnWidth(0, 5000);
        foglio1.setColumnWidth(1, 2000);
        foglio1.setColumnWidth(2, 10000);
        foglio1.setColumnWidth(3, 5000);
        foglio1.setColumnWidth(4, 5000);
        foglio1.setColumnWidth(5, 2000);
        foglio1.setColumnWidth(6, 2000);
        //Write the workbook in file system
        FileOutputStream out = null;


        try {
            out = new FileOutputStream(wbfile);
            wb.write(out);
            readPdfFile(Uri.fromFile(wbfile), context, "excel");
            Log.w("FileUtils", "Writing file" + wbfile);
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + wbfile, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != out)
                    out.close();
            } catch (Exception ex) {
            }
        }

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
            Toast.makeText(context, "No Application Available to View Excel", Toast.LENGTH_SHORT).show();
        }
    }
}
