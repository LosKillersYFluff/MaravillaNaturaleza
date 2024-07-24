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

import androidx.activity.EdgeToEdge;
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

        // Get ApiService instance and call fetchAnimalData
        ApiInterface apiService = ApiService.getApiService();
        fetchAnimalData(apiService);
    }

    private void fetchAnimalData(ApiInterface apiService) {
        Call<List<Respuestas>> call = apiService.organizar();
        call.enqueue(new Callback<List<Respuestas>>() {
            @Override
            public void onResponse(Call<List<Respuestas>> call, Response<List<Respuestas>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    respuestasList = response.body();
                    if (!respuestasList.isEmpty()) {
                        shuffledList.addAll(respuestasList);
                        Collections.shuffle(shuffledList); // Shuffle the list
                        displayCurrentSentence();
                    }
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
                    checkSentenceCompletion();
                }
            }
        };

        boton_frase1.setOnClickListener(listener);
        boton_frase2.setOnClickListener(listener);
        boton_frase3.setOnClickListener(listener);
        boton_frase4.setOnClickListener(listener);
        boton_frase5.setOnClickListener(listener);
    }

    private void checkSentenceCompletion() {
        if (currentSentence.toString().trim().equalsIgnoreCase(fraseCompleta)) {
            Log.d("OrganizarActivity", "Frase completada correctamente");
            incrementScore(5);
        } else {
            Log.d("OrganizarActivity", "Frase incorrecta");
            incrementScore(-5); // Subtract 5 points for incorrect completion

            // Re-show all buttons if the sentence is incorrect
            boton_frase1.setVisibility(View.VISIBLE);
            boton_frase2.setVisibility(View.VISIBLE);
            boton_frase3.setVisibility(View.VISIBLE);
            boton_frase4.setVisibility(View.VISIBLE);
        }

        currentIndex++;
        processedCount++; // Increment the processed count
        currentSentence.setLength(0);
        sentenceLine1.setText("");
        clickedButtons.clear();

        if (processedCount >= 3) {
            navigateToMenuActivity();
        } else if (currentIndex < shuffledList.size()) {
            displayCurrentSentence();
        } else {
            Log.d("OrganizarActivity", "No hay más frases para mostrar");
            showFinalScore();
        }

        boton_frase5.setEnabled(true);
    }

    private void incrementScore(int points) {
        score += points;
        updateScoreLabel();
    }

    private void updateScoreLabel() {
        puntajeLabel.setText("Puntaje: " + score);
    }

    private void showFinalScore() {
        Toast.makeText(this, "Puntaje final: " + score, Toast.LENGTH_LONG).show();
        resetGame();
    }

    private void resetGame() {
        currentIndex = 0;
        processedCount = 0;
        score = 0;
        updateScoreLabel();
        shuffledList.clear();
        fetchAnimalData(ApiService.getApiService());
    }

    private void navigateToMenuActivity() {
        Intent intent = new Intent(OrganizarActivity.this, MenuActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity to prevent returning to it
    }

    private void setButtonText(ImageButton button, String text) {
        RelativeLayout parent = (RelativeLayout) button.getParent();
        if (parent != null) {
            int textViewId = getTextViewIdForButton(button.getId());
            if (textViewId != -1) {
                TextView textView = parent.findViewById(textViewId);
                if (textView != null) {
                    textView.setText(text);
                } else {
                    Log.e("OrganizarActivity", "TextView con ID '" + textViewId + "' no encontrado en el layout del botón.");
                }
            } else {
                Log.e("OrganizarActivity", "ID del botón desconocido.");
            }
        } else {
            Log.e("OrganizarActivity", "RelativeLayout padre del botón no encontrado.");
        }
    }

    private int getTextViewIdForButton(int buttonId) {
        if (buttonId == R.id.boton_frase1) {
            return R.id.text_in_button1;
        } else if (buttonId == R.id.boton_frase2) {
            return R.id.text_in_button2;
        } else if (buttonId == R.id.boton_frase3) {
            return R.id.text_in_button3;
        } else if (buttonId == R.id.boton_frase4) {
            return R.id.text_in_button4;
        } else {
            return -1;
        }
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