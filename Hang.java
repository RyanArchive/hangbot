// 

import java.util.*;

public class Hang {
	private String[] levels = {"EXPRESSION","COMMUNITY","LEADERSHIP","ACHIEVEMENT","HACKATON"};
	private String answerWord;
	private char[] answerKey;
	private char[] guessWord;
	private char guess;
	private ArrayList<Integer> location;

	public void discreteProcedure(int levelNum) {
		Random rand = new Random();
		answerWord = levels[levelNum];
		answerKey = new char[answerWord.length()];
		guessWord = new char[answerWord.length()];
		location = new ArrayList<Integer>();

		for (int i = 0; i < guessWord.length; i++)
			guessWord[i] = answerWord.charAt(i);

		while(true) {
			int n = rand.nextInt(answerWord.length()-2);

			if (n != 0 && n >= 5) {
				while(location.size() < n) {
				   int hallelujah = rand.nextInt(answerWord.length()-1);

				   if (!location.contains(hallelujah))
				       location.add(hallelujah);
				}

				for (int i = 0; i < location.size();i++) {
					answerKey[location.get(i)] = guessWord[location.get(i)];
					guessWord[location.get(i)] = '_';
				}

				break;
			}
		}
	}

	public void setAnswer(char c) {
		guess = c;
	}

	public boolean validate() {
		boolean correct = false;

		for (int i = 0; i < answerKey.length; i++) {
			if (guess == answerKey[i]) {
				guessWord[i] = answerKey[i];
				answerKey[i] = ' ';
				correct = true;
			}
		}

		return correct;
	}

	public char[] getGuessWord() {
		return guessWord;
	}

	public String[] getLevels() {
		return levels;
	}

	public boolean complete() {
		String validateWord = "";
		boolean complete = false;

		for (int i = 0; i < answerKey.length; i++) {
			validateWord += guessWord[i];
		}

		if (validateWord.equals(answerWord)) {
			complete = true;
		}

		return complete;
	}
}