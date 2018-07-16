package com.gmail.timothy10.silas.HymnBook.presenter;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.gmail.timothy10.silas.HymnBook.model.GreenBook;
import com.gmail.timothy10.silas.HymnBook.model.HymnBook;
import com.gmail.timothy10.silas.HymnBook.view.impl.PdfRendererBasicViewImpl;

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
        //remove the hint value
        pdfRendererBasicView.clearHymnSearchText();

        //when the text is erased it will show up as a value of '""' therefore we want to check for this
        //to avoid filling up log with stack traces and save that for when we actually have a problem
        //we also want to reset the hymn search hint text
        if(s.toString().equals("")) {
            pdfRendererBasicView.resetHymnSearchText();
            return;
        }

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


    public int getHymnNumberForPageNumber(int current_page) {
        return hymnBook.getHymnNumber(current_page);
    }
}
