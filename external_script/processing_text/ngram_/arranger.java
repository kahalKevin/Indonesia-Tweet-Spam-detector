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
public class arranger {
        
    public static void main(String[] args) {
        System.out.println(arrange("65341216"));
    }
    
static String arrange(String input){
	int length=input.length();
    int i,j;
    String build="";
    char[] arr=input.toCharArray();
	for(i=0;i<length-1;i++){
        int max_pos=i;
        for(j=i+1;j<length;j++){
            if(Character.getNumericValue(arr[max_pos]) < Character.getNumericValue(arr[j])){
        		max_pos=j;
        	}
        }
        char temp1=arr[i];                                
		arr[i]=arr[max_pos];
		arr[max_pos]=temp1;                        
        build+=arr[i];
    }
    build+=arr[i];
    return build;
}
}
