package com.letrasyvida.evaluaciones_letrasyvida.ui.autoevaluacion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.letrasyvida.evaluaciones_letrasyvida.R;
import com.letrasyvida.evaluaciones_letrasyvida.SpinnerAdaptador;
import com.letrasyvida.evaluaciones_letrasyvida.conexion;
import com.letrasyvida.evaluaciones_letrasyvida.consulta;
import com.letrasyvida.evaluaciones_letrasyvida.spins;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AutoevaluacionFragment extends Fragment {

    ArrayList<String> cat, doc;
    ArrayList<spins> list_cat, list_doc;
    private Spinner spin_cat, spin_doc;
    private Button consultar;
    private String btnconsulta;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_autoevalucion, container, false);

        cat = new ArrayList<>();
        doc = new ArrayList<>();

        list_cat = new ArrayList<>();
        list_doc = new ArrayList<>();

        spin_cat = (Spinner) root.findViewById(R.id.spin_cat_aut);
        spin_doc = (Spinner) root.findViewById(R.id.spin_doc_aut);
        consultar= root.findViewById(R.id.btn_consultar_aut);

        cate();

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnconsulta =="false"){
                    Toast.makeText(getActivity().getApplicationContext(),"Primero Seleccione el docente",Toast.LENGTH_LONG).show();
                } else{
                    Intent intencion = new Intent(getActivity(), consulta.class);
                    startActivity(intencion);
                }

            }
        });

        spin_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String especialidad = spin_cat.getItemAtPosition(spin_cat.getSelectedItemPosition()).toString();
                String id_cat= "";
                for (int x = 0; x < list_cat.size(); x++) {
                    spins e = list_cat.get(x);
                    if (e.getName().equals(especialidad)) {
                        id_cat = e.getId();
                        break;
                    }
                }
                if(id_cat!="0"){
                    docen(id_cat);
                    btnconsulta = "false";
                    reset_cur();
                } else {
                    spin_doc.setAdapter(null);
                    btnconsulta = "false";
                    reset_cur();
                }
                SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("categoria", id_cat);
                editor.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        spin_doc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String id_doc="";
                String docente = spin_doc.getItemAtPosition(spin_doc.getSelectedItemPosition()).toString();
                for (int x = 0; x < list_doc.size(); x++) {
                    spins d = list_doc.get(x);
                    if (d.getName().equals(docente)) {
                        id_doc = d.getId();
                        break;
                    }
                }

                SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                String id_cat = preferences.getString("categoria","");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("direccion", conexion.URL_WEB_SERVICES+"datos_apk.php?sel_doc_auto="+id_doc+"&sel_cur_auto2="+id_cat);
                editor.commit();
                if(id_doc=="0"){btnconsulta = "false";}else{ btnconsulta = "true";}
                //Toast.makeText(getApplicationContext(),id_doc,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        return root;
    }

    public void cate(){

        doc.clear();
        list_doc.clear();
        cat.clear();
        cat.add(0,"SELECCIONE LA CATEGORIA");
        list_cat.clear();
        list_cat.add(new spins("0","SELECCIONE LA CATEGORIA"));
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES+"datos_apk.php?carga=2",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("datos_apk");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String espe=jsonObject1.getString("name");
                                String sid = jsonObject1.getString("id");
                                cat.add(espe);
                                list_cat.add(new spins(sid,espe));
                            }
                            //spin_esp.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.textview_spinner, esp));
                            spin_cat.setAdapter(new SpinnerAdaptador(getActivity(),cat));
                        }catch (JSONException e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void docen(String categoria){
        doc.clear();
        doc.add(0,"SELECCIONE EL DOCENTE");
        list_doc.clear();
        list_doc.add(new spins("0","SELECCIONE EL DOCENTE"));

        RequestQueue requestQueue=Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES+"datos_apk.php?sel_cur_auto="+categoria,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("datos_apk");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String doce=jsonObject1.getString("NOMBRE");
                                String sid = jsonObject1.getString("ID_DOCENTE");
                                doc.add(doce);
                                list_doc.add(new spins(sid,doce));
                            }
                            //spin_doc.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, doc));
                            spin_doc.setAdapter(new SpinnerAdaptador(getActivity(),doc));
                        }catch (JSONException e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void reset_cur(){
        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("direccion",conexion.URL_WEB_SERVICES+"datos_apk.php?sel_doc_auto=0&sel_cur_auto2=0");
        editor.commit();
    }
}