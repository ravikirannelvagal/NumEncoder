package com.T360.NumEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.T360.NumEncoder.Utils.DictHelper;
import com.T360.NumEncoder.Utils.EncoderUtils;
import com.T360.NumEncoder.Utils.MyWords;

/**
 * Encoder!!
 *
 */
public class Encoder 
{
	// Class variable to store the dictionary as a collection 
	HashMap<Integer, ArrayList<MyWords>> myDictionary = new HashMap<Integer, ArrayList<MyWords>>();
	
	public static void main(String[] args) {
		// Start the encoder
		Encoder encoder = new Encoder();
		/* Dictionary Helper class, that generates Number Strings and 
		 * helps in storing the dictionary values in the collection */
		DictHelper dh = new DictHelper();
		
		try {
			// populate the Dictionary to the collection 
			encoder.populateMyDictionary(dh);
			// read the input and calculate the corresponding String values. Display the matches found
			encoder.readInputAndCalc(dh);
		} catch (IOException e) {
			System.out.println("File is not found");
		}
	}
	
	/*
	 * Method: populateMyDictionary
	 * Input: DictHelper
	 * Return: void
	 * Description: Read the words in the dictionary and 
	 * 				populate them and store in a collection.
	 * 				The words are stored as a below:
	 * 				Collection: HashMap
	 * 				Key:		Integer
	 * 				Value:		ArrayList<MyWords>
	 * 
	 *				Logic to populate:
	 *				The integer reference for the first character of the word is calculated with the help of DictHelper. 
	 *				An ArrayList of MyWords is maintained for each word in the dictionary. 
	 *				MyWord is an object that holds the String and the Numerical Equivalent of the String.
	 *				For Eg: Integer Key 1 holds an ArrayList of words that begin with J/N/Q
	 *				The HashMap has Keys ranging from 0-9
	 * */
	private void populateMyDictionary(DictHelper dh) throws IOException{
		InputStream dictStream = getClass().getResourceAsStream(EncoderUtils.dictionary);
		BufferedReader br = new BufferedReader(new InputStreamReader(dictStream));
		String ip=null;
		// Read input
		while((ip=br.readLine()) != null){
			// Encode the i/p String to its Numerical Equivalent
			String numStr= dh.encodeStringToNumString(ip);
			MyWords m = new MyWords(numStr, ip);
			// Find the first character of the word to figure out its Key value
			String keyStr=Character.toString(ip.charAt(0));
			int keyInt = dh.charEncodingRefMap.get(keyStr);
			// Get the existing ArrayList for the current Key
			ArrayList<MyWords> tempArr = myDictionary.get(keyInt);
			// If the HashMap doesn't have an ArrayList for the given Key, it means that's the first word in that list, so create a new ArrayList
			if(tempArr==null)
				tempArr = new ArrayList<MyWords>();
			
			// Add the word to the ArrayList
			tempArr.add(m);			
			// Put the Key and the updated ArrayList to the HashMap
			myDictionary.put(keyInt, tempArr);
		}
		// Close the buffered Reader and the Input Stream
		br.close();
		dictStream.close();
	}
	
