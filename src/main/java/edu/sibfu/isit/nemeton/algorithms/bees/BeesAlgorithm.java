/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sibfu.isit.nemeton.algorithms.bees;

import edu.sibfu.isit.nemeton.algorithms.IOptimization;
import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author Maximillian M.
 */
public class BeesAlgorithm implements IOptimization {
    private final NFunction f;
    
    private final int scouts;
    private final Random rnd;
    
    private final Point hivePosition;
    private final int hiveSize;
    private final int sources;
    private double sourceSize;
    private final double gamma;
    
    private final int eliteSites;
    private final int onElite;
    private final int onOther;
    
    public BeesAlgorithm(
        NFunction function, Point hivePosition, int hiveSize, int scouts,
        int sources, double sourceSize, double gamma,
        int eliteSites,
        int onElite, int onOther
    ) {
        this.hivePosition = hivePosition;
        this.scouts = scouts;
        this.hiveSize = hiveSize;
        this.sources = sources;
        this.sourceSize = sourceSize;
        this.gamma = gamma;
        
        this.eliteSites = eliteSites;
        this.onElite = onElite;
        this.onOther = onOther;
        
        this.f = function;
        rnd = new Random();
    }
    
    @Override
    public List<Point> run(Comparator<Point> comparator) {
        // Init algorithm
        List<Point> points = new ArrayList<>(sources);            
        int arity = f.getArity();
        for (int i = 0; i < scouts; i++) {
            Point x = Point.zero(arity).add(hivePosition);
            for (int v = 0; v < arity; v++) {
                x = x.add(((rnd.nextDouble() * 2) - 1) * hiveSize, v);
            }
            points.add(x);
        }
        points.sort(comparator);
        points = points.stream().limit(sources).collect(Collectors.toList());

        for (int it = 0; it < 1000; it++) {
            // Harvest elite sites
            for (int i = 0; i < eliteSites; i++) {
                Point centre = points.get(i);
                for (int j = 0; j < onElite; j++) {
                    Point x = new Point(centre);
                    for (int v = 0; v < arity; v++) {
                        x = x.add(((rnd.nextDouble() * 2) - 1) * sourceSize, v);
                    }
                    if (!x.equals(centre))
                        points.add(x);
                }
            }
            // Harvest other sites
            for (int i = eliteSites; i < sources; i++) {
                Point centre = points.get(i);
                for (int j = 0; j < onOther; j++) {
                    Point x = new Point(centre);
                    for (int v = 0; v < arity; v++) {
                        x = x.add(((rnd.nextDouble() * 2) - 1) * sourceSize, v);
                    }
                    if (!x.equals(centre))
                        points.add(x);
                }
            }
            // Scouts
            for (int i = 0; i < scouts; i++) {
                Point x = Point.zero(arity).add(hivePosition);
                for (int v = 0; v < arity; v++) {
                    x = x.add(((rnd.nextDouble() * 2) - 1) * hiveSize, v);
                }
                points.add(x);
            }
            
            sourceSize *= gamma;            
            points.sort(comparator);
            points = points.stream().limit(sources).collect(Collectors.toList());
        }
        return points;
    }
    
    @Override
    public List<Point> minimize() {
        return run((Point a, Point b) -> {
            return f.eval(a) > f.eval(b) ? 1 : -1;
        });
    }
    
    @Override
    public List<Point> maximize() {
        return run((Point a, Point b) -> {
            return f.eval(a) > f.eval(b) ? -1 : 1;
        });
    }

}
