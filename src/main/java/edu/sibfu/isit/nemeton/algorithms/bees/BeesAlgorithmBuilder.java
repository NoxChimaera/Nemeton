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
 * Default parameters:
 *      Centre of search area: (0, 0)
 *      Range of search area: 50
 *      Scouts: 10
 *      Sites: 10
 *      Elite sites: 6
 *      Range of local search area (site size): 5
 *      Gamma coefficient (local area shrink coef.): 0.99
 *      Workers on elite sites: 6
 *      Workers on non-elite sites: 4
 * 
 *      Maximum iterations: 100000
 *      Accuracy (epsilon): 1E-5
 * 
 * @author Max Balushkin
 */
public class BeesAlgorithmBuilder extends AlgorithmBuilder {
    
    private BeesSettings settingsView;
    
    private Point hivePosition = Point.zero( 2 );
    private int hiveSize = 50;
    
    private int scouts = 10;
    
    private int sources = 10;
    private int sourceSize = 5;
    private double gamma = 0.99;
    private int eliteSources = 6;
    
    private int onElite = 6;
    private int onOther = 4;

    private int maxIteration = 100000;
    private double accuracy = 1E-5;
    private boolean constrained = true;

    /**
     * Creates new builder with default parameters.
     */
    public BeesAlgorithmBuilder() {
    }
    
    /**
     * Creates new builder with parameters of another builder.
     * 
     * @param aSrc copied builder
     */
    public BeesAlgorithmBuilder( BeesAlgorithmBuilder aSrc ) {
        copy(aSrc);
    }

    /**
     * Sets hive position (centre of search area).
     * 
     * @param aPosition hive position
     * @return self
     */
    public BeesAlgorithmBuilder hivePosition( Point aPosition ) {
        hivePosition = aPosition;
        return this;
    }
    /**
     * Returns hive position (centre of search area).
     * 
     * @return hive position
     */
    public Point hivePosition() {
        return hivePosition;
    }
    
    /**
     * Sets hive size (search range).
     * 
     * @param aHiveSize hive size
     * @return self
     */
    public BeesAlgorithmBuilder hiveSize( int aHiveSize ) {
        hiveSize = aHiveSize;
        return this;
    }
    /**
     * Returns hive size (search range).
     * 
     * @return hive size
     */
    public int hiveSize() {
        return hiveSize;
    }

    /**
     * Sets amount of scouts.
     * 
     * @param aScouts amount of scouts
     * @return self
     */    
    public BeesAlgorithmBuilder scouts( int aScouts ) {
        scouts = aScouts;
        return this;
    }
    /**
     * Returns amount of scouts.
     * 
     * @return amount of scouts.
     */
    public int scouts() {
        return scouts;
    }
 
    /**
     * Sets amount of selected sources.
     * 
     * @param aSources selected sources
     * @return self
     */
    public BeesAlgorithmBuilder sources( int aSources ) {
        sources = aSources;
        return this;
    }
    /**
     * Returns amount of sources.
     * 
     * @return amount of sorces
     */
    public int sources() {
        return sources;
    }
    
    /**
     * Sets source size (local search area range).
     * 
     * @param aSize source size
     * @return self
     */
    public BeesAlgorithmBuilder sourceSize( int aSize ) {
        sourceSize = aSize;
        return this;
    }
    /**
     * Returns source size (local search area range).
     * 
     * @return source size
     */
    public int sourceSize() {
        return sourceSize;
    }
    
    /**
     * Sets gamma (shrink coefficient).
     * 
     * @param aGamma gamma
     * @return self
     */
    public BeesAlgorithmBuilder gamma( double aGamma ) {
        gamma = aGamma;
        return this;
    }
    /**
     * Returns gamma (shrink coefficient).
     * 
     * @return gamma
     */
    public double gamma() {
        return gamma;
    }
    
    /**
     * Sets amount of elite sources.
     * 
     * @param aBest amount of elite sources
     * @return self
     */
    public BeesAlgorithmBuilder eliteSources( int aBest ) {
        eliteSources = aBest;
        return this;
    }
    /**
     * Returns amount of elite sources.
     * 
     * @return amount of elite sources
     */
    public int eliteSources() {
        return eliteSources;
    }

    /**
     * Sets amount of workers on elite sources.
     * 
     * @param aOnElite amount of workers on elite sources
     * @return self
     */
    public BeesAlgorithmBuilder onElite( int aOnElite ) {
        onElite = aOnElite;
        return this;
    }
    /**
     * Returns amount of workers on elite sources.
     * 
     * @return amount of workers on elite sources
     */
    public int onElite() {
        return onElite;
    }
    
    /**
     * Sets amount of workers on another sources (total sources - elite sources).
     * 
     * @param aOnNonBest amount of workers on non-best sources
     * @return self
     */
    public BeesAlgorithmBuilder onOther( int aOnNonBest ) {
        onOther = aOnNonBest;
        return this;
    }
    /**
     * Returns amount of workers on non-best sources (total sources - elite sources).
     * 
     * @return amount of workers on non-best sources
     */
    public int onOther() {
        return onOther;
    }

    /**
     * Sets maximum iterations.
     * 
     * @param aMax maximum iterations
     * @return self
     */
    public BeesAlgorithmBuilder maxIteration( int aMax ) {
        maxIteration = aMax;
        return this;
    }
    /**
     * Returns maximum iterations.
     * 
     * @return maximum iterations
     */
    public int maxIteration() {
        return maxIteration;
    }
    
    /**
     * Sets accuracy.
     * 
     * @param aAccuracy accuracy
     * @return self
     */
    public BeesAlgorithmBuilder accuracy( double aAccuracy ) {
        accuracy = aAccuracy;
        return this;
    }
    /**
     * Returns accuracy.
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
    public BeesAlgorithmBuilder constrained( boolean aConstrained ) {
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
     * @param aSrc copied builder
     */
    public void copy( BeesAlgorithmBuilder aSrc ) {
        scouts = aSrc.scouts;
        sources = aSrc.sources;
        eliteSources = aSrc.eliteSources;
        onElite = aSrc.onElite;
        onOther = aSrc.onOther;
        hivePosition = new Point( aSrc.hivePosition );
        sourceSize = aSrc.sourceSize;
        hiveSize = aSrc.hiveSize;
        gamma = aSrc.gamma;
        maxIteration = aSrc.maxIteration;
        accuracy = aSrc.accuracy;
    }

    @Override
    public JFrame show() {
        if (settingsView == null) {
            settingsView = new BeesSettings( this );
        }
        return settingsView;
    }
    
    @Override
    public OptimizationAlgorithm build( NFunction aFunction ) {
        BeesAlgorithmParameters params = new BeesAlgorithmParameters(
            hivePosition, hiveSize, sources, scouts, eliteSources, 
            sourceSize, gamma, onElite, onOther, maxIteration, accuracy
        );
        BeesAlgorithm alg = new BeesAlgorithm( aFunction, params );
        alg.constraint( constraints );
        return alg;
    }

    @Override
    public String toString() {
        return "Пчелиный алгоритм";
    }
    
}
