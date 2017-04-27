package net.sistemasparainter.foxtrot.daragadito.foxtrot;

/**
 * Created by rafael.fccibim on 26/04/2017.
 */

public class SingletonUsuario {
    private static final SingletonUsuario INSTANCE = new SingletonUsuario();

    private Usuario usuarioLogado;

    private SingletonUsuario() {}

    public static SingletonUsuario getInstance() {
        return INSTANCE;
    }

    public Usuario getUsuarioLogado(){ return this.usuarioLogado; }

    public void setUsuarioLogado(Usuario usuario){ this.usuarioLogado = usuario; }
}
