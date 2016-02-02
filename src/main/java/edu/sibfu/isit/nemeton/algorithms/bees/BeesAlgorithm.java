/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sibfu.isit.nemeton.algorithms.bees;

import edu.sibfu.isit.nemeton.algorithms.IOptimization;
import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import java.util.Comparator;

/**
 *
 * @author Maximillian M.
 */
public class BeesAlgorithm implements IOptimization {
    private NFunction f;
    
    @Override
    public Point run(Comparator<Point> comparator) {
        
        
        
        return Point.zero(f.getArity());
    }
    
//    private Point run(Comparator<Point> comparator) {
//         // Init. Scouts are searching food sources
//        ArrayList<Point> foodSources = new ArrayList<>(scouts);
//        for (int i = 0; i < scouts; i++) {
//            foodSources.add(getRandomPointInRange(min, max));
//        }
//        Collections.sort(foodSources, comparator);
//        ArrayList<Point> best = new ArrayList(foodSources.subList(0, bestSources));
//        ArrayList<Point> nonbest = new ArrayList(foodSources.subList(bestSources, bestSources + nonbestSources));
//        
//        // Main
//        int iter = 0;
//        while (!isEnd(best)) {
//            if (stopByIterations && iter >= maxIteration) {
//                break;
//            }
//            
//            foodSources.clear();
//            // Harvest best sources
//            foodSources.addAll(best);
//            for (Point source : best) {
//                for (int i = 0; i < beesOnBest; i++) {
//                    foodSources.add(source.add(getRandomPointInRange(range, range.mul(-1))));
//                }
//            }
//            // Harvest near-best sources
//            for (Point source : nonbest) {
//                for (int i = 0; i < beesOnNonbest; i++) {
//                    foodSources.add(source.add(getRandomPointInRange(range, range.mul(-1))));
//                }
//            }
//            // Scouting
//            for (int i = 0; i < scouts; i++) {
//                foodSources.add(getRandomPointInRange(min, max));
//            }
//            Collections.sort(foodSources, comparator);
//            best = new ArrayList(foodSources.subList(0, bestSources));
//            nonbest = new ArrayList(foodSources.subList(bestSources, bestSources + nonbestSources));
//            Collections.sort(best, comparator);
//            iter++;
//            
//            range = range.div(1.25);
//        }
//        
//        System.out.println(iter);
//        return best.get(0);
//    }

    @Override
    public Point minimize() {
        return run((Point a, Point b) -> {
            return f.eval(a) > f.eval(b) ? 1 : -1;
        });
    }
    
    @Override
    public Point maximize() {
        return run((Point a, Point b) -> {
            return f.eval(a) > f.eval(b) ? -1 : 1;
        });
    }

}
