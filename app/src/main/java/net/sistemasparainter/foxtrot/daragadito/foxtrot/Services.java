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
        Call<Void> setCliente(Cliente cliente);
        @GET("cliente")
        Call<Cliente> getCliente(Cliente cliente);

    // ENDEREÇO
        @POST("endereco")
        Call<Void> setEndereco(Endereco endereco);
        @GET("endereco")
        Call<Endereco> getEndereco(Endereco endereco);
}
