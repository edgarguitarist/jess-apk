package com.letrasyvida.evaluaciones_letrasyvida.ui.coevaluacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CoevaluacionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CoevaluacionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Coevaluacion fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}