/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sibfu.isit.nemeton;

import edu.sibfu.isit.nemeton.algorithms.bees.BeesAlgorithmBuilder;
import edu.sibfu.isit.nemeton.algorithms.sac.SACBuilder;
import edu.sibfu.isit.nemeton.controllers.MainController;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import edu.sibfu.isit.nemeton.views.MainView;

/**
 *
 * @author Maximillian M.
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

        NFunction hyper = new NFunction((x, y) -> x*x + y*y)
        .setTitle("Гиперсфера")
        .setText("<html>I(x<sub>1</sub>, x<sub>2</sub>) = "
            + "x<sub>1</sub><sup>2</sup>+x<sub>2</sub><sup>2</sup></html>");
        ctrl.registerFunction(hyper);
           
        NFunction himmelblau = new NFunction((x, y) 
            -> Math.pow((Math.pow(x, 2) + y - 11), 2) +
                Math.pow((Math.pow(y, 2) + x - 7), 2)
        ).setTitle("Химмельблау")
        .setText("<html>I(x<sub>1</sub>, x<sub>2</sub>) = "
            + "(x<sub>1</sub><sup>2</sup> + x<sub>2</sub> - 11)<sup>2</sup> + "
            + "(x<sub>2</sub><sup>2</sup> + x<sub>1</sub> - 7)<sup>2</sup></html>");
        ctrl.registerFunction(himmelblau);
    }
}
