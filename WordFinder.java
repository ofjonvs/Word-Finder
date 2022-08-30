package abc;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WordFinder {

	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		Scanner readFile;
		File file;
		String choice = "";
		while(!choice.equals("4")) {
//			takes in option from user
			System.out.println("Chose from 4 options:\n1: Find word in text\n2: Find if word exists:\n"
					+ "3: Find words out of comination of letters\n4: Quit");
			choice = userInput.nextLine();
//			switch statement from choice
			switch(choice) {
//				finding word in text
//				gets user input for filename
				case "1": System.out.println("Enter file name to read from: ");
					String fileName = userInput.nextLine();
					file = new File(fileName);
				try {
//					gets user input for write file
					readFile = new Scanner(file);	
					System.out.println("Enter new file to write to: ");
					String outFileName = userInput.nextLine();
					File writeFile = new File(outFileName);
				    writeFile.createNewFile();		
					BufferedWriter write = new BufferedWriter(new FileWriter(outFileName));
//					gets user input for word
					System.out.println("Enter word: ");
					String word = userInput.nextLine();
//					highlights word
					String highlightedWord = "***" + word.toUpperCase() + "***";
					String line = "";
//					reading from file
					while(readFile.hasNextLine()) {
						line = readFile.nextLine();
//						splits line into string array
						String lineArr [] = line.split(" ");
						for(int i = 0; i < lineArr.length; i++) {
//							replace targeted word with highlighted word if found
							if(lineArr[i].equals(word))
								write.write(highlightedWord);
//							else write to the file regularly
							else
								write.write(lineArr[i]);
//							spaces
							if(i < lineArr.length - 1) {
								write.write(" ");
							}
							
						}
						if(readFile.hasNextLine()) {
							write.write("\n");
						}
					}
					write.close();
				} catch (FileNotFoundException e) {
					System.out.println("File not found");
				} catch (IOException e) {
					System.out.println("Error writing to file");
				}				
					break;
					
//					find if word exists
				case "2": System.out.println("Enter word: ");
//					gets intended word from user input
					String word = userInput.nextLine();
					Pattern p = Pattern.compile("[^a-zA-Z]");
			        Matcher m = p.matcher(word);
//			        if valid characters
			        if(!m.find()) {
//			        	open dictionary file
			        	file = new File("dictionary.txt");
				        try {
							Scanner readDictionary = new Scanner(file);						
							ArrayList<String> dictionary = new ArrayList<>();
//							creates arraylist of all valid words
							while(readDictionary.hasNextLine()) {
								dictionary.add(readDictionary.nextLine());
							}
							word = word.toUpperCase();
//							if word isnt found in list it is invalid
							if(dictionary.indexOf(word) == -1) 
								System.out.println("Word does not exist.");
							else
								System.out.println("Valid word.");
							readDictionary.close();
						} catch (FileNotFoundException e) {
							System.out.println("Dictionary file not found");
							
						}
			    
			        }
//			        if word contains special characters or numbers
			        else 
			        	System.out.println("Invalid word. Cannot contain special characters, spaces or numbers.");				
					break;
					
//					creating words from collection of letters
				case "3": System.out.println("Enter Letters");
//				gets user input
					String letters = userInput.nextLine().toUpperCase();
					Pattern p2 = Pattern.compile("[^A-Za-z]");
			        Matcher m2 = p2.matcher(letters);
//			        if word has valid characters
			        if(!m2.find()) {
//			        	creates map of characters from collections of letters
			        	Map <Character, Integer> charMap = createCharMap(letters);
			        	file = new File("dictionary.txt");
			        	Scanner readDictionary;
						try {
//							opens dictionary file
							readDictionary = new Scanner(file);
							while(readDictionary.hasNextLine()) {
								String currentWord = readDictionary.nextLine();
//								creates character map from current word from dictionary
				        		Map<Character, Integer> dictionaryMap = createCharMap(currentWord);
				        		boolean validWord = true;
//				        		iterates through characters in dictionary map
				        		for(Character ch : dictionaryMap.keySet()) {    
//				        			if character isnt in collection of letters map valid word is false
				        			if(!charMap.containsKey(ch)) {			   
				        				validWord = false;
				        				break;
				        			}
//				        			if count of character is greater than amount in collection of letters
//				        			word is false
				        			if(charMap.get(ch) < dictionaryMap.get(ch)) {	        				
				        				validWord = false;
				        				break;			        				
				        			}
				        		}
//				        		if iterates through word it is valid
				        		if(validWord)
				        			System.out.println(currentWord);
				        	}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
			        	
			        		
			        	}
//			        if word contains special characters or numbers
			        else
			        	System.out.println("Invalid word. Cannot contain special characters, spaces or numbers.");
					break;
//					to exit program
				case "4": System.out.println("exited");
					break;
				default: System.out.println("Invalid entry");
					break;
			}
		}
		
		
		userInput.close();
	}
	


//	creates map of character amounts with string
	private static Map<Character, Integer> createCharMap(String letters){
		Map <Character, Integer> charMap = new HashMap<>();
//		adds characters to map and adds 1 to current value
		for(int i = 0; i < letters.length(); i++) {
			char currentChar = letters.charAt(i);
			if(charMap.containsKey(currentChar)) 
				charMap.put(currentChar, charMap.get(currentChar) + 1);
			else 
				charMap.put(currentChar, 1);
			}
			
		return charMap;
		}

}
