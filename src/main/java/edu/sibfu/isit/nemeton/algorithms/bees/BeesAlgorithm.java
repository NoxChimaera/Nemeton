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

import edu.sibfu.isit.nemeton.algorithms.IOptimization;
import edu.sibfu.isit.nemeton.algorithms.PointHistory;
import edu.sibfu.isit.nemeton.models.CalculatedPoint;
import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.models.Result;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 *
 * @author Max Balushkin
 */
public class BeesAlgorithm implements IOptimization {
    private final NFunction f;
    
    private final int scouts;
    private final Random rnd;
    
    private final Point hivePosition;
    private final int hiveSize;
    private final int sources;
    private final double sourceSize;
    private final double gamma;
    
    private final int eliteSites;
    private final int onElite;
    private final int onOther;
    
    private int evaluations;
    
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
        
        evaluations = 0;
    }
    
    private List<Point> scouting() {        
        List<Point> points = new ArrayList<>(sources);            
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
        double sourceSize = this.sourceSize;
        String endClause = "нет данных";
        
        PointHistory history = new PointHistory();
        
        // Init algorithm
        List<Point> points = scouting();
        evaluations += scouts;
        points.sort(comparator);
        points = points.stream().limit(sources).collect(Collectors.toList());

        int it = 0;
        int max = 1000000;
        final double eps = 0.00000001;
        final int arity = f.getArity();
        
        for (it = 0; it < max && sourceSize > eps; it++) {
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
            points.addAll(scouting());
            evaluations += sources;
            sourceSize *= gamma;            

            try {
                points.sort(comparator);
            } catch (IllegalArgumentException ex) {
                points = points.stream().limit(sources).collect(Collectors.toList());
                endClause = "нарушение контракта сортировки";
                break;
            }
            points = points.stream().limit(sources).collect(Collectors.toList());
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
            
            if (stdDev <= eps) {
                endClause = "по точности";
                break;
            }
        }
        
        if (it == max) {
            endClause = "по итерациям";
        } else if (sourceSize <= eps) {
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
    public Result minimize() {
        return run((Point a, Point b) -> {
            return f.eval(a) > f.eval(b) ? 1 : -1;
        });
    }
    
    @Override
    public Result maximize() {
        return run((Point a, Point b) -> {
            return f.eval(a) > f.eval(b) ? -1 : 1;
        });
    }

    @Override
    public String toString() {
        return "Пчелиный алгоритм";
    }

}
