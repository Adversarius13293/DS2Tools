package adver.sarius.ds2tools.datacollector;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class DSPageProcessor {
	
	public abstract void readPage(BufferedReader page, int id) throws IOException;
	
	/**
	 * Extracts the string between prefix and suffix.
	 * If the prefix/suffix is null, it is evaluated as string from/to the start/end.
	 * The substring up to prefix is done before evaluating the suffix index.
	 * 
	 * @param line the line to work with.
	 * @param prefix after the first occurrence of the prefix the word starts.
	 * @param suffix the word ends with the suffix.
	 * @return returns the string between prefix and suffix.
	 */
	public String subString(String line, String prefix, String suffix){
		if(line == null){
			return null;
		}
		int start = 0;
		if(prefix != null){
			start = line.indexOf(prefix)+prefix.length();
		}
		line = line.substring(start);
		
		int end = line.length();
		if(suffix != null){
			end = line.indexOf(suffix);
		}
		
		return line.substring(0, end);
	}
	
	/**
	 * Converts a string into an integer.
	 * Removes all '.' characters as they are used as thousands separator.
	 * 
	 * @param number string representation of the number.
	 * @return number as an int.
	 */
	public int toInt(String number){
		return Integer.parseInt(number.trim().replaceAll("\\.", ""));
	}
}
