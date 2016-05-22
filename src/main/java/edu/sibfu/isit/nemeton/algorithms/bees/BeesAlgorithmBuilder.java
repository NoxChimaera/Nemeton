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
    
    private BeesSettings settingsView;
    
    private Point hivePosition = Point.zero(2);
    private int hiveSize = 50;
    
    private int scouts = 10;
    
    private int sources = 10;
    private int sourceSize = 5;
    private double gamma = 0.99;
    private int eliteSources = 6;
    
    private int onElite = 6;
    private int onOther = 4;

    private int maxIteration = 100000;
    private double accuracy = 0.000001;
    private boolean constrained = true;

    /**
     * Creates new builder with default parameters.
     */
    public BeesAlgorithmBuilder() {
    }
    
    /**
     * Creates new builder with parameters of another builder.
     * 
     * @param aSrc Copied builder
     */
    public BeesAlgorithmBuilder(final BeesAlgorithmBuilder aSrc) {
        copy(aSrc);
    }

    /**
     * Sets hive position (centre of search area).
     * 
     * @param aPosition Hive position
     * @return Self
     */
    public BeesAlgorithmBuilder hivePosition(final Point aPosition) {
        hivePosition = aPosition;
        return this;
    }
    /**
     * Returns hive position (centre of search area).
     * 
     * @return Hive position
     */
    public Point hivePosition() {
        return hivePosition;
    }
    
    /**
     * Sets hive size (search range).
     * 
     * @param aHiveSize Hive size
     * @return Self
     */
    public BeesAlgorithmBuilder hiveSize(final int aHiveSize) {
        hiveSize = aHiveSize;
        return this;
    }
    /**
     * Returns hive size (search range).
     * 
     * @return Hive size
     */
    public int hiveSize() {
        return hiveSize;
    }

    /**
     * Sets amount of scouts.
     * 
     * @param aScouts Amount of scouts
     * @return Self
     */    
    public BeesAlgorithmBuilder scouts(final int aScouts) {
        scouts = aScouts;
        return this;
    }
    /**
     * Returns amount of scouts.
     * 
     * @return Amount of scouts.
     */
    public int scouts() {
        return scouts;
    }
 
    /**
     * Sets amount of selected sources.
     * 
     * @param aSources Selected sources
     * @return Self
     */
    public BeesAlgorithmBuilder sources(final int aSources) {
        sources = aSources;
        return this;
    }
    /**
     * Returns amount of sources.
     * 
     * @return Amount of sorces
     */
    public int sources() {
        return sources;
    }
    
    /**
     * Sets source size (local search area range).
     * 
     * @param aSize Source size
     * @return Self
     */
    public BeesAlgorithmBuilder sourceSize(final int aSize) {
        sourceSize = aSize;
        return this;
    }
    /**
     * Returns source size (local search area range).
     * 
     * @return Source size
     */
    public int sourceSize() {
        return sourceSize;
    }
    
    /**
     * Sets gamma (shrink coefficient).
     * 
     * @param aGamma Gamma
     * @return Self
     */
    public BeesAlgorithmBuilder gamma(final double aGamma) {
        gamma = aGamma;
        return this;
    }
    /**
     * Returns gamma (shrink coefficient).
     * 
     * @return Gamma
     */
    public double gamma() {
        return gamma;
    }
    
    /**
     * Sets amount of elite sources.
     * 
     * @param aBest Amount of elite sources
     * @return Self
     */
    public BeesAlgorithmBuilder eliteSources(final int aBest) {
        eliteSources = aBest;
        return this;
    }
    /**
     * Returns amount of elite sources.
     * 
     * @return Amount of elite sources
     */
    public int eliteSources() {
        return eliteSources;
    }

    /**
     * Sets amount of workers on elite sources.
     * 
     * @param aOnElite Amount of workers on elite sources
     * @return Self
     */
    public BeesAlgorithmBuilder onElite(final int aOnElite) {
        onElite = aOnElite;
        return this;
    }
    /**
     * Returns amount of workers on elite sources.
     * 
     * @return Amount of workers on elite sources
     */
    public int onElite() {
        return onElite;
    }
    
    /**
     * Sets amount of workers on another sources (total sources - elite sources).
     * 
     * @param aOnNonBest Amount of workers on non-best sources
     * @return Self
     */
    public BeesAlgorithmBuilder onOther(final int aOnNonBest) {
        onOther = aOnNonBest;
        return this;
    }
    /**
     * Returns amount of workers on non-best sources (total sources - elite sources).
     * 
     * @return Amount of workers on non-best sources
     */
    public int onOther() {
        return onOther;
    }

    /**
     * Sets maximum iterations.
     * 
     * @param aMax Maximum iterations
     * @return Self
     */
    public BeesAlgorithmBuilder maxIteration(final int aMax) {
        maxIteration = aMax;
        return this;
    }
    /**
     * Returns maximum iterations.
     * 
     * @return Maximum iterations
     */
    public int maxIteration() {
        return maxIteration;
    }
    
    /**
     * Sets accuracy.
     * 
     * @param aAccuracy Accuracy
     * @return Self
     */
    public BeesAlgorithmBuilder accuracy(final double aAccuracy) {
        accuracy = aAccuracy;
        return this;
    }
    /**
     * Returns accuracy.
     * 
     * @return Accuracy
     */
    public double accuracy() {
        return accuracy;
    }
    
    /**
     * Sets function constraint mode.
     * 
     * @param aConstrained If true add function constraints
     * @return Self
     */
    public BeesAlgorithmBuilder constrained( final boolean aConstrained ) {
        constrained = aConstrained;
        return this;
    }
    
    @Override
    public boolean isConstrained() {
        return constrained;
    }
   
    /**
     * Copies parameters from another builder.
     * 
     * @param aSrc Copied builder
     */
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
