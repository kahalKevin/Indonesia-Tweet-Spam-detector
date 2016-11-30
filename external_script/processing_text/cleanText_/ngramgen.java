/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cleantext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
/**
 *
 * @author if412020
 */
public class ngramgen {
     static List<String> listLabel=new ArrayList<String>();
     static int rowWritten=0;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Starting...");
        HashMap<String,Integer> hmapN1=new HashMap<>();
        //HashMap<String,Integer> hmapN2=new HashMap<>();
        List<HashMap<String,Integer>> listOfMapN1occurence=new ArrayList<>();
        //List<HashMap<String,Integer>> listOfMapN1existence=new ArrayList<>(); 
        //List<HashMap<String,Integer>> listOfMapN2occurence=new ArrayList<>(); 
        //List<HashMap<String,Integer>> listOfMapN2existence=new ArrayList<>(); 
        
        try {
            Scanner input;
            File file = new File("CleanTweet.csv");

            input = new Scanner(file);
            input.nextLine();
            while (input.hasNextLine()) {
                String line = input.nextLine();
                listLabel.add(line.charAt(line.length()-1)+"");
                line=line.substring(0, line.length()-2);
                String [] arr =line.split(" ");
                int i=0,length=arr.length;
            /*for ( String kata : arr) {
                if(!hmapN1.containsKey(kata)){//filling word collection for N=1
                    if(!kata.isEmpty())
                    {hmapN1.put(kata, 0);}
                }*/
                /*if(length-1!=i){//kecuali kata terakhir, untuk n=2
                    String temp=arr[i]+"-"+arr[i+1];
                if(!hmapN2.containsKey(temp)){//filling word collection for N=2
                    hmapN2.put(temp, 0);
                    }
                }*/
                /*i++;//kata ke-i pada kalimat
                }*/
            }
            input.close();
            
            
                FileInputStream fis = new FileInputStream("WordN1Chi2.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                hmapN1 = (HashMap) ois.readObject();
                ois.close();
                fis.close();

            System.out.println(hmapN1.size());
            //System.out.println(hmapN2.size());
                                
            file = new File("CleanTweet.csv");
            input = new Scanner(file);
            input.nextLine();//skip label part
            int limited=0;//number of tweet want to be modeled from the file
            boolean fileCreated=false;
                HashMap<String,Integer> tempmapN1;
          //      HashMap<String,Integer> tempmapN1E;
          //  HashMap<String,Integer> tempmapN2;
          //  HashMap<String,Integer> tempmapN2E;

//                int batas=0;
            while (input.hasNextLine() /*&& batas<10*/) {
//                batas++;
//                System.out.println(batas);
                tempmapN1=(HashMap<String,Integer>) hmapN1.clone();
                limited++;
                String line = input.nextLine();
                line=line.substring(0, line.length()-2);
                String [] arr =line.split(" ");
                int i=0,length=arr.length;
                for ( String kata : arr) {
                    if(tempmapN1.containsKey(kata)){//if word is in map N=1
                        tempmapN1.put(kata, tempmapN1.get(kata)+1);//count the word occurence
                        //tempmapN1E.put(kata, 1); // check word existence
                    }
                    /*if(length-1!=i){
                        String temp=arr[i]+"-"+arr[i+1];
                    if(tempmapN2E.containsKey(temp)){//if word is in map N=2
                    //    tempmapN2.put(temp, tempmapN2.get(temp)+1);//count the word occurence
                        tempmapN2E.put(temp, 1);//check word existence
                       }
                    }*/
                i++;
                }
                listOfMapN1occurence.add(new HashMap<>(tempmapN1));
/*                listOfMapN1existence.add(tempmapN1E);
                listOfMapN2occurence.add(tempmapN2);
                listOfMapN2existence.add(tempmapN2E);*/
                tempmapN1.clear();
                if(limited>10 && !fileCreated){//create the file here
                    GenerateCsvFile(listOfMapN1occurence,1,"o");
                    fileCreated=true;
                    listOfMapN1occurence.clear();
                    limited=0;
                }
                if((limited>100 && fileCreated) || (!input.hasNextLine()&& fileCreated)){//append to fie
                    AppendCsvFile(listOfMapN1occurence,1,"o");
                    listOfMapN1occurence.clear();
                    limited=0;
                    System.out.println(rowWritten);
                }
            }
            input.close();

      /* Display content using Iterator*/
    /*  Set set = hmapN2.entrySet();
      Iterator iterator = set.iterator();
      while(iterator.hasNext()) {
         Map.Entry mentry = (Map.Entry)iterator.next();
         System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
         System.out.println(mentry.getValue());
      }*/
            System.out.println("OK");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    //N -> value of n 1 or 2  , mode -> value of mode e or o for existence and occurence
    static void GenerateCsvFile(List<HashMap<String,Integer>> listword,int N, String mode){
        try
	{
	    FileWriter writer = new FileWriter("ngram"+N+""+mode+".csv");
            for(Map.Entry<String,Integer> entry : listword.get(0).entrySet()){
                if(!entry.getKey().isEmpty()){
                    writer.append(entry.getKey());
                    writer.append(',');
                    writer.flush();//to prevent too much data get buffered
                }
            }
            writer.append("label");
	    writer.append('\n');

            for(int i=0;i<listword.size();i++){
            for(Map.Entry<String,Integer> entry : listword.get(i).entrySet()){
                writer.append(entry.getValue()+"");
                writer.append(',');
                writer.flush();//to prevent too much data get buffered
            }
            writer.append(listLabel.get(i));
	    writer.append('\n');
            rowWritten++;//count the row that written to file
            writer.flush();//to prevent too much data get buffered
            }
            writer.flush();
	    writer.close();
	}
	catch(IOException e)
	{
            e.printStackTrace();
	}
    }
    
    static void AppendCsvFile(List<HashMap<String,Integer>> listword,int N, String mode){
        try
	{
	    FileWriter writer = new FileWriter("ngram"+N+""+mode+".csv",true);
            for(int i=0;i<listword.size();i++){
            for(Map.Entry<String,Integer> entry : listword.get(i).entrySet()){
                writer.append(entry.getValue()+"");
                writer.append(',');
                writer.flush();//to prevent too much data get buffered
            }
            writer.append(listLabel.get(rowWritten));
            rowWritten++;
	    writer.append('\n');
            writer.flush();//to prevent too much data get buffered
            }
	    writer.flush();
	    writer.close();
	}
	catch(IOException e)
	{
            e.printStackTrace();
	}
    }    
}
