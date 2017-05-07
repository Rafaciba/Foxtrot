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
    @POST("cliente/add/index.php")
    Call<Cliente> createCliente(Cliente cliente);
}
