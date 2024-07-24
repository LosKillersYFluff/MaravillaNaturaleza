package com.example.maravillasdelanaturaleza;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    private MediaPlayer mediaplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_menu);
        hideSystemUI();
        this.InicializarControles();
    }

    private void InicializarControles() {
    }

    public void irAtutorial(View v){
        //Intent i = new Intent(this, .class);
    }
    public void irAperfil(View v){
        Intent i = new Intent(this, PerfilActivity.class);
        startActivity(i);
    }
    public void cerrarsesion(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void irAranking(View v){
        Intent i = new Intent(this, RankingActivity.class);
        startActivity(i);
    }

    public void irAcreditos(View v){
        Intent i = new Intent(this, CreditosActivity.class);
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