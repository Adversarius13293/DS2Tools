package adver.sarius.ds2tools.datacollector;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Stack;

public abstract class DSPageProcessor {
	
	public abstract void readPage(BufferedReader page, int id) throws IOException;
	
	// TODO: Auslagern nach util?
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
	public String subString(String line, String prefix, String suffix) {
		if (line == null) {
			return null;
		}
		int start = 0;
		if (prefix != null) {
			start = line.indexOf(prefix) + prefix.length();
		}
		line = line.substring(start);

		int end = line.length();
		if (suffix != null) {
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
	public int toInt(String number) {
		return Integer.parseInt(number.trim().replaceAll("\\.", ""));
	}

	/**
	 * Converts some html tags into DS2 format to be stored in the DB.
	 * 
	 * @param html the text with html tags.
	 * @return same text with replaced tags.
	 */
	public String convertHtmlToBBCode(String html) {
		html = html.replaceAll("<br>", "\\\\r\\\\n");
		Stack<String> closingTags = new Stack<>();
		int counter = 0;
		while (true && counter++ < 100) {
			int opening = html.indexOf("<span style=");
			int closing = html.indexOf("</span>");

			if (opening < closing && opening >= 0) {
				String tagContent = subString(html, "<span style=\"", "\">");
				if (tagContent.startsWith("color:")) {
					String color = tagContent.substring(6);
					html = html.replaceFirst("<span style=\"color:" + color + "\">", "[color=" + color + "]");
					closingTags.push("[/color]");
				} else if (tagContent.equals("font-weight:bold")) {
					html = html.replaceFirst("<span style=\"font-weight:bold\">", "[b]");
					closingTags.push("[/b]");
				} else if (tagContent.equals("text-decoration:underline")) {
					html = html.replaceFirst("<span style=\"text-decoration:underline\">", "[u]");
					closingTags.push("[/u]");
				} else if (tagContent.equals("font-style:italic")) {
					html = html.replaceFirst("<span style=\"font-style:italic\">", "[i]");
					closingTags.push("[/i]");
				} else {
					// TODO: remove unknown tag to continue?
					System.out.println("convertHtmlToBBCode: Unknown tagContent: " + tagContent);
					break;
				}
			} else if (opening > closing || (opening < 0 && closing >= 0)) {
				html = html.replaceFirst("</span>", closingTags.pop());
			} else {
				break;
			}
		}
		return html;
	}

	/**
	 * Turns everything into lower case and replaces or removes special characters.
	 * 
	 * @param name String to normalize.
	 * @return normalized String.
	 */
	public String normalizeString(String name) {
		name = name.toLowerCase();
		name = name.replace("[", "").replace("]", "").replace(".", "").replaceAll(":", "");
		name = name.replace("_", " ").replace("-", " ").replaceAll(" +", "_");
		name = name.replace("ß", "ss").replace("ä", "ae").replace("ö", "oe").replace("ü", "ue");
		return name;
	}
}