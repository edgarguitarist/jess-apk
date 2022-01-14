package com.letrasyvida.evaluaciones_letrasyvida.ui.directivos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DirectivosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DirectivosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Directivos fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}