package readability;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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
		double score = (4.71 * ((double) characters / words) + 0.5 * ((double) words / sentences) - 21.43);

		System.out.println("Words: " + words);
		System.out.println("Sentences: " + sentences);
		System.out.println("Characters: " + characters);
		System.out.format("The score is: %.2f\n", score);

		score = Math.ceil(score);
		if (score < 2) {
			System.out.format("This text should be understood by %d-%d year olds.", 5, 6);
		} else if (score >= 2 && score < 3) {
			System.out.format("This text should be understood by %d-%d year olds.", 6, 7);
		} else if (score >= 3 && score < 4) {
			System.out.format("This text should be understood by %d-%d year olds.", 7, 9);
		} else if (score >= 4 && score < 5) {
			System.out.format("This text should be understood by %d-%d year olds.", 9, 10);
		} else if (score >= 5 && score < 6) {
			System.out.format("This text should be understood by %d-%d year olds.", 10, 11);
		} else if (score >= 6 && score < 7) {
			System.out.format("This text should be understood by %d-%d year olds.", 11, 12);
		} else if (score >= 7 && score < 8) {
			System.out.format("This text should be understood by %d-%d year olds.", 12, 13);
		} else if (score >= 8 && score < 9) {
			System.out.format("This text should be understood by %d-%d year olds.", 13, 14);
		} else if (score >= 9 && score < 10) {
			System.out.format("This text should be understood by %d-%d year olds.", 14, 15);
		} else if (score >= 10 && score < 11) {
			System.out.format("This text should be understood by %d-%d year olds.", 15, 16);
		} else if (score >= 11 && score < 12) {
			System.out.format("This text should be understood by %d-%d year olds.", 16, 17);
		} else if (score >= 12 && score < 13) {
			System.out.format("This text should be understood by %d-%d year olds.", 17, 18);
		} else if (score >= 13 && score < 14) {
			System.out.format("This text should be understood by %d-%d year olds.", 18, 24);
		} else if (score >= 14) {
			System.out.format("This text should be understood by %d+ year olds.", 24);
		}
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
