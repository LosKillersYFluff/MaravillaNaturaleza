package com.example.maravillasdelanaturaleza;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.maravillasdelanaturaleza.Entidades.Respuestas;
import com.example.maravillasdelanaturaleza.Services.ApiInterface;
import com.example.maravillasdelanaturaleza.Services.ApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganizarActivity extends AppCompatActivity {
    private ImageButton boton_frase1, boton_frase2, boton_frase3, boton_frase4, boton_frase5;
    private TextView sentenceLine1, puntajeLabel;
    private ImageView imgMinijuego2;
    private List<Respuestas> respuestasList = new ArrayList<>();
    private List<Respuestas> shuffledList = new ArrayList<>();
    private int currentIndex = 0;
    private StringBuilder currentSentence = new StringBuilder();
    private Set<Integer> clickedButtons = new HashSet<>();
    private int score = 0;
    private int processedCount = 0; // Track processed elements
    private String fraseCompleta = ""; // To store the current complete sentence
    private String lastAnimalId = ""; // Track the last animal ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_organizar);
        hideSystemUI();

        // Initialize UI components
        boton_frase1 = findViewById(R.id.boton_frase1);
        boton_frase2 = findViewById(R.id.boton_frase2);
        boton_frase3 = findViewById(R.id.boton_frase3);
        boton_frase4 = findViewById(R.id.boton_frase4);
        boton_frase5 = findViewById(R.id.siguiente);
        sentenceLine1 = findViewById(R.id.sentence_line1);
        imgMinijuego2 = findViewById(R.id.imgMinijuego2);
        puntajeLabel = findViewById(R.id.puntaje_label);

        // Get ApiService instance and fetch the animal ID
        ApiInterface apiService = ApiService.getApiService();

        // Get animal ID from Intent
        Intent intent = getIntent();
        String animalId = intent.getStringExtra("ANIMAL_ID");

        if (animalId != null) {
            lastAnimalId = animalId;
            fetchAnimalData(apiService, animalId);
        } else {
            Log.e("OrganizarActivity", "No se ha recibido el ID del animal.");
        }
    }

    private void fetchAnimalData(ApiInterface apiService, String animalId) {
        Call<List<Respuestas>> call = apiService.organizar(animalId); // Use the correct endpoint
        call.enqueue(new Callback<List<Respuestas>>() {
            @Override
            public void onResponse(Call<List<Respuestas>> call, Response<List<Respuestas>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    respuestasList = response.body();
                    if (!respuestasList.isEmpty()) {
                        shuffledList.clear();
                        shuffledList.addAll(respuestasList);
                        Collections.shuffle(shuffledList); // Shuffle the list
                        displayCurrentSentence();
                    }
                } else {
                    Log.e("OrganizarActivity", "Respuesta vacía o fallida.");
                }
            }

            @Override
            public void onFailure(Call<List<Respuestas>> call, Throwable t) {
                Log.e("OrganizarActivity", "Error en la llamada API: " + t.getMessage());
            }
        });
    }

    private void displayCurrentSentence() {
        // Make all buttons visible when displaying a new sentence
        boton_frase1.setVisibility(View.VISIBLE);
        boton_frase2.setVisibility(View.VISIBLE);
        boton_frase3.setVisibility(View.VISIBLE);
        boton_frase4.setVisibility(View.VISIBLE);

        // Clear any previous button clicks
        clickedButtons.clear();

        // Clear current sentence and update UI
        currentSentence.setLength(0);
        sentenceLine1.setText("");

        if (currentIndex < shuffledList.size()) {
            Respuestas current = shuffledList.get(currentIndex);
            fraseCompleta = current.getFrase_completa(); // Update fraseCompleta

            List<String> características = new ArrayList<>();
            características.add(current.getCaracteristica1());
            características.add(current.getCaracteristica2());
            características.add(current.getCaracteristica3());
            características.add(current.getCaracteristica4());
            Collections.shuffle(características);

            setButtonText(boton_frase1, características.get(0));
            setButtonText(boton_frase2, características.get(1));
            setButtonText(boton_frase3, características.get(2));
            setButtonText(boton_frase4, características.get(3));

            Glide.with(OrganizarActivity.this)
                    .load(current.getUrl())
                    .into(imgMinijuego2);

            setupButtonListeners();
        }
    }

    private void setupButtonListeners() {
        View.OnClickListener listener = v -> {
            ImageButton clickedButton = (ImageButton) v;
            TextView buttonTextView = (TextView) ((RelativeLayout) clickedButton.getParent()).getChildAt(1);
            if (buttonTextView != null) {
                String buttonText = buttonTextView.getText().toString();
                currentSentence.append(buttonText).append(" ");
                sentenceLine1.setText(currentSentence.toString().trim());

                clickedButtons.add(clickedButton.getId());

                // Hide the clicked button
                clickedButton.setVisibility(View.GONE);

                if (clickedButtons.size() == 4) {
                    checkAnswer();
                }
            }
        };

        boton_frase1.setOnClickListener(listener);
        boton_frase2.setOnClickListener(listener);
        boton_frase3.setOnClickListener(listener);
        boton_frase4.setOnClickListener(listener);

        boton_frase5.setOnClickListener(v -> {
            if (currentIndex < shuffledList.size() - 1) {
                currentIndex++;
                displayCurrentSentence();
            } else {
                Toast.makeText(OrganizarActivity.this, "Has completado todas las frases.", Toast.LENGTH_SHORT).show();
                navigateToCaracteristicaActivity();
            }
        });
    }

    private void setButtonText(ImageButton button, String text) {
        // Assuming each button has a TextView child to display the text
        TextView textView = (TextView) ((RelativeLayout) button.getParent()).getChildAt(1);
        if (textView != null) {
            textView.setText(text);
        }
    }

    private void checkAnswer() {
        if (currentSentence.toString().trim().equals(fraseCompleta)) {
            score += 5; // Correct answer
            puntajeLabel.setText("Puntaje: " + score);
        } else {
            score -= 5; // Incorrect answer
            Toast.makeText(OrganizarActivity.this, "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
        }

        // Clear the current sentence and clicked buttons
        currentSentence.setLength(0);
        clickedButtons.clear();
        sentenceLine1.setText("");

        // End game and navigate to CaracteristicaActivity
        Toast.makeText(OrganizarActivity.this, "Juego terminado. Puntaje final: " + score, Toast.LENGTH_LONG).show();
        navigateToCaracteristicaActivity();
    }


    private void navigateToCaracteristicaActivity() {
        Intent intent = new Intent(OrganizarActivity.this, CaracteristicaActivity.class);
        intent.putExtra("LAST_ANIMAL_ID", lastAnimalId); // Pass the last animal ID to CaracteristicaActivity
        // Optionally, use FLAG_ACTIVITY_CLEAR_TOP to avoid multiple instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Finish the current activity to prevent returning to it
    }

    private void hideSystemUI() {
        // Hide system UI for full-screen experience
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
