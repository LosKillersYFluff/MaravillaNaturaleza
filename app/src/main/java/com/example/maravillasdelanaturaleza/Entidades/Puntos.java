package com.example.maravillasdelanaturaleza.Entidades;

public class Puntos {
    private int id_ranking;
    private int modo_juego;
    private int puntos;
    private int id_usuario;

    public Puntos() {
    }

    public Puntos(int id_ranking, int modo_juego, int puntos, int id_usuario) {
        this.id_ranking = id_ranking;
        this.modo_juego = modo_juego;
        this.puntos = puntos;
        this.id_usuario = id_usuario;
    }

    public int getId_ranking() {
        return id_ranking;
    }

    public void setId_ranking(int id_ranking) {
        this.id_ranking = id_ranking;
    }

    public int getModo_juego() {
        return modo_juego;
    }

    public void setModo_juego(int modo_juego) {
        this.modo_juego = modo_juego;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
