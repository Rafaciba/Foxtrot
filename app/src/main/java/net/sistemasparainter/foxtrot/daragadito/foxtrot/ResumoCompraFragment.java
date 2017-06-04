package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ResumoCompraFragment extends Fragment {

    private ViewGroup linearContainer;
    private TextView tvTotalResumo;

    public ResumoCompraFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_resumo_compra, container, false);

        final LayoutInflater li = inflater;

        linearContainer = (ViewGroup) fragmentView.findViewById(R.id.linearResumoContainer);

        tvTotalResumo = (TextView) fragmentView.findViewById(R.id.tvTotalResumo);

        SingletonCarrinho sc = SingletonCarrinho.getInstance();
        ArrayList<ItemCarrinho> carrinho = sc.getItensCarrinho();

        for(ItemCarrinho ic : carrinho){
            addCardView(ic, li);
        }

        tvTotalResumo.setText("R$ "+sc.TotalCarrinho().floatValue());

        sc.LimparCarrinho();

        return fragmentView;
    }

    private void addCardView(ItemCarrinho ic, LayoutInflater inflater) {

        final ItemCarrinho item = ic;

        CardView cardView = (CardView) inflater.inflate(R.layout.fragment_resumo_compra_cardview, linearContainer, false);

        TextView nomeProduto = (TextView) cardView.findViewById(R.id.nomeProdutoResumo);
        nomeProduto.setText(item.getProduto().getNomeProduto());

        TextView descProduto = (TextView) cardView.findViewById(R.id.descProdutoResumo);
        descProduto.setText(item.getProduto().getDescProduto());

        TextView precProduto = (TextView) cardView.findViewById(R.id.precoProdutoResumo);
        precProduto.setText("R$ "+item.getProduto().getPrecProduto().floatValue());

        TextView qtdProduto = (TextView) cardView.findViewById(R.id.qtdProdutoResumo);
        qtdProduto.setText(item.getQuantidade()+" unidade(s)");

        linearContainer.addView(cardView);

    }

}
