package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rafael.fccibim on 26/04/2017.
 */

public class ShowDialog {

    private Context atividade;

    public ShowDialog (Context atividade){
        this.atividade = atividade;
    }

    public void showMessage(String val, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(atividade);
        builder.setMessage(val);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showMessageAndRedirect(String val, String title, final Intent i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(atividade);
        builder.setMessage(val);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                atividade.startActivity(i);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showConnectionMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(atividade);
        builder.setMessage("Verifique sua conexão com a internet e tente novamente.");
        builder.setTitle("Falha de conexão");
        builder.setCancelable(false);
        builder.setPositiveButton("Tentar novamente", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(NetworkUtil.getConnectivityStatus(atividade) == 0){
                    showConnectionMessage();
                }else{
                    Intent i = new Intent(atividade,MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    atividade.startActivity(i);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
