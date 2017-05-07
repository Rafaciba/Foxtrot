package net.sistemasparainter.foxtrot.daragadito.foxtrot;

/**
 * Created by tobiasponce on 5/6/17.
 */

public class Endereco {

    Cliente cliente;
    private int idEndereco;
    private int idCliente;
    private String nomeEndereco;
    private String logradouroEndereco;
    private String numeroEndereco;
    private String CEPEndereco;
    private String complementoEndereco;
    private String cidadeEndereco;
    private String paisEndereco;
    private String UFEndereco;

    // CONSTRUTOR PARA ENDEREÇOS JÁ CADASTRADOS
    public Endereco(int idEndereco, int idCliente, String nomeEndereco, String logradouroEndereco, String numeroEndereco, String CEPEndereco, String complementoEndereco, String cidadeEndereco, String paisEndereco, String UFEndereco) {
        this.idEndereco = idEndereco;
        this.idCliente = idCliente;
        this.nomeEndereco = nomeEndereco;
        this.logradouroEndereco = logradouroEndereco;
        this.numeroEndereco = numeroEndereco;
        this.CEPEndereco = CEPEndereco;
        this.complementoEndereco = complementoEndereco;
        this.cidadeEndereco = cidadeEndereco;
        this.paisEndereco = paisEndereco;
        this.UFEndereco = UFEndereco;
    }

    // CONSTRUTOR PARA ENDEREÇOS NÃO CADASTRADOS
    public Endereco(int idCliente, String nomeEndereco, String logradouroEndereco, String numeroEndereco, String CEPEndereco, String complementoEndereco, String cidadeEndereco, String paisEndereco, String UFEndereco) {
        this.idCliente = idCliente;
        this.nomeEndereco = nomeEndereco;
        this.logradouroEndereco = logradouroEndereco;
        this.numeroEndereco = numeroEndereco;
        this.CEPEndereco = CEPEndereco;
        this.complementoEndereco = complementoEndereco;
        this.cidadeEndereco = cidadeEndereco;
        this.paisEndereco = paisEndereco;
        this.UFEndereco = UFEndereco;
    }
    // GETTERS
    public int getIdEndereco() {return idEndereco;}

    public int getIdCliente() {return idCliente;}

    public String getNomeEndereco() {return nomeEndereco;}

    public String getLogradouroEndereco() {return logradouroEndereco;}

    public String getNumeroEndereco() {return numeroEndereco;}

    public String getCEPEndereco() {return CEPEndereco;}

    public String getComplementoEndereco() {return complementoEndereco;}

    public String getCidadeEndereco() {return cidadeEndereco;}

    public String getPaisEndereco() {return paisEndereco;}

    public String getUFEndereco() {return UFEndereco;}

    public Cliente getCliente() {return cliente;}


    // SETTERS
    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setNomeEndereco(String nomeEndereco) {
        this.nomeEndereco = nomeEndereco;
    }

    public void setLogradouroEndereco(String logradouroEndereco) {
        this.logradouroEndereco = logradouroEndereco;
    }

    public void setNumeroEndereco(String numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }

    public void setCEPEndereco(String CEPEndereco) {
        this.CEPEndereco = CEPEndereco;
    }

    public void setComplementoEndereco(String complementoEndereco) {
        this.complementoEndereco = complementoEndereco;
    }

    public void setCidadeEndereco(String cidadeEndereco) {
        this.cidadeEndereco = cidadeEndereco;
    }

    public void setPaisEndereco(String paisEndereco) {
        this.paisEndereco = paisEndereco;
    }

    public void setUFEndereco(String UFEndereco) {
        this.UFEndereco = UFEndereco;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
