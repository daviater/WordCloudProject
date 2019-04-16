package ie.gmit.sw;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Runner {

	public static void main(String[] args) {
		
		//Testing
		String s_importTest = "../WarAndPeace-LeoTolstoy.txt";
		//
		
		String s_ignoreWords = "../ignorewords.txt";
		
		Set<String> hs_ignoreWords = new HashSet<String>();
		
		hs_ignoreWords = Parser.ReadIgnoreFile(s_ignoreWords);
		
		//hs_ignoreWords.forEach(System.out::println);
		
		Map<String, Integer> m_words = new LinkedHashMap<>();
		
		m_words = Parser.ReadFile(s_importTest, hs_ignoreWords);
		
		m_words = Sorter.mapSort(m_words);
		
		Artist.init(m_words);
		try {
			Artist.draw(m_words);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*for(Map.Entry<String, Integer> entry: m_words.entrySet()) {
			System.out.println(entry.getKey() + " : "+ entry.getValue());
		}*/
		
	}

}
