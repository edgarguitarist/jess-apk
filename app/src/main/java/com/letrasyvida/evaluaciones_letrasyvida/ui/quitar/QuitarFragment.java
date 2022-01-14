package com.letrasyvida.evaluaciones_letrasyvida.ui.quitar;

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
import com.letrasyvida.evaluaciones_letrasyvida.R;

public class QuitarFragment extends Fragment {

    private QuitarViewModel quitarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        quitarViewModel =
                new ViewModelProvider(this).get(QuitarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_quitar, container, false);

        return root;
    }
}