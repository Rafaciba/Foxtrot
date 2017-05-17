package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroEnderecoActivity extends AppCompatActivity {

    private EditText etNomeEndereco;
    private EditText etCEP;
    private EditText etLogradouroEndereco;
    private EditText etNumeroEndereco;
    private EditText etComplementoEndereco;
    private EditText etCidade;
    private EditText etEstado;
    private EditText etPais;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);

        // INSTANCIA DA CLASSE showDialog UTILIZADA PARA FAZER DIÁLOGOS
        final ShowDialog showDialog = new ShowDialog(CadastroEnderecoActivity.this);

        etNomeEndereco = (EditText) findViewById(R.id.etEmail);
        etCEP = (EditText) findViewById(R.id.etCEP);
        etLogradouroEndereco = (EditText) findViewById(R.id.etLogradouroEndereco);
        etNumeroEndereco = (EditText) findViewById(R.id.etNumeroEndereco);
        etComplementoEndereco = (EditText) findViewById(R.id.etComplementoEndereco);
        etCidade = (EditText) findViewById(R.id.etCidade);
        etEstado = (EditText) findViewById(R.id.etEstado);
        etPais = (EditText) findViewById(R.id.etPais);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // VERIFICA SE ALGUM CAMPO FOI DEIXADO EM BRANCO
                if (etNomeEndereco.getText().toString().equals("") ||
                        etCEP.getText().toString().equals("") ||
                        etLogradouroEndereco.getText().toString().equals("") ||
                        etNumeroEndereco.getText().toString().equals("") ||
                        etCidade.getText().toString().equals("") ||
                        etEstado.getText().toString().equals("") ||
                        etPais.getText().toString().equals("")){
                    showDialog.showMessage("Por favor preencha todos os campos", "Erro");
                    return;
                }

                try{
                    // PEGA O CLIENTE QUE ESTÁ LOGADO E PEGA SEU ID

                    SingletonCliente singletonCliente = SingletonCliente.getInstance();
                    Cliente cliente = singletonCliente.getClienteLogado();
//                    int idCliente = cliente.getIdCliente();
                    int idCliente = 88;


                    // CRIA UM OBJETO DE ENDEREÇO PARA CADASTRA-LO VIA RETROFIT
                    Endereco novoEnderecoCliente =  new Endereco(
                            idCliente,
                            etNomeEndereco.getText().toString(),
                            etCEP.getText().toString(),
                            etLogradouroEndereco.getText().toString(),
                            etNumeroEndereco.getText().toString(),
                            etComplementoEndereco.getText().toString(),
                            etCidade.getText().toString(),
                            etEstado.getText().toString(),
                            etPais.getText().toString()
                    );

                    // ENVIO DO OBJETO ENDEREÇO PARA O RETROFIT
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://kymera.solutions/foxtrot/")
                            .build();

                    Services service = retrofit.create(Services.class);

                    Call<Void> respostaServico = service.setEndereco(novoEnderecoCliente);


                    // TRATAMENTO DA RESPOTA DO SERVIÇO
                    respostaServico.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                            Intent i = new Intent(CadastroEnderecoActivity.this, SwitcherActivity.class);

                            int idEndereco = 98;
//                        novoEnderecoCliente.setCliente(cliente);
                            showDialog.showMessageAndRedirect("Endereço cadastrado com sucesso!","Sucesso", i);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                            showDialog.showMessage("Erro ao cadastrar endereço...","Erro");
                        }
                    });


                }catch(Exception e){

                }
            }
        });
    }


}
