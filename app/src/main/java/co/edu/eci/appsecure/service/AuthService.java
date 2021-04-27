package co.edu.eci.appsecure.service;

import co.edu.eci.appsecure.model.LoginWrapper;
import co.edu.eci.appsecure.model.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("auth")
    Call<Token> authenticate(@Body LoginWrapper loginWrapper);
}
