/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sibfu.isit.nemeton.models;

import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author Maximillian M.
 */
public class Point {
    private final int arity;
    private final double[] params;
    public Point(double ... params) {
        this.arity = params.length;
        this.params = params;
    }
    
    public static Point zero(int dim) {
        return new Point(new double[dim]);
    }
    
    public int getArity() {
        return arity;
    }
    
    public double get(int i) {
        if (i >= arity) {
            return 0;
        } else {
            return params[i];
        }
    }
    
    public Point add(Point b) {
        int n = Math.max(arity, b.getArity());
        double[] points = new double[n];
        for (int i = 0; i < n; i++) {
            points[i] = get(i) + b.get(i);
        }
        return new Point(points);
    }
    
    public Point add(double c) {
        int n = getArity();
        double[] points = new double[n];
        for (int i = 0; i < n; i++) {
            points[i] = get(i) + c;
        }
        return new Point(points);
    }
    
    public Point add(double c, int dim) {
        double[] p = ArrayUtils.clone(params);
        if (dim < getArity()) {
            p[dim] = params[dim] + c;
        }
        return new Point(p);
    }
    
    public Point sub(Point b) {
        int n = Math.max(arity, b.getArity());
        double[] points = new double[n];
        for (int i = 0; i < n; i++) {
            points[i] = get(i) - b.get(i);
        }
        return new Point(points);
    }
    public Point sub(double c) {
        int n = getArity();
        double[] points = new double[n];
        for (int i = 0; i < n; i++) {
            points[i] = get(i) - c;
        }
        return new Point(points);
    }
    
    public Point mul(double c) {
        int n = getArity();
        double[] points = new double[n];
        for (int i = 0; i < n; i++) {
            points[i] = get(i) * c;
        }
        return new Point(points);
    }
    public Point div(double c) {
        int n = getArity();
        double[] points = new double[n];
        for (int i = 0; i < n; i++) {
            points[i] = get(i) / c;
        }
        return new Point(points);
    }

    public double distance(Point b) {
        double res = 0;
        int n = Math.max(getArity(), b.getArity());
        for (int i = 0; i < n; i++) {
            res += Math.pow(get(i) - b.get(i), n);
        }
        return Math.pow(res, 1 / n);
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
            builder.append(d).append(", ");
        }
        builder.replace(builder.lastIndexOf(","), builder.length(), ")");
        return builder.toString();
    }
}
