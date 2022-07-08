/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import dictionary.Alphabet;
import dictionary.Dictionary;
import model.GridPoint;
import model.GridUnit;


/**
 *
 * @author Steven Joseph UCF COP 3330
 */
public class Game {
    
    // Declare a new grid
    private final GridUnit[][] grid;
    private final Dictionary dictionary;
    
    // Instantiating a new grid
    public Game(Dictionary dictionary) {
        this.dictionary = dictionary;
        this.grid = new GridUnit[4][4];
        this.populateGrid();
    }
    
    // Returns a grid
    public GridUnit[][] getGrid() {
        return grid;
    }  
    
    // Returns the grid points
    public GridUnit getGridUnit(GridPoint point) {
        return grid[point.x][point.y];
    }

    // Populates a 3 x 3 grid with random letters
    public void populateGrid() {
        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 4; y++){
                grid[x][y] = new GridUnit(Alphabet.newRandom(), new GridPoint(x, y));
            }
        }
    }

    // Displays to ouput, the 3 x 3 grid
    public void displayGrid() {
            
        for(int x = 0; x < 4; x++){
            System.out.println("--------------------");
            for(int y = 0; y < 4; y++){
                System.out.print("| " + grid[x][y].getLetter() + " |");
            }
            System.out.println("\n");
        }
        System.out.println("--------------------");
    }

    /**
     * @return the dictionary
     */
    public Dictionary getDictionary() {
        return dictionary;
    }
    
}
