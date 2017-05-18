package net.sistemasparainter.foxtrot.daragadito.foxtrot;


import java.math.BigDecimal;

public class Produto {

    private int idProduto;
    private String nomeProduto;
    private String descProduto;
    private BigDecimal precProduto;
    private BigDecimal descontoPromocao;
    private int idCategoria;
    private boolean ativoProduto;
    private int idUsuario;
    private int qtdMinEstoque;
    private String imagem;

    public Produto(int idProduto, String nomeProduto, String descProduto, BigDecimal precProduto, BigDecimal descontoPromocao, int idCategoria, boolean ativoProduto, int idUsuario, int qtdMinEstoque, String imagem) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.descProduto = descProduto;
        this.precProduto = precProduto;
        this.descontoPromocao = descontoPromocao;
        this.idCategoria = idCategoria;
        this.ativoProduto = ativoProduto;
        this.idUsuario = idUsuario;
        this.qtdMinEstoque = qtdMinEstoque;
        this.imagem = imagem;
    }
}
