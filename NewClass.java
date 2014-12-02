/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jb3d;

/**
 *
 * @author mfade_000
 */
public class NewClass {
    public static void main(String[] args){
        int x = 100000;
        for(int i = 1; i <= x; i++){
            for(int j = 0; j < x - i; j++) System.out.print(" ");
            for(int k = 0; k < 2*i - 1; k++) System.out.print("o");
            System.out.println();
        }
    }
}
