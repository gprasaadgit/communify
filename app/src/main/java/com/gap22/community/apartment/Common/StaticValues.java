package com.gap22.community.apartment.Common;

/**
 * Created by NARAYANAN on 10/9/2017.
 */

public class StaticValues {
    private static String EncriptionPass = "C0MmUn!F$";
    private static String EncriptionSalt = "S@L7";

    public static String getEncriptionPass() {
        return EncriptionPass;
    }

    public static String getEncriptionSalt() {
        return EncriptionSalt;
    }
}
