package com.gmail.timothy10.silas.HymnBook.presenter;

import android.app.Activity;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.gmail.timothy10.silas.HymnBook.Constants;
import com.gmail.timothy10.silas.HymnBook.view.impl.PdfRendererBasicViewImpl;

/**
 * PdfRendererPresenter
 * <p>
 * Created by Silas on 6/5/2018.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PdfRendererPresenter {

    @NonNull
    private PdfRendererBasicViewImpl pdfRendererBasicView;

    public PdfRendererPresenter (PdfRendererBasicViewImpl pdfRendererBasicView) {
        this.pdfRendererBasicView = pdfRendererBasicView;
    }

    public boolean onTouch(Activity activity, View view, MotionEvent event, PdfRenderer.Page mCurrentPage) {
        switch (event.getAction()) {
            case (MotionEvent.ACTION_UP):
                // if the keyboard is open, dont show next/prev page, just close the keyboard
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                if(!imm.hideSoftInputFromWindow(view.getWindowToken(), 0)) {
                    float eventX = event.getX();
                    if (eventX <= pdfRendererBasicView.getActivity().getWindow().getDecorView().getWidth() / 2) {

                        pdfRendererBasicView.showPage(getPrevPage(mCurrentPage.getIndex()));
                    } else {
                        pdfRendererBasicView.showPage(getNextPage(mCurrentPage.getIndex()));
                    }
                    break;
                }
        }
        performClick(view);
        return true;
    }

    public int getNextPage(int current_page) {
        Log.i("PdfRendererPresenter", "getNextPage for current_page: " + current_page);
        //range check
        if(++current_page > Constants.GREEN_BOOK_LAST_PAGE) return --current_page;
        return current_page;
    }

    public int getPrevPage(int current_page) {
        Log.i("PdfRendererPresenter", "getPrevPage for current_page: " + current_page);
        if(--current_page < Constants.GREEN_BOOK_FIRST_PAGE) return ++current_page;
        return current_page;
    }

    private boolean performClick(View view) {
        return view.isClickable() && view.isEnabled() && view.performClick();
    }


}
