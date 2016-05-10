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

import edu.sibfu.isit.nemeton.algorithms.PointHistory;
import edu.sibfu.isit.nemeton.models.CalculatedPoint;
import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.models.Result;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import edu.sibfu.isit.nemeton.algorithms.OptimizationAlgorithm;

/**
 *
 * @author Max Balushkin
 */
public class BeesAlgorithm extends OptimizationAlgorithm {

    private final int scouts;
    private final Random rnd;
    
    private final Point hivePosition;
    private final int hiveSize;
    private final int sites;
    private final double siteSize;
    private final double gamma;
    
    private final int eliteSites;
    private final int onElite;
    private final int onOther;
    
    private final int iterations;
    private final double accuracy;
    
    private int evaluations;
    
    public BeesAlgorithm(final NFunction aFunction, final BeesAlgorithmParameters aParams) {
        super(aFunction);
        hivePosition = aParams.hivePosition;
        hiveSize = aParams.hiveSize;
        scouts = aParams.scouts;
        
        sites = aParams.sites;
        siteSize = aParams.siteSize;
        gamma = aParams.gamma;
        
        eliteSites = aParams.eliteSites;
        onElite = aParams.onElite;
        onOther = aParams.onOther;
        
        iterations = aParams.iterations;
        accuracy = aParams.accuracy;
        
        rnd = new Random();
        
        evaluations = 0;
    }
    
    private List<Point> scouting() {        
        List<Point> points = new ArrayList<>(sites);            
        final int arity = f.getArity();

        for (int i = 0; i < scouts; i++) {
            Point x = Point.zero(arity).add(hivePosition);
            for (int v = 0; v < arity; v++) {
                x = x.add(((rnd.nextDouble() * 2) - 1) * hiveSize, v);
            }
            points.add(x);
        }
        evaluations += scouts;
        return points;
    }
    
    @Override
    public Result run(Comparator<Point> comparator) {
        double sourceSize = this.siteSize;
        String endClause = "нет данных";
        
        final PointHistory history = new PointHistory();
        
        // Init algorithm
        List<Point> points = scouting();
        evaluations += scouts;
        points.sort(comparator);
        points = points.stream().limit(sites).collect(Collectors.toList());

        int it = 0;
        final int arity = f.getArity();
        
        for (it = 0; it < iterations && sourceSize > accuracy; it++) {
            // Harvest elite sites
            for (int i = 0; i < eliteSites; i++) {
                Point centre = points.get(i);
                for (int j = 0; j < onElite; j++) {
                    Point x = new Point(centre);
                    for (int v = 0; v < arity; v++) {
                        x = x.add(((rnd.nextDouble() * 2) - 1) * sourceSize, v);
                    }
                    if (!x.equals(centre)) {
                        points.add(x);
                    }
                }
            }
            // Harvest other sites
            for (int i = eliteSites; i < sites; i++) {
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
            points.addAll(scouting());
            evaluations += sites;
            sourceSize *= gamma;            

            try {
                points.sort(comparator);
            } catch (IllegalArgumentException ex) {
                points = points.stream().limit(sites).collect(Collectors.toList());
                endClause = "нарушение контракта сортировки";
                break;
            }
            points = points.stream().limit(sites).collect(Collectors.toList());
            for (int i = 0; i < eliteSites; i++) {
                Point p = points.get(i);
                history.add(i, p, f.eval(p));
            }
            
            // End by accuracy
            double[] doubles = points
                .stream()
                .limit(eliteSites)
                .mapToDouble((point) -> f.eval(point)).toArray();
            
            double avg = DoubleStream.of(doubles).average().getAsDouble();
            double stdDev = Math.sqrt(
                    DoubleStream.of(doubles).reduce(0, (a, b) -> a + Math.pow(b - avg, 2)) / eliteSites
            );
            
            if (stdDev <= accuracy) {
                endClause = "по точности";
                break;
            }
        }
        
        if (it == iterations) {
            endClause = "по итерациям";
        } else if (sourceSize <= accuracy) {
            endClause = "по размеру области локального поиска";
        }
        
        final CalculatedPoint[] solutions = new CalculatedPoint[points.size()];
        final int n = points.size();
        for (int i = 0; i < n; i++) {
            Point x = points.get(i);
            solutions[i] = new CalculatedPoint(x, f.eval(x));
        }
        
        Result result = new Result(this, f, solutions, it, evaluations);
        result.setEndClause(endClause);
        result.setHistory(history);
        return result;
    }
   
    @Override
    public String toString() {
        return "Пчелиный алгоритм";
    }

}
