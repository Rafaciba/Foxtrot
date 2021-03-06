package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("aceito", false)) {
            Intent intent = new Intent(CadastroEnderecoActivity.this, TermosActivity.class);
            startActivity(intent);
            return;
        }

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
                    final Cliente cliente = singletonCliente.getClienteLogado();
                    int idCliente = cliente.getIdCliente();



                    // CRIA UM OBJETO DE ENDEREÇO PARA CADASTRA-LO VIA RETROFIT
                    final Endereco novoEnderecoCliente =  new Endereco(
                            idCliente,
                            etNomeEndereco.getText().toString(),
                            etLogradouroEndereco.getText().toString(),
                            etNumeroEndereco.getText().toString(),
                            etCEP.getText().toString(),
                            etComplementoEndereco.getText().toString(),
                            etCidade.getText().toString(),
                            etPais.getText().toString(),
                            etEstado.getText().toString()
                    );

                    // ENVIO DO OBJETO ENDEREÇO PARA O RETROFIT
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://foxtrotws.azurewebsites.net/g1/rest/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Services service = retrofit.create(Services.class);

                    Call<Long> respostaServico = service.setEndereco(novoEnderecoCliente);


                    // TRATAMENTO DA RESPOTA DO SERVIÇO
                    respostaServico.enqueue(new Callback<Long>() {
                        @Override
                        public void onResponse(Call<Long> call, Response<Long> response) {


                            if(response.isSuccessful()){
                                if(response.code() == 200){
                                    showDialog.showMessage("Nome já cadastrado...","Erro");
                                }else if(response.code() == 201){
                                    Intent i = new Intent(CadastroEnderecoActivity.this, PagamentoActivity.class);

                                    novoEnderecoCliente.setIdEndereco(response.body().intValue());

                                    SingletonPedido sp = SingletonPedido.getInstance();
                                    sp.setEndereco(novoEnderecoCliente);

                                    showDialog.showMessageAndRedirect("Endereço cadastrado com sucesso!","Sucesso", i);
                                }else if(response.code() == 500){
                                    showDialog.showMessage("Erro ao cadastrar cliente...","Erro");
                                }
                            }
                            else {
                                System.out.println(response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<Long> call, Throwable t) {

                            showDialog.showMessage("Erro ao cadastrar endereço...","Erro");
                        }
                    });


                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


}
