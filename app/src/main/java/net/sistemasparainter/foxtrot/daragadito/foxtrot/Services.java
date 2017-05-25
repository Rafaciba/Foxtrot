package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
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
        @GET("produto}")
        Call<Endereco> getProdutos();

        @GET("produto/{idProduto}")
        Call<Endereco> getProduto(@Path("idProduto") String idProduto);

        @GET("produto/{idCategoria}")
        Call<Endereco> getProdutoCategoria(@Path("idCategoria") String idCategoria);

        @GET("produto/busca/{busca}")
        Call<Endereco> getProdutoBusca(@Path("busca") String busca);

    // CATEGORIA
        @GET("categoria")
        Call<Categoria> getCategoria();

        @GET("categoria/{idCategoria}")
        Call<Endereco> getCategoria(@Path("idCategoria") String idCategoria);

    // LOGIN
        @POST("login")
        Call<Cliente> doLogin(@Body String email, String senha);

}
