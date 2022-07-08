/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import game.Game;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author Steven Joseph UCF COP 3330
 */
public class ScraggleUi {
    //top level container
    private JFrame frame;

    //menu components
    private JMenu file;
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem exit;
    private int response;
    private JMenuItem newGame;
    
    //Scraggle board
    private JPanel scragglePanel;
    private JButton[][] diceButtons;
    
    //enter found words
    private JPanel wordsPanel;
    private JScrollPane scrollPane;
    private JTextPane wordsArea;
    
    //time label
    private JPanel timePanel;
    private JLabel timeLabel;
    private JButton shakeDice;
    
    //enter current word
    private JPanel currentPanel;
    private JPanel currentWordPanel;
    private JLabel currentLabel;
    private JButton currentSubmit;
    
    //player's score
    private JLabel scoreLabel;
    private JPanel scorePanel;
    
    private Game game;
    
    //timer variables
    Timer timer;
    private static int minutes = 3;
    private static int seconds;
    
    int score;
    private ArrayList<String> foundWords;
    ResetGameListener resetGameListener;
    
    private final int GRID = 4;
    
    public ScraggleUi(Game inGame) {
        game = inGame;
        
        initObjects();
        initComponents(); 
    }
    
    private void initObjects() {
        foundWords = new ArrayList<String>();
        resetGameListener = new ResetGameListener();
    }
    
