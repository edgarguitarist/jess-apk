package com.letrasyvida.evaluaciones_letrasyvida.ui.salir;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SalirViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SalirViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Salir fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}