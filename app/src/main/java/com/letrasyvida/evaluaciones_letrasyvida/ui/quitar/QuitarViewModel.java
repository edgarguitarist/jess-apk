package com.letrasyvida.evaluaciones_letrasyvida.ui.quitar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuitarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public QuitarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Quitar fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}