package com.gmail.timothy10.silas.hymnbook.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * GreenBook
 * <p>
 * Created by Silas on 6/8/2018.
 */

public class GreenBook implements HymnBook{

    private static final int FIRST_HYMN = 1;
    private static final int LAST_HYMN = 438;

    /**
    TODO: These arrays represent the hymns that have 2 or more pages in the hymn book, this is used to populate
        the map that maps a hymn number to a page number, the algo used to do this requires these arrays
        and these arrays are only available by going through the hymn book and writing it down, I therefore
        am sure that there is a better way to do this, and by all means hope I can improve upon this in the
        future.
     */
    private static final int[] TWO_PAGE = { 2,7,15,16,18,24,30,35,36,43,44,48,57,63,68,72,75,76,77,78,81,82,83,84,85,88,89,98,99,100,101,104,105,106,109,110,113,120,121,122,125,128,129,132,135,143,144,147,150,151,161,163,173,176,181,188,189,194,195,196,197,202,205,210,213,222,227,228,237,240,245,246,247,248,253,256,257,260,265,268,269,270,273,274,275,278,279,285,286,289,291,294,295,296,301,314,317,320,325,330,335,338,345,350,355,356,361,364,369,372,377,380,383,384,385,386,389,400,403,414,421,429,430,435,436 };
    private static final int[] THREE_PAGE = { 3,10,28,37,93,95,116,127,134,183,209,299,307,328,339,360,367,373,394 };
    private static final int[] FOUR_PAGE = { 62,140,170,283,290,308,309,326,327 };

    private int current_hymn;

    private final Map hymn_book_map;

    public GreenBook() {
        hymn_book_map = Collections.unmodifiableMap(populateHymnBook());
        current_hymn = FIRST_HYMN;
    }

    // for resume app state, we want current hymn not to be the first hymn
    public GreenBook(int current_hymn) {
        hymn_book_map = Collections.unmodifiableMap(populateHymnBook());
        this.current_hymn = (current_hymn >= FIRST_HYMN && current_hymn <= LAST_HYMN) ? current_hymn : FIRST_HYMN;
    }

    @Override
    public int getNextHymnPage() {
        return 0;
    }

    @Override
    public int getPrevHymnPage() {
        return 0;
    }

    @Override
    public int getFirstHymnPage() {
        return 0;
    }

    @Override
    public int getLastHymnPage() {
        return 0;
    }

    /*
        populates the hymnbook map with <hymnNumber, pageNumber> values
     */
    private Map populateHymnBook() {
        Map<Integer, Integer> new_hymn_map = new HashMap<>();

        return new_hymn_map;
    }

}
