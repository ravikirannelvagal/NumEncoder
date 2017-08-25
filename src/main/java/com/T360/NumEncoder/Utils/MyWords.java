package com.T360.NumEncoder.Utils;

public class MyWords implements Comparable<MyWords>{

	private String numString;
	private String dictWord;
	
	public MyWords(String numString,String dictWord){
		this.numString=numString;
		this.dictWord=dictWord;
	}
	
	public String getNumString() {
		return numString;
	}
	
	public String getDictWord() {
		return dictWord;
	}
	
	public boolean equals(Object o){
		return this.dictWord.equalsIgnoreCase(((MyWords)o).dictWord);
	}

	public int compareTo(MyWords o) {
		return this.compareTo(o);
	}
}
