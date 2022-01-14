package com.letrasyvida.evaluaciones_letrasyvida.ui.autoevaluacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AutoevaluacionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AutoevaluacionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Autoevaluacion fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}