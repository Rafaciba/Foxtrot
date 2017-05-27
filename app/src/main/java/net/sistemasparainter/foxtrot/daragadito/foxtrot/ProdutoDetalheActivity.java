package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        final ShowDialog showDialog = new ShowDialog(ProdutoDetalheActivity.this);

        Intent i = getIntent();

        String idProduto = i.getStringExtra("idProduto");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://foxtrotws.azurewebsites.net/g1/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Services service = retrofit.create(Services.class);

        Call<Produto> produto = service.getProduto(idProduto);

        produto.enqueue(new Callback<Produto>() {
            @Override
            public void onResponse(Call<Produto> call, Response<Produto> response) {
                if(response.isSuccessful()){
                    response.body().getImagem();
                    response.body().getNomeProduto();
                    response.body().getPrecProduto();
                    response.body().getDescProduto();
                }
            }

            @Override
            public void onFailure(Call<Produto> call, Throwable t) {
                showDialog.showMessage("Erro ao consultar o banco de dados","Erro");
            }
        });

    }


}
