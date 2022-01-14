package com.letrasyvida.evaluaciones_letrasyvida;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class recovery extends AppCompatActivity {
    private EditText r_correo;
    private ProgressDialog progressDialog;
    Button ir_recuperar,btn_ini;
    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        r_correo=findViewById(R.id.r_correo);
        ir_recuperar=findViewById(R.id.ir_recuperar);
        btn_ini=findViewById(R.id.btn_ini);
        progressDialog = new ProgressDialog(this);
        rq = Volley.newRequestQueue(this);

        ir_recuperar.setOnClickListener(view -> resetpassword());

        btn_ini.setOnClickListener(view -> {
            startActivity(new Intent(recovery.this, MainActivity.class));
            finish();
        });

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

    private void resetpassword(){
        final String email = r_correo.getText().toString().trim();

        if(!isEmailValid(email)){
            Toast.makeText(this, "El Correo Ingresado no es Valido", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();
        String url = "https://aulavirtual.letrasyvida.com/enviar_correo.php?email=" + email;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        limpiarCajas();
                        Toast.makeText(recovery.this, "Se ha enviado un Correo", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(recovery.this, MainActivity.class));
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(recovery.this, "No se ha podido enviar el Correo", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        rq.add(stringRequest);
    }

    void limpiarCajas() {
        r_correo.setText("");
    }

}