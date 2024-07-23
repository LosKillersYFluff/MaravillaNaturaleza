package com.example.maravillasdelanaturaleza;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.maravillasdelanaturaleza.Entidades.Usuarios;
import com.example.maravillasdelanaturaleza.Services.ApiService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText correo, password;
    Usuarios _usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hideSystemUI();
        this.InicializarControles();
    }

    private void InicializarControles() {
        correo = findViewById(R.id.correo);
        password = findViewById(R.id.password);

    }

    public void iniciarSesion(View v){
        Usuarios usuario = new Usuarios();
        usuario.setCorreos(correo.getText().toString());
        usuario.setContrasena(password.getText().toString());
        Intent i = new Intent(this, MenuActivity.class);
        Call<Usuarios> caller = ApiService.getApiService().login(usuario);
        caller.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                if(response.isSuccessful() && response.body() != null){
                    Usuarios usuario = response.body();
                    SharedPreferences sp = getSharedPreferences("usuario", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("id", usuario.getId_usuario());
                    editor.putString("nombre", usuario.getNombre());
                    editor.putString("correo", usuario.getCorreos());
                    editor.putInt("id_rol", usuario.getId_rol());
                    editor.putBoolean("isLogged", true);
                    editor.apply();
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {

            }
        });
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