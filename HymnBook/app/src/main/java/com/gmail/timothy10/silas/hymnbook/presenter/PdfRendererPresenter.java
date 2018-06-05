package com.gmail.timothy10.silas.hymnbook.presenter;

import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;

import com.gmail.timothy10.silas.hymnbook.view.impl.PdfRendererBasicViewImpl;

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

    public boolean onTouch(View view, MotionEvent event, PdfRenderer.Page mCurrentPage) {
        switch(event.getAction()) {
            case(MotionEvent.ACTION_UP):
                float eventX = event.getX();
                if(eventX <= pdfRendererBasicView.getActivity().getWindow().getDecorView().getWidth()/2) {
                    pdfRendererBasicView.showPage(mCurrentPage.getIndex() - 1);
                } else {
                    pdfRendererBasicView.showPage(mCurrentPage.getIndex() + 1);
                }
                break;
        }
        performClick(view);
        return true;
    }

    private boolean performClick(View view) {
        return view.isClickable() && view.isEnabled() && view.performClick();
    }


}
