/*
 * The MIT License
 *
 * Copyright 2016 Max Balushkin.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package edu.sibfu.isit.nemeton.lib;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Max Balushkin
 */
public class Utils {
    
    /**
     * Converts integer to string.
     * 
     * @param num Number
     * @return String
     */
    public static String str(int num) {
        return Integer.toString(num);
    }
    /**
     * Converts double to string.
     * 
     * @param num Number
     * @return String
     */
    public static String str(double num) {
        return Double.toString(num);
    }
    
    public static double round( double aNum, int aDigitAfterComma ) {
        return new BigDecimal( aNum ).setScale( aDigitAfterComma, RoundingMode.HALF_DOWN ).doubleValue();
    }
    
    /**
     * Converts string to integer.
     * 
     * @param str String
     * @return Integer
     */
    public static int intg(String str) {
        return Integer.parseInt(str);
    }
    /**
     * Converts string to double.
     * 
     * @param str Stirng
     * @return Double
     */
    public static double real(String str) {
        return Double.parseDouble(str);
    }
}
