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
import edu.sibfu.isit.nemeton.models.Point;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jzy3d.plot3d.builder.Mapper;

/**
 * Represents function
 * @author Max Balushkin
 */
public class NFunction {
    protected Mapper mapper;
    private boolean isMapped;
    protected Function<Point, Double> f;
    
    private String title;
    private String text;
    
    private int arity;
    
    /**
     * Creates new function with specified arity
     * @param aFunction Function of n variables
     * @param aArity Function arity
     * Can't be mapped with Jzy3D
     */
    public NFunction(Function<Point, Double> aFunction, int aArity) {
        this.f = aFunction;
        arity = aArity;
        
        if (aArity == 2) {
            mapper = new Mapper() {
                @Override
                public double f(double x, double y) {
                    return aFunction.apply(new Point(x, y));
                }
            };
            isMapped = true;
        }
    }
    
    
    /**
     * Creates new function of 2 variables
     * @param function Function of 2 variables
     */
    public NFunction(BiFunction<Double, Double, Double> function) {
        this((Point t) ->  function.apply(t.get(0), t.get(1)), 2);
        mapper = new Mapper() {
            @Override
            public double f(double x, double y) {
                return function.apply(x, y);
            }
        };
        isMapped = true;
    }
    
    /**
     * Sets function title
     * @param title Title
     * @return Self
     */
    public NFunction setTitle(String title) {
        this.title = title;
        return this;
    }
    /**
     * @return Function title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Sets function textual representation
     * @param text Textual representation
     * @return Self
     */
    public NFunction setText(String text) {
        this.text = text;
        return this;
    }
    /**
     * @return Function textual representation
     */
    public String getText() {
        return text;
    }
    
    /**
     * @return Function arity
     */
    public int getArity() {
        return arity;
    }
    
    /**
     * @return True if function can be mapped with Jzy3D, else False
     */
    public boolean isMapped() {
        return isMapped;
    }
    
    public Mapper getMapper() {
        return mapper;
    }
    
    /**
     * Returns value of function in point
     * @param x Point
     * @return Value of function
     */
    public double eval(Point x) {
        return f.apply(x);
    }

    @Override
    public String toString() {
        return title;
    }
    
    /**
     * Registers function in function provider
     */
    public void register() {
        Functions.register( this );
    }
    
}
