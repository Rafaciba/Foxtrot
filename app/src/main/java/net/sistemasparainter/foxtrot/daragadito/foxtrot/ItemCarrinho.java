package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import retrofit2.http.GET;

/**
 * Created by gabriel.lgomes1 on 18/05/2017.
 */

public class ItemCarrinho {

    private Produto produto;
    private int quantidade;


    public ItemCarrinho(Produto produto) {
        this.produto = produto;
        this.quantidade = 1;
    }


    public int getQuantidade(){
        return quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void incQuantidade(){
        this.quantidade++;
    }

    public void decQuantidade(){
        this.quantidade--;
    }

}
