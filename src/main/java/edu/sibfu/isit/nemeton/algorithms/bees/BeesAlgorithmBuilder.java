/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sibfu.isit.nemeton.algorithms.bees;

import edu.sibfu.isit.nemeton.algorithms.AlgorithmBuilder;
import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import edu.sibfu.isit.nemeton.views.BeesSettings;
import javax.swing.JFrame;
import edu.sibfu.isit.nemeton.algorithms.OptimizationAlgorithm;

/**
 *
 * @author
 */
public class BeesAlgorithmBuilder extends AlgorithmBuilder {
    BeesSettings settingsView;

    Point hivePosition = new Point(0, 0);
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
    public BeesAlgorithmBuilder hiveSize(final int aHiveSize) {
        hiveSize = aHiveSize;
        return this;
    }

    public BeesAlgorithmBuilder scouts(final int aScouts) {
        scouts = aScouts;
        return this;
    }

    public BeesAlgorithmBuilder sources(final int aSources) {
        sources = aSources;
        return this;
    }
    public BeesAlgorithmBuilder sourceSize(final int aSize) {
        sourceSize = aSize;
        return this;
    }
    public BeesAlgorithmBuilder gamma(final double aGamma) {
        gamma = aGamma;
        return this;
    }
    public BeesAlgorithmBuilder eliteSources(final int aBest) {
        eliteSources = aBest;
        return this;
    }

    public BeesAlgorithmBuilder onElite(final int aOnBest) {
        onElite = aOnBest;
        return this;
    }
    public BeesAlgorithmBuilder onOther(final int aOnNonBest) {
        onOther = aOnNonBest;
        return this;
    }

    public BeesAlgorithmBuilder maxIteration(final int aMax) {
        maxIteration = aMax;
        return this;
    }
    
    public BeesAlgorithmBuilder accuracy(final double aAccuracy) {
        accuracy = aAccuracy;
        return this;
    }

    public Point getHivePosition() {
        return hivePosition;
    }
    public int getHiveSize() {
        return hiveSize;
    }

    public int getScouts() {
        return scouts;
    }
    
    public int getSources() {
        return sources;
    }
    public int getSourceSize() {
        return sourceSize;
    }
    public double getGamma() {
        return gamma;
    }
    public int getEliteSources() {
        return eliteSources;
    }
    
    public int getOnElite() {
        return onElite;
    }
    public int getOnOther() {
        return onOther;
    }

    public int getMaxIteration() {
        return maxIteration;
    }
    
    public double getAccuracy() {
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
