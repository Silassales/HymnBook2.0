package com.gmail.timothy10.silas.hymnbook.presenter;

import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gmail.timothy10.silas.hymnbook.Constants;
import com.gmail.timothy10.silas.hymnbook.view.impl.PdfRendererBasicViewImpl;

/**
 * PdfRendererPresenter
 * <p>
 * Created by Silas on 6/5/2018.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PdfRendererPresenter extends Constants{

    @NonNull
    private PdfRendererBasicViewImpl pdfRendererBasicView;

    public PdfRendererPresenter (PdfRendererBasicViewImpl pdfRendererBasicView) {
        this.pdfRendererBasicView = pdfRendererBasicView;
    }

    public boolean onTouch(View view, MotionEvent event, PdfRenderer.Page mCurrentPage) {
        switch(event.getAction()) {
            case(MotionEvent.ACTION_UP):
                float eventX = event.getX();
                if(eventX <= pdfRendererBasicView.getActivity().getWindow().getDecorView().getWidth()/2) {

                    pdfRendererBasicView.showPage(getPrevPage(mCurrentPage.getIndex()));
                } else {
                    pdfRendererBasicView.showPage(getNextPage(mCurrentPage.getIndex()));
                }
                break;
        }
        performClick(view);
        return true;
    }

    private int getNextPage(int current_page) {
        Log.i("PdfRendererPresenter", "getNextPage for current_page: " + current_page);
        //range check
        if(++current_page > GREEN_BOOK_LAST_PAGE) return --current_page;
        return current_page;
    }

    private int getPrevPage(int current_page) {
        Log.i("PdfRendererPresenter", "getPrevPage for current_page: " + current_page);
        if(--current_page < GREEN_BOOK_FIRST_PAGE) return ++current_page;
        return current_page;
    }

    private boolean performClick(View view) {
        return view.isClickable() && view.isEnabled() && view.performClick();
    }


}
