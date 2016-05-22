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
package edu.sibfu.isit.nemeton.models.functions;

import edu.sibfu.isit.nemeton.framework.Pair;
import edu.sibfu.isit.nemeton.models.Point;

/**
 * Range constraint.
 * 
 * @author Max Balushkin
 */
public class RangeConstraint implements Constraint {

    private final Pair<Double, Double>[] range;

    /**
     * Creates new range constraint.
     * constraint = ( ( min, max ) ... ( min, max ) )
     * 
     * @param aMin lower range bound
     * @param aMax upper range bound
     * @param aArity range arity
     * @return range constraint
     */
    public static RangeConstraint create( double aMin, double aMax, int aArity ) {
        return new RangeConstraint( new Pair<>(aMin, aMax) , aArity );
    }
    
    /**
     * Creates new range constraint.
     * constraint = ( ( min0, max0 ) ... ( minN, maxN ) )
     * 
     * @param aRange ranges
     */
    public RangeConstraint( Pair<Double, Double> ... aRange ) {
        range = aRange;
    }
    
    /**
     * Creates new range constraint.
     * constraint = ( ( min, max ) ... ( min, max ) )
     * 
     * @param aRange range
     * @param aArity arity
     */
    public RangeConstraint( final Pair<Double, Double> aRange, final int aArity ) {
        range = new Pair[ aArity ];
        for ( int i = 0; i < aArity; i++ ) {
            range[ i ] = aRange;
        }
    }
    
    @Override
    public boolean check( final Point aPoint ) {
        int n = Math.min( aPoint.getArity(), range.length );
        for ( int i = 0; i < n; i++ ) {
            final double dim = aPoint.get(i);
            final Pair<Double, Double> r = range[ i ];
            if ( dim < r.left() ||  dim > r.right() ) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns ranges.
     * 
     * @return ranges
     */
    public Pair<Double, Double>[] getRange() {
        return range;
    }
    
}
