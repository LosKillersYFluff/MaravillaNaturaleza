package com.example.maravillasdelanaturaleza.Entidades;

public class Usuarios {
    private int id_usuario;
    private String nombre;
    private String correos;
    private String contrasena;
    private int id_rol;

    public Usuarios() {

    }

    public Usuarios(String nombre, String correos, String contrasena) {
        this.nombre = nombre;
        this.correos = correos;
        this.contrasena = contrasena;
    }

    public Usuarios(int id_usuario, String nombre, String correos, int id_rol) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.correos = correos;
        this.id_rol = id_rol;
    }

    public Usuarios(int id_usuario, String nombre, String correos, String contrasena, int id_rol) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.correos = correos;
        this.contrasena = contrasena;
        this.id_rol = id_rol;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreos() {
        return correos;
    }

    public void setCorreos(String correos) {
        this.correos = correos;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }
}
