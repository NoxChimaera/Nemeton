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

import edu.sibfu.isit.nemeton.models.Point;

/**
 * Utilities for points.
 * 
 * @author Max Balushkin
 */
public class PointUtil {
    
    private static final String DELIMITER_RE = "[ ,;]+";
    
    /**
     * Converts point to string.
     * 
     * @param aPoint point
     * @return doubles delimited with `, `: ( [param] ", " )* [param]
     */
    public static String str( Point aPoint ) {
        StringBuilder bldr = new StringBuilder();
        for ( double param : aPoint.get() ) {
            bldr.append( numberToString( param ) ).append( ", " );
        }
        if ( bldr.length() != 0 ) {
            return bldr.delete( bldr.length() - 2, bldr.length() ).toString();
        } else {
            return "";
        }
    }
    
    /**
     * Converts number to string.
     * 
     * @param aNum number
     * @return string representation of number
     */
    private static String numberToString( double aNum ) {
        if ( aNum % 1 == 0 ) {
            return Integer.toString( (int) aNum );
        } else {
            return Double.toString( aNum );
        }
    }
    
    /**
     * Parses point from string.
     * 
     * @param aSrc string
     * @return point
     */
    public static Point parse( String aSrc ) {
        final String[] parts = aSrc.split( DELIMITER_RE );
        final int n = parts.length;
        final double[] params = new double[ n ];
        for ( int i = 0; i < n; i++ ) {
            params[ i ] = Double.parseDouble( parts[ i ] );
        }
        return new Point( params );
    }
    
    /**
     * Parses point with specified arity from string.
     * If string contains numbers less than arity, fill the rest params with last number:
     *  In: "42", 3
     *  Out: { 42, 42, 42 }
     * 
     * @param aSrc string
     * @param aArity point arity
     * @return point
     */
    public static Point parse( final String aSrc, final int aArity ) {
        final String[] parts = aSrc.split( DELIMITER_RE );
        final int n = parts.length;
        final double[] params = new double[ aArity ];
        double last = 0;
        for ( int i = 0; i < n && i < aArity; i++ ) {
            last = Double.parseDouble( parts[ i ] );
            params[ i ] = last;
        }
        if ( n < aArity ) {
            for ( int i = n; i < aArity; i++ ) {
                params[ i ] = last;
            }
        }
        return new Point( params );
    }
    
}
