package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CadastroActivity extends AppCompatActivity {

    private EditText etNome;
    private EditText etEmail;
    private EditText etSenha;
    private EditText etConfSenha;
    private EditText etCpf;
    private EditText etCelular;
    private EditText etTelComercial;
    private EditText etTelResidencial;
    private EditText etDtNascimento;
    private CheckBox cbNewsletter;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        final ShowDialog showDialog = new ShowDialog(CadastroActivity.this);

        etNome = (EditText) findViewById(R.id.etNome);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSenha = (EditText) findViewById(R.id.etSenha);
        etConfSenha = (EditText) findViewById(R.id.etConfSenha);
        etCpf = (EditText) findViewById(R.id.etCpf);
        etCelular = (EditText) findViewById(R.id.etCelular);
        etTelComercial = (EditText) findViewById(R.id.etTelComercial);
        etTelResidencial = (EditText) findViewById(R.id.etTelResidencial);
        etDtNascimento = (EditText) findViewById(R.id.etDtNascimento);
        cbNewsletter = (CheckBox) findViewById(R.id.cbNewsletter);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNome.getText().toString().equals("") ||
                        etNome.getText().toString().equals("") ||
                        etEmail.getText().toString().equals("") ||
                        etSenha.getText().toString().equals("") ||
                        etConfSenha.getText().toString().equals("") ||
                        etCpf.getText().toString().equals("") ||
                        etCelular.getText().toString().equals("") ||
                        etTelComercial.getText().toString().equals("") ||
                        etTelResidencial.getText().toString().equals("") ||
                        etDtNascimento.getText().toString().equals("")) {
                    showDialog.showMessage("Por favor preencha todos os campos", "Erro");
                    return;
                }

                if (!etSenha.getText().toString().equals(etConfSenha.getText().toString())){
                    showDialog.showMessage("As senhas devem ser iguais", "Erro");
                    return;
                }

//                try{
//
//                    HttpURLConnection urlConnection = (HttpURLConnection) new URL("").openConnection();
//
//                    InputStream in = urlConnection.getInputStream();
//
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
//
//                    StringBuilder resultado = new StringBuilder();
//                    String linha = bufferedReader.readLine();
//
//
//                }catch(Exception e){
//
//                }
            }
        });
    }
}
