/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sibfu.isit.nemeton.algorithms.bees;

import edu.sibfu.isit.nemeton.algorithms.IAlgorithmBuilder;
import edu.sibfu.isit.nemeton.algorithms.IOptimization;
import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.views.BeesSettings;
import javax.swing.JFrame;

/**
 *
 * @author
 */
public class BeesAlgorithmBuilder implements IAlgorithmBuilder  {
    BeesSettings settings;
    
    int scouts;
    int sources;
    int bestSources;
    int onBest;
    int onNonBest;
    
    Point hivePosition;
    // range
    
    public BeesAlgorithmBuilder hivePosition(Point point) {
        hivePosition = point;
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
    
    public BeesAlgorithmBuilder bestSources(int best) {
        bestSources = sources;
        return this;
    }
    
    public BeesAlgorithmBuilder onBest(int onBest) {
        this.onBest = onBest;
        return this;
    }
    
    public BeesAlgorithmBuilder onNonBest(int onNonBest) {
        this.onNonBest = onNonBest;
        return this;
    }
    
    @Override
    public IOptimization build() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Пчелиный алгоритм";
    }
    
    @Override
    public JFrame show() {
        if (settings == null) {
            settings = new BeesSettings(this);
        }
        return settings;
    }
        
//    public ArtificialBeeColony params(int scouts, int selectedSources, int bestSources, int beesOnBest, int beesOnNonBest, Range sourceRange) {
//        return new ArtificialBeeColony(function, scouts, bestSources, selectedSources - bestSources, beesOnBest, beesOnNonBest, sourceRange);
//    }
}
