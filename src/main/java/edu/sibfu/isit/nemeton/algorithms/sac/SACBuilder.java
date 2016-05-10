/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sibfu.isit.nemeton.algorithms.sac;

import edu.sibfu.isit.nemeton.algorithms.AlgorithmBuilder;
import edu.sibfu.isit.nemeton.algorithms.sac.kernels.ParabolicKernel;
import edu.sibfu.isit.nemeton.algorithms.sac.kernels.SelectiveKernel;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import javax.swing.JFrame;
import edu.sibfu.isit.nemeton.algorithms.OptimizationAlgorithm;

/**
 *
 * @author Maximillian M.
 */
public class SACBuilder extends AlgorithmBuilder {

    private SelectiveKernel kernel = new ParabolicKernel();
    private double selectiveness = 100;
    
    @Override
    public OptimizationAlgorithm build(NFunction function) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "Селективное усреднение координат";
    }

    @Override
    public JFrame show() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}