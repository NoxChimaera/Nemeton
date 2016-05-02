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

import edu.sibfu.isit.nemeton.algorithms.AlgorithmBuilder;
import edu.sibfu.isit.nemeton.algorithms.IOptimization;
import edu.sibfu.isit.nemeton.algorithms.PointHistory;
import edu.sibfu.isit.nemeton.controllers.providers.FunctionProvider;
import edu.sibfu.isit.nemeton.controllers.providers.FunctionProviderSubscriber;
import edu.sibfu.isit.nemeton.lib.JzyHelper;
import edu.sibfu.isit.nemeton.models.CalculatedPoint;
import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.models.Result;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import edu.sibfu.isit.nemeton.views.MainView;
import edu.sibfu.isit.nemeton.views.ResultView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.contour.DefaultContourColoringPolicy;
import org.jzy3d.contour.MapperContourPictureGenerator;
import org.jzy3d.factories.AxeFactory;
import org.jzy3d.factories.JzyFactories;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.MultiColorScatter;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.primitives.axes.ContourAxeBox;
import org.jzy3d.plot3d.primitives.axes.IAxe;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.view.View;


/**
 *
 * @author Max Balushkin
 */
public class MainController implements FunctionProviderSubscriber {
    MainView view;
    ArrayList<AlgorithmBuilder> builders;
    DefaultTableModel buildersTableModel;
    
    DefaultComboBoxModel<NFunction> functionsListModel;
    
    public enum Goal {
        Minimize,
        Maximize
    }
    
