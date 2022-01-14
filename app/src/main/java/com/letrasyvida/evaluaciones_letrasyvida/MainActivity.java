package com.letrasyvida.evaluaciones_letrasyvida;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {
    private Button iniciar_sesion,r_olvidar;
    private EditText mEmailField,mPasswordField;
    private ProgressDialog progressDialog;

    RequestQueue rq;
    JsonRequest jrq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailField = findViewById(R.id.et_correo);
        mPasswordField = findViewById(R.id.et_contrasena);
        iniciar_sesion=findViewById(R.id.ir_login);
        r_olvidar=findViewById(R.id.r_olvidar);

        progressDialog = new ProgressDialog(this);

        rq = Volley.newRequestQueue(this);

        iniciar_sesion.setOnClickListener(view -> {
            final String email = mEmailField.getText().toString().trim();
            String password = mPasswordField.getText().toString().trim();
            LoginUsuario(email, password);
        });

        r_olvidar.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, recovery.class));
            //finish();
        });
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        mEmailField.setText(preferences.getString("pcorreo",""));
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    private void LoginUsuario(String email, String password){
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Por favor Ingrese su Correo", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!isEmailValid(email)){
            Toast.makeText(this, "El Correo Ingresado no es Valido", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Por favor Ingrese su Contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();

        String url = "https://aulavirtual.letrasyvida.com/apk/sesion.php?Correo=" + email + "&Contrasena=" + password;
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.dismiss();
        Toast.makeText(MainActivity.this, "El Correo o la Contraseña no es correcta.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        User usuario = new User();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObjectson = null;

        try {
            jsonObjectson = jsonArray.getJSONObject(0);
            usuario.setId_user(jsonObjectson.optString("id"));
            usuario.setCorreo(jsonObjectson.optString("email"));
            usuario.setContrasena(jsonObjectson.optString("contrasena"));
            usuario.setNombre(jsonObjectson.optString("firstname"));
            usuario.setApellido(jsonObjectson.optString("lastname"));
            usuario.setUsername(jsonObjectson.optString("username"));
            usuario.setIdnumber(jsonObjectson.optString("idnumber"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.dismiss();

        //Toast.makeText(MainActivity.this, "Bienvenid@ " + usuario.getNombre() + " " + usuario.getApellido(), Toast.LENGTH_LONG).show();
        Intent intencion = new Intent(MainActivity.this, menu.class);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id",usuario.getId_user());
        editor.putString("nombre",usuario.getNombre());
        editor.putString("apellido",usuario.getApellido());
        editor.putString("correo",usuario.getCorreo());
        editor.putString("contrasena", usuario.getContrasena());
        editor.putString("username",usuario.getUsername());
        //id.... 2 es Admin... 3 es nharo... 19 es jparrales... 20 es prueba
        String id= usuario.getId_user();
        String idnum= usuario.getIdnumber();
        if (TextUtils.equals(idnum,"")){
            if(!TextUtils.equals(id,"2") && !TextUtils.equals(id,"3") && !TextUtils.equals(id,"1") && !TextUtils.equals(id,"14")){
            editor.putString("rol","Estudiante");   //entrar con jparrales
            } else if(TextUtils.equals(id,"2")){
            editor.putString("rol","Admin");        //entrar con lvadmin
            } else if(TextUtils.equals(id,"14")){
            editor.putString("rol","Directivo");    //entrar con sandra moscol
            }
        }
        if (!TextUtils.equals(idnum,"")){
            editor.putString("rol","Docente");      // entrar con prueba
        }

        //Toast.makeText(MainActivity.this, "Bienvenid@ " + usuario.getNombre() + " " + usuario.getApellido() + " tu number es: " + idnum + " tu id es: " + id + " tu rol es: " + preferences.getString("rol",""), Toast.LENGTH_LONG).show();

        editor.commit();
        startActivity(intencion);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String email = preferences.getString("correo","");
        String password = preferences.getString("contrasena","");
        rq = Volley.newRequestQueue(this);
        if (email !=""){
            LoginUsuario(email, password);
        }
    }

}