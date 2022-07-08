/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.util.Random;

/**
 *
 * @author Steven Joseph UCF COP 3330
 */

//instantiating the letters of the alphabet and it's values into the program's 
// dictionary
public enum Alphabet {
    // constants
    A(1), B(3), C(3), D(2), E(1), F(4), G(2), H(4), I(1),  J(8), K(5), L(1), 
    M(3), N(1), O(1), P(3), Q(10), R(1), S(1), T(1), U(1), V(4), W(4), X(8), 
    Y(4), Z(10);
      
    private final int score;
    private static final int LETTERS = 26;
      
    // custom constructor, no need for private, already implemented from enum
    Alphabet(int letterValue) {
        score = letterValue;
    }
    
    //convert word to lowercase
    public String get() {
        return this.toString().toLowerCase();
    }
    
    //get word score
    public int getScore() {
        return this.score;
    }
    
    public static Alphabet newRandom() {
        Random random = new Random();
        
        int value = random.nextInt(LETTERS);
        
        Alphabet alphabet = Alphabet.values()[value];
        
        return alphabet;
    }
}
