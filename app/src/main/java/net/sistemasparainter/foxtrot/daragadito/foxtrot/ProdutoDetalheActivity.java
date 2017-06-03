package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProdutoDetalheActivity extends AppCompatActivity {

    private ImageView imageProductDetails;
    private TextView nameProductDetatils;
    private TextView precoProdutoDetails;
    private TextView descriptionProductDetails;
    private TextView categoriaProductDetails;
    private FloatingActionButton fabButton;
    private Produto thisProd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_produto_detalhe);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        imageProductDetails = (ImageView) findViewById(R.id.imageProductDetails);
        nameProductDetatils = (TextView) findViewById(R.id.nameProductDetatils);
        precoProdutoDetails = (TextView) findViewById(R.id.precoProdutoDetails);
        descriptionProductDetails = (TextView) findViewById(R.id.descriptionProductDetails);
        categoriaProductDetails = (TextView) findViewById(R.id.categoriaProductDetails);
        fabButton = (FloatingActionButton) findViewById(R.id.fabAddCarrinho);

        final ShowDialog showDialog = new ShowDialog(ProdutoDetalheActivity.this);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonCarrinho sc = SingletonCarrinho.getInstance();
                sc.AdicionaCarrinho(thisProd);

                Snackbar.make(v, "Produto adicionado ao carrinho!", Snackbar.LENGTH_SHORT).setAction("Ver", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ProdutoDetalheActivity.this, CarrinhoActivity.class);
                        startActivity(i);
                    }
                }).show();
            }
        });

        Intent i = getIntent();

        String idProduto = i.getStringExtra("idProduto");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://foxtrotws.azurewebsites.net/g1/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Services service = retrofit.create(Services.class);

        final Call<Produto> produto = service.getProduto(idProduto);

        produto.enqueue(new Callback<Produto>() {
            @Override
            public void onResponse(Call<Produto> call, Response<Produto> response) {
                if(response.isSuccessful()){
                    thisProd = response.body();
                    nameProductDetatils.setText(thisProd.getNomeProduto());
                    precoProdutoDetails.setText("R$ "+thisProd.getPrecProduto());
                    descriptionProductDetails.setText(thisProd.getDescProduto());

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://foxtrotws.azurewebsites.net/g1/rest/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Services service = retrofit.create(Services.class);

                    final Call<Categoria> categoria = service.getCategoria(""+thisProd.getIdCategoria());

                    categoria.enqueue(new Callback<Categoria>() {
                        @Override
                        public void onResponse(Call<Categoria> call, Response<Categoria> response) {
                            if(response.isSuccessful()){
                                categoriaProductDetails.setText(response.body().getNomeCategoria());
                            }
                        }

                        @Override
                        public void onFailure(Call<Categoria> call, Throwable t) {
                            showDialog.showMessage("Erro ao consultar o banco de dados","Erro");
                            t.printStackTrace();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Produto> call, Throwable t) {
                showDialog.showMessage("Erro ao consultar o banco de dados","Erro");
                t.printStackTrace();
            }
        });

    }


}
