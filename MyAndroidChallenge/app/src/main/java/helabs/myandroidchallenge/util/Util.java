package helabs.myandroidchallenge.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Carlos Alexandre on 05/06/2015.
 */
public class Util {

    public static void toastShort(Context context, String messsage){
        Toast.makeText(context, messsage, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context context, String messsage){
        Toast.makeText(context, messsage, Toast.LENGTH_LONG).show();
    }


    public static void alertDialogYesNo(String titulo, String mensagem, final Context context){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Título
        alertDialog.setTitle(titulo);

        // Mensagem
        alertDialog.setMessage(mensagem);

        // Iconé se houver
        //alertDialog.setIcon(R.drawable.delete);

        // Configura o botão OK
        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(context,"You clicked on YES", Toast.LENGTH_SHORT).show();
                    }
                });

        // Configura o botão Cancelar
        /*alertDialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(context,"You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });*/

        // Mostra Alert Dialog
        alertDialog.show();
    }

    public static boolean isConnectingToInternet(Context c) {
        ConnectivityManager connectivity = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static String convertTimestamp (String recebe){
        long seconds = Long.parseLong(recebe);
        long millis = seconds * 1000;
        Date date = new Date(millis);
        //SimpleDateFormat sdf = new SimpleDateFormat("EEEE,MMMM d,yyyy h:mm,a", Locale.ENGLISH);
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = sdf.format(date);
        //System.out.println(formattedDate);
        return formattedDate;
    }

}
