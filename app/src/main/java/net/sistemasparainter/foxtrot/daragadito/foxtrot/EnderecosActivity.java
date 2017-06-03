package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnderecosActivity extends AppCompatActivity {

    protected ViewGroup linearContainer;
    private Button btnAddEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enderecos);

        linearContainer = (ViewGroup) findViewById(R.id.linearEnderecoContainer);
        btnAddEndereco = (Button) findViewById(R.id.btnAddEndereco);

        btnAddEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EnderecosActivity.this, CadastroEnderecoActivity.class);
                startActivity(i);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://foxtrotws.azurewebsites.net/g1/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Services service = retrofit.create(Services.class);

        SingletonCliente sc = SingletonCliente.getInstance();

        Call<ArrayList<Endereco>> enderecos = service.getEndereco(""+sc.getClienteLogado().getIdCliente());

        try {
        enderecos.enqueue(new Callback<ArrayList<Endereco>>() {
                @Override
                public void onResponse(Call<ArrayList<Endereco>> call, Response<ArrayList<Endereco>> response) {
                    if(response.isSuccessful() && response.body() != null) {
                        for (Endereco e : response.body()) {
                            addCardView(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Endereco>> call, Throwable t) {

                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void addCardView(Endereco e){
        CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.carrinho_cardview, linearContainer, false);

        final Endereco end = e;

        TextView tvNome = (TextView) cardView.findViewById(R.id.tvNomeEndereco);
        tvNome.setText(e.getNomeEndereco());

        TextView tvLogradouro = (TextView) cardView.findViewById(R.id.tvLogradouroEndereco);
        tvLogradouro.setText(e.getLogradouroEndereco()+", "+e.getNumeroEndereco());

        TextView tvCEP = (TextView) cardView.findViewById(R.id.tvCEPEndereco);
        tvCEP.setText(e.getCidadeEndereco()+" - "+e.getUFEndereco()+" - "+e.getPaisEndereco());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonPedido sp = SingletonPedido.getInstance();
                sp.setEndereco(end);
            }
        });

        linearContainer.addView(cardView);
    }
}
