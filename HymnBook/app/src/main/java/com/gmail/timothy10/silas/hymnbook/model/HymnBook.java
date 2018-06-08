package com.gmail.timothy10.silas.hymnbook.model;

/**
 * HymnBook
 * <p>
 * Created by Silas on 6/8/2018.
 */

/**
 * This interface is used to represent all we need to know about a Hymnbook at the presenter level
 * Presenter *should* never need to write to any of these values, and should not interact with
 * the hymn book in any way other than represented here
 */
public interface HymnBook {

    /**
     * returns the page value of the next hymn (based on current static current hymn)
     * @return the page number for the next hymn
     */
    int getNextHymnPage();

    /**
     * returns the page value of the previous hymn
     * @returns the page number for the prev hymn
     */
    int getPrevHymnPage();

    /**
     * returns the page value of the first hymn in the hymn book (first page might not be the first
     * page of the hymn (title, etc))
     * @returns the page number for the first hymn in the hymnbook
     */
    int getFirstHymnPage();

    /**
     * returns the page value of the last hymn in the hymn book
     * @returns the page number for the last hymn in the hymnbook
     */
    int getLastHymnPage();
}
