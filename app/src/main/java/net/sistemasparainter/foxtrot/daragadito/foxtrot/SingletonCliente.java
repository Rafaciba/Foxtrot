package net.sistemasparainter.foxtrot.daragadito.foxtrot;

/**
 * Created by rafael.fccibim on 26/04/2017.
 */

public class SingletonCliente {
    private static final SingletonCliente INSTANCE = new SingletonCliente();

    private Cliente clienteLogado = null;

    private SingletonCliente() {}

    public static SingletonCliente getInstance() {
        return INSTANCE;
    }

    public Cliente getClienteLogado(){ return this.clienteLogado; }

    public void setClienteLogado(Cliente cliente){ this.clienteLogado = cliente; }

    public boolean estaLogado(){
        if (clienteLogado.equals(null)){
            return false;
        }else{
            return true;
        }
    }
}
