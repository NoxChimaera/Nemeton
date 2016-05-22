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
package edu.sibfu.isit.nemeton.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * Represents point in N-dimensional space.
 * 
 * @author Max Balushkin
 */
public class Point {
    
    private final int arity;
    private final double[] params;
    
    /**
     * Creates point from list of coordinates.
     * 
     * @param aParams coordinates
     */
    public Point( double ... aParams ) {
        this.arity = aParams.length;
        this.params = aParams;
    }
    
    /**
     * Copying constructor.
     * 
     * @param aSrc copied point
     */
    public Point( Point aSrc ) {
        arity = aSrc.getArity();
        params = Arrays.copyOf( aSrc.params, arity );
    }
    
    /**
     * Creates new point.
     * point = ( value ... value )
     * 
     * @param aArity point arity
     * @param aValue coordinate
     */
    public Point( int aArity, double aValue ) {
        arity = aArity;
        params = new double[ aArity ];
        Arrays.fill( params, aValue );
    }
    
    /**
     * Returns zero in N-dimensions.
     * 
     * @param aDim point dimension
     * @return zero
     */
    public static Point zero( int aDim ) {
        return new Point( new double[ aDim ] );
    }
    
    /**
     * Returns point arity.
     * 
     * @return arity
     */
    public int getArity() {
        return arity;
    }
    
    /**
     * Returns coordinate by specified dimension.
     * 
     * @param aDim dimension
     * @return coordinate
     */
    public double get( int aDim ) {
        if ( aDim >= arity ) {
            return 0;
        } else {
            return params[ aDim ];
        }
    }
    
    /**
     * Returns coordinates.
     * 
     * @return coordinates
     */
    public double[] get() {
        return params;
    }
    
    /**
     * Adds point to point.
     * 
     * @param aB other point
     * @return new point
     */
    public Point add( Point aB ) {
        int n = Math.max( arity, aB.getArity() );
        double[] points = new double[ n ];
        for ( int i = 0; i < n; i++ ) {
            points[ i ] = get( i ) + aB.get( i );
        }
        return new Point( points );
    }
    
    /**
     * Adds constant to every coordinate of point.
     * 
     * @param aC constant
     * @return new point
     */
    public Point add( double aC ) {
        int n = getArity();
        double[] points = new double[ n ];
        for ( int i = 0; i < n; i++ ) {
            points[ i ] = get( i ) + aC;
        }
        return new Point( points );
    }
    
    /**
     * Adds constant to specified coordinate/
     * 
     * @param aC constant
     * @param aDim dimension
     * @return new point
     */
    public Point add( double aC, int aDim ) {
        double[] p = Arrays.copyOf( params, arity );
        if ( aDim < getArity() ) {
            p[ aDim ] = params[ aDim ] + aC;
        }
        return new Point( p );
    }
    
    /**
     * Subtracts point from point.
     * 
     * @param aB other point
     * @return new point
     */
    public Point sub( Point aB ) {
        int n = Math.max( arity, aB.getArity() );
        double[] points = new double[ n ];
        for ( int i = 0; i < n; i++ ) {
            points[ i ] = get( i ) - aB.get( i );
        }
        return new Point( points );
    }
    
    /**
     * Subtracts constant from every dimension.
     * 
     * @param aC constant
     * @return new point
     */
    public Point sub( double aC ) {
        int n = getArity();
        double[] points = new double[ n ];
        for ( int i = 0; i < n; i++ ) {
            points[ i ] = get( i ) - aC;
        }
        return new Point( points );
    }
    
    /**
     * Multiplies points. 
     * 
     * @param aB other point
     * @return new point
     */
    public Point mul( Point aB ) {
        int n = Math.max( arity, aB.getArity() );
        double[] points = new double[ n ];
        for ( int i = 0; i < n; i++ ) {
            points[ i ] = get( i ) * aB.get( i );
        }
        return new Point( points );
    }
    
    /**
     * Multiplies point on constant.
     * 
     * @param aC constant
     * @return new point
     */
    public Point mul( double aC ) {
        int n = getArity();
        double[] points = new double[ n ];
        for ( int i = 0; i < n; i++ ) {
            points[ i ] = get( i ) * aC;
        }
        return new Point( points );
    }
    
    /**
     * Divides point on constant.
     * 
     * @param aC constant
     * @return new point
     */
    public Point div( double aC ) {
        int n = getArity();
        double[] points = new double[ n ];
        for ( int i = 0; i < n; i++ ) {
            points[ i ] = get( i ) / aC;
        }
        return new Point( points );
    }

    /**
     * Calculates distance between point.
     * 
     * @param b other point
     * @return distance
     */
    public double distance( Point b ) {
        double res = 0;
        int n = Math.max( getArity(), b.getArity() );
        for ( int i = 0; i < n; i++ ) {
            res += Math.pow( get( i ) - b.get( i ), n );
        }
        return Math.pow( res, 1.0 / n );
    }
    
    /**
     * Applies `abs` function to point coordinates.
     * 
     * @return new point
     */
    public Point abs() {
        int n = getArity();
        double[] points = new double[ n ];
        for ( int i = 0; i < n; i++ ) {
            points[ i ] = Math.abs( get( i ) );
        }
        return new Point( points );
    }
    
    /**
     * Point exponentiation.
     * 
     * @param aPower exponent
     * @return new point
     */
    public Point pow( double aPower ) {
        int n = getArity();
        double[] points = new double[ n ];
        for ( int i = 0; i < n; i++ ) {
            points[ i ] = Math.pow( get( i ), aPower );
        }
        return new Point( points );
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Arrays.hashCode( params );
        return hash;
    }

    @Override
    public boolean equals( Object aObj ) {
        if ( this == aObj ) {
            return true;
        }
        if ( aObj == null ) {
            return false;
        }
        if ( getClass() != aObj.getClass() ) {
            return false;
        }
        Point other = (Point) aObj;
        if ( this.arity != other.arity ) {
            return false;
        }
        return Arrays.equals( this.params, other.params );
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder( "(" );
        for ( double d : params ) {
            builder.append( new BigDecimal( d ).setScale( 4, RoundingMode.HALF_DOWN ).doubleValue())
                .append( ", " );
        }
        builder.replace( builder.lastIndexOf( "," ), builder.length(), ")" );
        return builder.toString();
    }
}
