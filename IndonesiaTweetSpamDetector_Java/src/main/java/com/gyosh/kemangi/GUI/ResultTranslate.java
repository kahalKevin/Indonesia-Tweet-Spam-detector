/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gyosh.kemangi.GUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author if412020
 */
public class ResultTranslate {
    public ResultTranslate() throws FileNotFoundException{
    mDict.load("en_to_id.dic");
    }
    
private class Range {
		public Range(int first, int last) {
			this.first = first;
			this.last = last;
		}

		public int first;
		public int last;
	}

	private ArrayList<Range> mResult = new ArrayList<Range>();
	private dictionary mDict = new dictionary();

public Object getItem(int position) {
			for (int i = 0; i < mResult.size(); i++) {
				Range range = mResult.get(i);
				int count = range.last - range.first + 1;
				if (position < count) {
					return mDict.get(range.first + position);
				}
				position -= count;
			}
			return null;
		}        
        
           public String translate(String s) {
		StringTokenizer tokens = new StringTokenizer(s, " ");
		ArrayList<String> used = new ArrayList<String>();
		mResult.clear();
		while (tokens.hasMoreTokens()) {
			String word = tokens.nextToken().toLowerCase();
			if (word.length() > 0 && !used.contains(word)) {
				used.add(word);
				int first = mDict.searchFirst(word);
				if (first != -1) {
					int last = mDict.searchLast(word);
					mResult.add(new Range(first, last));
				} else {/*
					first = mDict.searchNearest(word);
					if (first != -1) {
						mResult.add(new Range(first, first));
					}*/
				}
			}
		}
		if (used.size() > 1) {
			for (int i = 0; i < mResult.size(); i++) {
				Range range = mResult.get(i);
				range.last = range.first;
			}
		}
                final dictionary.Entry e = (dictionary.Entry) getItem(0);
                int i=0;
                String [] arr =e.val.split(" ");
                for ( String kata : arr) {
                    if(kata.contains("ks") || kata.contains("kk") || kata.contains("kkt") || 
                    kata.contains("kb") || isInteger(kata)||kata.contains("kd") ||
                    kata.contains("Sl") || kata.contains("Inf.:")){
                        i++;
                    }
                    else{
                        break;
                    }
                }
                
                if(arr[i].substring(arr[i].length()-1).equals(".")||
                arr[i].substring(arr[i].length()-1).equals(",")){
                    arr[i]=arr[i].substring(0, arr[i].length()-1);
                }
                return arr[i];
	}
        
        public static void main(String[] args) throws FileNotFoundException{
            ResultTranslate terjemah=new ResultTranslate();
            try{
            BufferedReader br = new BufferedReader(new FileReader(new File("afterKBBA.csv")));
            String verify;
            File file = new File("TranslateEnglish.csv");
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
                    verify=verify.substring(0, verify.length()-3);
                    String putData="";
                    String [] arr =verify.split(" ");
                    for ( String kata : arr) {
                    try{
                        putData+=terjemah.translate(kata)+" ";
                    }
                    catch(Exception e1){
                        putData+=kata+" ";
                    }
                    }
                    bw.write(putData+","+label);
                    bw.newLine();
                    bw.flush();
                }
            }
            br.close();
            }
            catch(Exception e){
                System.out.println(e);
            }
        }

public static boolean isInteger(String s) {
    try { 
        Integer.parseInt(s); 
    } catch(NumberFormatException e) { 
        return false; 
    } catch(NullPointerException e) {
        return false;
    }
    // only got here if we didn't return false
    return true;
}

}
