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
package edu.sibfu.isit.nemeton.controllers;

import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.models.Result;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import edu.sibfu.isit.nemeton.views.ResultView;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

/**
 * ResultView controller.
 * @see ResultView
 * 
 * @author Max Balushkin
 */
public class ResultController {
    
    final private ResultView view;
    final private ArrayList<Result> results;
    
    final private DefaultListModel<Result> algorithmListModel;
    final private DefaultTableModel solutionsTableModel;
    
    /**
     * Creates new controller.
     * 
     * @param aView view
     * @param aResults algorithm results
     */
    public ResultController( ResultView aView, ArrayList<Result> aResults ) {
        view = aView;
        results = aResults;
        
        algorithmListModel = new DefaultListModel<>();
        
        solutionsTableModel = new DefaultTableModel(
            new Object[][] {}, new String[] { "Точка", "Значение" }
        ) {
            Class[] types = new Class[] {
                Point.class, Double.class
            };
            
            @Override
            public Class getColumnClass( int columnIndex ) {
                return types[columnIndex];
            }
        };
        
        for ( Result result : aResults ) {
            algorithmListModel.addElement( result );
        }
        
        if ( aResults.isEmpty() ) return;
        
        fillSolutionsTable( aResults.get(0) );
    }
    
    /**
     * Fills solution table with values.
     * 
     * @param aResult algorithm result
     */
    public void fillSolutionsTable( Result aResult ) {
        solutionsTableModel.setRowCount( 0 );
        NFunction f = aResult.getFunction();
        for ( Point point : aResult.getValues() ) {
            solutionsTableModel.addRow( new Object[] { point, f.eval( point ) } );
        }
    }
    
    /**
     * Returns algorithm list model.
     * 
     * @return algorithm list
     */
    public DefaultListModel<Result> getAlgorithmListModel() {
        return algorithmListModel;
    }

    /**
     * Returns solutions table model.
     * 
     * @return solutions table
     */
    public DefaultTableModel getSolutionsTableModel() {
        return solutionsTableModel;
    }
    
}
