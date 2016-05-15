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
    
    private int iterations = 10000;
    private double accuracy = 0.00001;
 
    public SACBuilder centre(final Point aCentre) {
        centre = aCentre;
        return this;
    } 
    public Point centre() {
        return centre;
    }
    
    public SACBuilder searchRange(final double aRange) {
        searchRange = aRange;
        return this;
    }
    public double searchRange() {
        return searchRange;
    }

    public SACBuilder sampleSize(final int aSampleSize) {
        sampleSize = aSampleSize;
        return this;
    }
    public int sampleSize() {
        return sampleSize;
    }

    public SACBuilder gamma(final double aGamma) {
        gamma = aGamma;
        return this;
    }
    public double gamma() {
        return gamma;
    }

    public SACBuilder metric(final int aMetric) {
        metric = aMetric;
        return this;
    }
    public int metric() {
        return metric;
    }

    public SACBuilder kernel(final SelectiveKernel aKernel) {
        kernel = aKernel;
        return this;
    }
    public SelectiveKernel kernel() {
        return kernel;
    }

    public SACBuilder selectiveness(final double aSelectiveness) {
        selectiveness = aSelectiveness;
        return this;
    }
    public double selectiveness() {
        return selectiveness;
    }
    
    public SACBuilder iterations(final int aIterations) {
        iterations = aIterations;
        return this;
    }
    public int iterations() {
        return iterations;
    }
    
    public SACBuilder accuracy(final double aAccuracy) {
        accuracy = aAccuracy;
        return this;
    }
    public double accuracy() {
        return accuracy;
    }
 
    @Override
    public OptimizationAlgorithm build(final NFunction aFunction) {
        final SACAlgorithmParameters params = new SACAlgorithmParameters(
            centre, searchRange, sampleSize, gamma, metric, kernel, sampleSize,
            iterations, accuracy
        );
        return new SACAlgorithm(aFunction, params);
    }

    @Override
    public String toString() {
        return "Селективное усреднение координат";
    }

    @Override
    public JFrame show() {
        if (view == null) {
            view = new SACSettings(this);
        }
        return view;
    }
    
}