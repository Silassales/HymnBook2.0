/**
 * SeeScore For Android
 * Dolphin Computing http://www.dolphin-com.co.uk
 */
/* SeeScoreLib Key for Christadelphian Hymnbook

 IMPORTANT! This file is for Christadelphian Hymnbook only.
 It must be used only for the application for which it is licensed,
 and must not be released to any other individual or company.

 Please keep it safe, and make sure you don't post it online or email it.
 Keep it in a separate folder from your source code, so that when you backup the code
 or store it in a source management system, the key is not included.
 */

package com.gmail.timothy10.silas.hymnbook;


import uk.co.dolphin_com.sscore.SScoreKey;

/**
 * The licence key to enable features in SeeScoreLib supplied by Dolphin Computing
 */

public class LicenceKeyInstance
{
    // licence keys: draw, android, embed_id
    private static final int[] keycap = {0X84001,0X0};
    private static final int[] keycode = {0X7191aa55,0X1245ecb3,0X4dbfbc77,0X2cb6e524,0X1b7db56b,0X90282c4f,0X5a1191e6,0X3781c13c,0X8951e98f,0X27b5669c,0X7663a021,0X30222f5c,0X6a61340,0X139d229,0X21c75448};

    public static final SScoreKey SeeScoreLibKey = new SScoreKey("Christadelphian Hymnbook", keycap, keycode);
}
