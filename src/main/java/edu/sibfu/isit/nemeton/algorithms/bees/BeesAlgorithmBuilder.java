/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sibfu.isit.nemeton.algorithms.bees;

import edu.sibfu.isit.nemeton.algorithms.AlgorithmBuilder;
import edu.sibfu.isit.nemeton.algorithms.IOptimization;
import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import edu.sibfu.isit.nemeton.views.BeesSettings;
import javax.swing.JFrame;

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

    int maxIteration = 10000;

    public BeesAlgorithmBuilder() {
    }
    public BeesAlgorithmBuilder(BeesAlgorithmBuilder src) {
        copy(src);
    }

    public BeesAlgorithmBuilder hivePosition(Point point) {
        hivePosition = point;
        return this;
    }
    public BeesAlgorithmBuilder hiveSize(int hiveSize) {
        this.hiveSize = hiveSize;
        return this;
    }

    public BeesAlgorithmBuilder scouts(int scouts) {
        this.scouts = scouts;
        return this;
    }

    public BeesAlgorithmBuilder sources(int sources) {
        this.sources = sources;
        return this;
    }
    public BeesAlgorithmBuilder sourceSize(int size) {
        sourceSize = size;
        return this;
    }
    public BeesAlgorithmBuilder gamma(double gamma) {
        this.gamma = gamma;
        return this;
    }
    public BeesAlgorithmBuilder eliteSources(int best) {
        eliteSources = best;
        return this;
    }

    public BeesAlgorithmBuilder onElite(int onBest) {
        this.onElite = onBest;
        return this;
    }
    public BeesAlgorithmBuilder onOther(int onNonBest) {
        this.onOther = onNonBest;
        return this;
    }

    public BeesAlgorithmBuilder maxIteration(int max) {
        this.maxIteration = max;
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

    // ??
    public void copy(BeesAlgorithmBuilder src) {
        scouts = src.scouts;
        sources = src.sources;
        eliteSources = src.eliteSources;
        onElite = src.onElite;
        onOther = src.onOther;
        hivePosition = new Point(src.hivePosition);
        sourceSize = src.sourceSize;
        hiveSize = src.hiveSize;
        gamma = src.gamma;
    }
   
    @Override
    public JFrame show() {
        if (settingsView == null) {
            settingsView = new BeesSettings(this);
        }
        return settingsView;
    }
    
    @Override
    public IOptimization build(NFunction function) {
        return new BeesAlgorithm(
                function, hivePosition, hiveSize, scouts, sources, sourceSize,
                gamma, eliteSources, onElite, onOther
        );
    }

    @Override
    public String toString() {
        return "Пчелиный алгоритм";
    }
}
