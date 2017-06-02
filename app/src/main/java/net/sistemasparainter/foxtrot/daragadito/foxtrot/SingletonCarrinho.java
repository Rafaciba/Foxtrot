package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by gabriel.lgomes1 on 18/05/2017.
 */

public class SingletonCarrinho {
    private static final SingletonCarrinho INSTANCE = new SingletonCarrinho();

    private SingletonCarrinho() {}

    private ArrayList<Produto> carrinho = new ArrayList<Produto>();

    public static SingletonCarrinho getInstance() {
        return INSTANCE;
    }

    public void AdicionaCarrinho (Produto p) { carrinho.add(p); }

    public void ExcluirCarrinho (int index) { carrinho.remove(index); }

    public BigDecimal TotalCarrinho () {

        BigDecimal total = BigDecimal.valueOf(0);

        for(Produto p : carrinho) {
            total.add(p.getPrecProduto());
        }

        return total;

    }

    public ArrayList<Produto> getProdutosCarrinho(){
        return carrinho;
    }

}



