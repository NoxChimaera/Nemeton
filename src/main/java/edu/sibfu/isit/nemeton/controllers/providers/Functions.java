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
package edu.sibfu.isit.nemeton.controllers.providers;

import edu.sibfu.isit.nemeton.models.functions.NFunction;
import java.util.ArrayList;
import edu.sibfu.isit.nemeton.framework.Listener;

/**
 * Provides functions.
 * 
 * @author Max Balushkin
 */
public class Functions {
    
    private static final ArrayList<NFunction> functions = new ArrayList<>();
    private static final ArrayList<Listener<NFunction>> subscribers = new ArrayList<>();
    
    public static void subscribe( final Listener<NFunction> aSubscriber ) {
        subscribers.add(aSubscriber);
    }
    
    private static void publish( final NFunction aFunction ) {
        for ( final Listener<NFunction> subscriber : subscribers ) {
            subscriber.publish( aFunction );
        }
    }
    
    /**
     * Registers function.
     * 
     * @param aFunction Function
     */
    public static void register( final NFunction aFunction ) {
        functions.add( aFunction );
        publish( aFunction );
    }
    
    /**
     * Provides function by index.
     * 
     * @param aIndex Index of function
     * @return Function
     */
    public static NFunction get( final int aIndex ) {
        return functions.get( aIndex );
    }
    
    /**
     * Provides function by title.
     * 
     * @param aTitle Function title
     * @return Function
     */
    public static NFunction get( final String aTitle ) {
        return functions.stream().filter( ( NFunction f ) -> f.getTitle().equals( aTitle ) )
            .findFirst().orElse( null );
    }
    
    /**
     * @return List of functions
     */
    public static ArrayList<NFunction> get() {
        return functions;
    }
    
}
