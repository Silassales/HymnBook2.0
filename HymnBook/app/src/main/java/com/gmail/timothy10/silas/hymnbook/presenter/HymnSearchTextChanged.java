package com.gmail.timothy10.silas.hymnbook.presenter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

/**
 * HymnSearchTextChanged
 * <p>
 * Created by Silas on 6/6/2018.
 */

public class HymnSearchTextChanged implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i("HymnTextChanged", s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
