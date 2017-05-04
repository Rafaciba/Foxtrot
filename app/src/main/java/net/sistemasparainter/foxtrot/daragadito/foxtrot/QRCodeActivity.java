package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeActivity extends AppCompatActivity {

    private ZXingScannerView scannerView;
    ShowDialog showDialog = new ShowDialog(QRCodeActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // instanciar scannerView
        scannerView = new ZXingScannerView(QRCodeActivity.this);
        setContentView(scannerView);

        //checa permissão e solicita
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    // Executa quando a função termina a criação e starta a camera
    @Override
    protected void onResume() {
        super.onResume();
        final Intent output = new Intent();

        scannerView.startCamera();
        scannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                // show the scanner result into dialog box.
                showDialog.showMessage(result.getText().toString(), "ID do Produto");
//                output.putExtra("return", result.getText().toString());
//                setResult(RESULT_OK, output);
//                finish();
            }
        });

    }

    // Pausa a aplicação e stopa a camera
    @Override
    protected void onPause(){
        super.onPause();
        scannerView.stopCamera();
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_DENIED) {
                Toast toast = Toast.makeText(QRCodeActivity.this, "Não é possível utilizar a aplicação " +
                        "sem permissão de acesso a câmera. Saindo...", Toast.LENGTH_LONG);
                toast.show();
                finish();
            }
        }
    }
}
