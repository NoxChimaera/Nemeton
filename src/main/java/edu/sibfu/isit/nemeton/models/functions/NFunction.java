/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sibfu.isit.nemeton.models.functions;

import edu.sibfu.isit.nemeton.models.Point;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jzy3d.plot3d.builder.Mapper;

/**
 *
 * @author Maximillian M.
 */
public class NFunction {
    protected Mapper mapper;
    private boolean isMapped;
    protected Function<Point, Double> f;
    
    private String title;
    private String text;
    
    private int arity;
    
    public NFunction(Function<Point, Double> function, int arity) {
        this.f = function;
        this.arity = arity;
    }
    
    public NFunction(BiFunction<Double, Double, Double> function) {
        this((Point t) ->  function.apply(t.get(0), t.get(1)), 2);
        mapper = new Mapper() {
            @Override
            public double f(double d, double d1) {
                return function.apply(d, d);
            }
        };
    }
    
    public NFunction setTitle(String title) {
        this.title = title;
        return this;
    }
    public NFunction setText(String text) {
        this.text = text;
        return this;
    }
    public String getText() {
        return text;
    }
    
    public int getArity() {
        return arity;
    }
    
    public boolean isMapped() {
        return isMapped;
    }
    
    public double eval(Point x) {
        return f.apply(x);
    }

    @Override
    public String toString() {
        return title;
    }
    
    
}
