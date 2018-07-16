package com.gmail.timothy10.silas.HymnBook.view.impl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gmail.timothy10.silas.HymnBook.Constants;
import com.gmail.timothy10.silas.HymnBook.R;
import com.gmail.timothy10.silas.HymnBook.presenter.HymnSearchTextChanged;
import com.gmail.timothy10.silas.HymnBook.presenter.PdfRendererPresenter;
import com.gmail.timothy10.silas.HymnBook.util.BitmapScalar;
import com.gmail.timothy10.silas.HymnBook.util.DeviceDimensionsHelper;
import com.gmail.timothy10.silas.HymnBook.view.def.PdfRendererBasicView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * PdfRendererBasicViewImpl
 * <p>
 * Created by Silas on 6/2/2018.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PdfRendererBasicViewImpl extends Fragment implements View.OnTouchListener, PdfRendererBasicView {
    /**
     * Key string for saving the state of current page index.
     */
    private static final String STATE_CURRENT_PAGE_INDEX = "current_page_index";

    /**
     * The filename of the PDF.
     */
    private static final String FILENAME = "hymn_book.pdf";

    /**
     * File descriptor of the PDF.
     */
    private ParcelFileDescriptor mFileDescriptor;

    /**
     * {@link android.graphics.pdf.PdfRenderer} to render the PDF.
     */
    private PdfRenderer mPdfRenderer;

    /**
     * Page that is currently shown on the screen.
     */
    private PdfRenderer.Page mCurrentPage;

    /**
     * {@link android.widget.ImageView} that shows a PDF page as a {@link android.graphics.Bitmap}
     */
    private ImageView mImageView;

    /**
     * PDF page index
     */
    private int mPageIndex;

    /**
     * Presenter for this class
     */
    @NonNull
    private PdfRendererPresenter pdfRendererPresenter;

    /**
     * shared preferences
     */
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    /**
     * hymn search bar
     */
    private EditText hymnSearchBar;

    private HymnSearchTextChanged hymnSearchTextChanged;

    public PdfRendererBasicViewImpl() {
        pdfRendererPresenter = new PdfRendererPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {

        Log.i("PRBVI", "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        // Retain view references.
        mImageView = view.findViewById(R.id.pdfImageView);

        view.setOnTouchListener(this);

        //set up search bar
        hymnSearchBar = view.findViewById(R.id.HymnSearchBar);
        resetHymnSearchText();

        /*
            listener for hymn search bar
        */
        hymnSearchTextChanged = new HymnSearchTextChanged(this);
        hymnSearchBar.addTextChangedListener(hymnSearchTextChanged);

        //shared preferences init
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        sharedPreferencesEditor = sharedPreferences.edit();

        mPageIndex = Constants.GREEN_BOOK_FIRST_HYMN_PAGE;
        // If there is a savedInstanceState (screen orientations, etc.), we restore the page index.
        if (null != savedInstanceState) {
            mPageIndex = savedInstanceState.getInt(STATE_CURRENT_PAGE_INDEX, Constants.GREEN_BOOK_FIRST_HYMN_PAGE);
            Log.i("PdfRendererBasic","Restoring current page state: " + mPageIndex);
        } else {
            // Try to retrieve it from the fragments bundle
            Bundle fragment_bundle = this.getArguments();
            if (fragment_bundle != null) {
                mPageIndex = fragment_bundle.getInt(STATE_CURRENT_PAGE_INDEX, Constants.GREEN_BOOK_FIRST_HYMN_PAGE);
                Log.i("PdfRendererBasic","Restoring current page state: " + mPageIndex);
            }
        }
    }

    @Override
    public void onStart() {

        Log.i("PRBVI", "onStart");
        super.onStart();
        try {
            //see if there is a saved page to restore to
            mPageIndex = (sharedPreferences.getInt(STATE_CURRENT_PAGE_INDEX, 0) != 0) ? sharedPreferences.getInt(STATE_CURRENT_PAGE_INDEX, 0) : mPageIndex;
            Log.i("PRVBI", "Loaded pageIndex: " + mPageIndex);
            openRenderer(getActivity());
            showPage(mPageIndex);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        try {
            closeRenderer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i("PRBVI", "onResume");
        // Try to resume page it from the fragments bundle
        Bundle fragment_bundle = this.getArguments();
        if (fragment_bundle != null) {
            mPageIndex = fragment_bundle.getInt(STATE_CURRENT_PAGE_INDEX, Constants.GREEN_BOOK_FIRST_HYMN_PAGE);
            Log.i("PdfRendererBasic","Restoring current page state: " + mPageIndex);
            showPage(mPageIndex);
        }
    }


    @Override
        public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != mCurrentPage) {
            Log.i("PdfRendererBasic","Saving current page state: " + mCurrentPage.getIndex());
            outState.putInt(STATE_CURRENT_PAGE_INDEX, mCurrentPage.getIndex());
            sharedPreferencesEditor.putInt(STATE_CURRENT_PAGE_INDEX, mCurrentPage.getIndex()).apply();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i("PRBVI","onActivityCreated");
    }

    @SuppressLint("ClickableViewAccessibility") // performClick is called in presenter
    @Override
    public boolean onTouch(View view, MotionEvent event) {
            return pdfRendererPresenter.onTouch(getActivity(), view, event, mCurrentPage);
    }


    public void openRenderer(Context context) throws IOException {
        Log.i("PRBVI","here");
        // In this sample, we read a PDF from the assets directory.
        File file = new File(context.getCacheDir(), FILENAME);
        if (!file.exists()) {
            // Since PdfRenderer cannot handle the compressed asset file directly, we copy it into
            // the cache directory.
            InputStream asset = context.getAssets().open(FILENAME);
            FileOutputStream output = new FileOutputStream(file);
            final byte[] buffer = new byte[1024];
            int size;
            while ((size = asset.read(buffer)) != -1) {
                output.write(buffer, 0, size);
            }
            asset.close();
            output.close();
        }
        mFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        // This is the PdfRenderer we use to render the PDF.
        if (mFileDescriptor != null) {
            mPdfRenderer = new PdfRenderer(mFileDescriptor);
        }
    }


    public void closeRenderer() throws IOException {
        Log.i("Info", "Closing pdfViewRenderer");

        if (null != mCurrentPage) {
            try {
                mCurrentPage.close();
            } catch(IllegalStateException e) {
                Log.w("PDFRendererBasic", "mCurrentPage attempted to be closed twice");
            }

        }
        mPdfRenderer.close();
        mFileDescriptor.close();
    }

    public void showPage(int index) {
        if (mPdfRenderer.getPageCount() <= index) {
            return;
        }
        // Make sure to close the current page before opening another one.
        if (null != mCurrentPage) {
            try {
                mCurrentPage.close();
            } catch(IllegalStateException e) {
                Log.w("PDFRendererBasic", "mCurrentPage attempted to be closed twice");
            }
        }
        // Use `openPage` to open a specific page in PDF.
        mCurrentPage = mPdfRenderer.openPage(index);
        // Important: the destination bitmap must be ARGB (not RGB).
        Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(), mCurrentPage.getHeight(),
                Bitmap.Config.ARGB_8888);

        int height = DeviceDimensionsHelper.getDisplayHeight(getContext());
        Bitmap scaledBitmap = BitmapScalar.scaleToFitHeight(bitmap, height);

        // Here, we render the page onto the Bitmap.
        // To render a portion of the page, use the second and third parameter. Pass nulls to get
        // the default result.
        // Pass either RENDER_MODE_FOR_DISPLAY or RENDER_MODE_FOR_PRINT for the last parameter.
        mCurrentPage.render(scaledBitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        // We are ready to show the Bitmap to user.
        mImageView.setImageBitmap(scaledBitmap);
        updateUi();
    }

    public void updateUi() {
        int hymn_number = hymnSearchTextChanged.getHymnNumberForPageNumber(mCurrentPage.getIndex() + 1);
        getActivity().setTitle(getString(R.string.title_name));
    }

    public int getPageCount() {
        return mPdfRenderer.getPageCount();
    }

    public void clearHymnSearchText() {
        hymnSearchBar.setHint("");
    }

    public void resetHymnSearchText() {
        hymnSearchBar.setHint(R.string.HymnSearchText);
    }

}