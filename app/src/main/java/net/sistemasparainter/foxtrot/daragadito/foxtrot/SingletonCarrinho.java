package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by gabriel.lgomes1 on 18/05/2017.
 */

public class SingletonCarrinho {
    private static final SingletonCarrinho INSTANCE = new SingletonCarrinho();

    private SingletonCarrinho() {}

    private ArrayList<ItemCarrinho> carrinho = new ArrayList<ItemCarrinho>();

    public static SingletonCarrinho getInstance() {
        return INSTANCE;
    }

    public void AdicionaCarrinho (Produto p) {
        if(!temItem(p)){
            carrinho.add(new ItemCarrinho(p));
        }
    }

    public void ExcluirCarrinho (int index) { carrinho.remove(index); }

    public BigDecimal TotalCarrinho () {

        BigDecimal total = BigDecimal.valueOf(0);

        for(ItemCarrinho ic : carrinho) {
            total.add(ic.getProduto().getPrecProduto());
        }

        return total;

    }

    public ArrayList<ItemCarrinho> getItensCarrinho(){
        return carrinho;
    }

    private boolean temItem(Produto p){
        for(ItemCarrinho ic : carrinho){
            if(ic.getProduto().getIdProduto() == p.getIdProduto()){
                return true;
            }
        }
        return false;
    }

}