    public MainController(MainView view) {
        this.view = view;
        builders = new ArrayList<>();
        buildersTableModel = 
            new DefaultTableModel(new Object[][] {}, new String[] { "", "Название" }) {
                Class[] types = new Class [] {
                    Boolean.class, AlgorithmBuilder.class
                };

                @Override
                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }
            };
        functionsListModel = new DefaultComboBoxModel<>();
    }
    
    public TableModel getAlghotitmsTableModel() {
        return buildersTableModel;
    }

    @Override
    public void register(final NFunction aFunction) {
        functionsListModel.addElement(aFunction);
    }
    
    public ComboBoxModel getFunctionListModel() {
        return functionsListModel;
    }
    
    public void registerBuilder(AlgorithmBuilder bldr) {
        builders.add(bldr);
        buildersTableModel.addRow(new Object[] { true, bldr });
    }
   
    public NFunction getFunction(int index) {
        return FunctionProvider.get(index);
    }
    
    public void runAlgorithms(Goal aGoal, final NFunction aFunction) {
        final ArrayList<IOptimization> algorithms = new ArrayList<>();
        for (Object item : buildersTableModel.getDataVector().toArray()) {
            Vector vector = (Vector) item;
            if ((Boolean) vector.get(0)) {
                AlgorithmBuilder bldr = (AlgorithmBuilder) vector.get(1);
                algorithms.add(bldr.build(aFunction));
            }
        }
 
        final ArrayList<Result> results = new ArrayList<>();
        for (IOptimization al : algorithms) {
            switch (aGoal) {
                case Maximize:
                    results.add(al.maximize());
                    break;
                case Minimize:
                    results.add(al.minimize());
//                    results.add(al.minimize());
//                    results.add(al.minimize());
//                    results.add(al.minimize());
            }
        }
        
        ResultView resultView = new ResultView(results);
        resultView.setVisible(true);
        show(aFunction, results);
//        showHistory(aFunction, results.get(0));
    }
    
    public void error(String message) {
        JOptionPane.showMessageDialog(view, message);
    }
    
    public void showHistory(NFunction aFunction, Result result) {
        PointHistory ph = result.getHistory();
        
        ArrayList<Coord3d> coords = new ArrayList<>();
        Chart chart = new Chart(Quality.Advanced);
        
//        int n = ph.size();
//        for (int i = 0; i < n; i++) {
//            for (CalculatedPoint p : ph.get(i)) {
//                coords.add(JzyHelper.toCoord3d(p));
//            }
//            LineStrip ls = new LineStrip(coords);
//            ls.setWireframeColor(Color.BLUE);
//            chart.addDrawable(ls);
//            
//            coords = new ArrayList<>();
//        }
//
        LineStrip ls = new LineStrip(new ArrayList<Coord3d>() {{
            add(new Coord3d(0, 0, 10));
            add(new Coord3d(0, 10, 0));
//            add(new Coord3d(10, 0, 0));
        }});
        ls.setWireframeColor(Color.BLUE);
        chart.addDrawable(ls);
        
        ChartLauncher.openChart(chart, "Перемещение лучших точек");
    }
    
    public void show(final NFunction aFunction, final ArrayList<Result> aResults) {
        if (!aFunction.isMapped()) {
            error("График выбранной функции не может быть построен");
            return;
        }
        
        Mapper mapper = aFunction.getMapper();
   
        Range range = new Range(Double.MAX_VALUE, Double.MIN_VALUE);
        for (Result result : aResults) {
            Range r = JzyHelper.range(result.getValues(), 5);
            range = JzyHelper.union(range, r);
            
            int n = result.getHistory().size();
            for (int i = 0; i < n; i++) {
                r = JzyHelper.range(result.getHistory().get(i), 5);
                range = JzyHelper.union(range, r);
            }
        }

        int steps = 80;
        Shape surface = (Shape) 
            Builder.buildOrthonormal(new OrthonormalGrid(range, steps), mapper);
        ColorMapper colorMapper = new ColorMapper(
            new ColorMapRainbow(), 
            surface.getBounds().getZmin(),
            surface.getBounds().getZmax(),
            new Color(1, 1, 1, 0.25f)
        );
        surface.setColorMapper(colorMapper);
        surface.setFaceDisplayed(false);
        surface.setWireframeDisplayed(false);
//        surface.setWireframeColor(Color.BLACK);
        
        JzyFactories.axe = new AxeFactory() {
            @Override
            public IAxe getInstance(BoundingBox3d box, View view) {
                return new ContourAxeBox(box);
            }
        };
        
        Chart chart = new Chart(Quality.Advanced);
        
        ArrayList<ArrayList<Coord3d>> results = new ArrayList<>();
        for (Result result : aResults) {
            ArrayList<Coord3d> res = new ArrayList<>();
            for (Point point : result.getValues()) {
                res.add(JzyHelper.toCoord3d(aFunction, point));
            }
            results.add(res);
        }
        
        for (ArrayList<Coord3d> points : results) {
            Color colour = Color.random();
            
            Color[] colours = new Color[points.size()];
            Arrays.fill(colours, colour);
            
            MultiColorScatter scatter = new MultiColorScatter(
                points.toArray(new Coord3d[] {}), 
                colours,
                new ColorMapper(new ColorMapRainbow(), -0.5f, 0.5f),
                5
            );
            chart.addDrawable(scatter);
        }
  
        MapperContourPictureGenerator contour = new MapperContourPictureGenerator(mapper, range, range);
        ContourAxeBox cab = (ContourAxeBox) chart.getView().getAxe();
        cab.setContourImg(
            contour.getContourImage(new DefaultContourColoringPolicy(colorMapper), 1000, 1000, 5), range, range
        );
        
        
        PointHistory ph = aResults.get(0).getHistory();
        
        int n = ph.size();
//        int n = 1;
        for (int i = 0; i < n; i++) {
            Color colour = Color.random();
            
            int m = ph.get(i).size();
            for (int j = 0, k = 1; k < m; j++, k++) {
                ArrayList<Coord3d> coords = new ArrayList<>();
                coords.add(JzyHelper.toCoord3d(ph.get(i).get(j)));
                coords.add(JzyHelper.toCoord3d(ph.get(i).get(k)));
                LineStrip ls = new LineStrip(coords);
                ls.setWireframeColor(colour);
                chart.addDrawable(ls);
            }
        }
        
        
//        ArrayList<Coord3d> coords = new ArrayList<>();
////        Chart chart = new Chart(Quality.Advanced);
//        
//        int n = ph.size();
//        for (int i = 0; i < n; i++) {
//            Color colour = Color.random();
//            
//            for (CalculatedPoint p : ph.get(i)) {
//                coords.add(JzyHelper.toCoord3d(p));
//            }
//            LineStrip ls = new LineStrip(coords);
//            ls.setWireframeColor(Color.BLUE);
//            chart.addDrawable(ls);
//            
//            coords = new ArrayList<>();
//        }
//        
        chart.addDrawable(surface);
        ChartLauncher.openChart(chart);
    }
}
