/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication2;

/**
 *
 * @author if412020
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class NGram {
	private static final int RANDOM_SEED = 1234;
    // like a die, generates a series of random numbers
    private static final Random ourGenerator = new Random(RANDOM_SEED);
    
	// chooser allows users to select a file by navigating through
	// directories
	private static JFileChooser ourChooser = new JFileChooser(System
			.getProperties().getProperty("user.dir"));

	/**
	 * Brings up chooser for user to select a file
	 * 
	 * @return Scanner for user selected file, null if file not found
	 */
	public static Scanner getScanner() {

		int retval = ourChooser.showOpenDialog(null);

		if (retval == JFileChooser.APPROVE_OPTION) {
			File f = ourChooser.getSelectedFile();
			Scanner s;
			try {
				s = new Scanner(f);
			} catch (FileNotFoundException e) {
				return null;
			}
			return s;
		}
		return null;
	}

    /**
     * Read a file into a string.
     *
     * @return returns a string containing all of the text in the
     * file
     */
    public String readFile (Scanner input)
    {
    		return input.useDelimiter("\\Z").next();
    }
    
    /**
     * Generates random text that is similar to the given reference text.
     *
     * @requires ngram > 0
     * @return returns string whose length is numLetters of randomly selected 
     *         characters based on picking representive characters that follow
     *         each ngram characters 
     */
    public String makeNGram (String referenceText, int ngram, int numLetters)
    {
        String predictor = getRandomSubstring(referenceText, ngram);
        String result = "";

        for (int k = 0; k < numLetters; k += 1)
        {
            // TODO: get all characters that immediately follow predictor
            // TODO: pick one character randomly and update predictor
        }

        return result;
    }

    /**
     * Choose a random substring of the given string.
     *
     * @requires subSize <= s.length()
     * @return random substring of s whose length is subSize
     */
    public String getRandomSubstring (String s, int subSize)
    {
        // TODO: choose random substring from s
        return s.substring(0, subSize);
    }

    /**
     * Find all characters that immediately follow toFind in the given string.
     *
     * @return a string containing each character that immediately
     *         follows toFind in s
     */
    public String getFollowingCharacters (String s, String toFind)
    {
        String result = "";
      
        // TODO: get each character following toFind in s and add to result

        return result;
    }


    	
	/**
	 * Main to be used for testing
	 */
	public static void main(String[] args) {
            HashMap<String,Integer> maploaded;
            try{
                FileInputStream fis = new FileInputStream("wordN1Lib.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                maploaded = (HashMap) ois.readObject();
                ois.close();
                fis.close();
                System.out.println(maploaded.size());
                System.out.println(maploaded.get("taut"));
            }
            catch(Exception e){
            
            }
	}

}