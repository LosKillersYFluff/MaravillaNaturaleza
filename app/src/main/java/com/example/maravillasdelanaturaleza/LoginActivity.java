package com.example.maravillasdelanaturaleza;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.maravillasdelanaturaleza.Entidades.Usuarios;
import com.example.maravillasdelanaturaleza.Services.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText correo, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
        Call<Usuarios> caller = ApiService.getApiService().login(usuario);
        caller.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                if(response.isSuccessful() && response.body()!=null){
                    SharedPreferences sp = getSharedPreferences("usuario", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    Usuarios usuario = response.body();
                    editor.putInt("id", usuario.getId_usuario());
                    editor.putString("nombre", usuario.getNombre());
                    editor.putString("correo", usuario.getCorreos());
                    editor.putInt("id_rol", usuario.getId_rol());
                    editor.putBoolean("isLogged", true);
                    editor.apply();
                    Intent i = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {

            }
        });

    }

    public void irAinicio(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void irAregistro(View v){
        Intent i = new Intent(this, RegistroActivity.class);
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