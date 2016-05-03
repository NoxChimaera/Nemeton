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
package edu.sibfu.isit.nemeton.jzy;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.plot3d.rendering.canvas.Quality;

/**
 * Wrapper for Jzy3d Chart.
 * 
 * @author Max Balushkin
 */
public class JzyChart {
    
    private final Chart chart;
    
    /**
     * Creates new chart with specified quality.
     * @param aQuality Chart quality
     */
    public JzyChart(final Quality aQuality) {
        chart = new Chart(aQuality);
    }
    
    /**
     * Adds plot. 
     * @param aPlot Plot
     * @return Self
     */
    public JzyChart addPlot(final JzyPlot aPlot) {
        aPlot.append(chart);
        return this;
    }
    
    /**
     * Adds contour plot on chart axis.
     * @param aContourPlot Contour plot
     * @return Self
     */
    public JzyChart addContourPlot(final JzyContourPlot aContourPlot) {
        aContourPlot.append(chart);
        return this;
    }
    
    /**
     * Shows chart window
     * @param title Window title
     */
    public void show(final String title) {
        ChartLauncher.openChart(chart, title);
    }
    
    /**
     * Returns chart bounds
     * @return Bounding box
     */
    public BoundingBox3d getBounds() {
        return chart.getView().getBounds();
    }
    
}
