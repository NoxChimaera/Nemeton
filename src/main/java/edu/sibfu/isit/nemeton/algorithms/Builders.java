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
package edu.sibfu.isit.nemeton.algorithms;

import java.util.ArrayList;
import edu.sibfu.isit.nemeton.framework.Listener;

/**
 * Provides algorithm builders and notifies subscribers about them.
 * 
 * @author Max Balushkin
 */
public class Builders {

    private static final ArrayList<Listener<AlgorithmBuilder>> subscribers;
    private static final ArrayList<AlgorithmBuilder> builders;
    
    static {
       subscribers = new ArrayList<>();
       builders = new ArrayList<>();
    }
    
    /**
     * Subscribes listener.
     * Messages are sent when registering new algorithm builder
     * 
     * @param aListener listener
     */
    public static void subscribe( final Listener<AlgorithmBuilder> aListener ) {
        subscribers.add(aListener);
    }
    
    /**
     * Registers new algorithm builder.
     * 
     * @param aBldr builder
     */
    public static void register( final AlgorithmBuilder aBldr ) {
        builders.add( aBldr );
        for ( Listener<AlgorithmBuilder> subscriber : subscribers ) {
            subscriber.publish(aBldr);
        }
    }

}
