/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.LinkedHashMap;
import java.util.Map;
import model.WordResult;

/**
 *
 * @author Steven Joseph UCF COP 3330
 */
public class UserResult {
    
    private int totalScore;
    // Instantiating a linked hash map to fill with the word results
    private final Map<String, WordResult> wordToResultMap = 
            new LinkedHashMap<>(); 
    
    // Returns the total score
    public int getTotalScore() {
        return this.totalScore;
    }
    
    // Adds the current word to the linked hash map and the score of the current
    // word to the total score
    public void add(String word, WordResult result) {
        this.wordToResultMap.put(word, result);
        this.totalScore += result.getScore();
    }
    // Returns the current word
    public WordResult get(String word) {
        return this.wordToResultMap.get(word);
    }
    
    // Returns the linked hash map
    public Map<String, WordResult> all() {
        return this.wordToResultMap;
    }
    
    // Checks if the linked hash map contains the current word
    public Boolean exists(String word) {
        return this.wordToResultMap.containsKey(word);
    }
    
    // Returns the number of word results
    public int getWordCount() {
        return this.wordToResultMap.size();
    }
}
