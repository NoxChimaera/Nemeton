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
import edu.sibfu.isit.nemeton.controllers.providers.FunctionProvider;
import edu.sibfu.isit.nemeton.controllers.providers.FunctionProviderSubscriber;
import edu.sibfu.isit.nemeton.lib.JzyDataAdapter;
import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.models.Result;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import edu.sibfu.isit.nemeton.views.MainView;
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
            }
        }
        
        show(aFunction, results);
    }
    
    public void error(String message) {
        JOptionPane.showMessageDialog(view, message);
    }
    
    public void show(final NFunction aFunction, final ArrayList<Result> aResults) {
        if (!aFunction.isMapped()) {
            error("График выбранной функции не может быть построен");
            return;
        }
        
        Mapper mapper = aFunction.getMapper();
        
        Range range = new Range(-100, 100);
        int steps = 50;
        Shape surface = (Shape) 
            Builder.buildOrthonormal(new OrthonormalGrid(range, steps), mapper);
        ColorMapper colorMapper = new ColorMapper(
            new ColorMapRainbow(), 
            surface.getBounds().getZmin(),
            surface.getBounds().getZmax(),
            new Color(1,1,1,.5f)
        );
        surface.setColorMapper(colorMapper);
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(true);
        surface.setWireframeColor(Color.BLACK);
        
        JzyFactories.axe = new AxeFactory() {
            @Override
            public IAxe getInstance(BoundingBox3d box, View view) {
                return new ContourAxeBox(box);
            }
        };
        
        Chart chart = new Chart(Quality.Intermediate);
        
        ArrayList<ArrayList<Coord3d>> results = new ArrayList<>();
        for (Result result : aResults) {
            ArrayList<Coord3d> res = new ArrayList<>();
            for (Point point : result.getValues()) {
                res.add(JzyDataAdapter.toCoord3d(aFunction, point));
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
  
//            // === Цикл по истории движения к экстремуму ===
//            // Графическое представление линий движения к глоб. экстремумам.
//            //LineStrip lines = DrawHelper.createLineStrip(extremumHistory, color);
//            
//            chart.getScene().getGraph().add(pointScatter);
//            //chart.getScene().getGraph().add(lines);
//        }
        
        
        
        
        MapperContourPictureGenerator contour = new MapperContourPictureGenerator(mapper, range, range);
        ContourAxeBox cab = (ContourAxeBox) chart.getView().getAxe();
        cab.setContourImg(
            contour.getContourImage(new DefaultContourColoringPolicy(colorMapper), 400, 400, 25), range, range
        );
        
        chart.addDrawable(surface);
        ChartLauncher.openChart(chart);

//        // Add the surface and its colorbar
//        chart.addDrawable(surface);
//        surface.setLegend(new ColorbarLegend(surface, 
//            chart.getView().getAxe().getLayout().getZTickProvider(), 
//            chart.getView().getAxe().getLayout().getZTickRenderer()));
//        surface.setLegendDisplayed(true); // opens a colorbar on the right part of the display
//        ChartLauncher.openChart(chart);
    }
}
