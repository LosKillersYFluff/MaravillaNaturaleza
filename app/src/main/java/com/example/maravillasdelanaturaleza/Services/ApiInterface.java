package com.example.maravillasdelanaturaleza.Services;

import com.example.maravillasdelanaturaleza.Entidades.Usuarios;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("/nature/registrar")
    Call<Usuarios> agregarUsuarios(@Body Usuarios usuario);

    @POST("/nature/login")
    Call<Usuarios> login(@Body Usuarios usuario);
}
