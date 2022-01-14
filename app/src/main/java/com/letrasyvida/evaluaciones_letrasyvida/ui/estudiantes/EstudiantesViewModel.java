package com.letrasyvida.evaluaciones_letrasyvida.ui.estudiantes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EstudiantesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EstudiantesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is estudiantes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}