package readability;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		//create Scanner instance
		Scanner scanner = new Scanner(System.in);
		//read user input
		String input = scanner.nextLine();

		//split input into sentences
		String[] sentences = input.split("[!\\?\\.]");
		//create counter to count amount of word in sentence
		int counter = 0;

		//for each sentence
		for (String sentence : sentences) {
			//split sentence into words
			String[] words = sentence.split("\\s");

			//for each word in sentence counter++
			for (String word : words) {
				counter++;
			}
		}

		if (counter / sentences.length > 10) {
			System.out.println("HARD");
		} else {
			System.out.println("EASY");
		}
	}
}
