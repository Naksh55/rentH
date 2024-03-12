package com.naksh.renth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> userEmail = new MutableLiveData<>();

    public LiveData<String> getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String email) {
        userEmail.setValue(email);
    }
}
