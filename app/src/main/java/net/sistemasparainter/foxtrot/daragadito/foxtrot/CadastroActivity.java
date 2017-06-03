package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("aceito", false)) {
            Intent intent = new Intent(CadastroActivity.this, TermosActivity.class);
            startActivity(intent);
            return;
        }

        final ShowDialog showDialog = new ShowDialog(CadastroActivity.this);

        etNome = (EditText) findViewById(R.id.etNome);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSenha = (EditText) findViewById(R.id.etCEP);
        etConfSenha = (EditText) findViewById(R.id.etLogradouroEndereco);
        etCpf = (EditText) findViewById(R.id.etNumeroEndereco);
        etCelular = (EditText) findViewById(R.id.etComplementoEndereco);
        etTelComercial = (EditText) findViewById(R.id.etTelComercial);
        etTelResidencial = (EditText) findViewById(R.id.etTelResidencial);
        etDtNascimento = (EditText) findViewById(R.id.etDtNascimento);
        cbNewsletter = (CheckBox) findViewById(R.id.cbNewsletter);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNome.getText().toString().equals("") ||
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

                Integer getNewsletter = cbNewsletter.isChecked() ? 1 : 0;

                try{

                    Cliente novoCliente =  new Cliente(
                            etNome.getText().toString(),
                            etEmail.getText().toString(),
                            etSenha.getText().toString(),
                            etCpf.getText().toString(),
                            etCelular.getText().toString(),
                            etTelComercial.getText().toString(),
                            etTelResidencial.getText().toString(),
                            etDtNascimento.getText().toString(),
                            getNewsletter
                    );

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://foxtrotws.azurewebsites.net/g1/rest/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Services service = retrofit.create(Services.class);

                    Call<Void> respostaCliente = service.setCliente(novoCliente);

                    respostaCliente.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                                if(response.code() == 200){
                                    showDialog.showMessage("E-mail já cadastrado...","Erro");
                                }else if(response.code() == 201){
                                    Intent i = new Intent(CadastroActivity.this, LoginActivity.class);
                                    i.putExtra("email", etEmail.getText().toString());
                                    i.putExtra("senha", etSenha.getText().toString());
                                    showDialog.showMessageAndRedirect("Cliente cadastrado com sucesso!","Sucesso", i);
                                }else if(response.code() == 500){
                                    showDialog.showMessage("Erro ao cadastrar cliente...","Erro");
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            showDialog.showMessage("Erro...","Erro");

                        }
                    });


                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
