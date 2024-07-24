package com.example.maravillasdelanaturaleza.Entidades;

public class AgregarAnimalUsuario {
    private int id_usuario;
    private int id_animal;

    public AgregarAnimalUsuario() {
    }

    public AgregarAnimalUsuario(int id_usuario, int id_animal) {
        this.id_usuario = id_usuario;
        this.id_animal = id_animal;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_animal() {
        return id_animal;
    }

    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
    }
}
