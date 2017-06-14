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
    private TextView tvEnderecoEntrega;
    private TextView tvMetodoPagamento;

    public ResumoCompraFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_resumo_compra, container, false);

        final LayoutInflater li = inflater;

        linearContainer = (ViewGroup) fragmentView.findViewById(R.id.linearResumoContainer);

        tvTotalResumo = (TextView) fragmentView.findViewById(R.id.tvTotalResumo);
        tvEnderecoEntrega = (TextView) fragmentView.findViewById(R.id.tvEnderecoEntrega);
        tvMetodoPagamento = (TextView) fragmentView.findViewById(R.id.tvMetodoPagamento);

        SingletonCarrinho sc = SingletonCarrinho.getInstance();
        SingletonPedido sp = SingletonPedido.getInstance();
        ArrayList<ItemCarrinho> carrinho = sc.getItensCarrinho();

        for(ItemCarrinho ic : carrinho){
            addCardView(ic, li);
        }

        tvEnderecoEntrega.setText("Endereço: " + sp.getEndereco().getLogradouroEndereco() + ", " + sp.getEndereco().getNumeroEndereco());
        String pagto = "";
        switch(sp.getIdTipoPagto()){
            case 1: pagto = "Cartão"; break;
            case 2: pagto = "Boleto"; break;
            case 3: pagto = "pagSeguro"; break;
            case 4: pagto = "Paypal"; break;
        }
        tvMetodoPagamento.setText("Método de pagamento: " + pagto);
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
