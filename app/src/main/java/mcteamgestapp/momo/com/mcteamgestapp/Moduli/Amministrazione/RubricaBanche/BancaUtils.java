package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Amministrazione.RubricaBanche;

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
import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Banca;

/**
 * Created by meddaakouri on 14/01/2016.
 */
public class BancaUtils {

    public static Font tableTitleFond = new Font(Font.FontFamily.UNDEFINED, Font.BOLD);


    public static void print(Banca banca, Context context) throws Exception {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GestApp/banche/pdf");

        File pdf = new File(dir, banca.getNome() + ".pdf");

        tableTitleFond.setColor(BaseColor.WHITE);

        tableTitleFond.setSize(14);

        //creazione tabella
        //make them in case they're not there
        dir.mkdirs();
        Font boldTitle = new Font(Font.FontFamily.HELVETICA, 20f, Font.BOLD);
        Document pdfToPrint = new Document(PageSize.A4.rotate(), 20, 20, 100, 25);


        PdfWriter writer = PdfWriter.getInstance(pdfToPrint, new FileOutputStream(pdf));

        HeaderFooterPageEvent event = new HeaderFooterPageEvent("BANCA");

        event.setImage(context);

        writer.setPageEvent(event);


        pdfToPrint.open();

        //adding meta data
        pdfToPrint.addTitle(banca.getNome());
        Paragraph titolo = new Paragraph(banca.getNome(), boldTitle);
        titolo.setAlignment(Element.ALIGN_CENTER);


        pdfToPrint.add(titolo);

        pdfToPrint.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(2);

        PdfPCell c1 = new PdfPCell(new Phrase("NOMINATIVO", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("INDIRIZZO", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        table.setHeaderRows(1);

        table.addCell(banca.getNome() == null || banca.getNome().equals("") ? " " : banca.getNome());
        table.addCell(banca.getIndirizzo() == null || banca.getIndirizzo().equals("") ? " " : banca.getIndirizzo());

        c1 = new PdfPCell(new Phrase("IBAN", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("REFERENTE", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        table.setHeaderRows(1);

        table.addCell(banca.getIban() == null || banca.getIban().equals("") ? " " : banca.getIban());
        table.addCell(banca.getReferente() == null || banca.getReferente().equals("") ? " " : banca.getReferente());

        pdfToPrint.add(table);

        //create a standard java.io.File object for the Workbook to use

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


    public static void printAll(ArrayList<Banca> banche, Context context) throws Exception {

        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GestApp/banche/pdf");

        File pdf = new File(dir, "stampa_banche.pdf");

        tableTitleFond.setColor(BaseColor.WHITE);

        tableTitleFond.setSize(14);

        //creazione tabella
        //make them in case they're not there
        dir.mkdirs();
        Font boldTitle = new Font(Font.FontFamily.HELVETICA, 20f, Font.BOLD);
        Document pdfToPrint = new Document(PageSize.A4.rotate(), 20, 20, 100, 25);


        PdfWriter writer = PdfWriter.getInstance(pdfToPrint, new FileOutputStream(pdf));

        HeaderFooterPageEvent event = new HeaderFooterPageEvent("STAMPA TUTTO BANCHE");

        event.setImage(context);

        writer.setPageEvent(event);


        pdfToPrint.open();

        PdfPTable table = new PdfPTable(4);

        PdfPCell c1 = new PdfPCell(new Phrase("NOMINATIVO", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("INDIRIZZO", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("IBAN", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("REFERENTE", tableTitleFond));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(BaseColor.RED);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (Banca banca : banche) {
            table.addCell(banca.getNome() == null || banca.getNome().equals("") ? " " : banca.getNome());
            table.addCell(banca.getIndirizzo() == null || banca.getIndirizzo().equals("") ? " " : banca.getIndirizzo());
            table.addCell(banca.getIban() == null || banca.getIban().equals("") ? " " : banca.getIban());
            table.addCell(banca.getReferente() == null || banca.getReferente().equals("") ? " " : banca.getReferente());
        }
        pdfToPrint.add(table);

        //create a standard java.io.File object for the Workbook to use

        readPdfFile(Uri.fromFile(pdf), context, "pdf");

        pdfToPrint.close();
    }

    public static void esportaExcel(ArrayList<Banca> listaBanche, Context context) throws Exception {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GestApp/banche/excel");

        //make them in case they're not there
        dir.mkdirs();
        //create a standard java.io.File object for the Workbook to use
        File wbfile = new File(dir, "Banche.xlsx");

        Workbook wb = new HSSFWorkbook();

        Cell cella = null;

        CellStyle cs = wb.createCellStyle();

        cs.setFillForegroundColor(HSSFColor.RED.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        Sheet foglio1 = null;
        foglio1 = wb.createSheet("Banche");

        Row row = foglio1.createRow(0);

        cella = row.createCell(0);
        cella.setCellValue("Nominativo");
        cella.setCellStyle(cs);

        cella = row.createCell(1);
        cella.setCellValue("Indirizzo");
        cella.setCellStyle(cs);

        cella = row.createCell(2);
        cella.setCellValue("Iban");
        cella.setCellStyle(cs);

        cella = row.createCell(3);
        cella.setCellValue("Referente");
        cella.setCellStyle(cs);

        int nRow = 1;

        for (Banca banca : listaBanche) {

            row = foglio1.createRow(nRow++);

            cella = row.createCell(0);
            cella.setCellValue(banca.getNome());

            cella = row.createCell(1);
            cella.setCellValue(banca.getIndirizzo());

            cella = row.createCell(2);
            cella.setCellValue(banca.getIban());

            cella = row.createCell(3);
            cella.setCellValue(banca.getReferente());

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
