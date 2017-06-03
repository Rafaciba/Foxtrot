package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PagamentoActivity extends AppCompatActivity {

    private Button btnFinalizar;
    private RadioButton cartao;
    private RadioButton boleto;
    private RadioButton pagseguro;
    private RadioButton paypal;

    final ShowDialog showDialog = new ShowDialog(PagamentoActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        cartao = (RadioButton) findViewById(R.id.rbTpPagtoCartao);
        boleto = (RadioButton) findViewById(R.id.rbTpPagtoBoleto);
        pagseguro = (RadioButton) findViewById(R.id.rbTpPagtoPagSeguro);
        paypal = (RadioButton) findViewById(R.id.rbTpPagtoPaypal);
        btnFinalizar = (Button) findViewById(R.id.btnPagtoFinalizar);

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tipoPagto = 1;

                if(cartao.isChecked()) {
                    tipoPagto = 1;
                }else if(boleto.isChecked()) {
                    tipoPagto = 2;
                }else if(pagseguro.isChecked()) {
                    tipoPagto = 3;
                }else {
                    tipoPagto = 4;
                }

                SingletonPedido sp = SingletonPedido.getInstance();
                sp.setIdTipoPagto(tipoPagto);

                finalizaCompra();
            }
        });

    }

    private void finalizaCompra(){

        SingletonCliente sc = SingletonCliente.getInstance();
        SingletonPedido sp = SingletonPedido.getInstance();
        SingletonCarrinho carrinho = SingletonCarrinho.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = sdf.format(new Date());

        Pedido p = new Pedido(0, sc.getClienteLogado().getIdCliente(), 1, currentDate, sp.getIdTipoPagto(), sp.getEndereco().getIdEndereco(), 2);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://foxtrotws.azurewebsites.net/g1/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Services service = retrofit.create(Services.class);

        Call<Void> pedido = service.createPedido(p);

        try {
            pedido.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful() && response.code() == 201){
                        Intent i = new Intent(PagamentoActivity.this, MainActivity.class);
                        showDialog.showMessageAndRedirect("Seu pedido foi enviado para a loja!","Pedido", i);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
