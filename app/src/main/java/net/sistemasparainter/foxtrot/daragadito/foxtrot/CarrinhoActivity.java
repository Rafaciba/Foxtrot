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

import java.math.BigDecimal;

public class CarrinhoActivity extends AppCompatActivity {

    protected ViewGroup linearContainer;
    protected Button btProsseguir;

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
            int singletonIndex = 0;
            for(ItemCarrinho ic : singletonCarrinho.getItensCarrinho()) {
                addCardView(ic, singletonIndex);
                singletonIndex++;
            }

            // AÇÃO DO BOTÃO
            btProsseguir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(CarrinhoActivity.this, EnderecosActivity.class);
                    startActivity(i);
                }
            });
        }

    }

    // FUNÇÃO PARA CRIAR CARDVIEWS DE PRODUTOS
    private void addCardView(ItemCarrinho p, int index){
        CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.carrinho_cardview, linearContainer, false);

        final ItemCarrinho itemCarrinho = p;
        final int singletonIndex= index;

        ImageView imgItemCarrinho = (ImageView) cardView.findViewById(R.id.imgItemCarrinho);
        ImageView removeItemCarrinho = (ImageView) cardView.findViewById(R.id.removeItemCarrinho);
        TextView tvTituloItemCarrinho = (TextView) cardView.findViewById(R.id.tvTituloItemCarrinho);
        TextView tvValorItemCarrinho = (TextView) cardView.findViewById(R.id.tvValorItemCarrinho);
        final TextView tvQtItemCarrinho = (TextView) cardView.findViewById(R.id.tvQtItemCarrinho);
        Button incItemCarrinho = (Button) cardView.findViewById(R.id.incItemCarrinho);
        Button decItemCarrinho = (Button) cardView.findViewById(R.id.decItemCarrinho);

//        ImageLoader imageLoader = ImageLoader.getInstance();
//        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
//        imageLoader.displayImage(url, imagem);

        decItemCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tvQtItemCarrinho.getText().toString().equals("1")){
                    int qtItemCarrinho = Integer.parseInt(tvQtItemCarrinho.getText().toString())-1;
                    tvQtItemCarrinho.setText(String.valueOf(qtItemCarrinho ));
                    singletonCarrinho.decrementItemCarrinho(singletonIndex);
                }
            }
        });


        incItemCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtItemCarrinho = Integer.parseInt(tvQtItemCarrinho.getText().toString())+1;
                tvQtItemCarrinho.setText(String.valueOf(qtItemCarrinho ));
                singletonCarrinho.incrementItemCarrinho(singletonIndex);
            }
        });


        removeItemCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singletonCarrinho.RemoverItemCarrinho(singletonIndex);
                linearContainer.removeViewAt(singletonIndex);
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
        tvValorItemCarrinho.setText(itemCarrinho.getProduto().getPrecProduto().toString());
        tvQtItemCarrinho.setText(String.valueOf(itemCarrinho.getQuantidade()));

        linearContainer.addView(cardView);
    }
}
