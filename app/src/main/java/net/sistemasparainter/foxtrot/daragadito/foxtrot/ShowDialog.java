package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rafael.fccibim on 26/04/2017.
 */

public class ShowDialog {

    private AppCompatActivity atividade;

    public ShowDialog (AppCompatActivity atividade){
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
}
