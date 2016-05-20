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

import edu.sibfu.isit.nemeton.algorithms.AlgorithmBuilder;
import edu.sibfu.isit.nemeton.algorithms.OptimizationAlgorithm;
import edu.sibfu.isit.nemeton.analysis.Analysis;
import edu.sibfu.isit.nemeton.controllers.providers.Functions;
import edu.sibfu.isit.nemeton.jzy.JzyChart;
import edu.sibfu.isit.nemeton.jzy.JzyContourPlot;
import edu.sibfu.isit.nemeton.jzy.JzyLinePlot;
import edu.sibfu.isit.nemeton.jzy.JzyScatterPlot;
import edu.sibfu.isit.nemeton.jzy.JzySurface;
import edu.sibfu.isit.nemeton.lib.JzyHelper;
import edu.sibfu.isit.nemeton.models.CalculatedPoint;
import edu.sibfu.isit.nemeton.models.Result;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import edu.sibfu.isit.nemeton.views.AnalysisView;
import edu.sibfu.isit.nemeton.views.HistoryView;
import edu.sibfu.isit.nemeton.views.MainView;
import edu.sibfu.isit.nemeton.views.ResultView;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import edu.sibfu.isit.nemeton.framework.Listener;

/**
 *
 * @author Max Balushkin
 */
public class MainController {
    
    /**
     * Registers functions.
     */
    public final Listener<NFunction> functionRegisterCallback = new Listener<NFunction>() {
        @Override
        public void publish( final NFunction aFunc ) {
            functionsListModel.addElement( aFunc );
        }
    };
    
    /**
     * Registers algorithm builders.
     */
    public final Listener<AlgorithmBuilder> builderRegisterCallback = new Listener<AlgorithmBuilder>() {
        @Override
        public void publish( final AlgorithmBuilder aBldr ) {
            buildersTableModel.addRow( new Object[] { true, aBldr } );
        }
    };
    
    MainView view;
    
    /**
     * Table. 
     * Rows:
     *  0. Selection : boolean
     *  1. Algorithm : AlgorithmBuilder
     */
    DefaultTableModel buildersTableModel;
    
    /**
     * List of functions.
     */
    DefaultComboBoxModel<NFunction> functionsListModel;

    /**
     * Problem goal.
     */
    public enum Goal {
        Minimize,
        Maximize
    }
    
    public MainController( MainView view ) {
        this.view = view;
        buildersTableModel = 
            new DefaultTableModel( new Object[][] {}, new String[] { "", "Название" } ) {
                Class[] types = new Class [] {
                    Boolean.class, AlgorithmBuilder.class
                };

                @Override
                public Class getColumnClass( int columnIndex ) {
                    return types[ columnIndex ];
                }
            };
        functionsListModel = new DefaultComboBoxModel<>();
    }

    public void analyse( Goal aGoal, NFunction aFunction, int aN, double aAccuracy ) {
        List<OptimizationAlgorithm> algos = collectAlgorithms( aFunction );
        Analysis foo = new Analysis();
        
        AnalysisView anView = new AnalysisView();
        anView.setVisible(true);
        
        foo.analyse( anView, aFunction, algos, aN, aAccuracy );
    }

    public List<OptimizationAlgorithm> collectAlgorithms( final NFunction aFunction ) {
        final ArrayList<OptimizationAlgorithm> algos = new ArrayList<>();
        for ( Object item : buildersTableModel.getDataVector().toArray() ) {
            final Vector vector = (Vector) item;
            if ( (Boolean) vector.get( 0 ) ) {
                AlgorithmBuilder bldr = (AlgorithmBuilder) vector.get( 1 );
                OptimizationAlgorithm alg = bldr.build( aFunction );
                if ( bldr.isConstrained() ) {
                    alg.constraint( aFunction.constraints() );
                }
                algos.add( alg );
            }
        }
        return algos;
    }

    public void error( String message ) {
        JOptionPane.showMessageDialog( view, message );
    }
    
    /**
     * Returns algorithm table model.
     * 
     * @return Table model
     */
    public TableModel getAlghoritmsTableModel() {
        return buildersTableModel;
    }
   
    /**
     * Returns function with specified index.
     * @see Functions#get(int) 
     * 
     * @param index Function index
     * @return Function or null
     */
    public NFunction getFunction( int index ) {
        return Functions.get(index);
    }

    public ComboBoxModel getFunctionListModel() {
        return functionsListModel;
    }
    
    /**
     * Runs selected algorithms. 
     * 
     * @param aGoal Problem goal
     * @param aFunction Optimized function
     * @param showSurface Show surface plot?
     * @param showHistory Show point history?
     */
    public void runAlgorithms( Goal aGoal, NFunction aFunction, boolean showSurface, boolean showHistory ) {
        List<OptimizationAlgorithm> algorithms = collectAlgorithms( aFunction );
        ArrayList<Result> results = new ArrayList<>();
        for ( OptimizationAlgorithm al : algorithms ) {
            switch ( aGoal ) {
                case Maximize:
                    results.add( al.maximize() );
                    break;
                case Minimize:
                    results.add( al.minimize() );
            }
        }
     
        ResultView resultView = new ResultView( results );
        resultView.setVisible( true );
        
        if ( showSurface ) showSurface( aFunction, results );
        if ( showHistory ) showHistory( results );
    }
    
    /**
     * Shows algorithm results.
     * 
     * @param aResults Results
     */
    public void showHistory( final ArrayList<Result> aResults ) {
        HistoryView history = new HistoryView( aResults );
        history.showAsFrame();
    }
    
    /**
     * Shows surface plot.
     * 
     * @param aFunction Function
     * @param aResults Algorithm results
     */
    public void showSurface( final NFunction aFunction, final ArrayList<Result> aResults ) {
        if ( !aFunction.isMapped() ) {
            error( "Specified function can not be mapped with Jzy3d" );
            return;
        }
        
        // Calculate plot range: 
        //  [ min(X) - margin .. max(X) + margin ] 
        //      where X in [ sloutions, point history ]
        Range range = new Range( Double.MAX_VALUE, Double.MIN_VALUE );
        for ( Result result : aResults ) {
            Range r = JzyHelper.range( result.getValues(), 5 );
            range = JzyHelper.union( range, r );
            
            final int n = result.getHistory().size();
            for ( int i = 0; i < n; i++ ) {
                r = JzyHelper.range( result.getHistory().get( i ), 5 );
                range = JzyHelper.union( range, r );
            }
        }

        JzyChart chart = new JzyChart( Quality.Advanced );
        
        // Surface plot
        final int steps = 80;
        JzySurface surface = new JzySurface( aFunction, range, steps )
            .colourize( new ColorMapRainbow(), 0.95f );
        
        // Contour plot
        JzyContourPlot contour = new JzyContourPlot(
            surface, 
            new ColorMapRainbow(), 
            0f, 
            10, 
            chart.getBounds()
        );
        
        chart.addPlot( surface ).addContourPlot( contour );
        
        for ( Result result : aResults ) {
            // Scatter plot. Solutions
            final CalculatedPoint[] solution = result.getValues();
            final Color colour = Color.random();
            JzyScatterPlot scatterPlot = new JzyScatterPlot( solution, colour );
            chart.addPlot( scatterPlot );
            
            // Best solution iterations
            // final int n = result.getHistory().size();
            final int n = 1;
            for ( int i = 0; i < n; i++ ) {
                final ArrayList<CalculatedPoint> points = result.getHistory().get( i );
                JzyLinePlot line = new JzyLinePlot( points );
                chart.addPlot( line );
            }
        }
        
        chart.show( "График функции и найденные точки" );
    }
    
}
