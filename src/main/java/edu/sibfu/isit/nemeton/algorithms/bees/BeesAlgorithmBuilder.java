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
package edu.sibfu.isit.nemeton.algorithms.bees;

import edu.sibfu.isit.nemeton.algorithms.AlgorithmBuilder;
import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import edu.sibfu.isit.nemeton.views.BeesSettings;
import javax.swing.JFrame;
import edu.sibfu.isit.nemeton.algorithms.OptimizationAlgorithm;

/**
 * Bees Algorithm builder.
 * 
 * @author Max Balushkin
 */
public class BeesAlgorithmBuilder extends AlgorithmBuilder {
    
    BeesSettings settingsView;

    Point hivePosition = Point.zero(2);
    int hiveSize = 255;
    
    int scouts = 10;
    
    int sources = 10;
    int sourceSize = 10;
    double gamma = 0.99;
    int eliteSources = 6;
    
    int onElite = 6;
    int onOther = 4;

    int maxIteration = 100000;
    double accuracy = 0.000001;

    public BeesAlgorithmBuilder() {
    }
    
    public BeesAlgorithmBuilder(final BeesAlgorithmBuilder aSrc) {
        copy(aSrc);
    }

    public BeesAlgorithmBuilder hivePosition(final Point aPosition) {
        hivePosition = aPosition;
        return this;
    }
    public Point hivePosition() {
        return hivePosition;
    }
    
    public BeesAlgorithmBuilder hiveSize(final int aHiveSize) {
        hiveSize = aHiveSize;
        return this;
    }
    public int hiveSize() {
        return hiveSize;
    }

    public BeesAlgorithmBuilder scouts(final int aScouts) {
        scouts = aScouts;
        return this;
    }
    public int scouts() {
        return scouts;
    }
 
    public BeesAlgorithmBuilder sources(final int aSources) {
        sources = aSources;
        return this;
    }
    public int sources() {
        return sources;
    }
    
    public BeesAlgorithmBuilder sourceSize(final int aSize) {
        sourceSize = aSize;
        return this;
    }
    public int sourceSize() {
        return sourceSize;
    }
    
    public BeesAlgorithmBuilder gamma(final double aGamma) {
        gamma = aGamma;
        return this;
    }
    public double gamma() {
        return gamma;
    }
    
    public BeesAlgorithmBuilder eliteSources(final int aBest) {
        eliteSources = aBest;
        return this;
    }
    public int eliteSources() {
        return eliteSources;
    }

    public BeesAlgorithmBuilder onElite(final int aOnBest) {
        onElite = aOnBest;
        return this;
    }
    public int onElite() {
        return onElite;
    }
    public BeesAlgorithmBuilder onOther(final int aOnNonBest) {
        onOther = aOnNonBest;
        return this;
    }
    public int onOther() {
        return onOther;
    }

    public BeesAlgorithmBuilder maxIteration(final int aMax) {
        maxIteration = aMax;
        return this;
    }
    public int maxIteration() {
        return maxIteration;
    }
    
    public BeesAlgorithmBuilder accuracy(final double aAccuracy) {
        accuracy = aAccuracy;
        return this;
    }
    public double accuracy() {
        return accuracy;
    }
    
    // ??
    public void copy(final BeesAlgorithmBuilder aSrc) {
        scouts = aSrc.scouts;
        sources = aSrc.sources;
        eliteSources = aSrc.eliteSources;
        onElite = aSrc.onElite;
        onOther = aSrc.onOther;
        hivePosition = new Point(aSrc.hivePosition);
        sourceSize = aSrc.sourceSize;
        hiveSize = aSrc.hiveSize;
        gamma = aSrc.gamma;
        maxIteration = aSrc.maxIteration;
        accuracy = aSrc.accuracy;
    }
   
    @Override
    public JFrame show() {
        if (settingsView == null) {
            settingsView = new BeesSettings(this);
        }
        return settingsView;
    }
    
    @Override
    public OptimizationAlgorithm build(final NFunction aFunction) {
        BeesAlgorithmParameters params = new BeesAlgorithmParameters(
                hivePosition, hiveSize, sources, scouts, eliteSources, 
                sourceSize, gamma, onElite, onOther, maxIteration, accuracy
        );
        return new BeesAlgorithm(aFunction, params);
    }

    @Override
    public String toString() {
        return "Пчелиный алгоритм";
    }
    
}
