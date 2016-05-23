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

import edu.sibfu.isit.nemeton.models.functions.Constraint;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import edu.sibfu.isit.nemeton.views.BeesSettings;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

/**
 * Base class for algorithm builders.
 * @see BeesSettings
 * 
 * @author Max Balushkin
 */
public abstract class AlgorithmBuilder {

    /**
     * Function constraints.
     */
    protected final List<Constraint> constraints;
    
    /**
     * Default constructor.
     * Instantises empty constraint list
     */
    protected AlgorithmBuilder() {
        constraints = new ArrayList<>();
    }
    
    /**
     * Creates new Optimizing Algorithm object with specified function.
     * 
     * @param aFunction optimized function
     * @return algorithm object
     */
    public abstract OptimizationAlgorithm build( NFunction aFunction );
 
    /**
     * Is algorithm constrained?
     * 
     * @return true if has constrains else false
     */
    public abstract boolean isConstrained();
    
    /**
     * Adds constraint to algorithm.
     * 
     * @param aConstraint constraint
     */
    public void constraint( Constraint aConstraint ) {
        constraints.add( aConstraint );
    }
    
    /**
     * Adds several constraints to algorithm.
     * 
     * @param aConstraints constraints
     */
    public void constraint( List<Constraint> aConstraints ) {
        aConstraints.forEach( ( constr ) -> constraint( constr) );
    }

    /**
     * Deletes all contraints.
     */
    public void clearConstraints() {
        constraints.clear();
    }
    
    /**
     * Show settings window.
     * 
     * @return frame
     */
    public abstract JFrame show();
    
}