	/*
	 * Method: readInputAndCalc
	 * Input: DictHelper
	 * Return: void
	 * Description: Read the input words from the input file,
	 * 				replace the invalid characters like - and /, 
	 * 				and calculate the Numeric String for the same.
	 * 
	 * 				Find the first number and get the List of words 
	 * 				from the HashMap with the number as the Key. This
	 * 				gives us all the possible words whose first character
	 * 				will encode to the first number of the input string.
	 *				
	 *				
	 * */
	private void readInputAndCalc(DictHelper dh) throws IOException{
		InputStream inpStream = getClass().getResourceAsStream(EncoderUtils.inputFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(inpStream));
		String ip=null;
		ArrayList<String> finalList=new ArrayList<String>();
		// Read the input numbers
		while((ip=br.readLine()) != null){
			// Replace invalid characters in the input string
			String simplifiedNumStr=ip.replace("-", "");
			simplifiedNumStr=simplifiedNumStr.replace("/", "");
			if(simplifiedNumStr.length()<2)
				continue;
			// Get the first character of the input string and get the list of all words whose encoded value matches
			String firstChar = Character.toString(simplifiedNumStr.charAt(0));
			ArrayList<MyWords> matchingWords = myDictionary.get(Integer.parseInt(firstChar));
			if(matchingWords == null){
				//System.out.println("first character didn't have any matches");
				// if there are no words that start with the encoded value, then skip the first character and get the list of words starting with the second character.
				String skipOneChar=Character.toString(simplifiedNumStr.charAt(1));
				matchingWords=myDictionary.get(Integer.parseInt(skipOneChar));
				// for the substring from the current position to the end of the string, check if there are words that match.
				String simplifiedNumSubStr=simplifiedNumStr.substring(1, simplifiedNumStr.length());
				ArrayList<String> skippedMatches=checkForMatch(dh,matchingWords,simplifiedNumSubStr,true,"");
				// for each of the matches that are found, append the first character that was skipped and print the result on screen.
				for(String s:skippedMatches){
					if(!finalList.contains(ip+":"+simplifiedNumStr.charAt(0)+" "+s))
						finalList.add(ip+":"+simplifiedNumStr.charAt(0)+" "+s);
					
				}
				ArrayList<String> skippedMatchesRev=checkForMatchReverse(dh,matchingWords,simplifiedNumSubStr,true,Character.toString(simplifiedNumStr.charAt(0)));
				for(String s:skippedMatchesRev){
					if(!finalList.contains(ip+":"+simplifiedNumStr.charAt(0)+" "+s))
						finalList.add(ip+":"+simplifiedNumStr.charAt(0)+" "+s);
				}
			}else{
				//System.out.println("first character has matches");
				// for the substring from the current position to the end of the string, check if there are words that match.
				ArrayList<String> matches=checkForMatch(dh,matchingWords,simplifiedNumStr,false,"");
				// the above arraylist contains all the words that are matching. print the result on the screen.
				for(String s:matches){
					if(!finalList.contains(ip+":"+s))
						finalList.add(ip+":"+s);
				}
				ArrayList<String> matchesRev=checkForMatchReverse(dh,matchingWords,simplifiedNumStr,false,"");
				for(String s:matchesRev){
					if(!finalList.contains(ip+":"+s))
						finalList.add(ip+":"+s);
				}
			}
		}
		// close the buffered reader and the input stream.
		br.close();
		inpStream.close();
		
		for(String s: finalList){
			System.out.println(s);
		}
	}
	
