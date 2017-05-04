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
    private String celularCliente;
    private String telComercialCliente;
    private String telResindencialCliente;
    private String dtNascCliente;
    private int recebeNewsLetter;

    public Cliente(int idCliente, String nomeCompletoCliente, String emailCliente, String senhaCliente, String CPFCliente, String celularCliente,
                   String telComercialCliente, String telResindencialCliente, String dtNascCliente, int recebeNewsLetter) {
        this.idCliente = idCliente;
        this.nomeCompletoCliente = nomeCompletoCliente;
        this.emailCliente = emailCliente;
        this.senhaCliente = senhaCliente;
        this.CPFCliente = CPFCliente;
        this.celularCliente = celularCliente;
        this.telComercialCliente = telComercialCliente;
        this.telResindencialCliente = telResindencialCliente;
        this.dtNascCliente = dtNascCliente;
        this.recebeNewsLetter = recebeNewsLetter;
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

    public String getCelularCliente() {
        return celularCliente;
    }

    public String getTelComercialCliente() {
        return telComercialCliente;
    }

    public String getTelResindencialCliente() {
        return telResindencialCliente;
    }

    public String getDtNascCliente() {
        return dtNascCliente;
    }

    public int getRecebeNewsLetter() {
        return recebeNewsLetter;
    }
}
