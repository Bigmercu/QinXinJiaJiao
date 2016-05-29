package com.bigmercu.qinxinjiajiao.Util;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Listener used to capture "on selected item" event on MaterialBetterSpinner.
 * Created by douglas on 22/09/15.
 */
public abstract class OnItemSelectedListener implements TextWatcher {

    @Override
    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // nothing here
    }

    @Override
    public final void onTextChanged(CharSequence s, int start, int before, int count) {
        // nothing here
    }

    @Override
    public final void afterTextChanged(Editable editable) {
        onItemSelected(editable.toString());
    }

    protected abstract void onItemSelected(String string);
}