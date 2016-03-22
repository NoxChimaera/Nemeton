/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sibfu.isit.nemeton.controllers;

import edu.sibfu.isit.nemeton.algorithms.AlgorithmBuilder;
import edu.sibfu.isit.nemeton.algorithms.IOptimization;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import edu.sibfu.isit.nemeton.views.MainView;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


/**
 *
 * @author Maximillian M.
 */
public class MainController {
    MainView view;
    ArrayList<AlgorithmBuilder> builders;
    DefaultTableModel buildersTableModel;
    
    ArrayList<NFunction> functions;
    DefaultComboBoxModel<NFunction> functionsListModel;
    
    public enum Goal {
        Minimize,
        Maximize
    }
    
    public MainController(MainView view) {
        this.view = view;
        builders = new ArrayList<>();
        functions = new ArrayList<>();
        buildersTableModel = 
            new DefaultTableModel(new Object[][] {}, new String[] { "", "Название" }) {
                Class[] types = new Class [] {
                    Boolean.class, AlgorithmBuilder.class
                };

                @Override
                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }
            };
        functionsListModel = new DefaultComboBoxModel<>();
    }
    
    public TableModel getAlghotitmsTableModel() {
        return buildersTableModel;
    }
    
    public ComboBoxModel getFunctionListModel() {
        return functionsListModel;
    }
    
    public void registerBuilder(AlgorithmBuilder bldr) {
        builders.add(bldr);
        buildersTableModel.addRow(new Object[] { true, bldr });
    }
    
    public void registerFunction(NFunction func) {
        functions.add(func);
        functionsListModel.addElement(func);
    }
    public NFunction getFunction(int index) {
        return functions.get(index);
    }
    
    public void runAlgorithms(Goal g, NFunction function) {
        ArrayList<IOptimization> algorithms = new ArrayList<>();
        for (Object item : buildersTableModel.getDataVector().toArray()) {
            Vector vector = (Vector) item;
            if ((Boolean) vector.get(0)) {
                AlgorithmBuilder bldr = (AlgorithmBuilder) vector.get(1);
                algorithms.add(bldr.build(function));
            }
        }
 
        for (IOptimization al : algorithms) {
            switch (g) {
                case Maximize:
                    al.maximize();
                    break;
                case Minimize:
                    al.minimize();
            }
        }
    }
}
