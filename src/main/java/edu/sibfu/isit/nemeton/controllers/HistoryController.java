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

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import edu.sibfu.isit.nemeton.models.PointHistory;
import edu.sibfu.isit.nemeton.models.CalculatedPoint;
import edu.sibfu.isit.nemeton.framework.Pair;
import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.models.Result;
import edu.sibfu.isit.nemeton.views.HistoryView;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * HistoryView controller.
 * @see HistoryView
 * 
 * @author Max Balushkin
 */
public class HistoryController {
    
    private final HistoryView view;
    private final List<Result> results;
    
    private final XYPlot plot;
    private List<Pair<String, XYPlot>> coordinates;
    
    /**
     * Creates new history view controller.
     * 
     * @param aView view
     * @param aResults algorithm results
     * @param showCoordinates show coordinate plots?
     */
    public HistoryController( HistoryView aView, List<Result> aResults, boolean showCoordinates ) {
        view = aView;
        results = aResults;
        coordinates = new ArrayList<>();
        
        List<DataSeries> data = generateDataSeries(aResults);
        plot = new XYPlot();
        
        Random rnd = new Random();
        for (DataSeries series : data) {
            Color colour = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
            LineRenderer lines = new DefaultLineRenderer2D();
            lines.setColor(colour);
            PointRenderer points = new DefaultPointRenderer2D();
            points.setColor(colour);
            
            plot.add(series);
            plot.setLineRenderers( series, lines );
            plot.setPointRenderers( series, points );
        }
        plot.setLegendVisible( true );
        
        if ( !showCoordinates ) return;
        List<Pair<String, List<DataSeries>>> coords = generateParametersSeries( aResults );
        for ( Pair<String, List<DataSeries>> algo : coords ) {
            XYPlot coordPlot = new XYPlot();
            for ( DataSeries c : algo.right() ) {
                Color colour = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
                LineRenderer lines = new DefaultLineRenderer2D();
                lines.setColor(colour);
                PointRenderer points = new DefaultPointRenderer2D();
                points.setColor(colour);

                coordPlot.add( c );
                coordPlot.setLineRenderers( c, lines );
                coordPlot.setPointRenderers( c, points );
                coordPlot.setLegendVisible( true );
            }
            coordinates.add( new Pair<>( algo.left(), coordPlot ) );
        }
    }
    
    /**
     * Generates values plot data.
     * 
     * @param aResults algorithm results
     * @return values plot data
     */
    private List<DataSeries> generateDataSeries( List<Result> aResults ) {
        ArrayList<DataSeries> dataSeries = new ArrayList<>();
        final int n = aResults.size();
        for ( int i = 0; i < n; i++ ) {
            Result result = aResults.get( i );
            PointHistory history = result.getHistory();
            final int m = history.size();
            for ( int j = 0; j < m; j++ ) {
                List<CalculatedPoint> points = history.get ( j );
                DataTable data = new DataTable( Integer.class, Double.class );
                final int o = points.size();
                for ( int k = 0; k < o; k++ ) {
                    data.add( k, points.get( k ).getValue() );
                }
                DataSeries series = new DataSeries(
                    String.format(
                        "%s-%d", 
                        result.getAlgorithm().toString(),
                        j
                    ), 
                    data, 0, 1
                );
                dataSeries.add( series );
            }
        }
        return dataSeries;
    }
    
    /**
     * Generates parameters plot data.
     * 
     * @param aResults algorithm results
     * @return parameters plot data
     */
    private List<Pair<String, List<DataSeries>>> generateParametersSeries( List<Result> aResults ) {
        List<Pair<String, List<DataSeries>>> dataSeries = new ArrayList<>();
        for ( Result result : aResults ) {
            List<DataSeries> data = generateParameterSeries( result );
            dataSeries.add( new Pair<>( result.getAlgorithm().toString(), data ) );
        }
        return dataSeries;
    }
    
    /**
     * Generates parameter plot data.
     * 
     * @param aResult algorithm results
     * @return parameter plot data
     */
    private List<DataSeries> generateParameterSeries( Result aResult ) {
        List<DataSeries> dataSeries = new ArrayList<>();
        PointHistory history = aResult.getHistory();
        final int m = history.size() > 0 ? 1 : 0;
        for ( int i = 0; i < m; i++ ) {
            List<CalculatedPoint> points = history.get( i );
            final int o = points.size();
            final int arity = points.get( 0 ).getArity();
            DataTable[] data = new DataTable[ arity ];
            for (int j = 0; j < arity; j++) {
                data[ j ] = new DataTable( Integer.class, Double.class );
            }
            
            for ( int iter = 0; iter < o; iter++ ) {
                Point point = points.get( iter );
                for ( int dim = 0; dim < arity; dim++ ) {
                    data[ dim ].add( iter, point.get( dim ) );
                }
            }
            
            for ( int dim = 0; dim < arity; dim ++ ) {
                DataSeries series = new DataSeries(
                    String.format( "x_%d-%d", dim, i ), 
                    data[ dim ], 
                    0, 1
                );
                dataSeries.add( series );
            }
        }
           
        return dataSeries;
    }
    
    /**
     * Returns values plot.
     * 
     * @return values plot
     */
    public XYPlot getPlot() {
        return plot;
    }
    
    /**
     * Returns parameters plot.
     * 
     * @return parameters plot.
     */
    public List<Pair<String, XYPlot>> getCoordinatePlots() {
        return coordinates;
    }
    
}
