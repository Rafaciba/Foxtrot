package net.sistemasparainter.foxtrot.daragadito.foxtrot;

/**
 * Created by rafael.fccibim on 26/04/2017.
 */

public class Cliente {

    private int idCliente;
    private String nomeCompletoCliente;
    private String emailCliente;
    private String senhaCliente;
    private String CPFCliente;

    public Cliente(int idCliente, String nomeCompletoCliente, String emailCliente, String senhaCliente, String CPFCliente) {
        this.idCliente = idCliente;
        this.nomeCompletoCliente = nomeCompletoCliente;
        this.emailCliente = emailCliente;
        this.senhaCliente = senhaCliente;
        this.CPFCliente = CPFCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNomeCompletoCliente() {
        return nomeCompletoCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public String getSenhaCliente() {
        return senhaCliente;
    }

    public String getCPFCliente() {
        return CPFCliente;
    }
}
