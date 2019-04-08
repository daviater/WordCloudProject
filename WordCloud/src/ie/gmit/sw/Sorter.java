package ie.gmit.sw;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Sorter {

	
	public static Map<String, Integer> mapSort(Map<String, Integer> m_words){
		
		//List<Entry<String, Integer>> l_words = new ArrayList<>(m_words.entrySet());
		
		//l_words.sort(Entry.comparingByValue());
		
		Map<String, Integer>m_sortedWords = m_words.entrySet().stream().sorted(Entry.comparingByValue(/*Comparator.reverseOrder()*/)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		//for(Entry<String, Integer> entry: l_words) {
			//m_sortedWords.put(entry.getKey(), entry.getValue());
		//}
		
		return m_sortedWords;
		
	}
}
