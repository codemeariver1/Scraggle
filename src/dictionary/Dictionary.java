/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author Steven Joseph UCF COP 3330
 */

//used to initialize a trie, instantiate it with a word or prefix, and using 
//such to search through it
public class Dictionary {
    
    private static final String WORDS_FILE =  "words.txt";
    Trie trie;
    
    public Dictionary() {
        
        Scanner inputFile;
        String word;
        
        try{
            
            trie = new Trie(); // initialize new trie.
            URL url = getClass().getResource(WORDS_FILE);
            File file = new File(url.toURI());
            inputFile = new Scanner(file);
            
            // convert letters to lowercase, and load the word into the trie
            while(inputFile.hasNext()) { 
                word = inputFile.next();
                word = word.trim().toLowerCase();
                trie.insert(word);
            }
            
            System.out.println("Loaded all words into the trie");
            inputFile.close();
            
        //used to catch exceptions and output error message.   
        }catch(IOException | URISyntaxException e) { 
            
            System.out.println("Error loading words into the trie");
            throw new RuntimeException(e);
        }
    }
    
    
    //returns the score of the word if it's valid, 0 otherwise.
    public int search(String word) {
        return this.trie.search(word);
    }
    
    //return true if the word is a prefix of some other word, false otherwise.
    public boolean prefix(String word) {
        return this.trie.prefix(word);
    }
    
}
