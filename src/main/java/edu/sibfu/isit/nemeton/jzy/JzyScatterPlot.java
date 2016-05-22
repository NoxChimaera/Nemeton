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
package edu.sibfu.isit.nemeton.jzy;

import edu.sibfu.isit.nemeton.lib.JzyHelper;
import edu.sibfu.isit.nemeton.models.CalculatedPoint;
import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.Scatter;

/**
 * Scatter plot.
 * 
 * @author Max Balushkin
 */
public class JzyScatterPlot extends JzyPlot {

    private final Scatter plot;
    
    /**
     * Creates new scatter plot.
     * 
     * @param aPoints points
     * @param aColour colour of points
     */
    public JzyScatterPlot( CalculatedPoint[] aPoints, Color aColour ) {
        int n = aPoints.length;
        Coord3d[] coords = new Coord3d[ n ];
        for ( int i = 0; i < n; i++ ) {
            coords[ i ] = JzyHelper.toCoord3d( aPoints[ i ] );
        }
        plot = new Scatter( coords, aColour, 5 );
    }
    
    /**
     * Creates new scatter plot.
     * 
     * @param aPoints points
     * @param aFunction function
     * @param aColour colour of points
     */
    public JzyScatterPlot( Point[] aPoints, NFunction aFunction, Color aColour ) {
        int n = aPoints.length;
        Coord3d[] coords = new Coord3d[ n ];
        for ( int i = 0; i < n; i++ ) {
            coords[ i ] = JzyHelper.toCoord3d( aFunction, aPoints[ i ] );
        }
        plot = new Scatter( coords, aColour, 5 );
    }
    
    @Override
    public AbstractDrawable getDrawable() {
        return plot;
    }

    @Override
    public void append( Chart aChart ) {
        aChart.getScene().add( plot );
    }
    
}
