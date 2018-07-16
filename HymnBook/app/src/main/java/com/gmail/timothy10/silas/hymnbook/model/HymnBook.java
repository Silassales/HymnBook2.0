package com.gmail.timothy10.silas.HymnBook.model;

/**
 * HymnBook
 * <p>
 * Created by Silas on 6/8/2018.
 */

/**
 * This interface is used to represent all we need to know about a Hymnbook at the presenter level
 * Presenter *should* never need to write to any of these values, and should not interact with
 * the hymn book in any way other than represented here
 *
 * Currently getNext/prev is not needed due to the pdf being one large on, but if we ever split that up
 * we will need those methods
 */
public interface HymnBook {

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

    /**
     * returns the page value for a given hymn number (used for search)
     * @returns the page number for the given hymn number, or -1 for invalid input
     */
    int getHymnPageNumber(int hymn_number);

    /**
     * returns the hymn number for a given page value
     * @returns the hymn number for a given page value, or -1 for invalid input
     */
    int getHymnNumber(int page_value);
}
