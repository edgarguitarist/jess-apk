package com.letrasyvida.evaluaciones_letrasyvida;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class EvaluacionesAdaptador extends RecyclerView.Adapter<EvaluacionesAdaptador.ViewHolderEvaluaciones> implements View.OnClickListener{

    ArrayList<Evaluaciones> listaEvaluaciones;
    private View.OnClickListener listener;


    public EvaluacionesAdaptador(ArrayList<Evaluaciones> listaEvaluaciones) {
        this.listaEvaluaciones = listaEvaluaciones;
    }

    @NonNull
    @Override
    public ViewHolderEvaluaciones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_evaluaciones,null,false);
        view.setOnClickListener(this);

        return new EvaluacionesAdaptador.ViewHolderEvaluaciones(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EvaluacionesAdaptador.ViewHolderEvaluaciones viewHolderEvaluaciones, int i) {

        viewHolderEvaluaciones.Id_ev.setText(listaEvaluaciones.get(i).getId_ev());
        viewHolderEvaluaciones.Name_ev.setText(listaEvaluaciones.get(i).getName_ev());
        viewHolderEvaluaciones.Id_course.setText(listaEvaluaciones.get(i).getId_course());
        viewHolderEvaluaciones.Index.setText(listaEvaluaciones.get(i).getIndex());
    }

    @Override
    public int getItemCount() {return listaEvaluaciones.size();
    }


    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderEvaluaciones extends RecyclerView.ViewHolder {

        TextView Id_ev, Name_ev, Id_course, Index;

        public ViewHolderEvaluaciones(@NonNull View itemView) {
            super(itemView);

            Id_ev= (TextView) itemView.findViewById(R.id.evid);
            Name_ev= (TextView) itemView.findViewById(R.id.evname);
            Id_course= (TextView) itemView.findViewById(R.id.evcourse);
            Index = (TextView) itemView.findViewById(R.id.evindex);
        }
    }
}

