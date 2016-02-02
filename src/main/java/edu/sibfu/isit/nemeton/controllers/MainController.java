/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sibfu.isit.nemeton.controllers;

import edu.sibfu.isit.nemeton.algorithms.IAlgorithmBuilder;
import edu.sibfu.isit.nemeton.algorithms.IOptimization;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import edu.sibfu.isit.nemeton.views.MainView;
import java.util.ArrayList;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    ArrayList<IAlgorithmBuilder> builders;
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
                    Boolean.class, IAlgorithmBuilder.class
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
    
    public void registerBuilder(IAlgorithmBuilder bldr) {
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
    
    public void runAlgorithms(Goal g) {
        Object[] foo = Stream.of(buildersTableModel.getDataVector().toArray())
            .filter(v -> (Boolean) ((Vector) v).get(0))
            .map(v -> ((Vector) v).get(1))
            .toArray();
        
        IOptimization opt[] = new IOptimization[foo.length];
    }
}
