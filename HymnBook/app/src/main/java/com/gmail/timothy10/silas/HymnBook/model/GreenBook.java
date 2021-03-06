package com.gmail.timothy10.silas.HymnBook.model;

import android.util.Log;

import com.gmail.timothy10.silas.HymnBook.Constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * GreenBook
 * <p>
 * Created by Silas on 6/8/2018.
 */

public class GreenBook implements HymnBook{

    /**
    TODO: These arrays represent the hymns that have 2 or more pages in the hymn book, this is used to populate
        the map that maps a hymn number to a page number, the algo used to do this requires these arrays
        and these arrays are only available by going through the hymn book and writing it down, I therefore
        am sure that there is a better way to do this, and by all means hope I can improve upon this in the
        future.
     */
    private static final int[] TWO_PAGE = { 2,7,15,16,18,24,30,35,36,43,44,48,57,63,68,72,75,76,77,78,81,82,83,84,85,88,89,98,99,100,101,104,105,106,109,110,113,120,121,122,125,128,129,132,135,143,144,147,150,151,161,163,173,176,181,188,189,194,195,196,197,202,205,210,213,222,227,228,237,240,245,246,247,248,253,256,257,260,265,268,269,270,273,274,275,278,279,285,286,289,291,294,295,296,301,314,317,320,325,330,335,338,345,350,355,356,361,364,369,372,377,380,383,384,385,386,389,400,403,414,421,426,429,430,435,436 };
    private static final int[] THREE_PAGE = { 3,10,28,37,93,95,116,127,134,183,209,299,307,328,339,360,367,373,394 };
    private static final int[] FOUR_PAGE = { 62,140,170,283,290,308,309,326,327 };

    private final Map hymn_book_map;

    public GreenBook() {
        hymn_book_map = Collections.unmodifiableMap(populateHymnBook());
    }

    @Override
    public int getFirstHymnPage() { return Constants.GREEN_BOOK_FIRST_HYMN_PAGE; }

    @Override
    public int getLastHymnPage() { return Constants.GREEN_BOOK_LAST_HYMN_PAGE; }

    @Override
    public int getHymnPageNumber(int hymn_number) {
        //validation
        if(hymn_number < Constants.GREEN_BOOK_FIRST_HYMN || hymn_number  > Constants.GREEN_BOOK_LAST_HYMN) {
            Log.w("GreenBook", "Invalid hymn number in getHymnPageNumber: " + hymn_number);
            return -1;
        }

        int page_num = (int)hymn_book_map.get(hymn_number);

        Log.i("GreenBook", "GetHymnPageNumber value: " + page_num);
        return page_num;
    }

    @Override
    public int getHymnNumber(int page_value) {
        Integer hymn_number = (Integer) hymn_book_map.get(page_value);
        if(hymn_number != null) {
            return hymn_number;
        } else {
            return -1;
        }
    }

    /*
        populates the hymnbook map with <hymnNumber, pageNumber> values
     */
    private Map populateHymnBook() {
        Map<Integer, Integer> new_hymn_map = new HashMap<>();
        int page_num = Constants.GREEN_BOOK_FIRST_HYMN_PAGE;

        Log.i("GreenBook", "populating green book map");

        for(int i = 1; i <= Constants.GREEN_BOOK_LAST_HYMN; i++) {
            new_hymn_map.put(i, page_num);
            if(arrayContains(TWO_PAGE, i)) {
                page_num+=2;
            } else if(arrayContains(THREE_PAGE, i)) {
                page_num+=3;
            } else if(arrayContains(FOUR_PAGE, i)) {
                page_num+=4;
            } else {
                page_num++;
            }
        }

        return new_hymn_map;
    }

    private boolean arrayContains(int[] list, int i) {
        for(int el : list) {
            if (el == i) return true;
        }
        return false;
    }


}
