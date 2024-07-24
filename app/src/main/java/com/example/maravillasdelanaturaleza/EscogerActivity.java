package com.example.maravillasdelanaturaleza;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.maravillasdelanaturaleza.Entidades.AgregarAnimalUsuario;
import com.example.maravillasdelanaturaleza.Entidades.Animales;
import com.example.maravillasdelanaturaleza.Entidades.Puntos;
import com.example.maravillasdelanaturaleza.Entidades.Validar;
import com.example.maravillasdelanaturaleza.Services.ApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EscogerActivity extends AppCompatActivity {

    private TextView puntajeValue;

    private TextView opcion1, opcion2, opcion3, opcion4;
    private ImageButton homeButton, animal1, animal2, animal3, animal4;
    private ImageView imgAnimal;
    private List<Animales> _animalCorrecto = new ArrayList<>();
    private List<Animales> _animales = new ArrayList<>();
    private int puntajePorPregunta = 10, _animalAnterior;
    private int cantPreguntas = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_escoger);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        hideSystemUI();

        // Inicializar los elementos de la interfaz
        puntajeValue = findViewById(R.id.puntaje_value);
        homeButton = findViewById(R.id.home);
        opcion1 = findViewById(R.id.opcion1);
        opcion2 = findViewById(R.id.opcion2);
        opcion3 = findViewById(R.id.opcion3);
        opcion4 = findViewById(R.id.opcion4);
        animal1= findViewById(R.id.animal1);
        animal2 = findViewById(R.id.animal2);
        animal3 = findViewById(R.id.animal3);
        animal4 = findViewById(R.id.animal4);
        imgAnimal = findViewById(R.id.imgAnimal);

        cargarAnimales();

        // Configurar eventos de clic
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para el botón de inicio
                finish(); // Ejemplo: cerrar la actividad actual y volver a la anterior
            }
        });

        animal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para la opción 1
                verificarRespuesta("Opcion1");
            }
        });

        animal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para la opción 2
                verificarRespuesta("Opcion2");
            }
        });

        animal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para la opción 3
                verificarRespuesta("Opcion3");
            }
        });

        animal4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para la opción 4
                verificarRespuesta("Opcion4");
            }
        });
    }

    private void cargarAnimales(){
        Call<List<Animales>> call = ApiService.getApiService().consultarAnimales();
        call.enqueue(new Callback<List<Animales>>() {
            @Override
            public void onResponse(Call<List<Animales>> call, Response<List<Animales>> response) {
                if(response.isSuccessful()){
                    List<Animales> animal = response.body();
                    _animales = animal;
                    cargarOpciones(animal);
                }
            }

            @Override
            public void onFailure(Call<List<Animales>> call, Throwable t) {

            }
        });
    }

    private void cargarOpciones(List<Animales> animales) {
        Collections.shuffle(animales);

        int posicionRandom = (int) (Math.random() * ( 3 - 0 + 1 ) + 0);
        Glide.with(this)
                .load(animales.get(posicionRandom).getUrl_animal())
                .into(imgAnimal);
        _animalAnterior = posicionRandom;
        _animalCorrecto.add(animales.get(posicionRandom));
        opcion1.setText(String.valueOf(animales.get(0).getNombres()));
        opcion2.setText(String.valueOf(animales.get(1).getNombres()));
        opcion3.setText(String.valueOf(animales.get(2).getNombres()));
        opcion4.setText(String.valueOf(animales.get(3).getNombres()));
    }

    // Método para verificar la respuesta seleccionada
    private void verificarRespuesta(String opcionSeleccionada) {
        int puntajeActual = Integer.parseInt(puntajeValue.getText().toString());
        if(opcionSeleccionada.equals("Opcion1")){
            if(opcion1.getText().toString().equals(_animalCorrecto.get(0).getNombres())){//Si la oopcion es correcta
                puntajeActual += puntajePorPregunta;//suma
                _animales.remove(_animalAnterior);//quita el animal anterior de la lista
                _animalCorrecto.remove(0);
                if(!(cantPreguntas==0)){//la cantidad de preguntas es diferente de 0
                    cantPreguntas--;
                    cargarOpciones(_animales);
                }else{
                    agregarPuntos();//para meter en la tabla
                }
            }else{
                if(puntajeActual<=0){
                    puntajeActual=0;
                }else{
                    puntajeActual -= puntajePorPregunta;
                }
            }
        }else if(opcionSeleccionada.equals("Opcion2")){
            if(opcion2.getText().toString().equals(_animalCorrecto.get(0).getNombres())){
                puntajeActual += puntajePorPregunta;
                _animales.remove(_animalAnterior);
                _animalCorrecto.remove(0);
                if(!(cantPreguntas==0)){
                    cantPreguntas--;
                    cargarOpciones(_animales);
                }else{
                    agregarPuntos();
                }
            }else{
                if(puntajeActual<=0){
                    puntajeActual=0;
                }else{
                    puntajeActual -= puntajePorPregunta;
                }
            }
        }else if(opcionSeleccionada.equals("Opcion3")){
            if(opcion3.getText().toString().equals(_animalCorrecto.get(0).getNombres())){
                puntajeActual += puntajePorPregunta;
                _animales.remove(_animalAnterior);
                _animalCorrecto.remove(0);
                if(!(cantPreguntas==0)){
                    cantPreguntas--;
                    cargarOpciones(_animales);
                }else{
                    agregarPuntos();
                }
            }else{
                if(puntajeActual<=0){
                    puntajeActual=0;
                }else{
                    puntajeActual -= puntajePorPregunta;
                }
            }
        }else if(opcionSeleccionada.equals("Opcion4")){
            if(opcion4.getText().toString().equals(_animalCorrecto.get(0).getNombres())){
                puntajeActual += puntajePorPregunta;
                agregarAnimalAlbum(_animalCorrecto);
                _animales.remove(_animalAnterior);
                _animalCorrecto.remove(0);
                if(!(cantPreguntas==0)){
                    cantPreguntas--;
                    cargarOpciones(_animales);
                }else{
                    agregarPuntos();
                }
            }else{
                if(puntajeActual<=0){
                    puntajeActual=0;
                }else{
                    puntajeActual -= puntajePorPregunta;
                }
            }
        }
        puntajeValue.setText(String.valueOf(puntajeActual));
    }

    private void agregarPuntos() {
        Puntos puntos = new Puntos();
        SharedPreferences sp = getSharedPreferences("usuario", MODE_PRIVATE);
        puntos.setId_usuario(sp.getInt("id",0));
        puntos.setModo_juego(1);
        puntos.setPuntos(Integer.parseInt(puntajeValue.getText().toString()));
        Call<Validar> caller = ApiService.getApiService().agregarPuntos(puntos);
        caller.enqueue(new Callback<Validar>() {
            @Override
            public void onResponse(Call<Validar> call, Response<Validar> response) {
                if(response.isSuccessful()){
                    Intent i = new Intent(EscogerActivity.this, MenuActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<Validar> call, Throwable t) {

            }
        });
    }

    private void agregarAnimalAlbum(List<Animales> animal){
        AgregarAnimalUsuario animalUsuario = new AgregarAnimalUsuario();
        SharedPreferences sp = getSharedPreferences("usuario", MODE_PRIVATE);
        animalUsuario.setId_usuario(sp.getInt("id", 0));
        animalUsuario.setId_animal(animal.get(0).getId_animal());
        Call<Validar> caller = ApiService.getApiService().agregarAnimalUsuario(animalUsuario);
        caller.enqueue(new Callback<Validar>() {
            @Override
            public void onResponse(Call<Validar> call, Response<Validar> response) {
                if (response.isSuccessful()){
                }
            }

            @Override
            public void onFailure(Call<Validar> call, Throwable t) {

            }
        });
    }
    public void irAinicio(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }
}