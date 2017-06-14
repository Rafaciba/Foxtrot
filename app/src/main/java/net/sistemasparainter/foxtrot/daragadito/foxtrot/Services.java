package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by tobias.vponce on 03/05/2017.
 */

public interface Services {
    // CLIENTE
        @POST("cliente")
        Call<Void> setCliente(@Body Cliente cliente);

        @GET("cliente/{idCliente}")
        Call<Cliente> getCliente(@Path("idCliente") String idCliente);


    // ENDEREÇO
        @POST("endereco")
        Call<Long> setEndereco(@Body Endereco endereco);

        @GET("endereco/{idCliente}")
        Call<ArrayList<Endereco>> getEndereco(@Path("idCliente") String idCliente);


    // PRODUTOS
        @GET("produto")
        Call<ArrayList<Produto>> getProdutos();

        @GET("produto/{idProduto}")
        Call<Produto> getProduto(@Path("idProduto") String idProduto);

        @GET("produto/categoria/{idCategoria}")
        Call<ArrayList<Produto>> getProdutoCategoria(@Path("idCategoria") String idCategoria);

        @GET("produto/busca/{busca}")
        Call<ArrayList<Produto>> getProdutoBusca(@Path("busca") String busca);

    // CATEGORIA
        @GET("categoria")
        Call<ArrayList<Categoria>> getCategorias();

        @GET("categoria/{idCategoria}")
        Call<Categoria> getCategoria(@Path("idCategoria") String idCategoria);

    // LOGIN
        @POST("login")
        Call<Cliente> doLogin(@Body Login login);

    // IMAGEM DO PRODUTO
        @GET("imagem/{param}/{width}/{height}")
        Call<Imagem> getImagemProduto(@Path("param") String imagemId, @Path("width") int IMG_WIDTH, @Path("height") int IMG_HEIGHT);

    //PEDIDO
        @GET("pedido/{idPedido}")
        Call<Pedido> getPedido(@Path("idPedido") String idPedido);

        @POST("pedido")
        Call<Void> createPedido(@Body Pedido pedido);

}
