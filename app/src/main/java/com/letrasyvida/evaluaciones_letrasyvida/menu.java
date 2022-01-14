package com.letrasyvida.evaluaciones_letrasyvida;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class menu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_estudiantes, R.id.nav_padres, R.id.nav_quitar, R.id.nav_directivos, R.id.nav_autoevalucion, R.id.nav_coevaluacion, R.id.nav_observacion, R.id.nav_responder, R.id.nav_salir)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        View headerView = navigationView.getHeaderView(0);

        TextView navUsername = (TextView) headerView.findViewById(R.id.username_menu);
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String nombre = preferences.getString("nombre","");
        String apellido = preferences.getString("apellido","");
        String rol = preferences.getString("rol","");
        navUsername.setText( rol + " " + nombre + " " + apellido);


        if(TextUtils.equals(rol,"Admin") || TextUtils.equals(rol,"Directivo")){
            navigationView.getMenu().findItem(R.id.nav_inicio).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_estudiantes).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_padres).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_directivos).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_autoevalucion).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_coevaluacion).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_observacion).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_responder).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_salir).setVisible(true);

        } else if(TextUtils.equals(rol,"Docente") || TextUtils.equals(rol,"Estudiante")){
            navigationView.getMenu().findItem(R.id.nav_inicio).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_estudiantes).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_padres).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_directivos).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_autoevalucion).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_coevaluacion).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_observacion).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_responder).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_salir).setVisible(true);
        } else {
            navigationView.getMenu().findItem(R.id.nav_inicio).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_estudiantes).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_padres).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_observacion).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_autoevalucion).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_coevaluacion).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_observacion).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_responder).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_salir).setVisible(false);
        }

        Toast.makeText(menu.this, "Bienvenido " + rol + " " + nombre + " "+ apellido , Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);



        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void salir(){
        startActivity(new Intent(menu.this,MainActivity.class));
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("correo","");
        editor.commit();
        finish();
    }


}