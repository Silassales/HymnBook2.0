package com.gmail.timothy10.silas.hymnbook.FileIO;

import android.content.Context;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by Silas on 5/26/2018.
 */

public class InternalFileOperations {

    private static final String HYMN_STRING_VALUE = "hymn";

    private Context context;

    public InternalFileOperations(Context context) {
        this.context = context;
    }

    /**
     * returns the RawResource value for the hymnNumber provided
     *
     * @param hymnNumber
     * @return a InputStream representing the raw resource, or null if something hymn could not be found
     */
    public InputStream getRawHymn(Integer hymnNumber) {

        String hymnName = HYMN_STRING_VALUE + hymnNumber.toString();

        int resId = context.getResources().getIdentifier("raw/" + hymnName, null, context.getPackageName());
        if(resId == 0) {
            Log.w("InternalIOWarn", String.format("hymnNumber %d with identifier %s could not be found as a raw resource.", hymnNumber, hymnName));
            return null;
        }

        return context.getResources().openRawResource(resId);

    }
}
