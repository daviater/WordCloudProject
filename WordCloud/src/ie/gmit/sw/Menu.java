package ie.gmit.sw;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Menu {

	private static String s_filePath;
	private static String s_ignoreFilePath  = "../ignorewords.txt";
	private static String s_imageName;
	private static int i_numOfWords;
	
	
	public static void RunMenu() {
		int i_choice = 0;
		Scanner sc_scanner = new Scanner(System.in);
		Set<String> hs_ignoreWords = new HashSet<String>();
		hs_ignoreWords = Parser.ReadIgnoreFile(s_ignoreFilePath);
		Map<String, Integer> m_words = new LinkedHashMap<>();
		EnterFileName(sc_scanner);
		EnterNumberOfWords(sc_scanner);
		EnterImageName(sc_scanner);
		
		while(i_choice != -1) {
			System.out.println("Menu:\n [1] Import Words & Create Image\n [2] Import Words [3] Create Image\n [4] Change file name\n [5] Change number of words\n [6] Change Image name\n [-1] Exit\n ");
			i_choice = sc_scanner.nextInt();
			if(i_choice == 1) {
				m_words = Parser.ReadFile(s_filePath, hs_ignoreWords);
				
				m_words = Sorter.mapSort(m_words);		
				
				Artist.init(m_words, i_numOfWords);
				try {
					Artist.draw(m_words, i_numOfWords);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(i_choice == 2) {
				m_words = Parser.ReadFile(s_filePath, hs_ignoreWords);
				
				m_words = Sorter.mapSort(m_words);		
			}
			else if(i_choice==3) {
				Artist.init(m_words, i_numOfWords);
				try {
					Artist.draw(m_words,i_numOfWords);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(i_choice==4) {
				EnterFileName(sc_scanner);
			}
			else if(i_choice==5) {
				EnterNumberOfWords(sc_scanner);
			}
			else if(i_choice==6) {
				EnterImageName(sc_scanner);
			}
		
		}
		
		sc_scanner.close();
	}
	
	private static void EnterFileName(Scanner sc_scanner) {
		System.out.println("Please enter file name and path\n");
		s_filePath = sc_scanner.next();
		
	}
	private static void EnterNumberOfWords(Scanner sc_Scanner) {
		System.out.println("Please enter maximum number of words\n");
		i_numOfWords = sc_Scanner.nextInt();
	}
	private static void EnterImageName(Scanner sc_scanner) {
		System.out.println("Please enter Image name\n");
		s_imageName = sc_scanner.next();
	}
}
