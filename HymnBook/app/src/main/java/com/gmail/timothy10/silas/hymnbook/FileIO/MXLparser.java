package com.gmail.timothy10.silas.hymnbook.FileIO;

import android.content.Context;
import android.util.Log;

import com.gmail.timothy10.silas.hymnbook.LicenceKeyInstance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import uk.co.dolphin_com.sscore.LoadOptions;
import uk.co.dolphin_com.sscore.SScore;
import uk.co.dolphin_com.sscore.ex.ScoreException;

/**
 * Created by Silas on 5/26/2018.
 */

public class MXLparser {

    private static final String OUTPUT_EXTENSION = ".xml";

    private Context context;
    private InternalFileOperations internalFileOperations;
    private ZipInputStreamImpl zipInputStream;

    public MXLparser(Context context) {
        this.context = context;
        this.internalFileOperations = new InternalFileOperations(context);
        this.zipInputStream = new ZipInputStreamImpl();
    }

    /**
     * generates an SScore for the given hymn number, assumes that the hymns are in the raw folder
     * will output to default application folder (data/data/{appname} on emulator)
     *
     * @param hymnNumber
     * @return the correct SScore or null if something went wrong (check logs)
     */
    public SScore generateSScore(Integer hymnNumber) {

        String outFileStringName = hymnNumber + OUTPUT_EXTENSION;

        InputStream inputStream = internalFileOperations.getRawHymn(hymnNumber);
        if(inputStream == null) {
            Log.w("MXLParseWarn", String.format("Invalid hymn #%d in generateSScore", hymnNumber));
            return null;
        }

        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(outFileStringName, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            //if openFileOutput throws an exception, something funky is going on so just print stack trace
            e.printStackTrace();
            return null;
        }

        //we now have an init'd input/output stream, so lets go ahead an unzip!
        try {
            zipInputStream.unzipFile(inputStream, outputStream);
        } catch (IOException e) {
            Log.w("MXLParseWarn", "zipInputStream had troubles reading/writing");
            return null;
        }

        //now create the SScore
        File hymnXMLFile = context.getFileStreamPath(outFileStringName);
        if(!hymnXMLFile.exists()) {
            Log.w("MXLParseWarn", String.format("Couldnt load %d file for hymn #%s", OUTPUT_EXTENSION, hymnNumber));
            return null;
        }

        //specify load options
        Log.i("MXLParseInfo", String.format("Attempting to create a SScore for hymn #%d", hymnNumber));
        LoadOptions loadOptions = new LoadOptions(LicenceKeyInstance.SeeScoreLibKey, true);

        try {
            return SScore.loadXMLFile(hymnXMLFile, loadOptions);
        } catch (ScoreException e) {
            Log.w("MXLParseWarn", String.format("SScore could not load xml file for hymn #%d", hymnNumber));
            e.printStackTrace();
            return null;
        }
    }
}
