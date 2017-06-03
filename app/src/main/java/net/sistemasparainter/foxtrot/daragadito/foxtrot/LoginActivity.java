package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText senha;
    private Button btnLogin;
    private CheckBox cbManterLogado;
    ShowDialog sd = new ShowDialog(LoginActivity.this);
    private Cliente usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getBoolean("aceito", false)) {
            Intent intent = new Intent(LoginActivity.this, TermosActivity.class);
            startActivity(intent);
            return;
        }

        SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
        if(prefs.getString("usuario", null) != null){

            try {
                JSONObject json = new JSONObject(prefs.getString("usuario", null));
                Cliente u = new Cliente(json.getInt("idCliente"),
                        json.getString("nomeCompletoCliente"),
                        json.getString("emailCliente"),
                        json.getString("senhaCliente"),
                        json.getString("CPFCliente"),
                        json.getString("celularCliente"),
                        json.getString("telComercialCliente"),
                        json.getString("telResidencialCliente"),
                        json.getString("dtNascCliente"),
                        json.getInt("recebeNewsLetter"));

                SingletonCliente singletonClienteLogado = SingletonCliente.getInstance();
                singletonClienteLogado.setClienteLogado(u);

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
                sd.showMessage("Erro ao recuperar seu login.\nPor favor entre com seus dados novamente.","Erro");
            }
        }

        setContentView(R.layout.activity_login);

        final ProgressDialog progress = new ProgressDialog(LoginActivity.this);

        usuario = (EditText) findViewById(R.id.etUsuario);
        senha = (EditText) findViewById(R.id.etCEP);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        cbManterLogado = (CheckBox) findViewById(R.id.cbManterLogado);

        Intent userInfos = getIntent();

        if (userInfos != null){
            usuario.setText(userInfos.getStringExtra("email"));
            senha.setText(userInfos.getStringExtra("senha"));
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress.setTitle("Login");
                progress.setMessage("Fazendo login ...");
                progress.setCancelable(false);
                progress.show();

                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://foxtrotws.azurewebsites.net/g1/rest/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Services service = retrofit.create(Services.class);

                    Login login = new Login(usuario.getText().toString(), senha.getText().toString());

                    Call<Cliente> respostaCliente = service.doLogin(login);

                    respostaCliente.enqueue(new Callback<Cliente>() {
                        @Override
                        public void onResponse(Call<Cliente> call, Response<Cliente> response) {

                            usuarioLogado = response.body();

                            JSONObject clienteJson = new JSONObject();
                            try {
                                clienteJson.put("idCliente", usuarioLogado.getIdCliente());
                                clienteJson.put("nomeCompletoCliente", usuarioLogado.getNomeCompletoCliente());
                                clienteJson.put("emailCliente", usuarioLogado.getEmailCliente());
                                clienteJson.put("senhaCliente", usuarioLogado.getSenhaCliente());
                                clienteJson.put("CPFCliente", usuarioLogado.getCPFCliente());
                                clienteJson.put("celularCliente", usuarioLogado.getCelularCliente());
                                clienteJson.put("telComercialCliente", usuarioLogado.getTelComercialCliente());
                                clienteJson.put("telResidencialCliente", usuarioLogado.getTelResindencialCliente());
                                clienteJson.put("dtNascCliente", usuarioLogado.getDtNascCliente());
                                clienteJson.put("recebeNewsLetter", usuarioLogado.getRecebeNewsLetter());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if(cbManterLogado.isChecked()) {
                                //TODO sharedPreferences Login
                                SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
                                SharedPreferences.Editor sharedEditor = prefs.edit();
                                sharedEditor.putString("usuario", clienteJson.toString());
                            }

                            SingletonCliente singletonClienteLogado = SingletonCliente.getInstance();
                            singletonClienteLogado.setClienteLogado(usuarioLogado);

                            progress.dismiss();

                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<Cliente> call, Throwable t) {
                            progress.dismiss();
                            sd.showMessage("Usuário ou senha inválidos","Erro");
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    sd.showMessage("Erro de login","Erro");
                }
            }
        });
    }
}
