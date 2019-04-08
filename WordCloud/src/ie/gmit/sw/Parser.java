package ie.gmit.sw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {

	
	public static Set<String> ReadIgnoreFile(String s_fileName){
		Set<String> hs_words = new HashSet<String>();
		
		try (Stream<String> stream = Files.lines(Paths.get(s_fileName))) {
			hs_words = stream.collect(Collectors.toSet());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
		return hs_words;
	}
	
 	public static Map<String, Integer> ReadFile(String s_fileName, Set<String> hs_ignore){
		Map<String, Integer> m_words = new LinkedHashMap<String, Integer>();
		
		try (Stream<String> stream = Files.lines(Paths.get(s_fileName))) {
			//Stream<String> stream_a; 
			stream.forEach(s->{for(String a: s.split("\\P{L}+")) {
				if((!hs_ignore.contains(a)) && !(a.length() < 1)) {
				if(m_words.get(a) != null) {
					m_words.put(a,m_words.get(a)+1);
				}else {
					m_words.put(a, 1);
				}
				}
			};}) ;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		return m_words;
		
	}
}
