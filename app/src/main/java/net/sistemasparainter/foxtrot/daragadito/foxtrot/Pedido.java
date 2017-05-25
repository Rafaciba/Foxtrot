package net.sistemasparainter.foxtrot.daragadito.foxtrot;

/**
 * Created by rafael.fccibim on 24/05/2017.
 */

public class Pedido {
    /*
    idStatus (1-aberto, 2-aguardando pagamento, 3-Enviado p/ transportadora, 4-Entregue, 5-Cancelado)
    idTipoPagto (1-Cartão, 2-Boleto, 3-pagSeguro, 4- Paypal)
    idAplicacao (1-Managment Studio, 2-Mobile, 3-Web)
     */
    private int idPedido;
    private int Cliente;
    private int idStatus;
    private String dataPedido;
    private int idTipoPagto;
    private int idEndereco;
    private int idAplicacao;

    public Pedido(int idPedido, int cliente, int idStatus, String dataPedido, int idTipoPagto, int idEndereco, int idAplicacao) {
        this.idPedido = idPedido;
        Cliente = cliente;
        this.idStatus = idStatus;
        this.dataPedido = dataPedido;
        this.idTipoPagto = idTipoPagto;
        this.idEndereco = idEndereco;
        this.idAplicacao = idAplicacao;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getCliente() {
        return Cliente;
    }

    public void setCliente(int cliente) {
        Cliente = cliente;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public int getIdTipoPagto() {
        return idTipoPagto;
    }

    public void setIdTipoPagto(int idTipoPagto) {
        this.idTipoPagto = idTipoPagto;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public int getIdAplicacao() {
        return idAplicacao;
    }

    public void setIdAplicacao(int idAplicacao) {
        this.idAplicacao = idAplicacao;
    }
}
