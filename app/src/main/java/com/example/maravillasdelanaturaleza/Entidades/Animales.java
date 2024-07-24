package com.example.maravillasdelanaturaleza.Entidades;

public class Animales {
    private int id_animal;
    private String nombres;
    private String caracteristicas1;
    private String caracteristica2;
    private String caracteristica3;
    private String caracteristica4;
    private String url_animal;

    public Animales(int id_animal, String nombres, String caracteristicas1, String caracteristica2, String caracteristica3, String caracteristica4, String url_animal) {
        this.id_animal = id_animal;
        this.nombres = nombres;
        this.caracteristicas1 = caracteristicas1;
        this.caracteristica2 = caracteristica2;
        this.caracteristica3 = caracteristica3;
        this.caracteristica4 = caracteristica4;
        this.url_animal = url_animal;
    }

    public int getId_animal() {
        return id_animal;
    }

    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCaracteristicas1() {
        return caracteristicas1;
    }

    public void setCaracteristicas1(String caracteristicas1) {
        this.caracteristicas1 = caracteristicas1;
    }

    public String getCaracteristica2() {
        return caracteristica2;
    }

    public void setCaracteristica2(String caracteristica2) {
        this.caracteristica2 = caracteristica2;
    }

    public String getCaracteristica3() {
        return caracteristica3;
    }

    public void setCaracteristica3(String caracteristica3) {
        this.caracteristica3 = caracteristica3;
    }

    public String getCaracteristica4() {
        return caracteristica4;
    }

    public void setCaracteristica4(String caracteristica4) {
        this.caracteristica4 = caracteristica4;
    }

    public String getUrl_animal() {
        return url_animal;
    }

    public void setUrl_animal(String url_animal) {
        this.url_animal = url_animal;
    }
}
