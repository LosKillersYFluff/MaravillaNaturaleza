package com.example.maravillasdelanaturaleza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
 public class AdminActivity extends AppCompatActivity
 {
     private ImageButton btnAnadir, btnModificar,btnEliminar,btnSalir;
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin);
            this.InicializarControles();
        }

     private void InicializarControles()
     {
         btnAnadir = findViewById(R.id.btnAnadir);
         btnModificar = findViewById(R.id.btnModificar);
         btnEliminar = findViewById(R.id.btnEliminar);
         btnSalir = findViewById(R.id.salir);
     }


     // Método para abrir la actividad Añadir
        public void abrirAnadir(View view) {
            Intent intent = new Intent(this, AnadirActivity.class);
            startActivity(intent);
        }

        // Método para abrir la actividad Modificar
        public void abrirModificar(View view) {
            Intent intent = new Intent(this, ModificarActivity.class);
            startActivity(intent);
        }

        // Método para abrir la actividad Eliminar
        public void abrirEliminar(View view) {
            Intent intent = new Intent(this, EliminarActivity.class);
            startActivity(intent);
        }

        // Método para cerrar sesión
        public void cerrarsesion(View view) {
            // Código para cerrar sesión, por ejemplo, volver a la actividad de login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finaliza la actividad actual
        }
    }