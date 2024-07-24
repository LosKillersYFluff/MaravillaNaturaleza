package com.example.maravillasdelanaturaleza;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.maravillasdelanaturaleza.Entidades.Num;
import com.example.maravillasdelanaturaleza.Entidades.Usuarios;
import com.example.maravillasdelanaturaleza.Services.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {

    EditText nombre, correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_perfil);
        hideSystemUI();
        this.InicializarControles();
        cargarPerfil();
    }

    private void cargarPerfil() {
        SharedPreferences sp = getSharedPreferences("usuario", MODE_PRIVATE);
        nombre.setText(sp.getString("nombre", ""));
        correo.setText(sp.getString("correo", ""));
    }

    private void InicializarControles() {
        nombre = findViewById(R.id.nombre);
        correo = findViewById(R.id.correo);

    }
    public void eliminar(View v){
        SharedPreferences sp = getSharedPreferences("usuario", MODE_PRIVATE);
        Call<Num> caller = ApiService.getApiService().deleteUser(sp.getInt("id",0));
        caller.enqueue(new Callback<Num>() {
            @Override
            public void onResponse(Call<Num> call, Response<Num> response) {
                if(response.isSuccessful()){
                    Intent i = new Intent(PerfilActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<Num> call, Throwable t) {

            }
        });
    }

    public void editar(View v){
        SharedPreferences sp = getSharedPreferences("usuario", MODE_PRIVATE);
        Usuarios usuario = new Usuarios();
        usuario.setId_usuario(sp.getInt("id", 0));
        usuario.setNombre(nombre.getText().toString());
        usuario.setCorreos(correo.getText().toString());
        Call<Num> caller = ApiService.getApiService().actualizarUsuario(usuario);
        caller.enqueue(new Callback<Num>() {
            @Override
            public void onResponse(Call<Num> call, Response<Num> response) {
                if(response.isSuccessful()){
                    actualizarPerfil();
                }
            }

            @Override
            public void onFailure(Call<Num> call, Throwable t) {

            }
        });
    }

    public void actualizarPerfil(){
        SharedPreferences sp = getSharedPreferences("usuario", MODE_PRIVATE);
        Call<Usuarios> caller = ApiService.getApiService().consultarUsuario(sp.getInt("id",0));
        caller.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                if(response.isSuccessful() && response.body() != null){
                    SharedPreferences sp = getSharedPreferences("usuario", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    Usuarios usuario = response.body();
                    editor.putInt("id", usuario.getId_usuario());
                    editor.putString("nombre", usuario.getNombre());
                    editor.putString("correo", usuario.getCorreos());
                    editor.putInt("id_rol", usuario.getId_rol());
                    editor.putBoolean("isLogged", true);
                    editor.apply();
                    cargarPerfil();
                }
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {

            }
        });
    }

    public void regresar(View v){
        finish();
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