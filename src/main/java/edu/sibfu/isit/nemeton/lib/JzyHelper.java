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
package edu.sibfu.isit.nemeton.lib;

import edu.sibfu.isit.nemeton.models.CalculatedPoint;
import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import java.util.List;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;

/**
 *
 * @author Max Balushkin
 */
public class JzyHelper {
    
    public static Range range(final List<? extends Point> aPoints, final double aMargin) {
        double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
        for (Point point : aPoints) {
            double x = point.get(0);
            double y = point.get(1);
            
            min = Math.min(Math.min(x, y), min);
            max = Math.max(Math.max(x, y), max);
        }
        
        return new Range(min - aMargin, max + aMargin);
    }
    
    public static Range union(final Range a, final Range b) {
        double min = Math.min(a.getMin(), b.getMin());
        double max = Math.max(a.getMax(), b.getMax());
        return new Range(min, max);
    }
    
    public static Coord3d toCoord3d(final NFunction aFunction, final Point aPoint) {
        return new Coord3d(aPoint.get(0), aPoint.get(1), aFunction.eval(aPoint));
    }
    
    public static Coord3d toCoord3d(final CalculatedPoint aPoint) {
        return new Coord3d(aPoint.get(0), aPoint.get(1), aPoint.getValue());
    }
}
