package com.letrasyvida.evaluaciones_letrasyvida;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.wifi.p2p.WifiP2pManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jorgesys
 */
public class SpinnerAdaptador extends BaseAdapter {
    List<String> values;
    Context context;

    public SpinnerAdaptador(Context context, List<String> values){
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount(){
        return values.size();
    }

    @Override
    public Object getItem(int position){
        return values.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent){
        LayoutInflater inflater=LayoutInflater.from(context);
        view=inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null);
        TextView txv=(TextView)view.findViewById(android.R.id.text1);
        if(pos==0) { //Primer elemento color Azul #39399F
            txv.setBackgroundColor(Color.parseColor("#00000000"));
            txv.setTextSize(16);
            txv.setTypeface(null, Typeface.BOLD);
            txv.setTextColor(Color.parseColor("#44A0D3")); //Texto color Blanco
        }else { //Otros elementos ...
            txv.setBackgroundColor(Color.parseColor("#00000000"));
            txv.setTextColor(Color.parseColor("#44A0D3")); //Texto color Azul
            txv.setTextSize(16);
            txv.setTypeface(null, Typeface.BOLD);
        }
        txv.setText(values.get(pos));
        return view;
    }

}
