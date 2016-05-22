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
 * Parameters of SAC algorithm.
 * @see SACAlgorithm
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
     * @param aCentre centre of search area, x^0
     * @param aSearchRange search range, delta x
     * @param aSampleSize amount of points in sample, n
     * @param aGamma shrink coefficient, gamma
     * @param aMetric metric, q
     * @param aKernel selective kernel, p(...)
     * @param aSelectiveness kernel selectiveness, s
     * @param aIterations maximum iterations
     * @param aAccuracy search accuracy, eps
     */
    public SACAlgorithmParameters(
        Point aCentre, double aSearchRange,
        int aSampleSize, double aGamma,
        int aMetric, 
        SelectiveKernel aKernel, int aSelectiveness,
        int aIterations, double aAccuracy
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
