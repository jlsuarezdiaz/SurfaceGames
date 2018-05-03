/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author jlsuarezdiaz
 */
public class Math {
    /**
     * Cálculo del valor correcto del resto (algunos lenguajes devuelven restos negativos para números negativos).
     * @param a Dividendo
     * @param b Divisor
     * @return  Resto
     */
    public static int mod(int a, int b){
        return (a % b + b) % b;
    }
}
