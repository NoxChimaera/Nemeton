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
package edu.sibfu.isit.nemeton.lib;

import com.sun.javafx.binding.StringFormatter;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Max Balushkin
 */
public class FunctionTextFormatterTest extends FunctionTextFormatter {
    public FunctionTextFormatterTest() {
    }

    @Test
    public void testToHTML() {
        String foo = FunctionTextFormatter.toHTML("\\Sigma^3_{i = 0} 100(x_i^2)", false);
        
        assertEquals("x<sub>1</sub>", FunctionTextFormatter.toHTML("x_1", 0));
        assertEquals("x<sub>1</sub><sup>2</sup>", FunctionTextFormatter.toHTML("x_1^2", 0));
        assertEquals("x<sub>(y<sup>2</sup>)</sub>", FunctionTextFormatter.toHTML("x_(y^2)", 0));
        assertEquals("y<sup>(x*(1+2))</sup>", FunctionTextFormatter.toHTML("y^(x*(1+2))", 0));
        assertEquals("y<sup>x*(1+2)</sup>", FunctionTextFormatter.toHTML("y^{x*(1+2)}", 0));
        assertEquals("Σ", FunctionTextFormatter.toHTML("\\Sigma", 0));
    }
}
