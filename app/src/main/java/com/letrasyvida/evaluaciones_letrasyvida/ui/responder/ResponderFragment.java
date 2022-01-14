package com.letrasyvida.evaluaciones_letrasyvida.ui.responder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.letrasyvida.evaluaciones_letrasyvida.Evaluaciones;
import com.letrasyvida.evaluaciones_letrasyvida.EvaluacionesAdaptador;
import com.letrasyvida.evaluaciones_letrasyvida.MainActivity;
import com.letrasyvida.evaluaciones_letrasyvida.R;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.letrasyvida.evaluaciones_letrasyvida.conexion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResponderFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject>{


    ArrayList<Evaluaciones> listaEvaluaciones;
    RecyclerView recyclerEvaluaciones;
    TextView restante, gracias;
    RequestQueue rq;
    JsonRequest jrq;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_responder, container, false);

        listaEvaluaciones = new ArrayList<>();

        restante = root.findViewById(R.id.restante);
        gracias = root.findViewById(R.id.gracias);
        recyclerEvaluaciones = root.findViewById(R.id.RecyclerEvaluaciones);
        recyclerEvaluaciones.setLayoutManager(new LinearLayoutManager(getActivity()));
        rq = Volley.newRequestQueue(getActivity());


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        consultar();
    }

    private void consultar(){
        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        String id_user = preferences.getString("id","");
        String rol = preferences.getString("rol","");

        String url = "https://aulavirtual.letrasyvida.com/apk/evaluaciones.php?id_user="+ id_user + "&rol=" + rol;
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(), "No se pudo conectar, Verifique su conexion a Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Evaluaciones evaluaciones = null;
        listaEvaluaciones.clear();

        JSONArray json = response.optJSONArray("datosev");
        Integer contador = 0;
        try {
            for(int i=0;i<json.length();i++){
                evaluaciones = new Evaluaciones();
                JSONObject jsonObjectson = null;
                jsonObjectson = json.getJSONObject(i);
                if(TextUtils.equals(jsonObjectson.optString("COMPLETO"),"NO")){
                contador += 1;
                evaluaciones.setIndex("Evaluación #" + String.valueOf(contador));
                evaluaciones.setId_ev(jsonObjectson.optString("ID_Survey"));
                evaluaciones.setName_ev("Evaluación de " + jsonObjectson.optString("NAME_COURSE"));
                evaluaciones.setId_course(jsonObjectson.optString("ID_COURSE"));
                listaEvaluaciones.add(evaluaciones);
                }
                restante.setText("Evaluaciones Restantes: " + String.valueOf(contador));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        EvaluacionesAdaptador adapter = new EvaluacionesAdaptador(listaEvaluaciones);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                String user = preferences.getString("id","");
                String rol = preferences.getString("rol","");
                int questid = Integer.parseInt(listaEvaluaciones.get(recyclerEvaluaciones.getChildAdapterPosition(view)).getId_ev());

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("direccion", conexion.URL_WEB_SERVICES+"survey.php?iduser="+user+"&questid="+questid+"&rol="+rol);
                //editor.putString("seleccion","si");
                editor.commit();

                //String prueba = preferences.getString("direccion","");;
                //Toast.makeText(getContext(), prueba, Toast.LENGTH_LONG).show();

                Intent intencion = new Intent(getActivity(), com.letrasyvida.evaluaciones_letrasyvida.consulta.class);
                startActivity(intencion);
            }
        });


        recyclerEvaluaciones.setAdapter(adapter);

        if (contador == 0){
            recyclerEvaluaciones.setVisibility(View.GONE);
            gracias.setVisibility(View.VISIBLE);
        } else{
            recyclerEvaluaciones.setVisibility(View.VISIBLE);
            gracias.setVisibility(View.GONE);
        }

    }


}