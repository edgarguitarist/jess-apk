package com.letrasyvida.evaluaciones_letrasyvida.ui.observacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ObservacionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ObservacionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Observacion fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}