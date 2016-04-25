package edu.sibfu.isit.nemeton;

import edu.sibfu.isit.nemeton.algorithms.bees.BeesAlgorithmBuilder;
import edu.sibfu.isit.nemeton.algorithms.sac.SACBuilder;
import edu.sibfu.isit.nemeton.controllers.MainController;
import edu.sibfu.isit.nemeton.lib.FunctionTextFormatter;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import edu.sibfu.isit.nemeton.views.MainView;
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
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.primitives.axes.ContourAxeBox;
import org.jzy3d.plot3d.primitives.axes.IAxe;
import org.jzy3d.plot3d.primitives.axes.layout.AxeBoxLayout;
import org.jzy3d.plot3d.primitives.axes.layout.IAxeLayout;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.legends.colorbars.ColorbarLegend;
import org.jzy3d.plot3d.rendering.view.View;

/**
 *
 * @author Maximillian M.
 */
public class Nemeton {
    
    private static void test() {
       // Define a function to plot
        Mapper mapper = new Mapper(){
                public double f(double x, double y) {
                        return 10*Math.sin(x/10)*Math.cos(y/20)*x;
                }
        };

        // Define range and precision for the function to plot
        Range xrange = new Range(50,100);
        Range yrange = new Range(50,100);
        int steps   = 50;
        final Shape surface = (Shape)Builder.buildOrthonormal(new OrthonormalGrid(xrange, steps, yrange, steps), mapper);
        ColorMapper myColorMapper=new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1,1,1,.5f)); 
        surface.setColorMapper(myColorMapper);
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(true);
        surface.setWireframeColor(Color.BLACK);

        JzyFactories.axe = new AxeFactory() {
            @Override
            public IAxe getInstance(BoundingBox3d box, View view) {
                return new ContourAxeBox(box);
            }
        };
        
        Chart chart = new Chart(); //TODO: Quality.Advanced contour buggy with axe box 
        ContourAxeBox cab = (ContourAxeBox)chart.getView().getAxe();
        MapperContourPictureGenerator contour = new MapperContourPictureGenerator(mapper, xrange, yrange);
        cab.setContourImg( contour.getContourImage(new DefaultContourColoringPolicy(myColorMapper), 400, 400, 10), xrange, yrange);

        // Add the surface and its colorbar
        chart.addDrawable(surface);
        surface.setLegend(new ColorbarLegend(surface, 
            chart.getView().getAxe().getLayout().getZTickProvider(), 
            chart.getView().getAxe().getLayout().getZTickRenderer()));
        surface.setLegendDisplayed(true); // opens a colorbar on the right part of the display
        ChartLauncher.openChart(chart);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        MainView view = new MainView();
        view.setVisible(true);
        MainController ctrl = view.getController();
        ctrl.registerBuilder(new BeesAlgorithmBuilder());
        ctrl.registerBuilder(new SACBuilder());

        registerFunctions();
    }
    
    private static void registerFunctions() {
        new NFunction((x, y) -> x*x + y*y)
        .setTitle("Гиперсфера")
        .setText(FunctionTextFormatter.toHTML(
            "I(x_1, x_2) = x_1^2 + x_2^2", true
        )).register();
           
        new NFunction((x, y) 
            -> Math.pow((Math.pow(x, 2) + y - 11), 2) +
                Math.pow((Math.pow(y, 2) + x - 7), 2)
        ).setTitle("Химмельблау")
        .setText(FunctionTextFormatter.toHTML(
            "I(x_1, x_2) = (x_1^2 + x_2 - 11)^2 + (x_2^2 + x_1 - 7)^2", true
        )).register();
        
        new NFunction((x, y)
            -> 3905.93 - 100 * Math.pow(x*x - y, 2) - Math.pow(1 - x, 2)
        ).setTitle("Де Джонга")
        .setText(FunctionTextFormatter.toHTML(
            "I(x_1, x_2) = 3905.53 - 100(x_1^2 - x_2)^2 - "
            + "(1 - x_1)^2", true
        )).register();
        
        new NFunction((x, y)
            -> (1 + Math.pow(x + y + 1, 2)*(19 - 14*x+3*x*x-14*y+6*x*y+3*y*y)
                * (30 + Math.pow(2*x-3*y, 2)*(18 - 32*x + 12*x*x + 48*y -36*x*y+27*y*y))
            )
        ).setTitle("Гольдштейна-Прайса")
        .setText(FunctionTextFormatter.toHTML(
            "[1 + (x_1 + x_2 + 1)^2 (19 - 14x_1 + 3x_1^2 + 6x_1 x_2 + 3x_2^2)] * "
            + "[30 + (2x_1 - 3x_2)^2 (18 - 32x_1 + 12x_1^2 + 48x_2 - 36x_1 x_2 + 27x_2^2)]", true
        )).register();
        
        final double a = 1, b = (5.1 / 4.0) * Math.pow(7.0 / 22.0, 2);
        final double c = 7.0 * (5.0 / 22.0), d = 6.0;
        final double e = 10.0, f = (1.0 / 8.0) * (7.0 / 22.0);
        new NFunction((x, y)
            -> a * Math.pow(y - b*x*x + c*x - d, 2)
                + e*(1 - f)*Math.cos(x) + e
        ).setTitle("Брейнина")
        .setText(FunctionTextFormatter.toHTML(
            "a(x_2 - bx_1^2 + cx_1 - d)^2 + e(1 - f)cos(x_1) + e", true
        )).register();
        
        new NFunction((x, y) 
            -> Math.pow(x - y, 2) + Math.pow((x + y - 10)/ 3, 2)
        ).setTitle("Мартина-Гедди")
        .setText(FunctionTextFormatter.toHTML(
            "(x_1 - x_2)^2 + ((x_1 + x_2 - 10)/3)^2", true
        )).register();
        
        new NFunction((x, y) 
            -> 100 * Math.pow(x*x - y, 2) + Math.pow(1 - x, 2)
        ).setTitle("Розенброка")
        .setText(FunctionTextFormatter.toHTML(
            "100(x_1^2 - x_2)^2 + (1 - x_1)^2", true
        )).register();
        
        new NFunction((x) -> {
            double y = 0;
            for (int i = 0; i < 6; i++) {
                y += Math.pow(x.get(i), 2);
            }
            return y;
        }, 6).setTitle("Гиперсфера (6-мерная)")
        .setText(FunctionTextFormatter.toHTML(
            "\\Sigma^6_{i = 0} 100(x^2_i)", true
        )).register();
    }
}
