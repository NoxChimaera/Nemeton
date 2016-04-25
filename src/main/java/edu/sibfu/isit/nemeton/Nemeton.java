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
package edu.sibfu.isit.nemeton;

import edu.sibfu.isit.nemeton.algorithms.bees.BeesAlgorithmBuilder;
import edu.sibfu.isit.nemeton.algorithms.sac.SACBuilder;
import edu.sibfu.isit.nemeton.controllers.MainController;
import edu.sibfu.isit.nemeton.controllers.providers.FunctionProvider;
import edu.sibfu.isit.nemeton.lib.FunctionTextFormatter;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import edu.sibfu.isit.nemeton.views.MainView;

/** 
 *
 * @author Max Balushkin
 */
public class Nemeton {
    
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
        
        FunctionProvider.subscribe(ctrl);
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