	/*
	 * Method: checkForMatch
	 * Input: DictHelper, ArrayList<MyWords>, String, boolean, String
	 * Return: ArrayList<String>
	 * Description: The input string is the string that needs to be matched. 
	 * 				The input string when encoded should match with the numeric 
	 * 				string of any word that is present in the ArrayList. 
	 * 				Return as many matches as found.
	 *				
	 * */
	private ArrayList<String> checkForMatch(DictHelper dh,ArrayList<MyWords> matchingWords, String inpStr, boolean isCharSkipped,String prefix){
		StringBuffer sb = new StringBuffer();
		ArrayList<String> matches = new ArrayList<String>();
		ArrayList<String> secMatches = new ArrayList<String>();
		int startPos = 0;
		boolean loc_isCharSkipped=isCharSkipped;
		
		for(int i=1;i<inpStr.length();i++){
			// get the substring of the input string
			String subStr=inpStr.substring(startPos, i+1);
			//System.out.println(matchingWords.size());
			// loop over all the MyWords in the ArrayList that can be potential matches.
			for(MyWords m:matchingWords){
				String nextString="";
				String partOne="";
				// if the numString of the MyWords matches the input Number String or a part of it, then proceed
				if(m.getNumString().equals(subStr)){
					// prepare a substring of the input string from the current position where the match is found, till the end of the string.
					nextString = inpStr.substring(i+1, inpStr.length());
					// below String is the first word which is a part of the input string or the whole string itself that has matched
					partOne = m.getDictWord();
					/* if the match has happened over a part of the input string, then calculate the match for 
					 * the remaining part of the string by passing the string recursively to the same method
					 * If the length of the substring is greater than 0, then call the method.
					 * If the length of the substring is 0, which means the whole string is matched, add the matching word to the arraylist
					 */
					if(nextString.length()>1){
						String nextStart = Character.toString(nextString.charAt(0));
						// get the arraylist of MyWords that match the next character.
						ArrayList<MyWords> nextList=myDictionary.get(Integer.parseInt(nextStart));
						if(nextList == null){
							if(loc_isCharSkipped)
								continue;//return new ArrayList<String>();
							loc_isCharSkipped=true;
							// if there are no words that match the starting character, then skip a character and continue from the second character.
							String skipOneChar = Character.toString(nextString.charAt(1));
							// get the arraylist of MyWords that match the next character after the skipped character.
							nextList=myDictionary.get(Integer.parseInt(skipOneChar));
							String nextStringSubStr=nextString.substring(1, nextString.length());
							// Call a modified form of the current method which replicates recursion in a different way
							ArrayList<String> skippedMatches=checkForMatch(dh,nextList,nextStringSubStr,loc_isCharSkipped,"");
							if(skippedMatches.size() ==0){
								continue;//return new ArrayList<String>();
							}
							// for each of the matches found for the string with one skipped character, add the skipped character to the list of matches found
							for(String s:skippedMatches){
								if(!secMatches.contains(partOne+" "+nextString.charAt(0)+" "+s))
									secMatches.add(partOne+" "+nextString.charAt(0)+" "+s);
							}
						}else{
							if(!loc_isCharSkipped)
								loc_isCharSkipped=false;
							// for each of the matches found for the string add the first part of the match to the each string in the list of matches found
							ArrayList<String> secMatchRes=checkForMatch(dh, nextList, nextString,loc_isCharSkipped,"");
							for(String s:secMatchRes){
								if(!secMatches.contains(partOne+" "+s))
									secMatches.add(partOne+" "+s);
							}
						}
					}else if(nextString.length() ==1){
						if(!secMatches.contains(partOne+" "+nextString))
							secMatches.add(partOne+" "+nextString);
					}else{
						// If the length of the substring is 0, which means the whole string is matched, add the matching word to the arraylist
						if(!secMatches.contains(partOne))
							secMatches.add(partOne);
					}
					// for each of the matches found add the secondary matches to the list of overall matches
					for(String s:secMatches){
						if(!matches.contains(prefix+s))
							matches.add(prefix+s);
					}
					
					if(secMatches.size()==0){
						if(loc_isCharSkipped)
							continue;//return new ArrayList<String>();
						loc_isCharSkipped=true;
						// if there are no secondary matches found immediately, then skip one character and start finding matches.
						String skipOneChar = Character.toString(nextString.charAt(1));
						ArrayList<MyWords>nextList=myDictionary.get(Integer.parseInt(skipOneChar));
						String nextStringSubStr=nextString.substring(1, nextString.length());
						ArrayList<String> skippedMatches=checkForMatch(dh,nextList,nextStringSubStr,loc_isCharSkipped,"");
						if(skippedMatches.size() ==0){
							continue;//return new ArrayList<String>();
						}
						for(String s:skippedMatches){
							if(!secMatches.contains(partOne+" "+nextString.charAt(0)+" "+s))
								secMatches.add(partOne+" "+nextString.charAt(0)+" "+s);
						}
						for(String s:secMatches){
							if(!matches.contains(prefix+s))
								matches.add(prefix+s);
						}
					}
				}
			}
			
		}
		
		return matches;
	}
	
	
	/*
	 * Method: checkForMatchReverse
	 * Input: DictHelper, ArrayList<MyWords>, String, boolean, String
	 * Return: ArrayList<String>
	 * Description: The input string is the string that needs to be matched. 
	 * 				The input string when encoded should match with the numeric 
	 * 				string of any word that is present in the ArrayList. 
	 * 				Return as many matches as found.
	 * 				This is a modified form of the original checkForMatch method
	 * 				This method is an indicator that a character has already been skipped
	 * 				The check begins by starting with a full word match and then one 
	 * 				letter is skipped from the beginning of the string based on 
	 * 				previous character skip condition and matched
	 *				
	 * */
	private ArrayList<String> checkForMatchReverse(DictHelper dh,ArrayList<MyWords> matchingWords, String inpStr, boolean isCharSkipped,String prefix){
		StringBuffer sb = new StringBuffer();
		ArrayList<String> matches = new ArrayList<String>();
		ArrayList<String> secMatches = new ArrayList<String>();
		int startPos = 0;
		boolean isMatchFound=false;
		boolean loc_isCharSkipped=isCharSkipped;
		
		for(MyWords m:matchingWords){
			//System.out.print(inpStr);
			//System.out.println(":m->"+m.getNumString());
			if(m.getNumString().equals(inpStr)){
				matches.add(prefix+m.getDictWord());
				String newSubStr=inpStr.substring(1, inpStr.length());
				if(loc_isCharSkipped){
					continue;//return matches;
				}
				loc_isCharSkipped=true;
				String skippedStr = Character.toString(inpStr.charAt(0));
				String newPrefix=prefix+skippedStr+" ";
				ArrayList<MyWords> listAfterSkip = myDictionary.get(Integer.parseInt(skippedStr));
				if(listAfterSkip == null)
					continue;//return matches;
				else{
					ArrayList<String> skippedMatchRev= checkForMatchReverse(dh,listAfterSkip,newSubStr,loc_isCharSkipped,newPrefix);
					ArrayList<String> skippedMatchAct= checkForMatch(dh,listAfterSkip,newSubStr,loc_isCharSkipped,newPrefix);
					if(skippedMatchRev.size()==0 && skippedMatchAct.size()==0)
						continue;//return matches;
					else{
						for(String s:skippedMatchRev){
							if(!matches.contains(s)){
								matches.add(s);
							}
						}
						
						for(String s:skippedMatchAct){
							if(!matches.contains(s)){
								matches.add(s);
							}
						}
					}
				}
				loc_isCharSkipped=false;
			}else{
				if(loc_isCharSkipped){
					continue;//return matches;
				}
				String newSubStr=inpStr.substring(1, inpStr.length());
				loc_isCharSkipped=true;
				String skippedStr = Character.toString(inpStr.charAt(0));
				
				String nextChar = Character.toString(inpStr.charAt(1));
				String newPrefix=prefix+skippedStr+" ";
				ArrayList<MyWords> listAfterSkip = myDictionary.get(Integer.parseInt(nextChar));
				if(listAfterSkip == null)
					continue;//return matches;
				else{
					ArrayList<String> skippedMatchRev= checkForMatchReverse(dh,listAfterSkip,newSubStr,loc_isCharSkipped,newPrefix);
					ArrayList<String> skippedMatchAct= checkForMatch(dh, listAfterSkip, newSubStr, loc_isCharSkipped,newPrefix);
					if(skippedMatchRev.size()==0 && skippedMatchAct.size()==0)
						continue;//return matches;
					else{
						for(String s:skippedMatchRev){
							if(!matches.contains(s)){
								matches.add(s);
							}
						}
						
						for(String s:skippedMatchAct){
							if(!matches.contains(s)){
								matches.add(s);
							}
						}
					}
				}
			}
			
		}
		return matches;
	}
	
	
	/*
	 * Method: checkForSkippedMatch
	 * Input: DictHelper, ArrayList<MyWords>, String
	 * Return: ArrayList<String>
	 * Description: The input string is the string that needs to be matched. 
	 * 				The input string when encoded should match with the numeric 
	 * 				string of any word that is present in the ArrayList. 
	 * 				Return as many matches as found.
	 * 				This is a modified form of the original checkForMatch method
	 * 				This method is an indicator that a character has already been skipped
	 *				
	 * */
	private ArrayList<String> checkForSkippedMatch(DictHelper dh,ArrayList<MyWords> matchingWords, String inpStr){
		ArrayList<String> matches = new ArrayList<String>();
		ArrayList<String> secMatches = new ArrayList<String>();
		int startPos = 0;
		for(int i=1;i<inpStr.length();i++){
			// get the substring of the input string
			String subStr=inpStr.substring(startPos, i+1);
			// loop over all the MyWords in the ArrayList that can be potential matches.
			for(MyWords m:matchingWords){
				// if the numString of the MyWords matches the input Number String or a part of it, then proceed
				if(m.getNumString().equals(subStr)){
					String nextString = inpStr.substring(i+1, inpStr.length());
					String partOne = m.getDictWord();
					if(nextString.length()>1){
						String nextStart = Character.toString(nextString.charAt(0));
						ArrayList<MyWords> nextList=myDictionary.get(Integer.parseInt(nextStart));
						ArrayList<String> secMatchRes=checkForMatch(dh, nextList, nextString,true,"");
						for(String s:secMatchRes){
							if(!secMatches.contains(partOne+" "+s))
								secMatches.add(partOne+" "+s);
						}
					}else if(nextString.length() == 1){
						if(!secMatches.contains(partOne+" "+nextString))
							secMatches.add(partOne+" "+nextString);
					}
					else{
						if(!secMatches.contains(partOne))
							secMatches.add(partOne);
					}
					for(String s:secMatches){
						if(!matches.contains(s))
							matches.add(s);
					}
				}
			}
			
		}
		
		return matches;
	}
}
