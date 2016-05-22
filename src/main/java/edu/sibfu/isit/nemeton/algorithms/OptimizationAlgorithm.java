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
package edu.sibfu.isit.nemeton.algorithms;

import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.models.Result;
import edu.sibfu.isit.nemeton.models.functions.Constraint;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Base class for optimizing algorithms.
 * 
 * @author Max Balushkin
 */
public abstract class OptimizationAlgorithm {
    
    /**
     * Optimized function.
     */
    protected final NFunction f;
    /**
     * Algorithm constraints.
     */
    protected final List<Constraint> constraints;
    
    /**
     * Creates new optimization algorithm. 
     * 
     * @param aFunction Optimized function
     */
    protected OptimizationAlgorithm(final NFunction aFunction) {
        f = aFunction;
        constraints = new ArrayList<>();
    }
    
    /**
     * Runs algorithm with custom comparator.
     * 
     * @param comparator Custom comparator for points
     * @return Result
     */
    public abstract Result run(Comparator<Point> comparator);
    
    /**
     * Adds new constraint to algorithm.
     * 
     * @param aConstraint Constraint
     */
    public void constraint( final Constraint aConstraint ) {
        constraints.add( aConstraint );
    }
    
    /**
     * Adds new constraints to algorithm.
     * 
     * @param aConstraints Constraints
     */
    public void constraint( final List<Constraint> aConstraints ) {
        aConstraints.forEach((constr) -> constraint(constr));
    }
    
    /**
     * Minimizes function.
     * 
     * @return Result
     */
    public Result minimize() {
        return run((Point a, Point b) -> {
            return f.eval(a) > f.eval(b) ? 1 : -1;
        });
    }
    
    /**
     * Maximizes function.
     * 
     * @return Result
     */
    public Result maximize() {
        return run((Point a, Point b) -> {
            return f.eval(a) > f.eval(b) ? -1 : 1;
        });
    }
    
}
