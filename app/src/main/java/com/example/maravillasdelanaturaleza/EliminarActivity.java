package com.example.maravillasdelanaturaleza;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EliminarActivity extends AppCompatActivity
{
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eliminar);
        this.InicializarControles();
    }

    private void InicializarControles()
    {
        spinner = findViewById(R.id.spinner);
    }

    public void Eliminar(View view)
    {
        //eliminar animal
    }
}