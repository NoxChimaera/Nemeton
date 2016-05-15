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
    
    public final Point centre;
    public final double searchRange;
    public final int sampleSize;
    public final double gamma;
    public final int metric;
    
    public final SelectiveKernel kernel;
    public final int selectiveness;
    
    public final int iterations;
    public final double accuracy;
    
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
