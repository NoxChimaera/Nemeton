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

import edu.sibfu.isit.nemeton.algorithms.Builders;
import edu.sibfu.isit.nemeton.algorithms.bees.BeesAlgorithmBuilder;
import edu.sibfu.isit.nemeton.algorithms.sac.SACBuilder;
import edu.sibfu.isit.nemeton.algorithms.sac.kernels.CubicKernel;
import edu.sibfu.isit.nemeton.algorithms.sac.kernels.Kernels;
import edu.sibfu.isit.nemeton.algorithms.sac.kernels.LinearKernel;
import edu.sibfu.isit.nemeton.algorithms.sac.kernels.ParabolicKernel;
import edu.sibfu.isit.nemeton.controllers.MainController;
import edu.sibfu.isit.nemeton.controllers.providers.Functions;
import edu.sibfu.isit.nemeton.lib.FunctionTextFormatter;
import edu.sibfu.isit.nemeton.models.CalculatedPoint;
import edu.sibfu.isit.nemeton.models.Pair;
import edu.sibfu.isit.nemeton.models.Point;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import edu.sibfu.isit.nemeton.models.functions.RangeConstraint;
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
        view.setVisible( true );
        MainController ctrl = view.getController();
        
        Builders.subscribe(ctrl.builderRegisterCallback );
        registerBuilders();
        
        Functions.subscribe(ctrl.functionRegisterCallback );
        registerFunctions();
        
        registerKernels();
    }
    
    private static void registerBuilders() {
        Builders.register( new BeesAlgorithmBuilder() );
        Builders.register( new SACBuilder() );
    }
    
    private static void registerKernels() {
        Kernels.register( new LinearKernel() );
        Kernels.register( new ParabolicKernel() );
        Kernels.register( new CubicKernel() );
    }
    
    /**
     * Registers function.
     */
    private static void registerFunctions() {        
        ClassLoader cl = NFunction.class.getClassLoader();

        // 2D Hypersphere
        new NFunction( ( x, y ) -> x*x + y*y )
            .setTitle( "Гиперсфера" )
            .setText( "I(x_1, x_2) = x_1^2 + x_2^2", FunctionTextFormatter::toHTML )
            .minima( new CalculatedPoint( 0, 0, 0 ) )
            .register();
       
        // Ackley function
        new NFunction( 
            ( x, y ) -> {
                final double a = 20;
                final double b = 0.2;
                final double c = 2 * Math.PI;
                final double d = 2; // dimensions
                final double res = -a * Math.exp( -b * Math.sqrt( 1 / d * ( x * x + y * y ) ) )
                    - Math.exp( 1 / d * ( Math.cos( c * x ) + Math.cos( c * y ) ) )
                    + a + Math.exp( 1 );
                return res;
            }
        )
            .setTitle( "Ackley" )
            .setText( "ackley", FunctionTextFormatter::image )
            .constraint( RangeConstraint.create( -32.0, 32.0, 2 ) )
            .minima( new CalculatedPoint( 0, 0, 0 ) )
            .register();
        
        // Adjiman function
        new NFunction(
            ( x, y ) -> {
                final double res = Math.cos( x ) * Math.sin( y ) 
                    - x / ( y * y + 1 );
                return res;
            }
        )
            .setTitle( "Adjiman" )   
            .setText( "adjiman", FunctionTextFormatter::image )
            .constraint( new RangeConstraint( new Pair<>( -1.0, 2.0 ), new Pair<>( -1.0, 1.0 ) ) )
            .minima( new CalculatedPoint( -2.02181, 2, 0.10578) )
            .register();
        
        // Alpine 1 function
        new NFunction( 
            ( x, y ) -> {
                final double d0 = Math.abs( x * Math.sin( x ) + 0.1 * x );
                final double d1 = Math.abs( y * Math.sin( y ) + 0.1 * y );
                return d0 + d1;
            }
        )
            .setTitle( "Alpine 1" )
            .setText( "alpine1", FunctionTextFormatter::image )
            .constraint( RangeConstraint.create( -10.0, 10.0, 2 ) )
            .minima( new CalculatedPoint( 0, 0, 0 ) )
            .register();
        
        // Bird function
        new NFunction(
            ( x, y ) -> {
                final double p0 = Math.pow( x - y, 2 );
                final double p1 = Math.exp( Math.pow( 1 - Math.sin( x ), 2) ) * Math.cos( y );
                final double p2 = Math.exp( Math.pow( 1 - Math.cos( y ), 2) ) * Math.sin( x );
                return p0 + p1 + p2;
            }
        )
            .setTitle( "Bird" )
            .setText( "bird", FunctionTextFormatter::image )
            .constraint( new RangeConstraint( new Pair<>( -Math.PI * 2, Math.PI * 2 ), 2 ) )
            .constraint( RangeConstraint.create( -Math.PI * 2, Math.PI * 2, 2 ) )
            .minima( new CalculatedPoint( -106.76453, 4.70105575, 3.15294601 ) )
            .minima( new CalculatedPoint( -106.76453, -1.58214217, -3.13024679 ) )
            .register();
        
        // Branin
        new NFunction(
            ( x, y ) -> {
                double a = Math.pow( -1.275 * ( x * x ) / Math.pow( Math.PI, 2 ) + 5 * x / Math.PI + y - 6, 2 );
                double b =( 10 - 5.0 / ( 4 * Math.PI ) ) * Math.cos( x );
                return a + b + 10;
            }
        )
            .setTitle( "Branin" )
            .setText( "branin", FunctionTextFormatter::image )
            .constraint( new RangeConstraint( new Pair<>( -5.0, 10.0 ), new Pair<>( 0.0, 15.0 ) ) )
            .minima( new CalculatedPoint( 0.3978873577, -Math.PI, 12.275 ) )
            .minima( new CalculatedPoint( 0.3978873577, Math.PI, 2.275 ) )
            .minima( new CalculatedPoint( 0.3978873577, 9.42478, 2.475 ) )
            .register();
        
        // Damavandi function
        new NFunction(
            ( x, y ) -> {
                final double p01 = Math.sin( Math.PI * ( x - 2 ) ) * Math.sin( Math.PI * ( y - 2 ) );
                final double p02 = Math.pow( Math.PI, 2 ) * ( x - 2 ) * ( y - 2 );
                final double p0 = 1 - Math.pow( Math.abs( p01 / p02 ), 5 );
                final double p1 = 2 + Math.pow( x - 7, 2 ) + 2 * Math.pow( y - 7, 2 );
                return p0 * p1;
            }
        )
            .setTitle( "Damavandi" )
            .setText( "damavandi", FunctionTextFormatter::image )
            .constraint( new RangeConstraint( new Pair<>( 0.0, 14.0 ), 2 ) )
            .constraint( RangeConstraint.create( 0.0, 14.0, 2 ) )
            .minima( new CalculatedPoint( 0, 2, 2 ) )
            .register();
       
        // Dropwave
        new NFunction(
            ( x, y ) -> {
                double num = 1 + Math.cos( 12 * Math.sqrt( x * x + y * y ) );
                double denum = 2 + 0.5 * ( x * x + y * y );
                return -num / denum;
            }
        )
            .setTitle( "DropWave ")
            .setText( "dropwave", FunctionTextFormatter::image )
            .constraint( new RangeConstraint( new Pair<>( -5.12, 5.12 ), 2 ) )
            .constraint( RangeConstraint.create( -5.12, 5.12, 2 ) )
            .minima( new CalculatedPoint( -1, 0, 0 ) )
            .register();
        
        // 3D Griewank function
        new NFunction( 
            ( x, y ) -> {
                double sum = x * x + y * y;
                double prod = Math.cos( x / 1 ) * Math.cos( y / Math.sqrt( 2 ) );
                return 1.0/4000.0 * sum - prod + 1;
            }
        )
            .setTitle( "Griewank 3D" )
            .setText( "griewank", FunctionTextFormatter::image )
            .constraint( RangeConstraint.create( -600, 600, 2 ) )
            .minima( new CalculatedPoint( 0, 0, 0 ) )
            .register();

        // 10D Griewank function
        new NFunction( 
            ( x ) -> {
                double sum = 0;
                for ( int i = 0; i < 9; i++ ) {
                    sum += x.get( i ) * x.get( i );
                }
                sum /= 4000;
                
                double prod = 1;
                for ( int i = 0; i < 9; i++ ) {
                    prod *= Math.cos( x.get( i ) / Math.sqrt( i + 1 ) );
                }
                return sum - prod + 1;
            }, 10
        )
            .setTitle( "Griewank 11D" )
            .setText( "griewank", FunctionTextFormatter::image )
            .constraint( RangeConstraint.create( -600, 600, 10 ) )
            .minima( new CalculatedPoint( new Point( 10, 0) , 0 ) )
            .register();
       
        // Himmelblau function
        new NFunction( ( x, y ) 
            -> Math.pow( ( Math.pow( x, 2 ) + y - 11 ), 2 ) 
                + Math.pow( ( Math.pow( y, 2 ) + x - 7 ), 2 ) )
            .setTitle( "Himmelblau" )
            .setText( 
                "I(x_1, x_2) = (x_1^2 + x_2 - 11)^2 + (x_2^2 + x_1 - 7)^2", 
                FunctionTextFormatter::toHTML 
            )
            .constraint( RangeConstraint.create( -6.0, 6.0, 2 ) )
            .minima( new CalculatedPoint( 0, 3, 2 ) )
            .minima( new CalculatedPoint( 0, -3.779, -3.283 ) )
            .minima( new CalculatedPoint( 0, -2.805, 3.131 ) )
            .minima( new CalculatedPoint( 0, 3.584, -1.848 ) )
            .register();
    
        // Rosenbrock function
        new NFunction( 
            ( x, y ) -> 100 * Math.pow( x * x - y, 2 ) + Math.pow( x - 1, 2 )
        )
            .setTitle( "Розенброка" )
            .setText( "100(x_1^2 - x_2)^2 + (1 - x_1)^2", FunctionTextFormatter::toHTML )
            .constraint( RangeConstraint.create( -5, 10, 2 ) )
            .minima( new CalculatedPoint( 0, 1, 1 ) )
            .register();
        
        // 6D Hypersphere
        new NFunction( ( x ) 
            -> {
                double y = 0;
                for ( int i = 0; i < 6; i++ ) {
                    y += Math.pow( x.get( i ), 2 );
                }
                return y;
            }, 6
        ).setTitle( "Гиперсфера (6-мерная)" )
        .setText(FunctionTextFormatter.toHTML(
            "\\Sigma^6_{i = 0} 100(x^2_i)", true
        )).register();
        
        // Algorithmic function with four extremums
        new NFunction( ( x, y ) 
            -> {
                final double[] parts = new double[ 4 ];
                parts[ 0 ] = -3 * Math.exp( -3 * ( Math.pow( Math.abs( x - 3 ), 1.5 ) + Math.pow( Math.abs( y ), 1.5) ) );
                parts[ 1 ] = -5 * Math.exp( -2.5 * (Math.pow( Math.abs( x + 3 ), 2.5 ) + Math.pow( Math.abs( y ), 2.5 ) ) );
                parts[ 2 ] = -7 * Math.exp( -( Math.pow( Math.abs( y - 3 ), 1.2 ) + Math.pow( Math.abs( x ), 1.2 ) ) );
                parts[ 3 ] = -10 * Math.exp( -2 * ( Math.pow( Math.abs( y + 3 ), 2 ) + Math.pow( Math.abs( x ), 2 ) ) );
                
                double minValue = Double.MAX_VALUE;
                for ( double part : parts ) {
                    minValue = Math.min( minValue, part );
                }
                return minValue;
            }
        ).setTitle( "Потенциальная" ).register();
        
        // Yet another algorithmic function
        new NFunction( ( x, y ) 
            -> {
                final double[] parts = new double[ 10 ];
                parts[ 0 ] = 6 * Math.pow( Math.abs( x ), 2 ) + 7 * Math.pow( Math.abs( y ), 2 );
                parts[ 1 ] = 5 * Math.pow( Math.abs( x + 2 ), 0.5 ) + 5 * Math.pow( Math.abs( y ), 0.5 ) + 6;
                parts[ 2 ] = 5 * Math.pow( Math.abs( x ), 1.3 ) + 5 * Math.pow( Math.abs( y + 2 ), 1.3 ) + 5;
                parts[ 3 ] = 4 * Math.pow( Math.abs( x ), 0.8 ) + 3 * Math.pow( Math.abs( y - 4 ), 1.2 ) + 8;
                parts[ 4 ] = 6 * Math.pow( Math.abs( x - 2 ), 1.1 ) + 4 * Math.pow( Math.abs( y - 2 ), 1.7 ) + 7;
                parts[ 5 ] = 5 * Math.pow( Math.abs( x - 4 ), 1.1 ) + 5 * Math.pow( Math.abs( y ), 1.8 ) + 9;
                parts[ 6 ] = 6 * Math.pow( Math.abs( x - 4 ), 0.6 ) + 7 * Math.pow( Math.abs( y - 4 ), 0.6 ) + 4;
                parts[ 7 ] = 6 * Math.pow( Math.abs( x + 4 ), 0.6 ) + 6 * Math.pow( Math.abs( y - 4 ), 1.6 ) + 3;
                parts[ 8 ] = 3 * Math.pow( Math.abs( x + 4 ), 1.2 ) + 3 * Math.pow( Math.abs( y + 4 ), 0.5 ) + 7.5;
                parts[ 9 ] = 2 * Math.pow( Math.abs( x - 3 ), 0.9 ) + 4 * Math.pow( Math.abs( y + 5 ), 0.3 ) + 8.5;

                double minValue = parts[ 0 ];
                for ( double part : parts )
                    minValue = part < minValue ? part : minValue;

                return minValue;
            }
        ).setTitle("Многоэсктремальная").register();
      
    }
}
