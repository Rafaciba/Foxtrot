package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import java.io.Serializable;

/**
 * Created by tobias.vponce on 24/05/2017.
 */

public class Categoria {

    private int idCategoria;
    private String nomeCategoria;
    private String descCategoria;

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    public int getIdCategoria() {
        return idCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setDescCategoria(String descCategoria) {
        this.descCategoria = descCategoria;
    }
    public String getDescCategoria() {
        return descCategoria;
    }
}
