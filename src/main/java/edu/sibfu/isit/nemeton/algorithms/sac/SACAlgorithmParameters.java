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
package edu.sibfu.isit.nemeton.algorithms.sac;

import edu.sibfu.isit.nemeton.algorithms.sac.kernels.SelectiveKernel;
import edu.sibfu.isit.nemeton.models.Point;

/**
 *
 * @author Max Balushkin
 */
public class SACAlgorithmParameters {
    
    /**
     * Centre point of search area, x^0.
     */
    public final Point centre;
    /**
     * Search range, delta x.
     */
    public final double searchRange;
    /**
     * Amount of points in sample, n.
     */
    public final int sampleSize;
    /**
     * Shrink coefficient, gamma.
     */
    public final double gamma;
    /**
     * Metric, q.
     */
    public final int metric;
    
    /**
     * Selective kernel, p(...).
     */
    public final SelectiveKernel kernel;
    /**
     * Kernel selectiveness, s.
     */
    public final int selectiveness;
    
    /**
     * Maximum iterations.
     */
    public final int iterations;
    /**
     * Search accuracy, eps.
     */
    public final double accuracy;
    
    /**
     * @param aCentre Centre of search area, x^0
     * @param aSearchRange Search range, delta x
     * @param aSampleSize Amount of points in sample, n
     * @param aGamma Shrink coefficient, gamma
     * @param aMetric Metric, q
     * @param aKernel Selective kernel, p(...)
     * @param aSelectiveness Kernel selectiveness, s
     * @param aIterations Maximum iterations
     * @param aAccuracy Search accuracy, eps
     */
    public SACAlgorithmParameters(
        final Point aCentre, final double aSearchRange,
        final int aSampleSize, final double aGamma,
        final int aMetric, 
        final SelectiveKernel aKernel, final int aSelectiveness,
        final int aIterations, final double aAccuracy
    ) {
        centre = aCentre;
        searchRange = aSearchRange;
        sampleSize = aSampleSize;
        gamma = aGamma;
        metric = aMetric;
        
        kernel = aKernel;
        selectiveness = aSelectiveness;
        
        iterations = aIterations;
        accuracy = aAccuracy;
    }
    
}
