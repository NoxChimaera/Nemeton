/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sibfu.isit.nemeton.algorithms;

import edu.sibfu.isit.nemeton.models.functions.NFunction;
import javax.swing.JFrame;

/**
 *
 * @author Maximillian M.
 */
public abstract class AlgorithmBuilder {
    public abstract IOptimization build(NFunction function);
    public abstract JFrame show();
}
