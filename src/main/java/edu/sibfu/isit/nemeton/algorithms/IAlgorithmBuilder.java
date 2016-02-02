/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sibfu.isit.nemeton.algorithms;

import javax.swing.JFrame;

/**
 *
 * @author Maximillian M.
 */
public interface IAlgorithmBuilder {
    IOptimization build();
    JFrame show();
}