    private void initComponents() {
        //initialize the JFrame.
        frame = new JFrame("Scraggle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(660, 500));
        
        //initialize the JMenuBar and add to the JFrame.
        menuBar = new JMenuBar();
        createMenu();
        
        //initialize the JPane for the current word
        setupCurrentPanel();
        
        //initialize the JPane for the word entry
        setupWordPanel();
        
        //initialize the JPane for the Scraggle dice
        setupScragglePanel();
        
        //initialize the timer
        timer = new Timer(1000, new TimerListener());
        timer.start();
        
        //add everything to the JFrame
        frame.add(scragglePanel, BorderLayout.WEST); //LINE_START
        frame.add(wordsPanel, BorderLayout.CENTER); //CENTER
        frame.add(currentPanel, BorderLayout.SOUTH); //PAGE_END
        frame.setVisible(true);
    }

    //inner classes for event handlers 
    private class ResetGameListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            score = 0;
            game.populateGrid();
            
            frame.remove(scragglePanel);
            scragglePanel.removeAll();
            setupScragglePanel();
            scragglePanel.revalidate();
            scragglePanel.repaint();
            frame.add(scragglePanel, BorderLayout.WEST);
            
            wordsArea.setText("");
            scoreLabel.setText("0");
            timeLabel.setText("3:00");
            currentLabel.setText("");

            foundWords.removeAll(foundWords);
            
            timer.stop();
            minutes = 3;
            seconds = 0;
            timer.start();
        }
    }
    
    private class TimerListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(seconds == 0 && minutes == 0)
            {            
                JOptionPane.showMessageDialog(frame, "Time's up! Game over!");
                timer.stop();
            }
            else
            {
                if(seconds == 0)
                {
                    seconds = 59;
                    minutes--;
                }
                else
                {
                    seconds--;
                }
            }

            if(seconds < 10)
            {
                String strSeconds = "0" + String.valueOf(seconds);
                timeLabel.setText(String.valueOf(minutes) + ":" + strSeconds);
            }
            else
            {
                timeLabel.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
            }
        }
    }
    
    private class ExitListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            response = JOptionPane.showConfirmDialog(frame, "Confirm to exit scraggle", "Exit?", JOptionPane.YES_NO_OPTION);
            
            if(response == JOptionPane.YES_OPTION){
                System.exit(0);
            }
        }
    }
    
    private class SubmitListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            //compare submitted word to dictionary
            int wordScore = game.getDictionary().search(currentLabel.getText().toLowerCase());
            
            //check if the word is used
            if(foundWords.contains(currentLabel.getText().toLowerCase()))
            {
                JOptionPane.showMessageDialog(frame, "Word already found");
            }
            else
            {
                if(wordScore > 0)
                {
                    foundWords.add(currentLabel.getText().toLowerCase());
                    score += wordScore;
                    wordsArea.setText(wordsArea.getText() + currentLabel.getText() + "\n");
                    wordsArea.setCaretPosition(wordsArea.getDocument().getLength());
                    scoreLabel.setText(String.valueOf(score));
                    
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "Not a valid word");
                }
                currentLabel.setText("");
                
                for(int row = 0; row < 4; row++)
                {
                    for(int col = 0; col < 4; col++)
                    {
                        diceButtons[row][col].setEnabled(true);
                    }
                }
            }
        }
    }
    
    private class TileListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {

            // based on the row and col of the JButton clicked
            // enable one row up, one row down, one col left, one col right
            if(ae.getSource() instanceof JButton)
            {
                JButton button = (JButton)ae.getSource();
                
                int row = (int)button.getClientProperty("row");
                int col = (int)button.getClientProperty("col");

                // disables all the JButtons
                for(int i = 0; i < GRID; i++)
                {
                    for(int j = 0; j < GRID; j++)
                    {
                        diceButtons[i][j].setEnabled(false);
                    }
                }
                
                // enable JButtons based on logic
                // cannot -1 for row
                if(row == 0 && col == 0) //top left corner
                {    
                    // enable surrounding
                    // row + 1 and current col
                    // position 0/0
                    diceButtons[row + 1][col].setEnabled(true);
                    diceButtons[row + 1][col + 1].setEnabled(true);
                    diceButtons[row][col + 1].setEnabled(true);
                }
                else if(row == 0 && col == 3) // top right corner
                {    
                    // enable surrounding
                    // row + 1 and current col
                    // position 0/0
                    diceButtons[row][col - 1].setEnabled(true);
                    diceButtons[row + 1][col - 1].setEnabled(true);
                    diceButtons[row + 1][col].setEnabled(true);
                }
                else if(row == 0 && col > 0) //top row
                {
                    // clicked tile row
                    diceButtons[row][col - 1].setEnabled(true);
                    diceButtons[row][col + 1].setEnabled(true);
                    
                    // row below clicked tile
                    diceButtons[row + 1][col - 1].setEnabled(true);
                    diceButtons[row + 1][col + 1].setEnabled(true);
                    diceButtons[row + 1][col].setEnabled(true);
                }
                
                else if(row == 3 && col == 0) //bottom left corner
                {    
                    diceButtons[row - 1][col].setEnabled(true);
                    diceButtons[row - 1][col + 1].setEnabled(true);
                    diceButtons[row][col + 1].setEnabled(true);
                }
               
                else if(row == 3 && col == 3) // bottom right corner
                {    
                    // enable surrounding
                    // row + 1 and current col
                    // position 0/0
                    diceButtons[row -1][col].setEnabled(true);
                    diceButtons[row - 1][col - 1].setEnabled(true);
                    diceButtons[row][col - 1].setEnabled(true);
                }  
                else if(row == 3 && col > 0) //bottom row
                {
                    // row above clicked tile
                    diceButtons[row - 1][col - 1].setEnabled(true);
                    diceButtons[row - 1][col].setEnabled(true);
                    diceButtons[row - 1][col + 1].setEnabled(true);
                    
                    // clicked tile row
                    diceButtons[row][col - 1].setEnabled(true);
                    diceButtons[row][col + 1].setEnabled(true);
                } 
                
                else if(row > 0 && col == 0) //left column
                {
                    // row above clicked tile
                    diceButtons[row - 1][col].setEnabled(true);
                    diceButtons[row - 1][col + 1].setEnabled(true);
                    
                    // clicked tile row
                    diceButtons[row][col + 1].setEnabled(true);
                    
                    // row below clicked tile
                    diceButtons[row + 1][col].setEnabled(true);
                    diceButtons[row + 1][col + 1].setEnabled(true);
                }
                
                else if(row > 0 && col == 3) //right column
                {
                    // row above clicked tile
                    diceButtons[row - 1][col - 1].setEnabled(true);
                    diceButtons[row - 1][col].setEnabled(true);
                    
                    // clicked tile row
                    diceButtons[row][col - 1].setEnabled(true);
                    
                    // row below clicked tile
                    diceButtons[row + 1][col - 1].setEnabled(true);
                    diceButtons[row + 1][col].setEnabled(true);
                }
                
                // this logic only works for row 1, 2 and col 1, 2
                else if(row > 0 && col > 0)
                {
                    // row above clicked tile
                    diceButtons[row - 1][col - 1].setEnabled(true);
                    diceButtons[row - 1][col].setEnabled(true);
                    diceButtons[row - 1][col + 1].setEnabled(true);

                    // clicked tile row
                    diceButtons[row][col - 1].setEnabled(true);
                    diceButtons[row][col + 1].setEnabled(true);
                    
                    // row below clicked tile
                    diceButtons[row + 1][col - 1].setEnabled(true);
                    diceButtons[row + 1][col].setEnabled(true);
                    diceButtons[row + 1][col + 1].setEnabled(true);
                }

            }

        }        
    } 
    
    private class LetterListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            if(ae.getSource() instanceof JButton)
            {
                JButton button = (JButton)ae.getSource();
                String letter = (String)button.getClientProperty("letter");
                currentLabel.setText(currentLabel.getText() + letter);
            }
        }
    }

    private void setupCurrentPanel() {
        currentPanel = new JPanel();
        currentPanel.setSize(new Dimension(100, 25));
        currentPanel.setBorder(BorderFactory.createTitledBorder("Current Word"));
        
        currentLabel = new JLabel();
        currentLabel.setBorder(BorderFactory.createTitledBorder("Current Word"));
        currentLabel.setMinimumSize(new Dimension(300, 50));
        currentLabel.setPreferredSize(new Dimension(300,50));
        currentLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        currentSubmit = new JButton();
        currentSubmit.addActionListener(new SubmitListener());
        currentSubmit.setMinimumSize(new Dimension(200, 50));
        currentSubmit.setPreferredSize(new Dimension(200, 50));
        currentSubmit.setText("Submit word");
        
        scoreLabel = new JLabel("0");
        scoreLabel.setBorder(BorderFactory.createTitledBorder("Score"));
        scoreLabel.setMinimumSize(new Dimension(100, 50));
        scoreLabel.setPreferredSize(new Dimension(100,50));
        
        currentPanel.add(currentLabel);
        currentPanel.add(currentSubmit);
        currentPanel.add(scoreLabel);  

    }

    private void setupWordPanel() {
        wordsPanel = new JPanel();
        wordsPanel.setSize(new Dimension(100, 25));
        wordsPanel.setBorder(BorderFactory.createTitledBorder("Enter words found"));
        
        wordsArea = new JTextPane();
        wordsArea.setMinimumSize(new Dimension(250, 120));
        wordsArea.setPreferredSize(new Dimension(250, 120));
        
        scrollPane = new JScrollPane(wordsArea);
        scrollPane.setPreferredSize(new Dimension(250, 120));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        timePanel = new JPanel();
        timePanel.setMinimumSize(new Dimension(250, 100));
        timePanel.setPreferredSize(new Dimension(250, 100));
        timePanel.setBorder(BorderFactory.createTitledBorder("Time Left"));
        timeLabel = new JLabel("3:00", JLabel.CENTER);
        timeLabel.setFont(new Font("Serif", Font.PLAIN, 60));
        
        shakeDice = new JButton();
        shakeDice.addActionListener(resetGameListener);
        shakeDice.setMinimumSize(new Dimension(250, 100));
        shakeDice.setPreferredSize(new Dimension(250, 100));
        shakeDice.setText("Shake Dice");
        
        wordsPanel.add(scrollPane);
        wordsPanel.add(timePanel);
        timePanel.add(timeLabel);
        wordsPanel.add(shakeDice);
        
    }

    private void setupScragglePanel() {
        scragglePanel = new JPanel();
        scragglePanel.setLayout(new GridLayout(4, 4));
        scragglePanel.setMinimumSize(new Dimension(400, 400));
        scragglePanel.setPreferredSize(new Dimension(400, 400));
        scragglePanel.setBorder(BorderFactory.createTitledBorder("Scraggle Board"));
        
        diceButtons = new JButton[GRID][GRID];
        
        for(int row = 0; row < GRID; row++) {
            for(int col = 0; col < GRID; col++) {
                URL imgPath = getClass().getResource(game.getGrid()[row][col].ImgPath());
                ImageIcon icon = new ImageIcon(imgPath);
                icon = imageResize(icon);
                
                diceButtons[row][col] = new JButton(icon);
                diceButtons[row][col].putClientProperty("row", row);
                diceButtons[row][col].putClientProperty("col", col);
                diceButtons[row][col].putClientProperty("letter", game.getGrid()[row][col].getLetter());
                
                TileListener tileListener = new TileListener();
                LetterListener letterListener = new LetterListener();
                diceButtons[row][col].addActionListener(tileListener);
                diceButtons[row][col].addActionListener(letterListener);
                
                scragglePanel.add(diceButtons[row][col]);
            }
        }
    }
    
    private ImageIcon imageResize(ImageIcon icon)
    {
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(95, 85, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);
        return icon;
    }   

    private void createMenu() {
        menuBar = new JMenuBar();
        gameMenu = new JMenu("File");
        
        gameMenu.setText("Scraggle");
        gameMenu.setMnemonic('S');
        
        newGame = new JMenuItem("New Game");
        newGame.setMnemonic('N');
        exit = new JMenuItem("Exit");
        exit.setMnemonic('E');
        
        newGame.addActionListener(resetGameListener);
        exit.addActionListener(new ExitListener());
        gameMenu.add(exit);
        gameMenu.add(newGame);
        menuBar.add(gameMenu);
        
        frame.setJMenuBar(menuBar);
    }
}