package com.gmail.timothy10.silas.HymnBook.view.def;

import android.content.Context;

import java.io.IOException;

/**
 * Created by Silas on 6/2/2018.
 */

public interface PdfRendererBasicView {

    /**
     * Sets up a {@link android.graphics.pdf.PdfRenderer} and related resources.
     */
    void openRenderer(Context context) throws IOException;

    /**
     * Closes the {@link android.graphics.pdf.PdfRenderer} and related resources.
     *
     * @throws java.io.IOException When the PDF file cannot be closed.
     */
    void closeRenderer() throws IOException;

    /**
     * Shows the specified page of PDF to the screen.
     *
     * @param index The page index.
     */
    void showPage(int index);

    /**
     * Gets the number of pages in the PDF. This method is marked as public for testing.
     *
     * @return The number of pages.
     */
    int getPageCount();
}
