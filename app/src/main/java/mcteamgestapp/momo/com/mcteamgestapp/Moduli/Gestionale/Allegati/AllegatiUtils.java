package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Allegati;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * Created by meddaakouri on 18/01/2016.
 */
public class AllegatiUtils {
    public static String getFileExt(String fileName) {
        return fileName.substring((fileName.lastIndexOf(".") + 1), fileName.length());
    }

    public static Bitmap getAllegatoLogo(Resources resources, String fileName) {

        String extention = getFileExt(fileName);

        Bitmap extLogo = null;

        switch (extention) {
            case "apk":
                extLogo = BitmapFactory.decodeResource(resources, R.drawable.apk);
                break;
            case "ppt":
                extLogo = BitmapFactory.decodeResource(resources, R.drawable.ppt);
                break;
            case "rar":
                extLogo = BitmapFactory.decodeResource(resources, R.drawable.rar);
                break;
            case "pdf":
                extLogo = BitmapFactory.decodeResource(resources, R.drawable.pdf);
                break;
            case "zip":
                extLogo = BitmapFactory.decodeResource(resources, R.drawable.zip);
                break;
            case "docx":
                extLogo = BitmapFactory.decodeResource(resources, R.drawable.docx);
                break;
            case "doc":
                extLogo = BitmapFactory.decodeResource(resources, R.drawable.doc);
                break;
            case "js":
                extLogo = BitmapFactory.decodeResource(resources, R.drawable.js);
                break;
            case "jpg":
                extLogo = BitmapFactory.decodeResource(resources, R.drawable.jpeg);
                break;
            case "xls":
                extLogo = BitmapFactory.decodeResource(resources, R.drawable.xls);
                break;
            case "xlsx":
                extLogo = BitmapFactory.decodeResource(resources, R.drawable.xlsx);
                break;
            case "png":
                extLogo = BitmapFactory.decodeResource(resources, R.drawable.png);
                break;
            default:
                break;
        }
        return extLogo;
    }
}
