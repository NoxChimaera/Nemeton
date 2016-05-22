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

import edu.sibfu.isit.nemeton.algorithms.OptimizationAlgorithm;
import edu.sibfu.isit.nemeton.models.functions.NFunction;

/**
 * Represents results of algorithm analysis.
 * 
 * @author Max Balushkin
 */
public class AnalysisResult {
    
    /**
     * Analysed algorithm.
     */
    public final OptimizationAlgorithm algorithm;
    /**
     * Optimised function.
     */
    public final NFunction function;
    
    /**
     * Probability of success.
     */
    public final double success;
    /**
     * Mean value of optimised function evaluations.
     */
    public final double meanIterations;
    
    /**
     * Creates new analysis result.
     * 
     * @param aAlgorithm analysed algorithm
     * @param aFunction optimised function
     * @param aSuccess success probability
     * @param aMeanIterations evaluations mean value
     */
    public AnalysisResult(
            OptimizationAlgorithm aAlgorithm, NFunction aFunction,
            double aSuccess, double aMeanIterations
    ) {
        algorithm = aAlgorithm;
        function = aFunction;
        success = aSuccess;
        meanIterations = aMeanIterations;
    }

    @Override
    public String toString() {
        return algorithm.toString();
    }
    
}
