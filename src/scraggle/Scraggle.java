/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scraggle;

import dictionary.Dictionary;
import game.Game;
import userInterface.ScraggleUi;

/**
 *
 * @author Steven Joseph UCF COP 3330
 */
public class Scraggle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Dictionary dictionary = new Dictionary();
        Game game = new Game(dictionary);
        ScraggleUi ui = new ScraggleUi(game);
        
        game.displayGrid();
        
        /*
        System.out.println("Word search...");
        System.out.println(dictionary.search("mask"));
        System.out.println(dictionary.search("silhouette"));
        System.out.println(dictionary.search("horse"));
        
        System.out.println("Prefix search...");
        System.out.println(dictionary.prefix("silhou"));
        System.out.println(dictionary.prefix("bir"));
        */
    }
    
}
