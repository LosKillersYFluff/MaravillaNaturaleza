package com.example.maravillasdelanaturaleza;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AnadirActivity extends AppCompatActivity
{
    private EditText caracter1, caracter2, caracter3, caracter4,nombre;
    private EditText imgEdit;
    private EditText caracterMini, caracterMini2, caracterMini3, caracterMini4;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir);
        this.InicializarControle();
    }

    private void InicializarControle()
    {
        // Inicializar los campos de texto
        nombre = findViewById(R.id.nombre);
        caracter1 = findViewById(R.id.caracter1);
        caracter2 = findViewById(R.id.caracter2);
        caracter3 = findViewById(R.id.caracter3);
        caracter4 = findViewById(R.id.caracter4);
        imgEdit = findViewById(R.id.imgEdit);
        caracterMini = findViewById(R.id.caracterMini);
        caracterMini2 = findViewById(R.id.caracterMini2);
        caracterMini3 = findViewById(R.id.caracterMini3);
        caracterMini4 = findViewById(R.id.caracterMini4);
    }

    // Método para manejar el clic del botón "Agregar"
    public void Anadir(View view)
    {
        // Obtener los valores ingresados en los campos
        String caracter1Text = caracter1.getText().toString().trim();
        String caracter2Text = caracter2.getText().toString().trim();
        String caracter3Text = caracter3.getText().toString().trim();
        String caracter4Text = caracter4.getText().toString().trim();
        String imgEditText = imgEdit.getText().toString().trim();
        String caracterMiniText = caracterMini.getText().toString().trim();
        String caracterMini2Text = caracterMini2.getText().toString().trim();
        String caracterMini3Text = caracterMini3.getText().toString().trim();
        String caracterMini4Text = caracterMini4.getText().toString().trim();

        // Validar que los campos no estén vacíos
        if (TextUtils.isEmpty(caracter1Text)) {
            mostrarError(R.id.campo2);
            return;
        }
        if (TextUtils.isEmpty(caracter2Text)) {
            mostrarError(R.id.campo3);
            return;
        }
        if (TextUtils.isEmpty(caracter3Text)) {
            mostrarError(R.id.campo4);
            return;
        }
        if (TextUtils.isEmpty(caracter4Text)) {
            mostrarError(R.id.campo5);
            return;
        }
        if (TextUtils.isEmpty(imgEditText)) {
            mostrarError(R.id.campo6);
            return;
        }
        if (TextUtils.isEmpty(caracterMiniText)) {
            mostrarError(R.id.campo7);
            return;
        }
        if (TextUtils.isEmpty(caracterMini2Text)) {
            mostrarError(R.id.campo8);
            return;
        }
        if (TextUtils.isEmpty(caracterMini3Text)) {
            mostrarError(R.id.campo9);
            return;
        }
        if (TextUtils.isEmpty(caracterMini4Text)) {
            mostrarError(R.id.campo10);
            return;
        }
        // Aquí puedes agregar la lógica para agregar los datos a la base de datos o a tu lista
        Toast.makeText(this, "Animal agregado correctamente", Toast.LENGTH_SHORT).show();
    }

    // Método para mostrar un error si un campo está vacío
    private void mostrarError(int textViewId) {
        TextView textView = findViewById(textViewId);
        textView.setVisibility(View.VISIBLE);
    }

    // Método para manejar el clic del botón "Regresar"
    public void onRegresarClicked(View view) {
        Intent i= new Intent(this,AdminActivity.class);
        startActivity(i);
    }
}












