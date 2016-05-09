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
package edu.sibfu.isit.nemeton.algorithms;

import edu.sibfu.isit.nemeton.models.CalculatedPoint;
import edu.sibfu.isit.nemeton.models.Point;
import java.util.ArrayList;

/**
 * Represents points by iterations.
 * 
 * @author Max Balushkin
 */
public class PointHistory {
    
    private final ArrayList<ArrayList<CalculatedPoint>> data;
    private int lastIndex;
    
    public PointHistory() {
        data = new ArrayList<>();
        data.add(new ArrayList<>());
        lastIndex = 0;
    }
    
    public void add(final int idx, final CalculatedPoint point) {
        while (idx >= data.size()) {
            data.add(new ArrayList<>());
        }
        data.get(idx).add(point);
        lastIndex = idx;
    }
    
    public void add(final int idx, final Point point, final double value) {
        add(idx, new CalculatedPoint(point, value));
    }
    
    public void add(final CalculatedPoint point) {
        data.get(lastIndex).add(point);
    }
    
    public void add(final Point point, final double value) {
        add(new CalculatedPoint(point, value));
    }
    
    public ArrayList<CalculatedPoint> get(int idx) {
        return data.get(idx);
    }
    
    public int size() {
        return lastIndex + 1;
    }
    
}
