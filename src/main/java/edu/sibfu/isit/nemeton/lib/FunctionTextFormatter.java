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

import edu.sibfu.isit.nemeton.models.functions.NFunction;
import java.util.Stack;

/**
 * Function text utils.
 * 
 * @author Max Balushkin
 */
public class FunctionTextFormatter {
    
    /**
     * Loads PNG-image from `resources/functions/`
     * 
     * @param aName file name
     * @return HTML Img tag
     */
    public static String image( String aName ) {
        ClassLoader cl = NFunction.class.getClassLoader();
        return "<html><img src=" + cl.getResource( "functions/" + aName + ".png" ) + "></html>";
    }
    
    /**
     *  TinyTeX parser state.
     */
    enum State {
        DEFAULT,
        SUB,
        SUP
    }
    
    /**
     * Lookup string.
     * 
     * @param pattern lookup pattern
     * @param src source string
     * @param offset pattern offset
     * @return true if pattern was found, else false
     */
    private static boolean isNext(String pattern, String src, int offset) {
        int n = src.length();
        int pn = pattern.length();
        if (offset + pn > n) return false;
        
        for (int i = 0; i < pn; i++) {
            if (pattern.charAt(i) != src.charAt(offset + i)) {
                return false;
            }
        }
        return true;
    }
    
    private static int idx;
    
    /**
     * Converts Tiny TeX string to HTML.
     * 
     * {@code
     * x -> x
     * x^2 -> x<sup>2<sup>
     * x_1 -> x<sub>1</sub>
     * x_1^2 -> x<sub>1</sub><sup>2</sup>
     * x^y^2 -> x<sup>y<sup>2</sup></sup>
     * x^2 + y -> x<sup>2</sup> + 2
     * x^(2 + y) -> x<sup>2 + y</sup>
     * x + (y - 10) -> x + (y - 10)
     * \\sigma -> Σ
     * }
     * 
     * @param aSrc source string
     * @return HTML
     */
    public static String toHTML( String aSrc ) {
        return toHTML(aSrc, true);
    }
    
    /**
     * Converts Tiny TeX string to HTML.
     * 
     * @param src source string
     * @param placeHtmlTags surround with `html` tag
     * @return HTML
     */
    public static String toHTML(String src, boolean placeHtmlTags) {
        return placeHtmlTags ? "<html>" + toHTML(src, 0) + "</html>"
            : toHTML(src, 0);
    }
    
    /**
     * Tiny TeX-2-HTML parser.
     * 
     * @param src source string
     * @param index offset
     * @return HTML
     */
    private static String toHTML(String src, int index) {
        StringBuilder bldr = new StringBuilder();
        int n = src.length();
        
        Stack<State> state = new Stack<>();
        state.push(State.DEFAULT);
        
        for (idx = index; idx < n; idx++) {
            char ch = src.charAt(idx);
            switch (ch) {
                case '_':
                    if (state.peek() == State.SUP) {
                        bldr.append("</sup>");
                        state.pop();
                    }
                    state.push(State.SUB);
                    bldr.append("<sub>");
                    break;
                case '^':
                    if (state.peek() == State.SUB) {
                        bldr.append("</sub>");
                        state.pop();
                    }
                    state.push(State.SUP);
                    bldr.append("<sup>");
                    break;
                case '\\':
                    if (isNext("Sigma", src, index + 1)) {
                        idx += 5;
                        bldr.append("Σ");
                    }
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                case ' ':
                case ',':
                case '=':
                    switch (state.peek()) {
                        case SUB:
                            bldr.append("</sub>");
                            state.pop();
                            break;
                        case SUP:
                            bldr.append("</sup>");
                            state.pop();
                            break;
                    }
                    bldr.append(ch);
                    break;
                case '(':
                    bldr.append('(');
                    String tmp = toHTML(src, idx + 1);
                    bldr.append(tmp);
                    switch (state.peek()) {
                        case SUB:
                            bldr.append("</sub>");
                            state.pop();
                            break;
                        case SUP:
                            bldr.append("</sup>");
                            state.pop();
                            break;
                    }
                    break;
                case ')':
                    switch (state.peek()) {
                        case SUB:
                            bldr.append("</sub>");
                            state.pop();
                            break;
                        case SUP:
                            bldr.append("</sup>");
                            state.pop();
                            break;
                    }
                    bldr.append(')');
                    return bldr.toString();
                case '{':
                    String tmp1 = toHTML(src, idx + 1);
                    bldr.append(tmp1);
                    switch (state.peek()) {
                        case SUB:
                            bldr.append("</sub>");
                            state.pop();
                            break;
                        case SUP:
                            bldr.append("</sup>");
                            state.pop();
                            break;
                    }
                    break;
                case '}':
                    switch (state.peek()) {
                        case SUB:
                            bldr.append("</sub>");
                            state.pop();
                            break;
                        case SUP:
                            bldr.append("</sup>");
                            state.pop();
                            break;
                    }
                    return bldr.toString();
                default:
                    bldr.append(ch);
            }
        }
        
        while (!state.empty()) {
            switch (state.pop()) {
                case SUB:
                    bldr.append("</sub>");
                    break;
                case SUP:
                    bldr.append("</sup>");
                    break;
            }
        }
        
        return bldr.toString();
    }
}
