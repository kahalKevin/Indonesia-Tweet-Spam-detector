/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cleantext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author if412020
 */
public class StringSimilarity {
public static int levenshteinDistance (String lhs, String rhs) {
    int len0 = lhs.length() + 1;
    int len1 = rhs.length() + 1;

    // the array of distances                                                       
    int[] cost = new int[len0];
    int[] newcost = new int[len0];

    // initial cost of skipping prefix in String s0                                 
    for (int i = 0; i < len0; i++) cost[i] = i;                                     
                                                                                    
    // dynamically computing the array of distances                                  
                                                                                    
    // transformation cost for each letter in s1                                    
    for (int j = 1; j < len1; j++) {                                                
        // initial cost of skipping prefix in String s1                             
        newcost[0] = j;                                                             
                                                                                    
        // transformation cost for each letter in s0                                
        for(int i = 1; i < len0; i++) {                                             
            // matching current letters in both strings                             
            int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;             
                                                                                    
            // computing cost for each transformation                               
            int cost_replace = cost[i - 1] + match;                                 
            int cost_insert  = cost[i] + 1;                                         
            int cost_delete  = newcost[i - 1] + 1;                                  

            // keep minimum cost
            newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
        }

        // swap cost/newcost arrays
        int[] swap = cost; cost = newcost; newcost = swap;
    }

    // the distance is the cost for transforming all letters in both strings
    return cost[len0 - 1];
}

public static void main(String[] args) throws FileNotFoundException {
        List<String> listKBBI=new ArrayList<>();
        List<String> listKBBA=new ArrayList<>();
        List<String> listWord=new ArrayList<>();
            try{
            Scanner input = new Scanner(System.in);
            File file = new File("kbba.txt");

            input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                String [] arr =line.split("\t");
                int length=arr.length;
                String [] arr2 =arr[length-1].split(" ");
                length=arr2.length;
                listKBBA.add(arr[0]);
                listWord.add(arr2[length-1]);
            }
            input.close();
            
            file = new File("KBBI.txt");
            input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String [] arr =line.split(",");
                listKBBI.add(arr[1].substring(2, arr[1].length()-1));                
            }
            input.close();
            String test="simbol";//silakan
            if(listKBBI.contains(test)){
                System.out.println(test);
            }
            else{
            int similarity=10, index=0;
            for(int i=0;i<listKBBA.size();i++){
                int temp=levenshteinDistance(test,listKBBA.get(i));
                if(temp<similarity){
                    similarity=temp;
                    index=i;
                }
            }
            //System.out.println(similarity);
            System.out.println(listWord.get(index));            
            }
        } catch (FileNotFoundException ex) {
        }
    }
}
