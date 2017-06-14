package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Rafael on 12/06/2017.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if(NetworkUtil.getConnectivityStatus(context) == 0){
            /*ShowDialog sd = new ShowDialog(context);
            sd.showConnectionMessage();*/
            Intent i = new Intent(context,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }

        //Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}
