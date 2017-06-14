package net.sistemasparainter.foxtrot.daragadito.foxtrot;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuscaFragment extends Fragment {

    private ViewGroup linearContainer;
    private ArrayList<Produto> produtosArrayList = null;
    private String busca;

    ShowDialog showDialog = new ShowDialog(getActivity());

    public BuscaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            busca = getArguments().getString("busca", "");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_busca, container, false);

        final LayoutInflater li = inflater;

        linearContainer = (ViewGroup) fragmentView.findViewById(R.id.linearBuscaContainer);

        //imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://foxtrotws.azurewebsites.net/g1/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Services service = retrofit.create(Services.class);

        Call<ArrayList<Produto>> produtos = service.getProdutoBusca(busca);

        try {
            produtos.enqueue(new Callback<ArrayList<Produto>>() {
                @Override
                public void onResponse(Call<ArrayList<Produto>> call, Response<ArrayList<Produto>> response) {
                    if(response.code() == 200) {
                        produtosArrayList = response.body();

                        if (produtosArrayList != null) {
                            for (Produto p : produtosArrayList) {
                                addCardView(p, li);
                            }
                        }
                    }else {
                        showDialog.showMessage("Não foi possível carregar os produtos.","Ocorreu um erro");
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Produto>> call, Throwable t) {
                    t.printStackTrace();
                    showDialog.showMessage("Não foi possível carregar os produtos.","Ocorreu um erro");
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        return fragmentView;
    }

    private void addCardView(Produto produto, LayoutInflater inflater) {

        final Produto p = produto;

        final CardView cardView = (CardView) inflater.inflate(R.layout.fragment_produtos_cardview, linearContainer, false);

        TextView nomeProduto = (TextView) cardView.findViewById(R.id.nomeProdutoResumo);
        nomeProduto.setText(p.getNomeProduto());

        TextView descProduto = (TextView) cardView.findViewById(R.id.tvDescricaoProduto);
        descProduto.setText(p.getDescProduto());

        TextView precProduto = (TextView) cardView.findViewById(R.id.tvPrecoProduto);
        precProduto.setText("R$ "+p.getPrecProduto().floatValue());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),ProdutoDetalheActivity.class);
                i.putExtra("idProduto", ""+p.getIdProduto());
                startActivity(i);
            }
        });

        linearContainer.addView(cardView);

    }

}
