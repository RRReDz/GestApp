package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Commesse;

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
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import mcteamgestapp.momo.com.mcteamgestapp.HeaderFooterPageEvent;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Commessa;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Nominativo;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Societa;
import mcteamgestapp.momo.com.mcteamgestapp.R;
import mcteamgestapp.momo.com.mcteamgestapp.ToolUtils;

/**
 * Created by meddaakouri on 22/12/2015.
 */
public class CommesseUtils {

    public static void printAll(ArrayList<Commessa> commesse, Context context) throws DocumentException, FileNotFoundException {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GestApp/commesseStampa");

        Font tableTitleFond = new Font(Font.FontFamily.UNDEFINED, Font.BOLD);

        tableTitleFond.setColor(BaseColor.WHITE);
        tableTitleFond.setSize(14);

        File pdf = new File(dir, "commesse.pdf");

        //creazione tabella
        //make them in case they're not there
        dir.mkdirs();

        Font boldTitle = new Font(Font.FontFamily.HELVETICA, 20f, Font.BOLD);
        Document pdfToPrint = new Document(PageSize.A4.rotate(), 20, 20, 100, 25);
        FileOutputStream fos = new FileOutputStream(pdf);
        PdfWriter writer = PdfWriter.getInstance(pdfToPrint, fos);
        HeaderFooterPageEvent event = new HeaderFooterPageEvent("STAMPA TUTTO COMMESSE");
        event.setImage(context);

        writer.setPageEvent(event);
        pdfToPrint.open();

        //adding meta data
        pdfToPrint.addTitle("STAMPA TUTTO COMMESSE");

        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100);

        //dimensione delle colonne
        table.setWidths(new float[]{0.7f, 1.4f, 1.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 1f});

