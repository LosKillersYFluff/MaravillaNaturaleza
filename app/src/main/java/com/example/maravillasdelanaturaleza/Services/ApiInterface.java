package com.example.maravillasdelanaturaleza.Services;

import com.example.maravillasdelanaturaleza.Entidades.Texto;
import com.example.maravillasdelanaturaleza.Entidades.Usuarios;
import com.example.maravillasdelanaturaleza.Entidades.Respuestas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("/nature/registrarUsuario")
    Call<Texto> registrarUsuario(@Body Usuarios usuario);

    @POST("/nature/login")
    Call<Usuarios> login(@Body Usuarios usuario);

    @GET("/nature/organizar/all")
    Call<List<Respuestas>> organizar();
}