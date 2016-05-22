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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents points by iterations.
 * 
 * @author Max Balushkin
 */
public class PointHistory {
    
    private final ArrayList<List<CalculatedPoint>> data;
    
    private final HashMap<String, List<Point>> pointParameters;
    private final HashMap<String, List<Double>> parameters;
    
    private int lastIndex;
    
    /**
     * Creates empty point history.
     */
    public PointHistory() {
        data = new ArrayList<>();
        data.add(new ArrayList<>());
        lastIndex = 0;
        
        parameters = new HashMap<>();
        pointParameters = new HashMap<>();
    }
    
    /**
     * Adds parameter to history.
     * 
     * @param aId Parameter id
     * @param aValue Parameter value
     */
    public void addParameter( String aId, double aValue ) {
        List<Double> params = parameters.get( aId );
        if ( params == null ) {
            params = new ArrayList<>();
            parameters.put( aId, params );
        }
        params.add( aValue );
    }
    
    /**
     * Adds point parameter to history.
     * 
     * @param aId Parameter id
     * @param aValue Parameter value
     */
    public void addPointParameter( String aId, Point aValue ) {
        List<Point> params = pointParameters.get( aId );
        if ( params == null ) {
            params = new ArrayList<>();
            pointParameters.put( aId, params );
        }
        params.add( aValue );
    }
    
    /**
     * Returns specified parameter history.
     * 
     * @param aId Parameter id
     * @return Parameter values
     */
    public List<Double> getParameter( String aId ) {
        return parameters.get( aId );
    }
    
    /**
     * Returns parameters history.
     * 
     * @return Parameters values
     */
    public HashMap<String, List<Double>> getParameters() {
        return parameters;
    }
    
    /**
     * Returns point parameters history.
     * 
     * @return Point parameters history
     */
    public HashMap<String, List<Point>> getPointParameters() {
        return pointParameters;
    }
    
    /**
     * Adds new point into specified row.
     * 
     * @param aIdx Row index
     * @param aPoint Point
     */
    public void add( int aIdx, CalculatedPoint aPoint ) {
        while ( aIdx >= data.size() ) {
            data.add( new ArrayList<>() );
        }
        data.get( aIdx ).add( aPoint );
        lastIndex = aIdx;
    }
    
    /**
     * Adds new point into specified row.
     * 
     * @param aIdx Row index
     * @param aPoint Point
     * @param aValue Function value
     */
    public void add( int aIdx, Point aPoint, double aValue) {
        add( aIdx, new CalculatedPoint( aPoint, aValue ) );
    }
    
    /**
     * Adds new point into last row.
     * @param aPoint Point
     */
    public void add( CalculatedPoint aPoint ) {
        data.get( lastIndex ).add( aPoint );
    }
    
    /**
     * Adds new point into last row.
     * 
     * @param aPoint Point
     * @param aValue Function value
     */
    public void add( Point aPoint, double aValue ) {
        add( new CalculatedPoint( aPoint, aValue ) );
    }
    
    /**
     * Returns history row.
     * 
     * @param aIdx Row index
     * @return List of points
     */
    public List<CalculatedPoint> get( int aIdx ) {
        return data.get( aIdx );
    }
    
    /**
     * Returns amount of rows.
     * 
     * @return Row count
     */
    public int size() {
        return lastIndex + 1;
    }
    
}
