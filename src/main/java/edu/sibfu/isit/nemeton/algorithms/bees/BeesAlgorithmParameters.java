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

import edu.sibfu.isit.nemeton.models.Point;

/**
 * Parameters of bees algorithm.
 * @see BeesAlgorithm
 * 
 * @author Max Balushkin
 */
public class BeesAlgorithmParameters {
    
    /**
     * Centre of search area.
     */
    public final Point hivePosition;
    /**
     * Size of search area.
     */
    public final int hiveSize;
    /**
     * Amount of scouts.
     */
    public final int scouts;
    
    /**
     * Amount of selected sites.
     */
    public final int sites;
    /**
     * Amount of elite sites.
     */
    public final int eliteSites;
    /**
     * Size of site.
     */
    public final double siteSize;
    /**
     * Site shrink coefficient.
     */
    public final double gamma;
    
    /**
     * Amount of workers on elite sites.
     */
    public final int onElite;
    /**
     * Amount of workets on non-elite sites.
     */
    public final int onOther;
    
    /**
     * Maximum iterations.
     */
    public final int iterations;
    /**
     * Search accuracy.
     */
    public final double accuracy;
   
    /**
     * @param aHivePosition Centre of search area
     * @param aHiveSize Size of serach area
     * @param aScouts Amount of scouts
     * @param aSites Amount of selected sites
     * @param aEliteSites Amount of elite sites 
     * @param aSiteSize Size of site
     * @param aGamma Site shrink coefficient
     * @param aOnElite Amount of workers on elite sites
     * @param aOnOther Amount of workers on non-elite sites
     * @param aIterations Maximum iterations
     * @param aAccuracy Search accuracy
     */
    public BeesAlgorithmParameters(
        Point aHivePosition, int aHiveSize, int aScouts, 
        int aSites, int aEliteSites, 
        double aSiteSize, double aGamma, 
        int aOnElite, int aOnOther, 
        int aIterations, double aAccuracy
    ) {
        hivePosition = aHivePosition;
        hiveSize = aHiveSize;
        scouts = aScouts;
        sites = aSites;
        eliteSites = aEliteSites;
        siteSize = aSiteSize;
        gamma = aGamma;
        onElite = aOnElite;
        onOther = aOnOther;
        iterations = aIterations;
        accuracy = aAccuracy;
    }
    
}
