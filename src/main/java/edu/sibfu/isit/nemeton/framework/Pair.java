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
package edu.sibfu.isit.nemeton.framework;

/**
 * Container for two values.
 * 
 * @author Max Balushkin
 * @param <L> type of first (left) element
 * @param <R> type of second (right) element
 */
public class Pair<L, R> {
    
    private final L left;
    private final R right;
    
    /**
     * Creates new pair.
     * 
     * @param aLeft left element
     * @param aRight right element
     */
    public Pair( L aLeft, R aRight ) {
        left = aLeft;
        right = aRight;
    }
    
    /**
     * Return left (first) element.
     * 
     * @return pair element
     */
    public L left() {
        return left;
    }
    
    /**
     * Returns right (second) element.
     * 
     * @return pair element
     */
    public R right() {
        return right;
    }
    
}
