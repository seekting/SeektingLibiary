
package com.seekting.util;

public class Utils {
    /**
     * @param c
     * @return c is Chinese or not
     */
    public static boolean isChinese(char c) {
        if ((19968 <= c && c < 40623)) {

            return true;
        }

        return false;
    }
}
