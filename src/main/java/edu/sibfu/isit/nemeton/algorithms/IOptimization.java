/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sibfu.isit.nemeton.algorithms;

import edu.sibfu.isit.nemeton.models.Point;
import java.util.Comparator;

/**
 *
 * @author Maximillian M.
 */
public interface IOptimization {
    public Point run(Comparator<Point> comparator);
    public Point minimize();
    public Point maximize();
}
