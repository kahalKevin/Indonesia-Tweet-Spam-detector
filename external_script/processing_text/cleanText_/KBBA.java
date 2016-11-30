/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cleantext;

import static cleantext.ngramgen.GenerateCsvFile;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author if412020
 */
public class KBBA {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        HashMap<String,String> hmapN1=new HashMap<>();
            try{
            Scanner input;
            File file = new File("kbba.txt");            
            input = new Scanner(file);
            
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String [] arr =line.split("\t");
                int length=arr.length;
                String [] arr2 =arr[length-1].split(" ");
                length=arr2.length;
                hmapN1.put(arr[0], arr2[length-1]);
            }
            input.close();
            //disini dia menerjemahkan alay nya
            // diambil dari hmapN1            
            //iterate through file
            BufferedReader br = new BufferedReader(new FileReader(new File("inputKBBA.csv")));
            String verify;
            file = new File("afterKBBA.csv");
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            br.readLine();//skip the label part
            bw.write("tweet,label");
            bw.newLine();
            while( (verify=br.readLine()) != null ){
                String label;
                if(verify != null){
                    label=verify.charAt(verify.length()-1)+"";//agar menjadi string
                    verify=verify.substring(0, verify.length()-2);
                    String putData="";
                    String [] arr =verify.split(" ");
                    for ( String kata : arr) {
                    if(hmapN1.containsKey(kata)){
                        putData+=hmapN1.get(kata)+" ";
                    }
                    else{
                        putData+=kata+" ";
                    }                    
                    }
                    bw.write(putData+","+label);
                    bw.newLine();
                    bw.flush();
                }
            }
            br.close();
        } catch (FileNotFoundException ex) {
        }
    }
        
}
