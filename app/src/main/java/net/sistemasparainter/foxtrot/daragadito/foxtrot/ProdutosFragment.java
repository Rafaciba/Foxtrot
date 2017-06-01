package net.sistemasparainter.foxtrot.daragadito.foxtrot;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProdutosFragment extends Fragment {


    private ViewGroup linearContainer;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public ProdutosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_produtos, container, false);

        final Bundle sii = savedInstanceState;

        linearContainer = (ViewGroup) fragmentView.findViewById(R.id.linearContainer);

        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://foxtrotws.azurewebsites.net/g1/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Services service = retrofit.create(Services.class);

        Call<ArrayList<Produto>> produtos = service.getProdutos();

        produtos.enqueue(new Callback<ArrayList<Produto>>() {
            @Override
            public void onResponse(Call<ArrayList<Produto>> call, Response<ArrayList<Produto>> response) {
                ArrayList<Produto> produtosArrayList = response.body();

                for(Produto p : produtosArrayList){
                    addCardView(p, sii);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Produto>> call, Throwable t) {

            }
        });


        /*ArrayList<String> urls = new ArrayList<>();
        //urls.add("http://localhost:8080/WSECommerce/rest/imagem/1/200/200");
        urls.add("https://riodegraca.files.wordpress.com/2016/07/casa_suica.jpg");
        urls.add("https://i2.wp.com/oprofessorweb.files.wordpress.com/2017/04/figura-4.jpg");

        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));

        for (int i = 0; i < 2; i++) {

            addCardView(urls.get(i), savedInstanceState);
        }*/

        // Inflate the layout for this fragment
        return fragmentView;
    }
    private void addCardView(Produto p, Bundle bundle) {

        CardView cardView = (CardView) getLayoutInflater(bundle).inflate(R.layout.fragment_produtos_cardview, linearContainer, false);

        ImageView verImagem = (ImageView) cardView.findViewById(R.id.imgProdutoCarrinho);
        imageLoader.displayImage("http://foxtrotws.azurewebsites.net/g1/rest/imagem/"+p.getIdProduto()+"/50/50", verImagem);

        TextView nomeProduto = (TextView) cardView.findViewById(R.id.NomeProdutoCarrinho);
        nomeProduto.setText(p.getNomeProduto());

        TextView descProduto = (TextView) cardView.findViewById(R.id.tvDescricaoProduto);
        descProduto.setText(p.getDescProduto());

        TextView precProduto = (TextView) cardView.findViewById(R.id.tvPrecoProduto);
        precProduto.setText("R$ "+p.getPrecProduto().toString());

        linearContainer.addView(cardView);

    }
}
