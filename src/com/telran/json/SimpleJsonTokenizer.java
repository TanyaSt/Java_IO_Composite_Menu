package com.telran.json;

import java.util.Iterator;

public interface SimpleJsonTokenizer extends Iterator<String>{
	// returns future token without advancing the iterator 
	public String peek();
}
