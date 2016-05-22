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

import edu.sibfu.isit.nemeton.models.functions.NFunction;
import org.jzy3d.chart.Chart;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.colors.colormaps.IColorMap;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.Shape;

/**
 * Wrapper for Jzy3d surface plot.
 * 
 * @author Max Balushkin
 */
public class JzySurface extends JzyPlot {
    
    private final Shape surface;
    private final NFunction function;
    private final Range range;
    
    private ColorMapper colourMapper;
  
    /**
     * Creates new surface plot.
     * 
     * @param aFunction 3D function
     * @param aRange range
     */
    public JzySurface( NFunction aFunction, Range aRange ) {
        this( aFunction, aRange, 80 );
    }
    
    /**
     * Creates new surface plot.
     * 
     * @param aFunction 3D function
     * @param aRange range
     * @param aSteps amount of steps
     */
    public JzySurface( NFunction aFunction, Range aRange, int aSteps ) {
        if ( !aFunction.isMapped() ) {
            throw new IllegalArgumentException( "Specified function can not be mapped with Jzy3d" );
        }
        
        surface = Builder.buildOrthonormal(
            new OrthonormalGrid( aRange, aSteps ), 
            aFunction.getMapper()
        );
        
        function = aFunction;
        range = aRange;
    }
    
    /**
     * Colourizes surface plot with rainbow colours.
     * 
     * @return self
     */
    public JzySurface colourize() {
        return colourize( new ColorMapRainbow(), 0.25f );
    }
    
    /**
     * Colourizes surface plot with specified parameters.
     * 
     * @param aColourMap colour map
     * @param aTransparency transparency (1 - invisible, 0 - no transparency)
     * @return self
     */
    public JzySurface colourize( IColorMap aColourMap, float aTransparency ) {
        if ( Math.abs( aTransparency ) > 1 ) {
            throw new IllegalArgumentException( "Transparency must be in [-1; 1]" );
        }
        
        ColorMapper mapper = new ColorMapper(
            aColourMap, 
            surface.getBounds().getZmin(),
            surface.getBounds().getZmax(),
            new Color( 1, 1, 1, 1 - aTransparency )
        );
        surface.setColorMapper( mapper );
        surface.setWireframeColor( mapper.getColor( 0 ) );
        surface.setWireframeDisplayed( true );
        
        colourMapper = mapper;
        return this;
    }
    
    /**
     * Returns colour mapper.
     * 
     * @return colour mapper
     * @deprecated 
     */
    public ColorMapper getColourMapper() {
        return colourMapper;
    }
    
    /**
     * Returns surface function.
     * 
     * @return 3D function
     */
    public NFunction getFunction() {
        return function;
    }
    
    /**
     * Returns surface plot range.
     * 
     * @return range
     */
    public Range getRange() {
        return range;
    }
    
    @Override
    public AbstractDrawable getDrawable() {
        return surface;
    }

    @Override
    public void append( Chart aChart ) {
        aChart.getScene().getGraph().add( surface );
    }
    
}
