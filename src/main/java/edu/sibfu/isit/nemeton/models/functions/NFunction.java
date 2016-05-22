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

import edu.sibfu.isit.nemeton.controllers.providers.Functions;
import edu.sibfu.isit.nemeton.lib.FunctionTextFormatter;
import edu.sibfu.isit.nemeton.models.CalculatedPoint;
import edu.sibfu.isit.nemeton.models.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jzy3d.plot3d.builder.Mapper;

/**
 * Represents function.
 * 
 * @author Max Balushkin
 */
public class NFunction {
    
    /**
     * Jzy mapper.
     */
    protected final Mapper mapper;
    /**
     * True if has jzy mapper, else false.
     */
    protected final boolean isMapped;
    /**
     * Function.
     */
    protected final Function<Point, Double> f;
    
    /**
     * Function minima.
     */
    protected final List<CalculatedPoint> minima;
    /**
     * Function maxima.
     */
    protected final List<CalculatedPoint> maxima;
    /**
     * Function constraints.
     */
    protected final List<Constraint> constraints;
    
    private String title;
    private String text;
    
    private int arity;
    
    private boolean unsafe;
    
    /**
     * Creates new function with specified arity.
     * 
     * @param aFunction function of n variables
     * @param aArity function arity
     * Can't be mapped with Jzy3D
     */
    public NFunction( Function<Point, Double> aFunction, int aArity ) {
        this.f = aFunction;
        arity = aArity;

        if ( aArity == 2 ) {
            mapper = new Mapper() {
                @Override
                public double f( double x, double y ) {
                    return aFunction.apply( new Point( x, y ) );
                }
            };
            isMapped = true;
        } else {
            mapper = null;
            isMapped = false;
        }
        
        minima = new ArrayList<>();
        maxima = new ArrayList<>();
        constraints = new ArrayList<>();
        
        unsafe = false;
    }
   
    /**
     * Creates new function of 2 variables.
     * 
     * @param function function of 2 variables
     */
    public NFunction( BiFunction<Double, Double, Double> function ) {
        this( ( Point t ) ->  function.apply( t.get( 0 ), t.get( 1 ) ), 2 );
    }
    
    /**
     * Sets function title.
     * 
     * @param title title
     * @return self
     */
    public NFunction setTitle( String title ) {
        this.title = title;
        return this;
    }
    
    /**
     * Returns function title.
     * 
     * @return function title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Sets function textual representation.
     * 
     * @param text textual representation
     * @return self
     */
    public NFunction setText( String text ) {
        this.text = text;
        return this;
    }
    
    /**
     * Sets function representation.
     * @see FunctionTextFormatter#toHTML(java.lang.String) HTML formatter
     * @see FunctionTextFormatter#image(java.lang.String) image from resourcesS
     * 
     * @param aText formatted string
     * @param aFormatter formatter
     * @return self
     */
    public NFunction setText( String aText, Function<String, String> aFormatter ) {
        text = aFormatter.apply( aText );
        return this;
    }
    
    /**
     * Returns function representation.
     * 
     * @return function textual representation
     */
    public String getText() {
        return text;
    }
    
    /**
     * Adds minimum.
     * 
     * @param aMinimum minimum
     * @return self
     */
    public NFunction minima( CalculatedPoint aMinimum ) {
        minima.add( aMinimum );
        return this;
    }
    /**
     * Returns function minima.
     * 
     * @return minima
     */
    public List<CalculatedPoint> minima() {
        return new ArrayList<>( minima );
    }
    
    /**
     * Adds maximum.
     * 
     * @param aMaximum maximum
     * @return self
     */
    public NFunction maxima( CalculatedPoint aMaximum ) {
        maxima.add( aMaximum );
        return this;
    }
    /**
     * Returns function maxima.
     * 
     * @return maxima
     */
    public List<CalculatedPoint> maxima() {
        return new ArrayList<>( maxima );
    }
    
    /**
     * Adds constraint.
     * 
     * @param aConstraint constraint
     * @return self
     */
    public NFunction constraint( Constraint aConstraint ) {
        constraints.add( aConstraint );
        return this;
    }
    /**
     * Returns function constraints.
     * 
     * @return constraints
     */
    public List<Constraint> constraints() {
        return new ArrayList<>(constraints);
    }
    
    /**
     * Marks function as unsafe.
     * @deprecated 
     * 
     * @return self
     */
    public NFunction unsafe() {
        unsafe = true;
        return this;
    }
    /**
     * Is funcation unsafe?
     * 
     * @return true if unsafe, else false
     */
    public boolean isUnsafe() {
        return unsafe;
    }
    
    /**
     * Returns function arity.
     * 
     * @return arity
     */
    public int getArity() {
        return arity;
    }
    
    /**
     * Is function mapped with Jzy?
     * 
     * @return true if function can be mapped with Jzy3D, else False
     */
    public boolean isMapped() {
        return isMapped;
    }
    
    /**
     * Returns Jzy mapper.
     * 
     * @return Jzy mapper
     */
    public Mapper getMapper() {
        return mapper;
    }
    
    /**
     * Returns value of function in point.
     * 
     * @param x point
     * @return value of function
     */
    public double eval( Point x ) {
        return f.apply( x );
    }

    @Override
    public String toString() {
        return title;
    }
    
    /**
     * Registers function in function provider.
     */
    public void register() {
        Functions.register( this );
    }
    
}
