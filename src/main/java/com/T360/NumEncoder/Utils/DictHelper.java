package com.T360.NumEncoder.Utils;

import java.util.HashMap;

public class DictHelper{

	public HashMap<String, Integer> charEncodingRefMap= new HashMap<String, Integer>();
	
	public DictHelper(){
		/* Based on the given input form a Hashmap that holds the int value for each letter */
		charEncodingRefMap.put("E", 0);
		charEncodingRefMap.put("e", 0);
		charEncodingRefMap.put("E\"", 0);
		charEncodingRefMap.put("e\"", 0);
		
		charEncodingRefMap.put("J", 1);
		charEncodingRefMap.put("j", 1);
		charEncodingRefMap.put("N", 1);
		charEncodingRefMap.put("n", 1);
		charEncodingRefMap.put("Q", 1);
		charEncodingRefMap.put("q", 1);
		
		charEncodingRefMap.put("R", 2);
		charEncodingRefMap.put("r", 2);
		charEncodingRefMap.put("W", 2);
		charEncodingRefMap.put("w", 2);
		charEncodingRefMap.put("X", 2);
		charEncodingRefMap.put("x", 2);
		
		charEncodingRefMap.put("D", 3);
		charEncodingRefMap.put("d", 3);
		charEncodingRefMap.put("S", 3);
		charEncodingRefMap.put("s", 3);
		charEncodingRefMap.put("Y", 3);
		charEncodingRefMap.put("y", 3);
		
		charEncodingRefMap.put("F", 4);
		charEncodingRefMap.put("f", 4);
		charEncodingRefMap.put("T", 4);
		charEncodingRefMap.put("t", 4);
		
		charEncodingRefMap.put("A", 5);
		charEncodingRefMap.put("a", 5);
		charEncodingRefMap.put("A\"", 5);
		charEncodingRefMap.put("a\"", 5);
		charEncodingRefMap.put("M", 5);
		charEncodingRefMap.put("m", 5);
		
		charEncodingRefMap.put("C", 6);
		charEncodingRefMap.put("c", 6);
		charEncodingRefMap.put("I", 6);
		charEncodingRefMap.put("i", 6);
		charEncodingRefMap.put("V", 6);
		charEncodingRefMap.put("v", 6);
		
		charEncodingRefMap.put("B", 7);
		charEncodingRefMap.put("b", 7);
		charEncodingRefMap.put("K", 7);
		charEncodingRefMap.put("k", 7);
		charEncodingRefMap.put("U", 7);
		charEncodingRefMap.put("u", 7);
		
		charEncodingRefMap.put("L", 8);
		charEncodingRefMap.put("l", 8);
		charEncodingRefMap.put("O", 8);
		charEncodingRefMap.put("o", 8);
		charEncodingRefMap.put("O\"", 8);
		charEncodingRefMap.put("o\"", 8);
		charEncodingRefMap.put("P", 8);
		charEncodingRefMap.put("p", 8);
		
		charEncodingRefMap.put("G", 9);
		charEncodingRefMap.put("g", 9);
		charEncodingRefMap.put("H", 9);
		charEncodingRefMap.put("h", 9);
		charEncodingRefMap.put("Z", 9);
		charEncodingRefMap.put("z", 9);
	}
	
	
	/*
	 * Method: encodeStringToNumString
	 * Input: String
	 * Return: String
	 * Description: The input string has to be converted to a Numeric String
	 * 				Make use of the charEncoding HashMap to get the numeric 
	 * 				value of each character and return the numeric string.
	 *				
	 * */
	public String encodeStringToNumString(String inpStr){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<inpStr.length();i++){
			if(inpStr.charAt(i)=='"'){
				continue;
			}
			int encode=charEncodingRefMap.get(Character.toString(inpStr.charAt(i)));
			sb.append(encode);
		}
		return sb.toString();
	}
}
