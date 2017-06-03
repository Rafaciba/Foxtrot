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

    // ADICIONA UM ITEM NO CARRINHO
    public void AdicionaCarrinho (Produto p) {
        if(!temItem(p)){
            carrinho.add(new ItemCarrinho(p));
        }
    }

    // REMOVE UM ITEM DO CARRINHO
    public void RemoverItemCarrinho (int index) { carrinho.remove(index); }

    // REMOVE TODOS OS ITENS DO CARRINHO
    public void LimparCarrinho () { carrinho.clear(); }

    // RETORNA O TOTAL DO CARRINHO (VALOR)
    public BigDecimal TotalCarrinho () {

        BigDecimal total = BigDecimal.valueOf(0);

        for(ItemCarrinho ic : carrinho) {
            total.add(ic.getProduto().getPrecProduto());
        }

        return total;

    }

    // RETORNA TODOS OS ITENS DO CARRINHO
    public ArrayList<ItemCarrinho> getItensCarrinho(){
        return carrinho;
    }

    // CHECA SE O UM ITEM JÁ EXISTE NO CARRINHO
    private boolean temItem(Produto p){
        for(ItemCarrinho ic : carrinho){
            if(ic.getProduto().getIdProduto() == p.getIdProduto()){
                return true;
            }
        }
        return false;
    }

    // CHECA SE O CARRINHO ESTÁ VAZIO
    public boolean checaSeCarrinhoVazio(){
        if (carrinho.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

}



