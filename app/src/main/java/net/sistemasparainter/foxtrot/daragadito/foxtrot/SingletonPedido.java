package net.sistemasparainter.foxtrot.daragadito.foxtrot;

/**
 * Created by Rafael on 03/06/2017.
 */

public class SingletonPedido {
    private static final SingletonPedido INSTANCE = new SingletonPedido();

    private Cliente cliente;
    private Endereco endereco;
    private int idTipoPagto;

    private SingletonPedido() {}

    public static SingletonPedido getInstance() {
        return INSTANCE;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public int getIdTipoPagto() {
        return idTipoPagto;
    }

    public void setIdTipoPagto(int idTipoPagto) {
        this.idTipoPagto = idTipoPagto;
    }
}
