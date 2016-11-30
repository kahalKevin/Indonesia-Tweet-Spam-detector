/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
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
        //List<HashMap<String,Integer>> listOfMapN1occurence=new ArrayList<>();
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
            for ( String kata : arr) {
                if(!hmapN1.containsKey(kata)){//filling word collection for N=1
                    if(!kata.isEmpty())
                    {hmapN1.put(kata, 0);}
                }
                /*if(length-1!=i){//kecuali kata terakhir, untuk n=2
                    String temp=arr[i]+"-"+arr[i+1];
                if(!hmapN2.containsKey(temp)){//filling word collection for N=2
                    hmapN2.put(temp, 0);
                    }
                }*/
                i++;//kata ke-i pada kalimat
                }
            }
            input.close();
            System.out.println(hmapN1.size());
//            System.out.println(hmapN2.size());
            
            //hitung kemunculan pada n=1
            file = new File("CleanTweet.csv");
            input = new Scanner(file);
            input.nextLine();     //skip line 1
            while (input.hasNextLine()) {
                String line = input.nextLine();
                line=line.substring(0, line.length()-2);
                String [] arr =line.split(" ");                
                for(Map.Entry<String,Integer> entry : hmapN1.entrySet()){
                    //ubah array jadi list, utk akses fungsi contain
                    if(Arrays.asList(arr).contains(entry.getKey())){
                    //jika key map terdapat pada list kata pd kalimat itu, tambahkan hmap value
                        hmapN1.put(entry.getKey(), hmapN1.get(entry.getKey())+1);                        
                    }
                }
            }
            
            //hitung kemunculan pada n=2
            /*file = new File("CleanTweet.csv");
            input = new Scanner(file);
            input.nextLine();     //skip line 1
            while (input.hasNextLine()) {
                String line = input.nextLine();
                line=line.substring(0, line.length()-2);
                String [] arr =line.split(" ");
                List arraytemp = new ArrayList();
                int length=arr.length;
                for(int i=0;i<=length-2;i++){
                    arr[i]=arr[i]+"-"+arr[i+1];
                    arraytemp.add(arr[i]);
                }
                for(Map.Entry<String,Integer> entry : hmapN2.entrySet()){
                    //ubah array jadi list, utk akses fungsi contain
                    if(arraytemp.contains(entry.getKey())){
                    //jika key map terdapat pada list kata pd kalimat itu, tambahkan hmap value
                        hmapN2.put(entry.getKey(), hmapN2.get(entry.getKey())+1);
                    }
                }
            }*/
            
            //menghapus dari map n=1 dengan nilai kemunculan kecil
            Iterator<Map.Entry<String,Integer>> iter = hmapN1.entrySet().iterator();
                while (iter.hasNext()) {
                Map.Entry<String,Integer> entry = iter.next();
                if(entry.getValue()<2){
                iter.remove();
                }
                else{
                   hmapN1.put(entry.getKey(),0);
                }
            }
                
            //menghapus dari map n=2 dengan nilai kemunculan kecil
            /*iter = hmapN2.entrySet().iterator();
                while (iter.hasNext()) {
                Map.Entry<String,Integer> entry = iter.next();
                if(entry.getValue()<2){
                iter.remove();
                }
                else{
                   hmapN2.put(entry.getKey(),0);
                }
            }*/
                       
                
            List fiturChi2=new ArrayList();
            FileInputStream fis = new FileInputStream("fiturList.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            fiturChi2 = (List) ois.readObject();
            ois.close();
            fis.close();                
                
            //menghapus dari map n=1 yang tidak ada pada chi2 selection
            iter = hmapN1.entrySet().iterator();
                while (iter.hasNext()) {
                Map.Entry<String,Integer> entry = iter.next();
                if(!fiturChi2.contains(entry.getKey())){
                iter.remove();
                }
                else{
                   hmapN1.put(entry.getKey(),0);
                }
            }
            
            System.out.println("final "+hmapN1.size());
                    
            //untuk n=1
            FileOutputStream fout = new FileOutputStream("WordN1Chi2.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(hmapN1);
            System.out.println(hmapN1.size());

            /*fout = new FileOutputStream("wordN2Lib.ser");
            oos = new ObjectOutputStream(fout);
            oos.writeObject(hmapN2);
            System.out.println(hmapN2.size());*/
            
            
            //jumlah awal 60647
            //dibawah 2 jadi 22205
            //dibawah 3 jadi 14609
            //dibawah 5 jadi 9159
            //jumlah n=2 272764
/*
            file = new File("CleanTweet.csv");
            input = new Scanner(file);
            input.nextLine();
            int limited=0;//number of tweet want to be modeled from the file
            boolean fileCreated=false;
                HashMap<String,Integer> tempmapN1;*/
  /*              HashMap<String,Integer> tempmapN2=(HashMap<String,Integer>) hmapN2.clone();
                HashMap<String,Integer> tempmapN1E=(HashMap<String,Integer>) hmapN1.clone();
                HashMap<String,Integer> tempmapN2E=(HashMap<String,Integer>) hmapN2.clone();
*/
/*            while (input.hasNextLine()) {
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
                    if(tempmapN2.containsKey(temp)){//if word is in map N=2
                        tempmapN2.put(temp, tempmapN2.get(temp)+1);//count the word occurence
                        tempmapN2E.put(temp, 1);//check word existence
                       }
                    }*/
              /*  i++;
                }
                listOfMapN1occurence.add(new HashMap<>(tempmapN1));*/
/*                listOfMapN1existence.add(tempmapN1E);
                listOfMapN2occurence.add(tempmapN2);
                listOfMapN2existence.add(tempmapN2E);*/
/*                tempmapN1.clear();
                if(limited>10 && !fileCreated){//create the file here
                    GenerateCsvFile(listOfMapN1occurence,1,"o");
                    fileCreated=true;
                    listOfMapN1occurence.clear();
                    limited=0;
                }
                if((limited>1000 && fileCreated) || (!input.hasNextLine()&& fileCreated)){//append to fie
                    AppendCsvFile(listOfMapN1occurence,1,"o");
                    listOfMapN1occurence.clear();
                    limited=0;
                    System.out.println(rowWritten);
                }
            }
            input.close();
*/
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
