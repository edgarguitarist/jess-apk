package com.letrasyvida.evaluaciones_letrasyvida.ui.salir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.letrasyvida.evaluaciones_letrasyvida.MainActivity;
import com.letrasyvida.evaluaciones_letrasyvida.R;
import com.letrasyvida.evaluaciones_letrasyvida.menu;

public class SalirFragment extends Fragment {

    private SalirViewModel salirViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        salirViewModel =
                new ViewModelProvider(this).get(SalirViewModel.class);
        View root = inflater.inflate(R.layout.fragment_salir, container, false);

        startActivity(new Intent(getActivity(), MainActivity.class));
        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("correo","");
        editor.commit();
        getActivity().finish();
        return root;
    }
}