package com.gmail.timothy10.silas.hymnbook.model;

/**
 * GreenBook
 * <p>
 * Created by Silas on 6/8/2018.
 */

public class GreenBook implements HymnBook{

    private static final int[] two_page = { 2,7,15,16,18,24,30,35,36,43,44,48,57,63,68,72,75,76,77,78,81,82,83,84,85,88,89,98,99,100,101,104,105,106,109,110,113,120,121,122,125,128,129,132,135,143,144,147,150,151,161,163,173,176,181,188,189,194,195,196,197,202,205,210,213,222,227,228,237,240,245,246,247,248,253,256,257,260,265,268,269,270,273,274,275,278,279,285,286,289,291,294,295,296,301,314,317,320,325,330,335,338,345,350,355,356,361,364,369,372,377,380,383,384,385,386,389,400,403,414,421,429,430,435,436 };
    private static final int[] three_page = { 3,10,28,37,93,95,116,127,134,183,209,299,307,328,339,360,367,373,394 };
    private static final int[] four_page = { 62,140,170,283,290,308,309,326,327 };

    private int current_hymn;


    @Override
    public int getNextHymn() {
        return 0;
    }

    @Override
    public int getPrevHymn() {
        return 0;
    }

    @Override
    public int getFirstHymn() {
        return 0;
    }

    @Override
    public int getLastHymn() {
        return 0;
    }
}