        PdfPCell c1 = new PdfPCell(new Phrase("COD", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("CLIENTE", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("NOME COMMESSA", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("RISORSA'", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("I REFERENTE", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("I TELEFONO", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("II REFERENTE", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);


        c1 = new PdfPCell(new Phrase("II TELEFONO", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("NOTE", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (Commessa commessa : commesse) {
            table.addCell(commessa.getCodice_commessa());
            if (commessa.getCliente() != null)
                table.addCell(commessa.getCliente().getNomeSocietà());
            else
                table.addCell("");

            String nomeCommessa = "";
            nomeCommessa += commessa.getNome_commessa();

            table.addCell(nomeCommessa.toLowerCase());

            if (commessa.getCommerciale() != null)
                table.addCell(commessa.getCommerciale().getCognome().toLowerCase() + " " + commessa.getCommerciale().getNome().toUpperCase());
            else
                table.addCell("");

            if (commessa.getReferente1() != null) {
                String referente1 = commessa.getReferente1().getCognome() + " " + commessa.getReferente1().getNome();
                table.addCell(referente1.toLowerCase());
                table.addCell(commessa.getReferente1().getTelefono());
            } else {
                table.addCell("");
                table.addCell("");
            }

            if (commessa.getReferente2() != null) {
                String referente2 = commessa.getReferente2().getCognome() + " " + commessa.getReferente2().getNome();
                table.addCell(referente2);
                table.addCell(commessa.getReferente2().getTelefono());
            } else {
                table.addCell("");
                table.addCell("");
            }

            table.addCell(commessa.getNote());
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
            Toast.makeText(context, "No Application Available to View Excel", Toast.LENGTH_SHORT).show();
        }
    }

    public static void printSimple(Commessa commessa, Context context) throws Exception {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GestApp/commesse/pdf");

        File pdf = new File(dir, commessa.getNome_commessa() + ".pdf");
        //creazione tabella
        //make them in case they're not there
        dir.mkdirs();

        PdfReader pdfTemplateReader = new PdfReader(getAssetsPdfPath(context, "commessa_template.pdf"));

        FileOutputStream outputSteam = new FileOutputStream(pdf);

        PdfStamper stamper = new PdfStamper(pdfTemplateReader, outputSteam);

        AcroFields forms = stamper.getAcroFields();

        forms.setField("nome_cliente", commessa.getCliente().getNomeSocietà());
        forms.setField("codice_commessa", commessa.getCodice_commessa());
        String[] date = null;
        if (ToolUtils.validateReverseDate(commessa.getData())) {
            date = commessa.getData().split("-");
        }

        forms.setField("anno_commessa", date != null ? date[0] : "");

        forms.setField("nome_commessa", commessa.getNome_commessa());
        forms.setField("chiusura_commessa", commessa.getAvanzamento().toUpperCase().equals("chiusura".toUpperCase()) ? "X" : "");

        Societa cliente = commessa.getCliente();

        forms.setField("tipologia_commessa", cliente != null ? cliente.getmTipologia() : "");
        forms.setField("indirizzo_cliente", cliente != null ? cliente.getIndirizzo() : "");
        forms.setField("cap_cliente", cliente != null ? cliente.getCap() : "");
        forms.setField("citta_cliente", cliente != null ? cliente.getmCitta() : "");
        forms.setField("stato_cliente", cliente != null ? cliente.getStato() : "");
        forms.setField("provincia_cliente", cliente != null ? cliente.getmProvincia() : "");
        forms.setField("telefono_cliente", cliente != null ? cliente.getmTelefono() : "");
        forms.setField("sito_cliente", cliente != null ? cliente.getSito() : "");
        forms.setField("partita_iva_cliente", cliente != null ? cliente.getPartitaIva() : "");
        forms.setField("fax_cliente", cliente != null ? cliente.getmFax() : "");

        Nominativo commerciale = commessa.getCommerciale();

        forms.setField("cognome_commerciale", commerciale != null ? commerciale.getCognome() : "");
        forms.setField("nome_commerciale", commerciale != null ? commerciale.getNome() : "");

        forms.setField("data_completa_commessa", ToolUtils.getFormattedDate(commessa.getData()));

        forms.setField("commessa_contatto", "MARIO COTTONE");
        forms.setField("commessa_marketing", commessa.getAvanzamento().equals("marketing") ? "SI" : "");
        forms.setField("commessa_sviluppo", commessa.getAvanzamento().equals("sviluppo") ? "SI" : "");
        forms.setField("commessa_offerta", commessa.getAvanzamento().equals("offerta") ? "SI" : "");
        forms.setField("commessa_fattura", commessa.getAvanzamento().equals("fattura") ? "SI" : "");
        forms.setField("commessa_ordine", commessa.getAvanzamento().equals("ordine") ? "SI" : "");
        forms.setField("commessa_pagamento", commessa.getAvanzamento().equals("pagamento") ? "SI" : "");
        forms.setField("commessa_note", commessa.getNote());

        Nominativo ref1 = commessa.getReferente1();


        forms.setField("titolo_ref_1", ref1 != null ? ref1.getTitolo() : "");
        forms.setField("cognome_ref_1", ref1 != null ? ref1.getCognome() : "");
        forms.setField("nome_ref_1", ref1 != null ? ref1.getNome() : "");
        forms.setField("telefono_ref_1", ref1 != null ? ref1.getTelefono() : "");
        forms.setField("cellulare_ref_1", ref1 != null ? ref1.getCellulare() : "");
        forms.setField("email_ref_1", ref1 != null ? ref1.getEmail() : "");
        forms.setField("note_ref_1", ref1 != null ? ref1.getNote() : "");

        Nominativo ref2 = commessa.getReferente2();

        forms.setField("cognome_ref_2", ref2 != null ? ref2.getCognome() : "");
        forms.setField("nome_ref_2", ref2 != null ? ref2.getNome() : "");
        forms.setField("titolo_ref_2", ref2 != null ? ref2.getTitolo() : "");
        forms.setField("telefono_ref_2", ref2 != null ? ref2.getTelefono() : "");
        forms.setField("cellulare_ref_2", ref2 != null ? ref2.getCellulare() : "");
        forms.setField("email_ref_2", ref2 != null ? ref2.getEmail() : "");
        forms.setField("note_ref_2", ref2 != null ? ref2.getNote() : "");

        Set<String> keys = forms.getFields().keySet();

        readPdfFile(Uri.fromFile(pdf), context, "pdf");

        stamper.close();
        pdfTemplateReader.close();
    }

    public static String getAssetsPdfPath(Context context, String pdfName) {
        String filePath = context.getFilesDir() + File.separator + pdfName;
        File destinationFile = new File(filePath);

        try {
            FileOutputStream outputStream = new FileOutputStream(destinationFile);
            InputStream inputStream = context.getAssets().open(pdfName);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            Log.e(context.getClass().getSimpleName(), "Error.");
        }

        return destinationFile.getPath();
    }

    public static class MyBorder implements PdfPCellEvent {
        public void cellLayout(PdfPCell cell, Rectangle position,
                               PdfContentByte[] canvases) {
            float x1 = position.getLeft() + 2;
            float x2 = position.getRight() - 10;
            float y1 = position.getTop() - 0;
            float y2 = position.getBottom() + 0;
            PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
            canvas.rectangle(x1, y1, x2 - x1, y2 - y1);
            canvas.stroke();
        }
    }

    public static void esportaExcel(ArrayList<Commessa> listaCommesse, Context context) throws Exception {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GestApp/nominativi/excel");

        //make them in case they're not there
        dir.mkdirs();
        //create a standard java.io.File object for the Workbook to use
        File wbfile = new File(dir, "Commesse.xlsx");

        Workbook wb = new HSSFWorkbook();

        Cell cella = null;

        CellStyle cs = wb.createCellStyle();

        cs.setFillForegroundColor(HSSFColor.RED.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        Sheet foglio1 = null;
        foglio1 = wb.createSheet("Commesse");

        Row row = foglio1.createRow(0);

        cella = row.createCell(0);
        cella.setCellValue("Cliente");
        cella.setCellStyle(cs);

        cella = row.createCell(1);
        cella.setCellValue("Avz");
        cella.setCellStyle(cs);

        cella = row.createCell(2);
        cella.setCellValue("COD");
        cella.setCellStyle(cs);

        cella = row.createCell(3);
        cella.setCellValue("Nome");
        cella.setCellStyle(cs);

        cella = row.createCell(4);
        cella.setCellValue("I referente");
        cella.setCellStyle(cs);

        cella = row.createCell(5);
        cella.setCellValue("II referente");
        cella.setCellStyle(cs);

        cella = row.createCell(6);
        cella.setCellValue("Risorsa");
        cella.setCellStyle(cs);


        int nRow = 1;

        for (Commessa commessa : listaCommesse) {

            row = foglio1.createRow(nRow++);

            cella = row.createCell(0);
            if (commessa.getCliente() != null)
                cella.setCellValue(commessa.getCliente().getNomeSocietà());

            cella = row.createCell(1);
            cella.setCellValue(commessa.getAvanzamento());

            cella = row.createCell(2);
            cella.setCellValue(commessa.getCodice_commessa());

            cella = row.createCell(3);
            cella.setCellValue(commessa.getNome_commessa());

            cella = row.createCell(4);
            cella.setCellValue(commessa.getReferente1() != null ? (commessa.getReferente1().getNome() + commessa.getReferente1().getCognome()) : "");

            cella = row.createCell(5);
            cella.setCellValue(commessa.getReferente2() != null ? (commessa.getReferente2().getNome() + commessa.getReferente2().getCognome()) : "");

            cella = row.createCell(6);
            cella.setCellValue(commessa.getCommerciale() != null ? (commessa.getCommerciale().getNome() + commessa.getCommerciale().getCognome()) : "");

        }

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

}
