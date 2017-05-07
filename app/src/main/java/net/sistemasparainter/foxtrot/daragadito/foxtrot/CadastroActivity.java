package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import retrofit2.Retrofit;

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
                            .baseUrl("http://kymera.solutions/foxtrot/")
                            .build();

                    Services service = retrofit.create(Services.class);

//                    Call<Cliente> respostaCliente = service.createCliente(novoCliente);

//                    showDialog.showMessage(respostaCliente.toString(), "Teste");

                    String respostaCliente = "Deu certo";

                    if (respostaCliente != null){

                        Intent i = new Intent(CadastroActivity.this, LoginActivity.class);
                        i.putExtra("email", etEmail.getText().toString());
                        i.putExtra("senha", etSenha.getText().toString());
                        showDialog.showMessageAndRedirect("Cliente cadastrado com sucesso!","Sucesso", i);

                    }else{
                        showDialog.showMessage("Erro ao cadastrar cliente...","Erro");
                    }

                }catch(Exception e){

                }
            }
        });
    }
}
