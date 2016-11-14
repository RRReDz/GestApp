package com.mcteam.gestapp.Moduli.Produzione.Consuntivi;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
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
import com.mcteam.gestapp.Models.Associazione;
import com.mcteam.gestapp.Models.Commessa;
import com.mcteam.gestapp.Models.OrariAttivita;
import com.mcteam.gestapp.Models.UserInfo;
import com.mcteam.gestapp.Utils.HeaderFooterPageEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Riccardo Rossi on 12/02/2016.
 */
public class ConsuntivoUtils {

    public static Font tableTitleFond = new Font(Font.FontFamily.UNDEFINED, Font.BOLD);

    public static void printSimple(ArrayList<Associazione> associazioniList, ArrayList<OrariAttivita> attivita, Context context, UserInfo risorsa) throws Exception {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GestApp/Consuntivi/pdf");

        File pdf = new File(dir, risorsa.getCognome() + "-" + attivita.get(0).getMonth_name() + ".pdf");

        //creazione tabella
        //make them in case they're not there
        dir.mkdirs();
        Font boldTitle = new Font(Font.FontFamily.HELVETICA, 20f, Font.BOLD);
        Document pdfToPrint = new Document(PageSize.A4, 20, 20, 100, 25);
        FileOutputStream fos = new FileOutputStream(pdf);
        PdfWriter writer = PdfWriter.getInstance(pdfToPrint, fos);
        HeaderFooterPageEvent event = new HeaderFooterPageEvent("");
        event.setImage(context);

        writer.setPageEvent(event);
        pdfToPrint.open();

        for (Associazione ass : associazioniList) {
            Commessa commessa = ass.getCommessa();

            tableTitleFond.setColor(BaseColor.BLACK);
            tableTitleFond.setSize(14);

            //adding meta data
            Paragraph titolo = new Paragraph("REPORT CONSUNTIVO COMMESSA", boldTitle);
            titolo.setAlignment(Element.ALIGN_CENTER);

            PdfPTable table = new PdfPTable(1);

            PdfPCell c1 = new PdfPCell(titolo);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            pdfToPrint.add(table);

            table = new PdfPTable(1);

            c1 = new PdfPCell(new Phrase("  ", tableTitleFond));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(Rectangle.NO_BORDER);
            table.addCell(c1);

            pdfToPrint.add(table);

            table = new PdfPTable(4);

            c1 = new PdfPCell(new Phrase("ID", tableTitleFond));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Commessa", tableTitleFond));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Cliente", tableTitleFond));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Risorsa", tableTitleFond));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            table.setHeaderRows(1);

            table.addCell(commessa.getCodice_commessa());
            table.addCell(commessa.getNome_commessa());
            table.addCell(commessa.getCliente() != null ? commessa.getCliente().getNomeSocietà() : "");
            table.addCell(risorsa.getCognome() + " " + risorsa.getNome());

            pdfToPrint.add(table);

            table = new PdfPTable(1);

            c1 = new PdfPCell(new Phrase("  "));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(Rectangle.NO_BORDER);
            table.addCell(c1);

            pdfToPrint.add(table);

            //******************************************************
            //          CICLO INSERIMENTO ATTIVITA
            //******************************************************
            table = new PdfPTable(5);
            table.setWidths(new float[]{0.75f, 0.55f, 0.68f, 3f, 0.55f});

