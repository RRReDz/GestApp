package com.mcteam.gestapp.Moduli.Amministrazione.PrimaNotaBanca;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mcteam.gestapp.BuildConfig;
import com.mcteam.gestapp.Models.PrimaNota.NotaBanca;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.HeaderFooterPageEvent;

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
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author Created by Riccardo Rossi on 24/05/2016.
 */
public class PrimaNotaBancaUtils {

    public static Font tableTitleFond = new Font(Font.FontFamily.UNDEFINED, Font.BOLD);

    public static void printAll(ArrayList<NotaBanca> notaBancaList, Context context, String month, String year) throws Exception {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GestApp/nota_banca/pdf");

        Log.d("PATH DI STAMPA", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GestApp/nota_banca/pdf");
        File pdf = new File(dir, month + "-" + year + ".pdf");
        //pdf.createNewFile();

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        //make them in case they're not there
        dir.mkdirs();

        //creazione tabella
        Font boldTitle = new Font(Font.FontFamily.HELVETICA, 20f, Font.BOLD);
        Document pdfToPrint = new Document(PageSize.A4.rotate(), 20, 20, 100, 25);
        FileOutputStream fos = new FileOutputStream(pdf);
        PdfWriter writer = PdfWriter.getInstance(pdfToPrint, fos);
        HeaderFooterPageEvent event = new HeaderFooterPageEvent("PRIMA NOTA BANCA");
        event.setImage(context);

        writer.setPageEvent(event);
        pdfToPrint.open();

        //adding meta data

        tableTitleFond.setColor(BaseColor.WHITE);
        tableTitleFond.setSize(15);

        Paragraph preface = new Paragraph();
        Paragraph title = new Paragraph(month.toUpperCase() + " - " + year.toUpperCase(), boldTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        preface.add(title);
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header

        pdfToPrint.add(preface);

        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 2, 2, 4, 2, 2, 2, 2, 2, 2});

        PdfPCell c1 = new PdfPCell(new Phrase("Progr.", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Data operazione", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Data valuta", tableTitleFond));
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

        c1 = new PdfPCell(new Phrase("PROVA", tableTitleFond));
        c1.setColspan(2);
        c1.setBorder(Rectangle.NO_BORDER);
        table.addCell(c1);

        table.setHeaderRows(1);

        int i = 0;
        float totDare = 0, totAvere = 0, totSaldo = 0;

        for (NotaBanca notaBanca : notaBancaList) {

            System.out.println(notaBanca.toString());

            totDare += notaBanca.getDare();
            totAvere += notaBanca.getAvere();
            totSaldo += notaBanca.getDare() - notaBanca.getAvere();

            table.addCell(i + 1 + "");
            table.addCell(notaBanca.getDataPagamento() == null ? " " : notaBanca.getDataPagamento());
            table.addCell(notaBanca.getDataValuta() == null ? " " : notaBanca.getDataValuta());
            table.addCell(notaBanca.getDescrizione() == null ? " " : notaBanca.getDescrizione());
            table.addCell(notaBanca.getNumeroProtocollo() == 0 ? " " : notaBanca.getNumeroProtocollo() + "");

            Font font = new Font(Font.FontFamily.UNDEFINED);
            font.setColor(BaseColor.BLUE);
            c1 = new PdfPCell(new Phrase(df.format(notaBanca.getDare()) + "", font));
            if (notaBanca.getDare() == 0)
                table.addCell(" ");
            else
                table.addCell(c1);

            Font font2 = new Font(Font.FontFamily.UNDEFINED);
            font2.setColor(BaseColor.RED);

            c1 = new PdfPCell(new Phrase(df.format(notaBanca.getAvere()) + "", font2));
            if (notaBanca.getAvere() == 0)
                table.addCell(" ");
            else
                table.addCell(c1);

            table.addCell(df.format(totSaldo) + "");
            if (notaBanca.getGruppo() == 1 || notaBanca.getGruppo() == 2) {
                if (notaBanca.getGruppo() == 2)
                    table.addCell("Carta di credito");
                else
                    table.addCell("Carta Sì");

                if (notaBanca.getAvere() == 0) {
                    c1 = new PdfPCell();
                    c1.setBorder(Rectangle.NO_BORDER);
                } else
                    c1 = new PdfPCell(new Phrase(df.format(notaBanca.getAvere())));

                table.addCell(c1);
            } else {
                c1 = new PdfPCell();
                c1.setColspan(2);
                c1.setBorder(Rectangle.NO_BORDER);
                table.addCell(c1);
            }

            i++;
        }

        pdfToPrint.add(table);

        PdfPTable table2 = new PdfPTable(4);
        table2.setWidthPercentage((float) 81);
        table2.setHorizontalAlignment(Element.ALIGN_LEFT);
        table2.setWidths(new float[]{11, 2, 2, 2});

        c1 = new PdfPCell(new Phrase("Totale"));
        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c1.setBackgroundColor(BaseColor.GRAY);
        table2.addCell(c1);

        Font font = new Font(Font.FontFamily.UNDEFINED);
        font.setColor(BaseColor.BLUE);
        c1 = new PdfPCell(new Phrase(df.format(totDare) + "", font));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        c1.setBackgroundColor(BaseColor.GRAY);
        table2.addCell(c1);

        Font font2 = new Font(Font.FontFamily.UNDEFINED);
        font2.setColor(BaseColor.RED);
        c1 = new PdfPCell(new Phrase(df.format(totAvere) + "", font2));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        c1.setBackgroundColor(BaseColor.GRAY);
        table2.addCell(c1);

        c1 = new PdfPCell(new Phrase(df.format(totSaldo) + ""));
        c1.setHorizontalAlignment(Element.ALIGN_LEFT);
        c1.setBackgroundColor(BaseColor.GRAY);
        table2.addCell(c1);

        pdfToPrint.add(table2);

        //Write the workbook in file system
        /* OLD SOLUTION - API 23 AND LOWER */
        readPdfFile(Uri.fromFile(pdf), context, "pdf");

        /* NEW SOLUTION */
        /*Uri fileURI = FileProvider.getUriForFile(
                context,
                context.getApplicationContext().getPackageName() + ".provider",
                pdf);
        readPdfFile(fileURI, context, "pdf");*/

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

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public static void esportaExcel(ArrayList<NotaBanca> notaBancaList, Context context, String month, String year) throws Exception {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GestApp/nota_banca/excel");

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        //make them in case they're not there
        dir.mkdirs();
        //create a standard java.io.File object for the Workbook to use
        File wbfile = new File(dir, month + "-" + year + ".xlsx");

        Workbook wb = new HSSFWorkbook();

        Cell cella;

        CellStyle cs = wb.createCellStyle();

        cs.setFillForegroundColor(HSSFColor.RED.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        Sheet foglio1;
        foglio1 = wb.createSheet("Prima nota banca");

        Row rowTitle = foglio1.createRow(0);

        //CellStyle csTitle = wb.createCellStyle();

        cella = rowTitle.createCell(0);
        cella.setCellValue(month + " " + year);

        Row row = foglio1.createRow(2);

        cella = row.createCell(0);
        cella.setCellValue("DATA OPERAZIONE");
        cella.setCellStyle(cs);

        cella = row.createCell(1);
        cella.setCellValue("DATA VALUTA");
        cella.setCellStyle(cs);

        cella = row.createCell(2);
        cella.setCellValue("DESCRIZIONE MOVIMENTI");
        cella.setCellStyle(cs);

        cella = row.createCell(3);
        cella.setCellValue("FATTURE NR PROTOCOLLO");
        cella.setCellStyle(cs);

        cella = row.createCell(4);
        cella.setCellValue("DARE(€)");
        cella.setCellStyle(cs);

        cella = row.createCell(5);
        cella.setCellValue("AVERE(€)");
        cella.setCellStyle(cs);

        cella = row.createCell(6);
        cella.setCellValue("SALDO(€)");
        cella.setCellStyle(cs);

        cella = row.createCell(7);
        cella.setCellValue("GRUPPO");
        cella.setCellStyle(cs);

        int nRow = 3;

        String[] gruppo = context.getResources().getStringArray(R.array.gruppo);

        float totDare = 0, totAvere = 0, totSaldo = 0;
        for (NotaBanca notaBanca : notaBancaList) {

            totDare += notaBanca.getDare();
            totAvere += notaBanca.getAvere();
            totSaldo += notaBanca.getDare() - notaBanca.getAvere();

            row = foglio1.createRow(nRow++);

            cella = row.createCell(0);
            cella.setCellValue(notaBanca.getDataPagamento());

            cella = row.createCell(1);
            cella.setCellValue(notaBanca.getDataValuta());

            cella = row.createCell(2);
            cella.setCellValue(notaBanca.getDescrizione());

            cella = row.createCell(3);
            cella.setCellValue(notaBanca.getNumeroProtocollo());

            cella = row.createCell(4);
            cella.setCellValue(df.format(notaBanca.getDare()));

            cella = row.createCell(5);
            cella.setCellValue(df.format(notaBanca.getAvere()));

            cella = row.createCell(6);
            cella.setCellValue(df.format(totSaldo));

            if (notaBanca.getGruppo() == 1 || notaBanca.getGruppo() == 2) {
                cella = row.createCell(7);
                cella.setCellValue(gruppo[notaBanca.getGruppo()]);
            }
        }

        row = foglio1.createRow(nRow);

        /*//Accessing the first worksheet in the Excel file
        Worksheet worksheet = wb.getWorksheets().get(0);
        Cells cells = worksheet.getCells();*/

        //Grandezza colonne -> column, size
        foglio1.setColumnWidth(0, 3000);
        foglio1.setColumnWidth(1, 3000);
        foglio1.setColumnWidth(2, 6000);
        foglio1.setColumnWidth(3, 3000);
        foglio1.setColumnWidth(4, 4000);
        foglio1.setColumnWidth(5, 4000);
        foglio1.setColumnWidth(6, 4000);
        foglio1.setColumnWidth(7, 5000);

        CellStyle cs2 = wb.createCellStyle();
        cs2.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
        cs2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        cella = row.createCell(3);
        cella.setCellValue("TOTALE");
        cella.setCellStyle(cs2);

        cella = row.createCell(4);
        cella.setCellValue(df.format(totDare));
        cella.setCellStyle(cs2);

        cella = row.createCell(5);
        cella.setCellValue(df.format(totAvere));
        cella.setCellStyle(cs2);

        cella = row.createCell(6);
        cella.setCellValue(df.format(totSaldo));
        cella.setCellStyle(cs2);

        //Write the workbook in file system
        FileOutputStream out = null;
        System.out.println("xlsx written successfully");

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
            } catch (Exception ignored) {
            }
        }
    }
}
