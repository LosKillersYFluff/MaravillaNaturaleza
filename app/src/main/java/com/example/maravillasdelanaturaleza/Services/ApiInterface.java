package com.example.maravillasdelanaturaleza.Services;

import com.example.maravillasdelanaturaleza.Entidades.AgregarAnimalUsuario;
import com.example.maravillasdelanaturaleza.Entidades.Animales;
import com.example.maravillasdelanaturaleza.Entidades.CaracteristicaAnimales;
import com.example.maravillasdelanaturaleza.Entidades.Num;
import com.example.maravillasdelanaturaleza.Entidades.Puntos;
import com.example.maravillasdelanaturaleza.Entidades.Texto;
import com.example.maravillasdelanaturaleza.Entidades.Usuarios;
import com.example.maravillasdelanaturaleza.Entidades.Validar;

import java.util.List;
import com.example.maravillasdelanaturaleza.Entidades.Respuestas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("/nature/registrarUsuario")
    Call<Texto> registrarUsuario(@Body Usuarios usuario);

    @POST("/nature/login")
    Call<Usuarios> login(@Body Usuarios usuario);

    @GET("/nature/consultarAnimales")
    Call<List<Animales>> consultarAnimales();

    @POST("/nature/agregarAnimalUsuario")
    Call<Validar> agregarAnimalUsuario(@Body AgregarAnimalUsuario animalUsuario);

    @GET("/nature/organizar/all")
    Call<List<Respuestas>> organizar();

    @POST("/nature/agregarPuntos")
    Call<Validar> agregarPuntos(@Body Puntos puntos);

    @GET("/nature/consultaUsuario/{id_usuario}")
    Call<Usuarios> consultarUsuario(@Path("id_usuario") int id_usuario);

    @PUT("/nature/updateUser")
    Call<Num> actualizarUsuario(@Body Usuarios usuario);

    @GET("nature/consultarCaracteristicasAnimales")
    Call<List<CaracteristicaAnimales>> getCaracteristicasAnimales();

    @GET("/nature/organizar")
    Call<List<Respuestas>> organizar(@Query("id_animal") String idAnimal);

}