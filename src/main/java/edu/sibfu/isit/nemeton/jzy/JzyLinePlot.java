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

import edu.sibfu.isit.nemeton.lib.JzyHelper;
import edu.sibfu.isit.nemeton.models.CalculatedPoint;
import java.util.ArrayList;
import java.util.List;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.LineStrip;

/**
 *
 * @author Max Balushkin
 */
public class JzyLinePlot extends JzyPlot {

    private final ArrayList<LineStrip> line;
    
    public JzyLinePlot(final List<CalculatedPoint> aPoint) {
        this(aPoint.toArray(new CalculatedPoint[] {}));
    }
    
    public JzyLinePlot(final CalculatedPoint[] aPoints) {
        line = new ArrayList<>();
        
        int n = aPoints.length;
        Color colour = Color.random();
        for (int i = 0, j = 1; j < n; i++, j++)  {
            ArrayList<Coord3d> segment = new ArrayList<>();
            segment.add(JzyHelper.toCoord3d(aPoints[i]));
            segment.add(JzyHelper.toCoord3d(aPoints[j]));
            LineStrip ls = new LineStrip(segment);
            ls.setWireframeColor(colour);
            
            line.add(ls);
        }   
    }
    
    @Override
    public AbstractDrawable getDrawable() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void append(Chart chart) {
        for (LineStrip segment : line) {
            chart.getScene().add(segment);
        }
    }
    
}
