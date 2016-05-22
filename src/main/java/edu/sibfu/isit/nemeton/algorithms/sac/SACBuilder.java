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

import edu.sibfu.isit.nemeton.algorithms.AlgorithmBuilder;
import edu.sibfu.isit.nemeton.algorithms.sac.kernels.ParabolicKernel;
import edu.sibfu.isit.nemeton.algorithms.sac.kernels.SelectiveKernel;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import javax.swing.JFrame;
import edu.sibfu.isit.nemeton.algorithms.OptimizationAlgorithm;
import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.views.SACSettings;

/**
 * SAC Algorithm builder.
 * Default parameters:
 *      Start point: (0, 0)
 *      Search range, delta x: (5, 5)
 *      Sample size, n: 50
 *      Metriq q: 2
 *      Kernel: parabolic kernel
 *      Kernel selectiveness, s: 10
 * 
 *      Maximum iterations: 100000
 *      Accuracy (epsilon): 1E-5
 * 
 * @author Max Balushkin
 */
public class SACBuilder extends AlgorithmBuilder {

    private SACSettings view;
    
    private Point centre = Point.zero(2);
    private double searchRange = 5;
    private int sampleSize = 50;
    
    private double gamma = 1;
    private int metric = 2;
    
    private SelectiveKernel kernel = new ParabolicKernel();
    private double selectiveness = 10;
    
    private int iterations = 100000;
    private double accuracy = 1E-5;
    private boolean constrained = true;
 
    /**
     * Sets centre of search area, start point.
     * 
     * @param aCentre start point
     * @return self
     */
    public SACBuilder centre( Point aCentre ) {
        centre = aCentre;
        return this;
    } 
    /**
     * Returns start point, centre of search area.
     * 
     * @return Start point
     */
    public Point centre() {
        return centre;
    }
    
    /**
     * Sets search range, delta x.
     * delta x = ( value ... value )
     * 
     * @param aRange search range
     * @return self
     */
    public SACBuilder searchRange( double aRange ) {
        searchRange = aRange;
        return this;
    }
    /**
     * Returns search range, delta x.
     * 
     * @return search range
     */
    public double searchRange() {
        return searchRange;
    }

    /**
     * Sets sample size, n.
     * 
     * @param aSampleSize sample size
     * @return self
     */
    public SACBuilder sampleSize( int aSampleSize ) {
        sampleSize = aSampleSize;
        return this;
    }
    /**
     * Returns sample size, n.
     * 
     * @return sample size
     */
    public int sampleSize() {
        return sampleSize;
    }

    /**
     * Sets gamma coefficient.
     * 
     * @param aGamma gamma
     * @return self
     */
    public SACBuilder gamma( double aGamma ) {
        gamma = aGamma;
        return this;
    }
    /**
     * Returns gamma coefficient.
     * 
     * @return gamma
     */
    public double gamma() {
        return gamma;
    }

    /**
     * Sets metric, q.
     * 
     * @param aMetric metric, q
     * @return self
     */
    public SACBuilder metric( int aMetric ) {
        metric = aMetric;
        return this;
    }
    /**
     * Returns metric, q.
     * 
     * @return metric, q
     */
    public int metric() {
        return metric;
    }

    /**
     * Sets selective kernel.
     * 
     * @param aKernel kernel
     * @return self
     */
    public SACBuilder kernel( SelectiveKernel aKernel ) {
        kernel = aKernel;
        return this;
    }
    /**
     * Returns selective kernel.
     * 
     * @return kernel
     */
    public SelectiveKernel kernel() {
        return kernel;
    }

    /**
     * Sets kernel selectiveness, s.
     * 
     * @param aSelectiveness kernel selectiveness, s
     * @return self
     */
    public SACBuilder selectiveness( double aSelectiveness ) {
        selectiveness = aSelectiveness;
        return this;
    }
    /**
     * Returns kernel selectiveness, s.
     * 
     * @return selectiveness,s
     */
    public double selectiveness() {
        return selectiveness;
    }
    
    /**
     * Sets maximum iterations.
     * 
     * @param aIterations maximum iterations
     * @return self
     */
    public SACBuilder iterations( int aIterations ) {
        iterations = aIterations;
        return this;
    }
    /**
     * Returns maximum iterations.
     * 
     * @return maximum iterations
     */
    public int iterations() {
        return iterations;
    }
    
    /**
     * Sets search accuracy, epsilon.
     * 
     * @param aAccuracy accuracy
     * @return self
     */
    public SACBuilder accuracy( double aAccuracy ) {
        accuracy = aAccuracy;
        return this;
    }
    /**
     * Returns accuracy, epsilon.
     * 
     * @return accuracy
     */
    public double accuracy() {
        return accuracy;
    }
 
    /**
     * Sets function constraint mode.
     * 
     * @param aConstrained if true add function constraints
     * @return self
     */
    public SACBuilder constrained( boolean aConstrained ) {
        constrained = aConstrained;
        return this;
    }
    @Override
    public boolean isConstrained() {
        return constrained;
    }
   
    @Override
    public OptimizationAlgorithm build( NFunction aFunction ) {
        final SACAlgorithmParameters params = new SACAlgorithmParameters(
            centre, searchRange, sampleSize, gamma, metric, kernel, sampleSize,
            iterations, accuracy
        );
        SACAlgorithm alg = new SACAlgorithm( aFunction, params );
        alg.constraint( constraints );
        return alg;
    }

    @Override
    public String toString() {
        return "Селективное усреднение координат";
    }

    @Override
    public JFrame show() {
        if ( view == null ) {
            view = new SACSettings( this );
        }
        return view;
    }
    
}