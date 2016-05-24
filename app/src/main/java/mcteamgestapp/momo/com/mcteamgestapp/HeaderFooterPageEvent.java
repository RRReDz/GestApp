package mcteamgestapp.momo.com.mcteamgestapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;


import org.apache.poi.hssf.usermodel.HeaderFooter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by meddaakouri on 08/01/2016.
 */
public class HeaderFooterPageEvent extends PdfPageEventHelper {
    Context mContext;
    Image mLogo;
    PdfTemplate total;
    String mTitle;

    public HeaderFooterPageEvent(String title) {
        mTitle = title;
    }

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(30, 16);
    }

    public void setImage(Context mContext) {
        Drawable logo = mContext.getResources().getDrawable(R.drawable.ic_mcteamlogo);
        try {
            // get input stream
            Bitmap bmp = ((BitmapDrawable) logo).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            mLogo = Image.getInstance(stream.toByteArray());
            mLogo.setAlignment(Element.ALIGN_CENTER);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        }
    }


    Font tableTitleFond = new Font(Font.FontFamily.UNDEFINED, Font.BOLD);

    @Override
    public void onEndPage(PdfWriter writer, Document document) {

        tableTitleFond.setSize(18);

        PdfPTable header = new PdfPTable(4);
        header.setTotalWidth(400);
        header.getDefaultCell().setBorder(Rectangle.BOTTOM);

        mLogo.setAbsolutePosition(document.left(), document.top());
        mLogo.scaleAbsolute(50, 50);


        PdfPTable myTable = new PdfPTable(5);
        myTable.setTotalWidth(700);

        PdfPCell logo = new PdfPCell(mLogo);
        PdfPCell cellTwo = new PdfPCell(new Paragraph("Gesteam", tableTitleFond));
        cellTwo.setHorizontalAlignment(Element.ALIGN_LEFT);

        PdfPCell title = new PdfPCell(new Paragraph(mTitle.toUpperCase(), tableTitleFond));

        logo.setBorder(Rectangle.BOTTOM);
        logo.setHorizontalAlignment(Element.ALIGN_LEFT);

        cellTwo.setBorder(Rectangle.BOTTOM);
        cellTwo.setHorizontalAlignment(Element.ALIGN_LEFT);

        title.setBorder(Rectangle.BOTTOM);
        title.setHorizontalAlignment(Element.ALIGN_LEFT);
        title.setColspan(2);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

        String formatted = format1.format(calendar.getTime());

        PdfPCell cellDate = new PdfPCell(new Paragraph(formatted, tableTitleFond));
        cellDate.setBorder(Rectangle.BOTTOM);
        cellDate.setHorizontalAlignment(Element.ALIGN_CENTER);

        myTable.addCell(logo);
        myTable.addCell(cellTwo);
        myTable.addCell(title);
        myTable.addCell(cellDate);

        myTable.writeSelectedRows(0, -1, document.left() + 60, document.top() + 80, writer.getDirectContent());

    }

}
