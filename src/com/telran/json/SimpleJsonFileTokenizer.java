package com.telran.json;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SimpleJsonFileTokenizer implements SimpleJsonTokenizer, Closeable{
	private static final int MAX_TOKEN_LENGTH = 1024;

	String nextToken;
	Scanner scanner;
	Pattern pattern;
	
	public SimpleJsonFileTokenizer(Reader reader) {
		scanner = new Scanner(reader);
		String singleBracketColonOrComma = "[\\{\\}\\[\\]:,]"; 		// regex [\{\}\[\]:,]
//		String quotedStringPattern = "\"[^\"]*\""; 					// regex "[^"]*" 		// does not cover sequence \"
		String quotedStringPattern = "\".*?(?<!\\\\)\"";			// regex ".*?(?<!\\)"	// covers sequence \"
		pattern = Pattern.compile("("+ singleBracketColonOrComma + "|" + quotedStringPattern + ")"); // token with blanks
		findNextToken();
	}
	
	private void findNextToken() {
		nextToken = scanner.findWithinHorizon(pattern, MAX_TOKEN_LENGTH);
		if (nextToken == null) {
			scanner.close();
		}
	}
	
	@Override
	public boolean hasNext() {		
		return nextToken!=null;
	}

	@Override
	public String next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		String res = nextToken;
		findNextToken();
		return res;
	}

	// returns future token without advancing the iterator 
	@Override
	public String peek() {
		return nextToken;
	}

	@Override
	public void close() throws IOException {
		scanner.close();
	}
	
}
