package com.example.maravillasdelanaturaleza;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.maravillasdelanaturaleza.Constantes.Constantes;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {

    EditText nombre, correo, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        hideSystemUI();
        this.InicializarControles();
    }

    private void InicializarControles() {
        nombre = findViewById(R.id.nombre);
        correo = findViewById(R.id.correo);
        password = findViewById(R.id.password);
    }

    public void registrarse(View v) {
        boolean retornar = false;

        // Verificar si nombreUsuario está vacío
        if (TextUtils.isEmpty(nombre.getText().toString())) {
            aplicarBordeRojo(nombre);
            retornar = true;
        } else {
            removerBordeRojo(nombre);
        }

        // Verificar si correo está vacío
        if (TextUtils.isEmpty(correo.getText().toString())) {
            aplicarBordeRojo(correo);
            retornar = true;
        } else {
            removerBordeRojo(correo);
        }

        // Validar formato de correo electrónico
        if (validarEmail(correo.getText().toString())) {
            removerBordeRojo(correo);
        } else {
            aplicarBordeRojo(correo);
            retornar = true;
        }

        // Verificar si contraseñaUsuario está vacío
        if (TextUtils.isEmpty(password.getText().toString())) {
            aplicarBordeRojo(password);
            retornar = true;
        } else {
            removerBordeRojo(password);
        }

        if (!retornar) {
            String url = Uri.parse(Constantes.URL_BASE + "registro.php")
                    .buildUpon()
                    .appendQueryParameter("nombre", nombre.getText().toString())
                    .appendQueryParameter("correos", correo.getText().toString())
                    .appendQueryParameter("contrasenas", password.getText().toString())
                    .build().toString();
            JsonObjectRequest JOR = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    respuesta(jsonObject);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(RegistroActivity.this, "Error de red: "+ volleyError, Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue cola = Volley.newRequestQueue(this);
            cola.add(JOR);
        }else{
            Toast.makeText(this, "Campos vacíos", Toast.LENGTH_LONG).show();
        }
    }
    private void respuesta(JSONObject jsonObject) {
        try {
            if (jsonObject.getString("resultado").compareTo("Usuario agregado") == 0) {

                startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(this, "Registrado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error de usuario/contraseña", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.getMessage();
        }
    }

    private void enviarALogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void irAiniciarSesion(View v){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
    public void irAinicio(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public static boolean validarEmail(String email) {
        // Expresión regular para validar un correo electrónico
        String regex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void aplicarBordeRojo(EditText editText){
        Drawable borderRed = getDrawable(R.drawable.border_red);
        editText.setBackground(borderRed);
        // Revertir el borde después de 3 segundos
        new Handler().postDelayed(() -> removerBordeRojo(editText), 3000);
    }
    private void removerBordeRojo(EditText editText) {
        // Configura el fondo del EditText a un drawable predeterminado sin borde rojo
        editText.setBackgroundResource(android.R.drawable.edit_text);
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