package com.letrasyvida.evaluaciones_letrasyvida.ui.coevaluacion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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

public class CoevaluacionFragment extends Fragment {

    ArrayList<String> cat,area, doc;
    ArrayList<spins> list_cat,list_area, list_doc;
    private Spinner spin_cat,spin_area, spin_doc;
    private Button consultar;
    private String btnconsulta;

    private CoevaluacionViewModel coevaluacionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        coevaluacionViewModel =
                new ViewModelProvider(this).get(CoevaluacionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_coevaluacion, container, false);

        cat = new ArrayList<>();
        area = new ArrayList<>();
        doc = new ArrayList<>();

        list_cat = new ArrayList<>();
        list_area = new ArrayList<>();
        list_doc = new ArrayList<>();

        spin_cat = (Spinner) root.findViewById(R.id.spin_cur_coev);
        spin_area = (Spinner) root.findViewById(R.id.spin_area_coev);
        spin_doc = (Spinner) root.findViewById(R.id.spin_doce_coev);
        consultar= root.findViewById(R.id.btn_consultar_coev);

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
                    spin_area.setAdapter(null);
                    spin_doc.setAdapter(null);
                    areas(id_cat);
                    btnconsulta = "false";
                    reset_cur();
                } else {
                    spin_area.setAdapter(null);
                    spin_doc.setAdapter(null);
                    btnconsulta = "false";
                    reset_cur();
                }
                SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("categoria", id_cat);
                editor.commit();
                //Toast.makeText(getContext(),id_cat,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        spin_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String id_area="";
                String arias = spin_area.getItemAtPosition(spin_area.getSelectedItemPosition()).toString();
                for (int x = 0; x < list_area.size(); x++) {
                    spins b = list_area.get(x);
                    if (b.getName().equals(arias)) {
                        id_area = b.getId();
                        break;
                    }
                }

                if(id_area!="0"){
                    spin_doc.setAdapter(null);
                    btnconsulta = "false";
                    reset_cur();
                    docen(id_area);

                } else {
                    spin_doc.setAdapter(null);
                    btnconsulta = "false";
                    reset_cur();
                }
                SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("id_area",id_area);
                editor.commit();
                //Toast.makeText(getContext(),id_area,Toast.LENGTH_LONG).show();
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
                String id_area = preferences.getString("id_area","");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("direccion", conexion.URL_WEB_SERVICES+"datos_apk.php?sel_doc_coev="+id_doc+"&sel_area_coev2="+id_area);
                editor.commit();
                if(id_doc=="0"){btnconsulta = "false";}else{ btnconsulta = "true";}
                //Toast.makeText(getContext(),id_doc,Toast.LENGTH_LONG).show();
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
        area.clear();
        list_area.clear();
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

                                String espe = jsonObject1.getString("name");
                                String sid = jsonObject1.getString("id");
                                cat.add(espe);
                                list_cat.add(new spins(sid, espe));

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

    public void areas(String aria){
        doc.clear();
        list_doc.clear();
        area.clear();
        area.add(0,"SELECCIONE EL AREA");
        list_area.clear();
        list_area.add(new spins("0","SELECCIONE EL AREA"));

        RequestQueue requestQueue=Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES+"datos_apk.php?sel_cur_coev="+aria,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("datos_apk");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String perio=jsonObject1.getString("NAME_AREA");
                                String sid = jsonObject1.getString("ID_AREA");
                                area.add(perio);
                                list_area.add(new spins(sid,perio));
                            }
                            spin_area.setAdapter(new SpinnerAdaptador(getActivity(),area));
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

    public void docen(String areo){
        doc.clear();
        doc.add(0,"SELECCIONE EL DOCENTE");
        list_doc.clear();
        list_doc.add(new spins("0","SELECCIONE EL DOCENTE"));
        //Toast.makeText(getContext(),"preconsultaaaaaaaaaaaaaaaaaaaaaaaaa",Toast.LENGTH_LONG).show();
        RequestQueue requestQueue=Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, conexion.URL_WEB_SERVICES+"datos_apk.php?sel_area_coev="+areo,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getContext(),"responde",Toast.LENGTH_LONG).show();
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("datos_apk");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                if(TextUtils.equals(jsonObject1.getString("COMPLETADO"),"SI")) {
                                    String doce = jsonObject1.getString("NAME_SURVEY");
                                    String sid = jsonObject1.getString("ID_SURVEY");
                                    doc.add(doce);
                                    list_doc.add(new spins(sid, doce));
                                }
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
        editor.putString("direccion",conexion.URL_WEB_SERVICES+"datos_apk.php?sel_doc_coev=0&sel_area_coev2=0");
        editor.commit();
    }
}