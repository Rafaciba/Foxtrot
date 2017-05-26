package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import org.json.JSONObject;

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
        Call<Void> setEndereco(@Body Endereco endereco);

        @GET("endereco/{idCliente}")
        Call<Endereco> getEndereco(@Path("idCliente") String idCliente);


    // PRODUTOS
        @GET("produto")
        Call<Produto> getProdutos();

        @GET("produto/{idProduto}")
        Call<Produto> getProduto(@Path("idProduto") String idProduto);

        @GET("produto/{idCategoria}")
        Call<Produto> getProdutoCategoria(@Path("idCategoria") String idCategoria);

        @GET("produto/busca/{busca}")
        Call<Produto> getProdutoBusca(@Path("busca") String busca);

    // CATEGORIA
        @GET("categoria")
        Call<Categoria> getCategoria();

        @GET("categoria/{idCategoria}")
        Call<Categoria> getCategoria(@Path("idCategoria") String idCategoria);

    // LOGIN
        @POST("login")
        Call<Cliente> doLogin(@Body Login login);

    // IMAGEM DO PRODUTO
        @GET("imagem")
        Call<byte[]> getImagemProduto(@Path("param") String imagemId, @Path("width") int IMG_WIDTH, @Path("height") int IMG_HEIGHT);

}
