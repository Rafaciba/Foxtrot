package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CarrinhoActivity extends AppCompatActivity {

    protected ViewGroup linearContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        linearContainer = (ViewGroup) findViewById(R.id.linearContainer);

        SingletonCarrinho singletonCarrinho = SingletonCarrinho.getInstance();

        for(Produto p : singletonCarrinho.getProdutosCarrinho()) {
            addCardView(p.getNomeProduto(), p.getPrecProduto());
        }
    }


    private void addCardView(String titulo, BigDecimal valor){
        CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.carrinho_cardview, linearContainer, false);
        TextView tituloProduto = (TextView) cardView.findViewById(R.id.NomeProdutoCarrinho);
        TextView valorProduto = (TextView) cardView.findViewById(R.id.tvDescricaoProduto);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        ImageLoader imageLoader = ImageLoader.getInstance();
//        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
//        imageLoader.displayImage(url, imagem);

        tituloProduto.setText(titulo);
        valorProduto.setText(valor.toString());

        linearContainer.addView(cardView);
    }
}
