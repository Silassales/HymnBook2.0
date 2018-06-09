package com.gmail.timothy10.silas.hymnbook.presenter;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.gmail.timothy10.silas.hymnbook.model.GreenBook;
import com.gmail.timothy10.silas.hymnbook.model.HymnBook;
import com.gmail.timothy10.silas.hymnbook.view.impl.PdfRendererBasicViewImpl;

/**
 * HymnSearchTextChanged
 * <p>
 * Created by Silas on 6/6/2018.
 */

public class HymnSearchTextChanged implements TextWatcher {

    @NonNull
    private PdfRendererBasicViewImpl pdfRendererBasicView;

    private HymnBook hymnBook;

    public HymnSearchTextChanged(PdfRendererBasicViewImpl pdfRendererBasicView) {
        this.pdfRendererBasicView = pdfRendererBasicView;
        this.hymnBook = new GreenBook();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int hymn_number;
        try {
            hymn_number = Integer.parseInt(s.toString());
        } catch (NumberFormatException e) {
            Log.w("HymnSearchTextChanged", "Invalid text in onTextChanged: " + s);
            Log.e("HymnSearchTextChanged", "NumberFormatException", e);
            return;
        }

        int hymn_page_number = hymnBook.getHymnPageNumber(hymn_number);
        if(hymn_page_number != -1) {
            pdfRendererBasicView.showPage(hymn_page_number);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