            c1 = new PdfPCell(new Phrase("Data", tableTitleFond));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setColspan(2);
            c1.setBackgroundColor(BaseColor.RED);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Durata", tableTitleFond));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.RED);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Descrizione", tableTitleFond));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.RED);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Totale", tableTitleFond));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.RED);
            table.addCell(c1);

            table.setHeaderRows(1);
            double totale = 0;

            for (OrariAttivita att : attivita) {
                if (att.getId_commessa() == commessa.getID()) {
                    if (att.getDay_of_week() == Calendar.SATURDAY || att.getDay_of_week() == Calendar.SUNDAY || att.isFerie()) {
                        c1 = new PdfPCell(new Phrase(att.getDay_name()));
                        c1.setBackgroundColor(BaseColor.RED);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(att.getDay() + " " + att.getMonth_name()));
                        c1.setBackgroundColor(BaseColor.RED);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(att.getOre_totali() + ""));
                        c1.setBackgroundColor(BaseColor.RED);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(att.getDescrizione()));
                        c1.setBackgroundColor(BaseColor.RED);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(att.getOre_totali() + ""));
                        c1.setBackgroundColor(BaseColor.RED);
                        table.addCell(c1);
                    } else {
                        table.addCell(att.getDay_name());
                        table.addCell(att.getDay() + " " + att.getMonth_name());
                        table.addCell(att.getOre_totali() + "");
                        table.addCell(att.getDescrizione());
                        table.addCell(att.getOre_totali() + "");
                    }
                    totale += att.getOre_totali();
                } else if (att.getOtherHalf() != null && att.getOtherHalf().getCommessa().getID() == commessa.getID()) {
                    if (att.getOtherHalf().getDay_of_week() == Calendar.SATURDAY || att.getOtherHalf().getDay_of_week() == Calendar.SUNDAY || att.isFerie()) {
                        c1 = new PdfPCell(new Phrase(att.getDay_name()));
                        c1.setBackgroundColor(BaseColor.RED);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(att.getDay() + " " + att.getMonth_name()));
                        c1.setBackgroundColor(BaseColor.RED);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase("0.5"));
                        c1.setBackgroundColor(BaseColor.RED);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(att.getOtherHalf().getDescrizione()));
                        c1.setBackgroundColor(BaseColor.RED);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase("0.5"));
                        c1.setBackgroundColor(BaseColor.RED);
                        table.addCell(c1);
                    } else {
                        table.addCell(att.getOtherHalf().getDay_name());
                        table.addCell(att.getOtherHalf().getDay() + " " + att.getMonth_name());
                        table.addCell("0.5");
                        table.addCell(att.getOtherHalf().getDescrizione());
                        table.addCell("0.5");
                    }
                    totale += 0.5;
                } else {
                    if (att.getDay_of_week() == Calendar.SATURDAY || att.getDay_of_week() == Calendar.SUNDAY || att.isFerie()) {
                        c1 = new PdfPCell(new Phrase(att.getDay_name()));
                        c1.setBackgroundColor(BaseColor.RED);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(att.getDay() + " " + att.getMonth_name()));
                        c1.setBackgroundColor(BaseColor.RED);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase("0.0"));
                        c1.setBackgroundColor(BaseColor.RED);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase(" "));
                        c1.setBackgroundColor(BaseColor.RED);
                        table.addCell(c1);

                        c1 = new PdfPCell(new Phrase("0.0"));
                        c1.setBackgroundColor(BaseColor.RED);
                        table.addCell(c1);
                    } else {
                        table.addCell(att.getDay_name());
                        table.addCell(att.getDay() + " " + att.getMonth_name());
                        table.addCell("0.0");
                        table.addCell(" ");
                        table.addCell("0.0");
                    }
                }
            }

            pdfToPrint.add(table);

            table = new PdfPTable(1);

            c1 = new PdfPCell(new Phrase("  ", tableTitleFond));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBorder(Rectangle.NO_BORDER);
            table.addCell(c1);

            pdfToPrint.add(table);

            table = new PdfPTable(4);
            table.setWidths(new float[]{1.5f, 0.68f, 3f, 0.6f});

            c1 = new PdfPCell(new Phrase("TOTALE MESE:", new Font(Font.FontFamily.UNDEFINED, 10)));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.YELLOW);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(totale + "", tableTitleFond));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.YELLOW);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("", tableTitleFond));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.YELLOW);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase(totale + "", tableTitleFond));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setBackgroundColor(BaseColor.YELLOW);
            table.addCell(c1);

            //table.setHeaderRows(1);

            pdfToPrint.add(table);

            pdfToPrint.newPage();
        }
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
}
