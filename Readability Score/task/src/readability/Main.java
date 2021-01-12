package readability;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("No file found!");
			System.exit(1);
		}

		String text = readFile(args[0]);
		System.out.println("The text is:\n" + text + "\n");

		int sentences = getNumberOfSentences(text);
		int words = getNumberOfWords(text);
		int characters = getNumberOfChars(text);
		int syllables = getNumberOfSyllablesInText(text);
		int polysyllables = getNumberOfPolysyllables(text);
		double L = (double) characters / words * 100;
		double S = (double) sentences / words * 100;

		System.out.println("Words: " + words);
		System.out.println("Sentences: " + sentences);
		System.out.println("Characters: " + characters);
		System.out.println("Syllables: " + syllables);
		System.out.println("Polysyllables: " + polysyllables);
		System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
		String input = new Scanner(System.in).next();

		switch (input) {
			case "ARI":
				double score = automatedReadabilityTest(sentences, words, characters);
				System.out.format("Automated Readability Index: %.2f %s", score, ageTable(Math.ceil(score)));
				break;
			case "FK":
				score = fleschKincaidReadabilityTest(sentences, words, syllables);
				System.out.format("Flesch-Kincaid readability tests: %.2f %s", score, ageTable(Math.ceil(score)));
				break;
			case "SMOG":
				score = smogTest(sentences, polysyllables);
				System.out.format("Simple Measure of Gobbledygook: %.2f %s", score, ageTable(Math.ceil(score)));
				break;
			case "CL":
				score = colemanLiauTest(L, S);
				System.out.format("Coleman-Liau index: %.2f %s", score, ageTable(Math.ceil(score)));
				break;
			case "all":
				score = automatedReadabilityTest(sentences, words, characters);
				System.out.format("Automated Readability Index: %.2f %s\n", score, ageTable(Math.ceil(score)));
				score = fleschKincaidReadabilityTest(sentences, words, syllables);
				System.out.format("Flesch-Kincaid readability tests: %.2f %s\n", score, ageTable(Math.ceil(score)));
				score = smogTest(sentences, polysyllables);
				System.out.format("Simple Measure of Gobbledygook: %.2f %s\n", score, ageTable(Math.ceil(score)));
				score = colemanLiauTest(L, S);
				System.out.format("Coleman-Liau index: %.2f %s\n", score, ageTable(Math.ceil(score)));
				break;
			default:
				System.out.println("Error!");
		}
	}

	/**
	 * Get readability score using automated readability test formula
	 *
	 * @param sentences  integer that represents number of sentences in text
	 * @param words      integer that represents number of words in text
	 * @param characters integer that represents number of characters in text
	 * @return integer with score
	 */
	private static double automatedReadabilityTest(int sentences, int words, int characters) {
		return (4.71 * ((double) characters / words) + 0.5 * ((double) words / sentences) - 21.43);
	}

	private static String ageTable(double score) {
		switch ((int) score) {
			case 1:
				return "(about 6 year olds)";
			case 2:
				return "(about 7 year olds)";
			case 3:
				return "(about 9 year olds)";
			case 4:
				return "(about 10 year olds)";
			case 5:
				return "(about 11 year olds)";
			case 6:
				return "(about 12 year olds)";
			case 7:
				return "(about 13 year olds)";
			case 8:
				return "(about 14 year olds)";
			case 9:
				return "(about 15 year olds)";
			case 10:
				return "(about 16 year olds)";
			case 11:
				return "(about 17 year olds)";
			case 12:
				return "(about 18 year olds)";
			default:
				return "(about 24 year olds)";
		}
	}

	/**
	 * Get readability score using coleman-liau index readability test formula
	 *
	 * @param L double which contains average number of letters per 100 words
	 * @param S double which contains average number of sentences per 100 words
	 * @return integer with score
	 */
	private static double colemanLiauTest(double L, double S) {
		return (0.0588 * L - 0.296 * S - 15.8);
	}

	/**
	 * Get readability score using smog index readability test formula
	 *
	 * @param sentences     integer which contains number of sentences in text
	 * @param polysyllables integer which contains number of polysyllable words in text
	 * @return integer with score
	 */
	private static double smogTest(int sentences, int polysyllables) {
		return (1.043 * Math.sqrt(polysyllables * (30.0 / sentences)) + 3.1291);
	}

	/**
	 * Get readability score using flesch-kincaid readability test formula
	 *
	 * @param sentences integer which contains number of sentences in text
	 * @param words     integer which contains number of words in text
	 * @param syllables integer which contains number of syllables in text
	 * @return integer with score
	 */
	private static double fleschKincaidReadabilityTest(int sentences, int words, int syllables) {
		return (0.39 * ((double) words / sentences) + 11.8 * ((double) syllables / words) - 15.59);
	}

	private static int getNumberOfPolysyllables(String text) {
		//remove from text all signs, turn it to lover case and split into words by spaces
		String[] words = text.replaceAll("[!?,.]", "")
				.replaceAll("['`]", "")
				.toLowerCase(Locale.ROOT)
				.split("\\s");
		//counter to count polysyllables words in text
		int counter = 0;

		//for each word from text count polysyllables
		for (String word : words) {
			if (isPolysyllableWord(word)) counter++;
		}

		return counter;
	}

	/**
	 * Determine if a word is a polysyllable (has more than 2 syllables)
	 *
	 * @param word string which contains a word in lover case without signs
	 * @return true if a word has more than 2 syllables
	 */
	private static boolean isPolysyllableWord(String word) {
		return getNumberOfSyllablesInWord(word) > 2;
	}

	/**
	 * Get number of syllables in whole text
	 *
	 * @param text string which contains a text from file
	 * @return integer with number of syllables in text
	 */
	private static int getNumberOfSyllablesInText(String text) {
		//remove from text all signs, turn it to lover case and split into words by spaces
		String[] words = text.replaceAll("[!?,.]", "")
				.replaceAll("['`]", "")
				.toLowerCase(Locale.ROOT)
				.split("\\s");
		//counter for syllables in text
		int counter = 0;

		//for each word from text count syllables
		for (String word : words) {
			counter += getNumberOfSyllablesInWord(word);
		}

		return counter;
	}

	/**
	 * Get number of syllables in word
	 *
	 * @param word string which contains one word in lover case without signs
	 * @return integer with number of syllables in word
	 */
	private static int getNumberOfSyllablesInWord(String word) {
		int counter = 0;

		if (word.charAt(word.length() - 1) == 'e') {
			if (silentE(word)) {
				word = word.substring(0, word.length() - 1);
				counter += countSyllablesInWord(word);
			} else {
				counter++;
			}
		} else {
			counter += countSyllablesInWord(word);
		}

		return counter;
	}

	/**
	 * Count syllables in a word
	 *
	 * @param word string which contain a word in lover case without signs
	 * @return integer with number of syllables in word
	 */
	private static int countSyllablesInWord(String word) {
		Pattern splitter = Pattern.compile("[^aeyuio]*[aeyuio]+");
		Matcher matcher = splitter.matcher(word);
		int counter = 0;

		while (matcher.find()) {
			counter++;
		}

		return counter;
	}

	/**
	 * Check if a words has a silent 'e'
	 *
	 * @param word string which contains lover case word without signs
	 * @return true if a silent 'e' in a word
	 */
	private static boolean silentE(String word) {
		String s = word.substring(0, word.length() - 1);
		Pattern pattern = Pattern.compile("[aeyuio]");
		Matcher matcher = pattern.matcher(s);

		return matcher.find();
	}

	/**
	 * Get number of chars in text
	 *
	 * @param text string which contains a text from file
	 * @return integer with number of chars in text
	 */
	private static int getNumberOfChars(String text) {
		return text.replaceAll(" ", "").length();
	}

	/**
	 * Get number of words
	 *
	 * @param text string which contains a text from file
	 * @return integer with number of words in text
	 */
	private static int getNumberOfWords(String text) {
		return text.split(" ").length;
	}

	/**
	 * Get number of sentences
	 *
	 * @param text string which contains a text from file
	 * @return integer with number of sentences in text
	 */
	private static int getNumberOfSentences(String text) {
		return text.split("[!.?]").length;
	}

	/**
	 * Read file and get everything from it
	 *
	 * @param path is a string that contains path to file
	 * @return string which contains text
	 */
	private static String readFile(String path) {
		//create File instance for file
		File file = new File(path);
		//string builder will contain insides of file
		StringBuilder stringBuilder = new StringBuilder();

		//try to get everything from file
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNext()) {
				stringBuilder.append(scanner.nextLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//return text and remove from it all newlines and tabs
		return stringBuilder.toString().replaceAll("[\n\t]", " ");
	}
}
