package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText senha;
    private Button btnLogin;
    private CheckBox cbManterLogado;
    ShowDialog sd = new ShowDialog(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
        if(prefs.getString("usuario", null) != null){

            JSONObject json = null;
            try {
                json = new JSONObject(prefs.getString("usuario", null));
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

                Intent i = new Intent(LoginActivity.this, ProdutosActivity.class);
                startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
                sd.showMessage("Erro ao recuperar seu login.\nPor favor entre com seus dados novamente.","Erro");
            }
        }

        setContentView(R.layout.activity_login);

        usuario = (EditText) findViewById(R.id.etUsuario);
        senha = (EditText) findViewById(R.id.etSenha);
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
                try {

                    /*HttpURLConnection urlConnection = (HttpURLConnection) new URL("").openConnection();

                    InputStream in = urlConnection.getInputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                    StringBuilder resultado = new StringBuilder();
                    String linha = bufferedReader.readLine();

                    while (linha != null) {
                        resultado.append(linha);
                        linha = bufferedReader.readLine();
                    }

                    String respostaCompleta = resultado.toString();*/

                    String respostaCompleta = "{\"idCliente\":1,\"nomeCompletoCliente\":\"Thiago\",\"emailCliente\":\"thiago@bolodesal.com.br\"," +
                                        "\"senhaCliente\":\"bolodesal\", \"CPFCliente\":\"98765432100\",\"celularCliente\":\"11987654321\","+
                                        "\"telComercialCliente\":\"55654175\",\"dtNascCliente\":\"1992-07-30\",\"recebeNewsLetter\":0}";

                    JSONObject json = new JSONObject(respostaCompleta);

                    Cliente u = new Cliente(json.getInt("idCliente"),
                            json.getString("nomeCompletoCliente"),
                            json.getString("emailCliente"),
                            json.getString("senhaCliente"),
                            json.getString("CPFCliente"),
                            (json.getString("celularCliente") != null)?json.getString("celularCliente"):"",
                            (json.getString("telComercialCliente") != null)?json.getString("telComercialCliente"):"",
                            (json.getString("telResidencialCliente") != null)?json.getString("telResidencialCliente"):"",
                            json.getString("dtNascCliente"),
                            json.getInt("recebeNewsLetter"));

                    if(cbManterLogado.isChecked()) {
                        //TODO sharedPreferences Login
                        SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor sharedEditor = prefs.edit();
                        sharedEditor.putString("usuario",respostaCompleta);
                    }

                    SingletonCliente singletonClienteLogado = SingletonCliente.getInstance();
                    singletonClienteLogado.setClienteLogado(u);

                } catch (Exception e) {
                    e.printStackTrace();
                    sd.showMessage("Erro de login","Erro");
                }
            }
        });
    }
}
