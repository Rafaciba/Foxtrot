package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.math.BigDecimal;

public class CarrinhoActivity extends AppCompatActivity {

    protected ViewGroup linearContainer;
    protected Button btProsseguir;
    protected TextView tvTotal;

    // INSTANCIA O SINGLETON QUE CONTÉM O CARRINHO
    SingletonCarrinho singletonCarrinho = SingletonCarrinho.getInstance();
    final ShowDialog showDialog = new ShowDialog(CarrinhoActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("aceito", false)) {
            Intent intent = new Intent(CarrinhoActivity.this, TermosActivity.class);
            startActivity(intent);
            return;
        }

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        linearContainer = (ViewGroup) findViewById(R.id.linearContainerCarrinho);
        btProsseguir = (Button) findViewById(R.id.btProsseguir);
        tvTotal = (TextView) findViewById(R.id.tvTotal);

        if(singletonCarrinho.checaSeCarrinhoVazio()){

            btProsseguir.setText("Ir para produtos");

            // AÇÃO DO BOTÃO
            btProsseguir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(CarrinhoActivity.this,MainActivity.class);
                    startActivity(i);

                }
            });
        }else{

            btProsseguir.setText("Fianlizar compra");

            // CRIA UM CARDVIEW PARA CADA ITEM DO CARRINHO
            for(ItemCarrinho ic : singletonCarrinho.getItensCarrinho()) {
                addCardView(ic);
            }

            tvTotal.setText("Total: R$ "+singletonCarrinho.TotalCarrinho().floatValue());

            // AÇÃO DO BOTÃO
            btProsseguir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i;

                    SingletonCliente singletonCliente = SingletonCliente.getInstance();

                    if(singletonCliente.estaLogado()) {
                        i = new Intent(CarrinhoActivity.this, EnderecosActivity.class);
                    } else {

                        SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
                        System.out.println("CLIENTE: "+prefs.getString("usuario", "nenhum"));
                        if (prefs.getString("usuario", null) != null) {

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

                                singletonCliente = SingletonCliente.getInstance();
                                singletonCliente.setClienteLogado(u);

                                showDialog.showMessage("Logado: " + singletonCliente.getClienteLogado().getIdCliente(), "Login");

                                i = new Intent(CarrinhoActivity.this, EnderecosActivity.class);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                i = new Intent(CarrinhoActivity.this, LoginActivity.class);
                                i.putExtra("compra","comprando");
                                showDialog.showMessageAndRedirect("Erro ao recuperar seu login.\nPor favor entre com seus dados novamente.", "Erro", i);
                            }
                        } else {
                            i = new Intent(CarrinhoActivity.this, LoginActivity.class);
                            i.putExtra("compra","comprando");
                        }
                    }

                    startActivity(i);
                }
            });
        }

    }

    // FUNÇÃO PARA CRIAR CARDVIEWS DE PRODUTOS
    private void addCardView(final ItemCarrinho p){
        CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.carrinho_cardview, linearContainer, false);

        final ItemCarrinho itemCarrinho = p;

        ImageView imgItemCarrinho = (ImageView) cardView.findViewById(R.id.imgItemCarrinho);
        ImageView removeItemCarrinho = (ImageView) cardView.findViewById(R.id.removeItemCarrinho);
        TextView tvTituloItemCarrinho = (TextView) cardView.findViewById(R.id.tvTituloItemCarrinho);
        TextView tvValorItemCarrinho = (TextView) cardView.findViewById(R.id.tvValorItemCarrinho);
        final TextView tvQtItemCarrinho = (TextView) cardView.findViewById(R.id.tvQtItemCarrinho);
        Button incItemCarrinho = (Button) cardView.findViewById(R.id.incItemCarrinho);
        Button decItemCarrinho = (Button) cardView.findViewById(R.id.decItemCarrinho);

        decItemCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tvQtItemCarrinho.getText().toString().equals("1")){
                    int qtItemCarrinho = Integer.parseInt(tvQtItemCarrinho.getText().toString())-1;
                    tvQtItemCarrinho.setText(String.valueOf(qtItemCarrinho ));
                    singletonCarrinho.decrementItemCarrinho(p);
                    tvTotal.setText("Total: R$ "+singletonCarrinho.TotalCarrinho().floatValue());
                }
            }
        });


        incItemCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtItemCarrinho = Integer.parseInt(tvQtItemCarrinho.getText().toString())+1;
                tvQtItemCarrinho.setText(String.valueOf(qtItemCarrinho ));
                singletonCarrinho.incrementItemCarrinho(p);
                tvTotal.setText("Total: R$ "+singletonCarrinho.TotalCarrinho().floatValue());
            }
        });


        removeItemCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearContainer.removeViewAt(singletonCarrinho.getItensCarrinho().indexOf(p));
                singletonCarrinho.RemoverItemCarrinho(p);
                tvTotal.setText("Total: R$ "+singletonCarrinho.TotalCarrinho().floatValue());
            }
        });


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CarrinhoActivity.this,ProdutoDetalheActivity.class);
                i.putExtra("idProduto", ""+itemCarrinho.getProduto().getIdProduto());
                startActivity(i);
            }
        });

        tvTituloItemCarrinho.setText(itemCarrinho.getProduto().getNomeProduto());
        tvValorItemCarrinho.setText("R$ "+itemCarrinho.getProduto().getPrecProduto().floatValue());
        tvQtItemCarrinho.setText(String.valueOf(itemCarrinho.getQuantidade()));

        linearContainer.addView(cardView);
    }
}
