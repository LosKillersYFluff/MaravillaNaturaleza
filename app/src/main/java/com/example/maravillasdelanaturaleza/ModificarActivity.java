package com.example.maravillasdelanaturaleza;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ModificarActivity extends AppCompatActivity
{

    private EditText nombre, caracter1, caracter2, caracter3, caracter4, imgEdit, caracterMini, caracterMini2, caracterMini3, caracterMini4;
    private ImageButton regresar, modificar;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar); // Asegúrate de que el nombre del archivo XML sea correcto
        this.InicializarControles();
    }

    private void InicializarControles()
    {
        regresar = findViewById(R.id.regresar);
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
        modificar = findViewById(R.id.modificar);
        spinner = findViewById(R.id.spinner);
    }

    public void onRegresar(View view)
    {
        Intent i= new Intent(this,AdminActivity.class);
        startActivity(i);
    }

    public void Modificar(View view)
    {
        // Obtener los valores ingresados en los campos
        String nombre1 =  nombre.getText().toString().trim();
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

        // Aquí puedes agregar la lógica para agregar los datos a la base de datos o a tu lista
        Toast.makeText(this, "Animal agregado correctamente", Toast.LENGTH_SHORT).show();
    }
}
