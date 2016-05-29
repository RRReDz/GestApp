package mcteamgestapp.momo.com.mcteamgestapp;

import android.app.DownloadManager;
import android.content.Context;
import android.net.ParseException;
import android.net.Uri;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by meddaakouri on 12/01/2016.
 */
public class ToolUtils {

    private static final String DATE_PATTERN =
            "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";

    public static boolean validateDate(final String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        //dateFormat.setLenient(false);
        try {
            //dateFormat.parse(date.trim());
            dateFormat.parse(date);
        } catch (java.text.ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean validateReverseDate(final String date) {

        Date parsedDate = null;
        try {
            parsedDate = java.sql.Date.valueOf(date);
        } catch (Exception e) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parsedDate);

        if (calendar.get(Calendar.YEAR) < 1900) {
            return false;
        }

        return true;
    }

    public static String getFormattedDate(String sqlDateFormat) throws ParseException {

        Date parsedDate = java.sql.Date.valueOf(sqlDateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parsedDate);

        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");

        String formatted = format1.format(calendar.getTime());

        return formatted;

    }

    public static String fromDateToSql(String date) throws java.text.ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date parsed = format.parse(date);
        java.sql.Date sql = new java.sql.Date(parsed.getTime());
        return sql.toString();
    }

    public static void downloadFile(String path, Context context) {
        String url = context.getResources().getString(R.string.download_url);
        url += path;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Download in corso del file ");
        request.setTitle(path);

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, path);

        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    public static <T> List<T> union(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if (list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }

    public static boolean validateNormalDate(final String date) {


        final String DATE_VALIDATION_PATTERN = "(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)";

        Pattern pattern = Pattern.compile(DATE_VALIDATION_PATTERN);

        Matcher matcher = pattern.matcher(date);


        System.out.println("ValidateNormalDate -> before matches");

        if (matcher.matches()) {
            System.out.println("ValidateNormalDate -> matches");

            matcher.reset();

            if (matcher.find()) {

                System.out.println(" DD -> " + matcher.group(1) + " MM -> " + matcher.group(2) + "YYYY ->" + matcher.group(3));

                String dd = matcher.group(1);
                String mm = matcher.group(2);
                int yy = Integer.parseInt(matcher.group(3));

                if (dd.equals("31") && (mm.equals("4") || mm.equals("6") || mm.equals("9") ||
                        mm.equals("11") || mm.equals("04") || mm.equals("06") ||
                        mm.equals("09"))) {
                    return false;
                } else if (mm.equals("2") || mm.equals("02")) {

                    if (yy % 4 == 0) {
                        if (dd.equals("30") || dd.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (dd.equals("29") || dd.equals("30") || dd.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.i("height of listItem:", String.valueOf(totalHeight));
    }

    //Nascondere o meno ListView e ProgressBar
    public static void showProgress(View listView, View progressBar, boolean show) {
        if (show) {
            listView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * Round to certain number of decimals
     *
     * @param d
     * @param decimalPlace
     * @return
     */
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
