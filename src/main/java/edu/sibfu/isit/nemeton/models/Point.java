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
package edu.sibfu.isit.nemeton.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * Represents point in N-dimensional space.
 * 
 * @author Max Balushkin
 */
public class Point {
    private final int arity;
    private final double[] params;
    
    /**
     * Creates point from list of coordinates
     * @param params Coordinates
     */
    public Point(double ... params) {
        this.arity = params.length;
        this.params = params;
    }
    
    /**
     * Copying constructor
     * @param src Other point
     */
    public Point(Point src) {
        arity = src.getArity();
        params = Arrays.copyOf(src.params, arity);
    }
    
    public Point(final int aArity, double aValue) {
        arity = aArity;
        params = new double[aArity];
        Arrays.fill(params, aValue);
    }
    
    /**
     * Returns zero in N-dimensions
     * @param dim Point dimension
     * @return Zero
     */
    public static Point zero(int dim) {
        return new Point(new double[dim]);
    }
    
    /**
     * @return Point arity
     */
    public int getArity() {
        return arity;
    }
    
    /**
     * Returns coordinate by specified dimension
     * @param i Dimension
     * @return Coordinate
     */
    public double get(int i) {
        if (i >= arity) {
            return 0;
        } else {
            return params[i];
        }
    }
    
    public double[] get() {
        return params;
    }
    
    /**
     * Adds point to point
     * @param b Other point
     * @return New point
     */
    public Point add(Point b) {
        int n = Math.max(arity, b.getArity());
        double[] points = new double[n];
        for (int i = 0; i < n; i++) {
            points[i] = get(i) + b.get(i);
        }
        return new Point(points);
    }
    
    /**
     * Adds constant to every coordinate of point
     * @param c Constant
     * @return New point
     */
    public Point add(double c) {
        int n = getArity();
        double[] points = new double[n];
        for (int i = 0; i < n; i++) {
            points[i] = get(i) + c;
        }
        return new Point(points);
    }
    
    /**
     * Adds constant to specified coordinate
     * @param c Constant
     * @param dim Dimension
     * @return New point
     */
    public Point add(double c, int dim) {
        double[] p = Arrays.copyOf(params, arity);
        if (dim < getArity()) {
            p[dim] = params[dim] + c;
        }
        return new Point(p);
    }
    
    /**
     * Subtracts point from point
     * @param b Other poiny
     * @return New point
     */
    public Point sub(Point b) {
        int n = Math.max(arity, b.getArity());
        double[] points = new double[n];
        for (int i = 0; i < n; i++) {
            points[i] = get(i) - b.get(i);
        }
        return new Point(points);
    }
    
    /**
     * Subtracts constant from every dimension
     * @param c Constant
     * @return New point
     */
    public Point sub(double c) {
        int n = getArity();
        double[] points = new double[n];
        for (int i = 0; i < n; i++) {
            points[i] = get(i) - c;
        }
        return new Point(points);
    }
    
    public Point mul(final Point b) {
        int n = Math.max(arity, b.getArity());
        double[] points = new double[n];
        for (int i = 0; i < n; i++) {
            points[i] = get(i) * b.get(i);
        }
        return new Point(points);
    }
    
    /**
     * Multiplies point on constant
     * @param c Constant
     * @return New point
     */
    public Point mul(double c) {
        int n = getArity();
        double[] points = new double[n];
        for (int i = 0; i < n; i++) {
            points[i] = get(i) * c;
        }
        return new Point(points);
    }
    
    /**
     * Divides point on constant
     * @param c Constant
     * @return New point
     */
    public Point div(double c) {
        int n = getArity();
        double[] points = new double[n];
        for (int i = 0; i < n; i++) {
            points[i] = get(i) / c;
        }
        return new Point(points);
    }

    /**
     * Calculates distance between point
     * @param b Other point
     * @return Distance
     */
    public double distance(Point b) {
        double res = 0;
        int n = Math.max(getArity(), b.getArity());
        for (int i = 0; i < n; i++) {
            res += Math.pow(get(i) - b.get(i), n);
        }
        return Math.pow(res, 1.0 / n);
    }
    
    public Point abs() {
        int n = getArity();
        double[] points = new double[n];
        for (int i = 0; i < n; i++) {
            points[i] = Math.abs(get(i));
        }
        return new Point(points);
    }
    
    public Point pow(double power) {
        int n = getArity();
        double[] points = new double[n];
        for (int i = 0; i < n; i++) {
            points[i] = Math.pow(get(i), power);
        }
        return new Point(points);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Arrays.hashCode(this.params);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (this.arity != other.arity) {
            return false;
        }
        return Arrays.equals(this.params, other.params);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("(");
        for (double d : params) {
//            builder.append(d).append(", ");
            builder.append(new BigDecimal(d).setScale(4, RoundingMode.HALF_DOWN).doubleValue()).append(", ");
        }
        builder.replace(builder.lastIndexOf(","), builder.length(), ")");
        return builder.toString();
    }
}
