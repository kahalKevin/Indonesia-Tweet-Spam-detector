/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gyosh.kemangi.GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author if412020
 */
public class FiturProcessor {
    public static void main(String[] args) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(new File("fitur.txt")));
        String verify;
        List fiturList=new ArrayList();
        while( (verify=br.readLine()) != null ){
            String [] arr =verify.split(" ");
            String sentence=arr[0].substring(2, arr[0].length()-2);
            fiturList.add(sentence);
        }
        br.close();
        FileOutputStream fout = new FileOutputStream("fiturList.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(fiturList);
    }
}
