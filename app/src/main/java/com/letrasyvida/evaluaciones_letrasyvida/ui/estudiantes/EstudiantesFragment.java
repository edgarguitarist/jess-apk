package com.letrasyvida.evaluaciones_letrasyvida.ui.estudiantes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.letrasyvida.evaluaciones_letrasyvida.pruebas;
import com.letrasyvida.evaluaciones_letrasyvida.spins;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EstudiantesFragment extends Fragment {

    ArrayList<String> esp, bac, doc, cur, per;
    ArrayList<com.letrasyvida.evaluaciones_letrasyvida.spins> list_esp, list_bac, list_doc, list_cur, list_per;
    private Spinner spin_esp, spin_bac, spin_doc, spin_cur, spin_per;
    private Button consultar;
    private String btnconsulta;

    private EstudiantesViewModel estudiantesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        estudiantesViewModel =
                new ViewModelProvider(this).get(EstudiantesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_estudiantes, container, false);

        esp = new ArrayList<>();
        bac = new ArrayList<>();
        doc = new ArrayList<>();
        cur = new ArrayList<>();
        per = new ArrayList<>();

        list_esp = new ArrayList<>();
        list_bac = new ArrayList<>();
        list_doc = new ArrayList<>();
        list_cur = new ArrayList<>();
        list_per = new ArrayList<>();

        spin_esp = (Spinner) root.findViewById(R.id.spin_esp);
        spin_bac = (Spinner) root.findViewById(R.id.spin_bac);
        spin_doc = (Spinner) root.findViewById(R.id.spin_doc);
        spin_cur = (Spinner) root.findViewById(R.id.spin_cur);
        spin_per = (Spinner) root.findViewById(R.id.spin_per);
        consultar= root.findViewById(R.id.btn_consultar);

        espe();

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnconsulta =="false"){
                    Toast.makeText(getActivity().getApplicationContext(),"Seleccione un Curso Primero",Toast.LENGTH_LONG).show();
                } else{
                    Intent intencion = new Intent(getActivity(), com.letrasyvida.evaluaciones_letrasyvida.consulta.class);
                    startActivity(intencion);
                }
            }
        });

        spin_esp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String especialidad = spin_esp.getItemAtPosition(spin_esp.getSelectedItemPosition()).toString();
                String id_esp= "";
                for (int x = 0; x < list_esp.size(); x++) {
                    spins e = list_esp.get(x);
                    if (e.getName().equals(especialidad)) {
                        id_esp = e.getId();
                        break;
                    }
                }
                if(id_esp!="0"){
                    spin_per.setAdapter(null);
                    spin_bac.setAdapter(null);
                    spin_doc.setAdapter(null);
                    spin_cur.setAdapter(null);
                    btnconsulta = "false";
                    reset_cur();
                    per(id_esp);
                } else {
                    spin_per.setAdapter(null);
                    spin_bac.setAdapter(null);
                    spin_doc.setAdapter(null);
                    spin_cur.setAdapter(null);
                    btnconsulta = "false";
                    reset_cur();
                }
                //Toast.makeText(getApplicationContext(),id_esp,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        spin_per.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String id_per="";
                String periodo = spin_per.getItemAtPosition(spin_per.getSelectedItemPosition()).toString();
                for (int x = 0; x < list_per.size(); x++) {
                    spins b = list_per.get(x);
                    if (b.getName().equals(periodo)) {
                        id_per = b.getId();
                        break;
                    }
                }

                if(id_per!="0"){
                    spin_bac.setAdapter(null);
                    spin_doc.setAdapter(null);
                    spin_cur.setAdapter(null);
                    btnconsulta = "false";
                    reset_cur();
                    bac(id_per);
                    SharedPreferences preferences = getActivity().getSharedPreferences("datosapk", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("id_per",id_per);
                    editor.commit();
                } else {
                    spin_bac.setAdapter(null);
                    spin_doc.setAdapter(null);
                    spin_cur.setAdapter(null);
                    btnconsulta = "false";
                    reset_cur();
                }
                //Toast.makeText(getApplicationContext(),id_bac,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        spin_bac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String id_bac="";
                String bachillerato = spin_bac.getItemAtPosition(spin_bac.getSelectedItemPosition()).toString();
                for (int x = 0; x < list_bac.size(); x++) {
                    spins b = list_bac.get(x);
                    if (b.getName().equals(bachillerato)) {
                        id_bac = b.getId();
                        break;
                    }
                }

                if(id_bac!="0"){
                    spin_doc.setAdapter(null);
                    spin_cur.setAdapter(null);
                    btnconsulta = "false";
                    reset_cur();
                    docen(id_bac);
                    SharedPreferences preferences = getActivity().getSharedPreferences("datosapk", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("id_bac",id_bac);
                    editor.commit();
                } else {
                    spin_doc.setAdapter(null);
                    spin_cur.setAdapter(null);
                    btnconsulta = "false";
                    reset_cur();
                }
                //Toast.makeText(getApplicationContext(),id_bac,Toast.LENGTH_LONG).show();
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
                //Recoger datos de "datosapk"
                SharedPreferences preferences = getActivity().getSharedPreferences("datosapk", Context.MODE_PRIVATE);
                String bach = preferences.getString("id_bac","");

                if(id_doc!="0"){
                    spin_cur.setAdapter(null);
                    btnconsulta = "false";
                    reset_cur();
                    cursos(id_doc,bach);
                } else {
                    spin_cur.setAdapter(null);
                    btnconsulta = "false";
                    reset_cur();
                }
                //Toast.makeText(getApplicationContext(),id_doc,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        spin_cur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String id_cur="";
                String curso = spin_cur.getItemAtPosition(spin_cur.getSelectedItemPosition()).toString();
                for (int x = 0; x < list_cur.size(); x++) {
                    spins c = list_cur.get(x);
                    if (c.getName().equals(curso)) {
                        id_cur = c.getId();
                        break;
                    }
                }
                SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("direccion",conexion.URL_WEB_SERVICES+"datos_apk.php?sel_mat="+id_cur);
                //editor.putString("seleccion","si");
                editor.commit();
                if(id_cur=="0"){btnconsulta = "false";}else{ btnconsulta = "true";}
                //Toast.makeText(getApplicationContext(),id_cur,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        return root;
    }

    public void espe(){
        cur.clear();
        list_cur.clear();
        doc.clear();
        list_doc.clear();
        bac.clear();
        list_bac.clear();
        per.clear();
        list_per.clear();
        esp.clear();
        esp.add(0,"SELECCIONE LA ESPECIALIDAD");
        list_esp.clear();
        list_esp.add(new spins("0","SELECCIONE LA ESPECIALIDAD"));
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES+"datos_apk.php?carga=1",
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
                                esp.add(espe);
                                list_esp.add(new spins(sid,espe));
                            }
                            //spin_esp.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.textview_spinner, esp));
                            spin_esp.setAdapter(new SpinnerAdaptador(getActivity(),esp));
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

    public void per(String peri){
        cur.clear();
        list_cur.clear();
        doc.clear();
        list_doc.clear();
        bac.clear();
        list_bac.clear();
        per.clear();
        per.add(0,"SELECCIONE EL PERIODO");
        list_per.clear();
        list_per.add(new spins("0","SELECCIONE EL PERIODO"));

        RequestQueue requestQueue=Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES+"datos_apk.php?sel_esp="+peri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("datos_apk");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String perio=jsonObject1.getString("name");
                                String sid = jsonObject1.getString("id");
                                per.add(perio);
                                list_per.add(new spins(sid,perio));
                            }
                            spin_per.setAdapter(new SpinnerAdaptador(getActivity(),per));
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

    public void bac(String bachi){
        cur.clear();
        list_cur.clear();

        doc.clear();
        list_doc.clear();

        bac.clear();
        bac.add(0,"SELECCIONE EL BACHILLERATO");
        list_bac.clear();
        list_bac.add(new spins("0","SELECCIONE EL BACHILLERATO"));

        RequestQueue requestQueue=Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES+"datos_apk.php?sel_per="+bachi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("datos_apk");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String bach=jsonObject1.getString("name");
                                String sid = jsonObject1.getString("id");
                                bac.add(bach);
                                list_bac.add(new spins(sid,bach));
                            }
                            //spin_bac.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, bac));
                            spin_bac.setAdapter(new SpinnerAdaptador(getActivity(),bac));
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

    public void docen(String docent){
        cur.clear();
        list_cur.clear();
        doc.clear();
        doc.add(0,"SELECCIONE EL DOCENTE");
        list_doc.clear();
        list_doc.add(new spins("0","SELECCIONE EL DOCENTE"));

        RequestQueue requestQueue=Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES+"datos_apk.php?sel_bac="+docent,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("datos_apk");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String doce=jsonObject1.getString("NOMBRES");
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

    public void cursos(String docente, String bachi){
        cur.clear();
        cur.add(0,"SELECCIONE EL CURSO");
        list_cur.clear();
        list_cur.add(new spins("0","SELECCIONE EL CURSO"));

        RequestQueue requestQueue=Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES+"datos_apk.php?sel_bac2="+bachi+"&sel_doc="+docente,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("datos_apk");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String fullname=jsonObject1.getString("FULL");
                                String sid = jsonObject1.getString("ID_CURSO");
                                cur.add(fullname);
                                list_cur.add(new spins(sid,fullname));
                            }
                            //spin_cur.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, cur));
                            spin_cur.setAdapter(new SpinnerAdaptador(getActivity(),cur));
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
        SharedPreferences preferences = getActivity().getSharedPreferences("datosapk", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("direccion",conexion.URL_WEB_SERVICES+"datos_apk.php?sel_mat=0");
        editor.commit();
    }


}