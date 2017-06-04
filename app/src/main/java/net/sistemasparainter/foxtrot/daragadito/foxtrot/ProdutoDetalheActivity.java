package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;

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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("aceito", false)) {
            Intent intent = new Intent(ProdutoDetalheActivity.this, TermosActivity.class);
            startActivity(intent);
            return;
        }

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

        final ProgressDialog progress = new ProgressDialog(ProdutoDetalheActivity.this);

        progress.setTitle("Carregando");
        progress.setMessage("Carregando os dados do produto ...");
        progress.setCancelable(false);
        progress.show();

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
                    if(thisProd.getDescontoPromocao() != BigDecimal.ZERO){
                        precoProdutoDetails.setText("De R$ "+thisProd.getPrecProduto().floatValue()+"\nPor R$ "+thisProd.getPrecProduto().subtract(thisProd.getDescontoPromocao()).floatValue());
                    }else {
                        precoProdutoDetails.setText("R$ " + thisProd.getPrecProduto().floatValue());
                    }
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

                                progress.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<Categoria> call, Throwable t) {
                            Intent i = new Intent(ProdutoDetalheActivity.this, MainActivity.class);
                            showDialog.showMessageAndRedirect("Erro ao consultar o banco de dados","Erro", i);
                            t.printStackTrace();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Produto> call, Throwable t) {
                Intent i = new Intent(ProdutoDetalheActivity.this, MainActivity.class);
                showDialog.showMessageAndRedirect("Erro ao consultar o banco de dados","Erro", i);
                t.printStackTrace();
            }
        });

        Call<Imagem> imagem = service.getImagemProduto(idProduto, 100, 100);

        imagem.enqueue(new Callback<Imagem>() {
            @Override
            public void onResponse(Call<Imagem> call, Response<Imagem> response) {
                if(response.isSuccessful()){
                    byte[] decodedString = Base64.decode(response.body().getImagem(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageProductDetails.setImageBitmap(decodedByte);
                }
            }

            @Override
            public void onFailure(Call<Imagem> call, Throwable t) {
                showDialog.showMessage("Erro ao consultar o banco de dados","Erro");
                t.printStackTrace();
            }
        });

    }
}
