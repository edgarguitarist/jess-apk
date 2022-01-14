package com.letrasyvida.evaluaciones_letrasyvida.ui.responder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResponderViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ResponderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Responder fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}