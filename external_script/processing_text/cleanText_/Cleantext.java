/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cleantext;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.regex.*;
/**
 *
 * @author if412020
 */
public class Cleantext {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        String regexlink= "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
//        String regexakunuser="^@\\w+|\\s@\\w+";
        String regexakunuser="@\\w+|\\s@\\w+";
        BufferedReader br = new BufferedReader(new FileReader(new File("TranslateEnglish.csv")));
         try{
            String verify, putData;
            File file = new File("TextNormal.csv");
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            br.readLine();//skip the label part
            bw.write("tweet,label");
            bw.newLine();
            while( (verify=br.readLine()) != null ){ //***editted
                String label;
                       //**deleted**verify = br.readLine();**
                if(verify != null){ //***edited
                    label=verify.charAt(verify.length()-1)+"";//agar menjadi string
                    verify=verify.substring(0, verify.length()-3);
                    putData = verify.replaceAll(regexlink, "tautan");
                    putData = putData.replaceAll(regexakunuser, " akunuser");
                    if(putData.substring(0, 1).equals(" ")){
                    putData=putData.substring(1);
                    }
                    putData = putData.replaceAll("@", "di");//@ diubah menjadi di
                    putData = cleanSymbol(putData);
                    bw.write(putData.substring(1)+","+label);
                    bw.newLine();
                    bw.flush();
                }
            }
            br.close();


        }catch(IOException e){
        }
    }
    
    static String cleanSymbol(String kalimat){
        String regexsymbol="[^0-9A-Za-z]";
        String builder="";
        String temp;
        String [] arr =kalimat.split(" ");
        for ( String kata : arr) {
           temp=kata;
           if(kata.equals("") || kata.equals(" ")){continue;}
           kata = kata.replaceAll(regexsymbol, "");
           if(kata.equals("")){
               builder+=" simbol";
           }
           else{
               builder+=" "+temp;
           }
          }
        return builder;
    }

}
