/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author if412020
 */
public class gug {
    HashMap<String,String> pair_=new HashMap<>();
    List word = new ArrayList();
    void insert(String key,String value){
        word.add(value);
        pair_.put(key, value);
    }

    String getValue(String key){
        return pair_.get(key);
    }
    
    void delete(String key){
        pair_.remove(key);
    }
    
    String getRandomValue(){
        Set set=pair_.entrySet();
        Random ran=new Random();
        int n=ran.nextInt(pair_.size())+0;
        Iterator i = set.iterator();
        Map.Entry result = null;
        while(i.hasNext()){
            result=(Map.Entry)i.next();   
            n--;
            if(n==0){
                break;
            }
        }
        return (String) result.getValue();
    }
    
    public static void main(String[] args){
        gug test=new gug();
        test.insert("1", "1");
        test.insert("2", "2");
        test.insert("3", "3");
        test.insert("4", "4");
        
        System.out.println(test.getValue("1"));
        System.out.println(test.getValue("1"));
        System.out.println(test.getRandomValue());
        
    }    
}
