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
package edu.sibfu.isit.nemeton.analysis;

import edu.sibfu.isit.nemeton.algorithms.AlgorithmBuilder;
import edu.sibfu.isit.nemeton.models.AnalysisResult;
import edu.sibfu.isit.nemeton.models.CalculatedPoint;
import edu.sibfu.isit.nemeton.models.Result;
import edu.sibfu.isit.nemeton.models.functions.NFunction;
import edu.sibfu.isit.nemeton.views.AnalysisView;
import edu.sibfu.isit.nemeton.views.ProgressView;
import java.util.List;
import javax.swing.SwingUtilities;
import edu.sibfu.isit.nemeton.framework.Listener;

/**
 * Algorithm analyzer.
 * 
 * @author Max Balushkin
 */
public class Analysis {
    
    /**
     * Analyzer routine.
     */
    private class AnalyserRoutine implements Runnable {

        private Listener<Integer> listener;
        private Listener<AnalysisResult> analysisResult;
        private NFunction function;
        private AlgorithmBuilder algo;
        private int n;
        private double accuracy;
        
        /**
         * Creates new analyser routine. 
         * 
         * @param aListener progress listener (notifies current algorithm run)
         * @param aAnalysisListener result listener (notifies analyse result)
         * @param aFunction optimized function
         * @param aAlgo analysed algorithm builder
         * @param aN amount of algorithm runs
         * @param aAccuracy analyse accuracy
         */
        public AnalyserRoutine( 
            Listener<Integer> aListener, Listener<AnalysisResult> aAnalysisListener, 
            NFunction aFunction, AlgorithmBuilder aAlgo, 
            int aN, double aAccuracy 
        ) {
            listener = aListener;
            analysisResult = aAnalysisListener;
            
            function = aFunction;
            algo = aAlgo;
            n = aN;
            accuracy = aAccuracy;
        }
        
        @Override
        public void run() {
            List<CalculatedPoint> minima = function.minima();
            int success = 0;
            int total = 0;
            int evaluations = 0;
            for ( int i = 0; i < n; i++ ) {
                Result result = algo.build( function ).minimize();
                CalculatedPoint[] solutions = result.getValues();

                final int ipp = i + 1;
                SwingUtilities.invokeLater( () -> listener.publish( ipp ) );

                boolean wasNaN = false;
                loop:
                for ( CalculatedPoint point : solutions ) {
                    wasNaN = false;
                    if ( Double.isNaN( point.getValue() ) ) {
                        wasNaN = true;
                        continue;
                    }

                    for ( CalculatedPoint min : minima ) {
                        double distance = min.distance(point);
                        double valueDiff = Math.abs( min.getValue() - point.getValue() );
                        if ( distance < accuracy || valueDiff < accuracy ) {
                            success++;
                            evaluations += result.getEvaluations();
                            break loop;
                        } 
                    }
                }
                if (!wasNaN) total++;
            }
            
            double prob = total != 0 ? (double) success / total : 0;
            double mean = total != 0 ? (double) evaluations / total : Double.NaN;
            
            AnalysisResult res = new AnalysisResult(algo.build( function ), function, prob, mean);
            SwingUtilities.invokeLater( () -> analysisResult.publish( res ) );
        }
        
    }
    
    /**
     * Analyses algorithms.
     * 
     * @param aView analyser view
     * @param aFunction optimized function
     * @param aBuilders algorithm builders
     * @param aN amount of runs
     * @param aAccuracy analyse accuracy
     */
    public void analyse( AnalysisView aView, NFunction aFunction, List<AlgorithmBuilder> aBuilders, int aN, double aAccuracy ) {
        for ( AlgorithmBuilder bldr : aBuilders ) {
            ProgressView panel = new ProgressView( bldr.toString() );
            panel.setMin( 0 );
            panel.setMax( aN );
            panel.setValue( 0 );
            panel.setVisible( true );
            new Thread(
                new AnalyserRoutine( panel.progressListener, aView.listener, aFunction, bldr, aN, aAccuracy )
            ).start();
        }
    }
    
}
