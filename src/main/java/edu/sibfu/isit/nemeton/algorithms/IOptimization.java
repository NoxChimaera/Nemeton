/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sibfu.isit.nemeton.algorithms;

import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.models.Result;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Maximillian M.
 */
public interface IOptimization {
    public Result run(Comparator<Point> comparator);
    public Result minimize();
    public Result maximize();
}
