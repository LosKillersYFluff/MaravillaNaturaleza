package com.example.maravillasdelanaturaleza.Entidades;
public class Respuestas {
    int id, id_animal;
    String  caracteristica1, caracteristica2, caracteristica3,caracteristica4, frase_completa, fecha_creacion, url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Respuestas(int id, String caracteristica1, String caracteristica2,
                      String caracteristica3, String caracteristica4, String frase_completa, int id_animal, String fecha_creacion, String url) {
        this.id = id;
        this.caracteristica1 = caracteristica1;
        this.caracteristica2 = caracteristica2;
        this.caracteristica3 = caracteristica3;
        this.caracteristica4 = caracteristica4;
        this.frase_completa = frase_completa;
        this.id_animal = id_animal;
        this.fecha_creacion=fecha_creacion;
        this.url=url;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }
    public int getId_animal() {
        return id_animal;
    }
    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
    }
    public String getCaracteristica1() {
        return caracteristica1;
    }
    public void setCaracteristica1(String caracteristica1) {
        this.caracteristica1 = caracteristica1;
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

    public String getFrase_completa() {
        return frase_completa;
    }
    public void setFrase_completa(String frase_completa) {
        this.frase_completa = frase_completa;
    }
    public Respuestas() {
    }


}