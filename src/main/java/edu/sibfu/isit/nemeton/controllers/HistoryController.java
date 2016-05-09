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
import edu.sibfu.isit.nemeton.algorithms.PointHistory;
import edu.sibfu.isit.nemeton.models.CalculatedPoint;
import edu.sibfu.isit.nemeton.models.Result;
import edu.sibfu.isit.nemeton.views.HistoryView;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Max Balushkin
 */
public class HistoryController {
    
    private final HistoryView view;
    private final ArrayList<Result> results;
    
    private final XYPlot plot;
    
    public HistoryController(final HistoryView aView, final ArrayList<Result> aResults) {
        view = aView;
        results = aResults;
        
        final ArrayList<DataSeries> data = generateDataSeries(aResults);
        plot = new XYPlot();
        
        final Random rnd = new Random();
        for (DataSeries series : data) {
            final Color colour = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
            final LineRenderer lines = new DefaultLineRenderer2D();
            lines.setColor(colour);
            final PointRenderer points = new DefaultPointRenderer2D();
            points.setColor(colour);
            
            plot.add(series);
            plot.setLineRenderers(series, lines);
            plot.setPointRenderers(series, points);
        }
        plot.setLegendVisible(true);
    }
    
    private ArrayList<DataSeries> generateDataSeries(final ArrayList<Result> aResults) {
        final ArrayList<DataSeries> dataSeries = new ArrayList<>();
        final int n = aResults.size();
        for (int i = 0; i < n; i++) {
            final Result result = aResults.get(i);
            final PointHistory history = result.getHistory();
            final int m = history.size();
            for (int j = 0; j < m; j++) {
                final ArrayList<CalculatedPoint> points = history.get(j);
                final DataTable data = new DataTable(Integer.class, Double.class);
                final int o = points.size();
                for (int k = 0; k < o; k++) {
                    data.add(k, points.get(k).getValue());
                }
                final DataSeries series = new DataSeries(
                    String.format(
                        "%s-%d", 
                        result.getAlgorithm().toString(),
                        j
                    ), 
                    data, 0, 1
                );
                dataSeries.add(series);
            }
        }
       
        return dataSeries;
    }
    
    public XYPlot getPlot() {
        return plot;
    }
    
}
