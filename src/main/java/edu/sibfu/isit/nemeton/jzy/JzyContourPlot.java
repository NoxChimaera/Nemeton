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
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.IColorMap;
import org.jzy3d.contour.DefaultContourColoringPolicy;
import org.jzy3d.contour.MapperContourPictureGenerator;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.plot3d.primitives.axes.ContourAxeBox;

/**
 * Wrapper for Jzy3d axis with contour plot.
 * 
 * @author Max Balushkin
 */
public class JzyContourPlot {
    
    private final ContourAxeBox axe;
    
    /**
     * Creates new contour plot.
     * 
     * @param aSurface surface
     * @param aColourMap contour plot colour map
     * @param aTransparency transparency (1 - invisible, 0 - no transparency) 
     * @param aLevels count of contour lines
     * @param aBounds chart bounds
     */
    public JzyContourPlot(
        JzySurface aSurface, 
        IColorMap aColourMap,
        float aTransparency,
        int aLevels,
        BoundingBox3d aBounds
    ) {
        MapperContourPictureGenerator contour = 
            new MapperContourPictureGenerator(
                aSurface.getFunction().getMapper(), 
                aSurface.getRange(), 
                aSurface.getRange()
            );
        
        ColorMapper mapper = new ColorMapper(
            aColourMap, 
            aSurface.getDrawable().getBounds().getZmin(),
            aSurface.getDrawable().getBounds().getZmax(),
            new Color( 1, 1, 1, 1 - aTransparency )
        );
        
        axe = new ContourAxeBox(aBounds);
        axe.setContourImg(
            contour.getContourImage(
                new DefaultContourColoringPolicy( mapper ), 
                1000, 
                1000, 
                aLevels
            ), 
            aSurface.getRange(),     
            aSurface.getRange()
        );
    }
 
    /**
     * Appends contour plot to chart axis.
     * 
     * @param aChart chart
     */
    public void append( Chart aChart ) {
        aChart.getView().setAxe( axe );
    }
    
}
