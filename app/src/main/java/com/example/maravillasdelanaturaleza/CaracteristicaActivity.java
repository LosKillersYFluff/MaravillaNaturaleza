package com.example.maravillasdelanaturaleza;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.maravillasdelanaturaleza.Entidades.CaracteristicaAnimales;
import com.example.maravillasdelanaturaleza.Services.ApiInterface;
import com.example.maravillasdelanaturaleza.Services.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaracteristicaActivity extends AppCompatActivity {
    private ImageView homeIcon, leftImage;
    private TextView text1, text2, text3, text4;
    private ImageButton continuar;
    private String animalId; // Variable para guardar el ID del animal
    private String lastAnimalId; // Variable para guardar el último ID del animal
    private List<CaracteristicaAnimales> animalList; // Lista para almacenar todos los animales
    private int currentAnimalIndex = 0; // Índice del animal actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caracteristica);

        // Initialize UI components
        homeIcon = findViewById(R.id.home_icon);
        leftImage = findViewById(R.id.left_image);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        continuar = findViewById(R.id.continuar);

        // Get ApiService instance and fetch animal data
        ApiInterface apiService = ApiService.getApiService();

        // Get the last animal ID from the Intent
        Intent intent = getIntent();
        lastAnimalId = intent.getStringExtra("LAST_ANIMAL_ID");

        fetchAllAnimalData(apiService);

        // Set up listener for "continuar" button
        continuar.setOnClickListener(v -> navigateToOrganizarActivity());
    }

    private void fetchAllAnimalData(ApiInterface apiService) {
        Call<List<CaracteristicaAnimales>> call = apiService.getCaracteristicasAnimales();
        call.enqueue(new Callback<List<CaracteristicaAnimales>>() {
            @Override
            public void onResponse(Call<List<CaracteristicaAnimales>> call, Response<List<CaracteristicaAnimales>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    animalList = response.body();
                    if (!animalList.isEmpty()) {
                        // Display a random animal different from the last one
                        displayRandomAnimal();
                    }
                } else {
                    Log.e("CaracteristicaActivity", "Respuesta vacía o fallida.");
                }
            }

            @Override
            public void onFailure(Call<List<CaracteristicaAnimales>> call, Throwable t) {
                Log.e("CaracteristicaActivity", "Error en la llamada API: " + t.getMessage());
            }
        });
    }

    private void displayRandomAnimal() {
        if (animalList == null || animalList.isEmpty()) {
            Log.e("CaracteristicaActivity", "Lista de animales vacía.");
            return;
        }

        // Find a random animal different from the last one
        CaracteristicaAnimales animal;
        do {
            currentAnimalIndex = (int) (Math.random() * animalList.size());
            animal = animalList.get(currentAnimalIndex);
        } while (animal.getIdAnimal().equals(lastAnimalId));

        if (animal != null) {
            displayAnimalDetails(animal);
            animalId = animal.getIdAnimal(); // Save the new animal ID
            Log.d("CaracteristicaActivity", "Animal ID seleccionado: " + animalId);
        } else {
            Log.e("CaracteristicaActivity", "No se ha encontrado un animal en la lista.");
        }
    }

    private void displayAnimalDetails(CaracteristicaAnimales animal) {
        text1.setText(animal.getCaracteristica1());
        text2.setText(animal.getCaracteristica2());
        text3.setText(animal.getCaracteristica3());
        text4.setText(animal.getCaracteristica4());

        Glide.with(CaracteristicaActivity.this)
                .load(animal.getUrlAnimal())
                .into(leftImage);
    }

    private void navigateToOrganizarActivity() {
        if (animalId != null) { // Check if animalId is not null
            Intent intent = new Intent(CaracteristicaActivity.this, OrganizarActivity.class);
            intent.putExtra("ANIMAL_ID", animalId); // Pass the animal ID to OrganizarActivity
            startActivity(intent);
            finish(); // Finish the current activity to prevent returning to it
        } else {
            Log.e("CaracteristicaActivity", "No se ha seleccionado un animal.");
        }
    }
}
