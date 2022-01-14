package com.letrasyvida.evaluaciones_letrasyvida.ui.padres;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PadresViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PadresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is padres fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}