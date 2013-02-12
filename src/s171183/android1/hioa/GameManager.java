package s171183.android1.hioa;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.TreeSet;

public class GameManager{

	private ArrayList<String> wrongChosenLetters;
	private ArrayList<Character> rightChosenLetters;
	private int totalAllowedTries;
	private String correctWord;
	private Status gameStatus;
	private int wins;
	private int losses;
	private Locale defaultLanguage;
	private int numberOfGamesInRound;
	private String[] word_collection;
	
	private GameActivity gameActivity;
	
	public GameManager(GameActivity ga, int allowedTries, String[] words){
	
		wins = 0;
		losses = 0;
		wrongChosenLetters = new ArrayList<String>();
		rightChosenLetters = new ArrayList<Character>(); 
		gameActivity = ga;
		totalAllowedTries = allowedTries;
		defaultLanguage = Locale.getDefault();
		word_collection = words;
		numberOfGamesInRound = word_collection.length;

	}
	
	public void setNewWord(){
		Random randomGenerator = new Random();
		int x = randomGenerator.nextInt(word_collection.length);
		correctWord = word_collection[x];
		
		//Word should not be used again in same round
		word_collection[x] = null;
	}
	
	public String getCorrectWord(){
		return correctWord;
	}
	
	public ArrayList<String> getWrongChosenLetters(){
		return wrongChosenLetters;
	}
	
	public Status getGameStatus(){
		return gameStatus;
	}
	
	public int getWins(){
		return wins;
	}
	
	public int getLosses(){
		return losses;
	}
		
	public void checkGameStatus(){
		if(roundIsOver()){
			gameActivity.showRoundOverDialog();
			gameActivity.showGameDialog();
		}
		else if(correctWordGuessed()){
			gameStatus = Status.WIN;
			wins++;
			gameActivity.updateStats();
			gameActivity.showGameDialog();
		}
		else if(wrongChosenLetters.size() == totalAllowedTries){
			gameStatus = Status.LOSS;
			losses++;
			gameActivity.updateStats();
			gameActivity.showGameDialog();
		}		
	}
	
	public boolean roundIsOver(){
		if( wins == numberOfGamesInRound || losses == numberOfGamesInRound
				|| (wins + losses) == numberOfGamesInRound){
			gameStatus = Status.ROUND_OVER;
			return true;
		}
		else return false;
		
		
	}
		
	private boolean correctWordGuessed(){
		//sorts the correct word into a list without duplicates
		TreeSet<Character> sortedLetters = new TreeSet<Character>();
		int correctLetterCounter = 0;
		for(int i = 0; i < correctWord.length(); i++)
		{
			sortedLetters.add(Character.valueOf(correctWord.charAt(i)));
		}
		if(sortedLetters.size() == rightChosenLetters.size()) {
			for(int i = 0; i < sortedLetters.size(); i++)
			{
				System.out.println("is it in the word? " + sortedLetters.contains(rightChosenLetters.get(i)));
				if(sortedLetters.contains(rightChosenLetters.get(i))){
					correctLetterCounter++;
					System.out.println("counter > " + correctLetterCounter);
				}
			}
			if(correctLetterCounter == sortedLetters.size()){
				return true;
			}
			
		} else {
			return false;
		}
		return false;
		
	}
	
	
	public Boolean chooseLetter(String letter){
		if(!correctWord.contains(letter.toLowerCase(defaultLanguage))){
			wrongChosenLetters.add(letter);
		} else {
			rightChosenLetters.add(Character.valueOf(letter.toLowerCase(defaultLanguage).charAt(0)));
		}
		gameActivity.updateProgress();
		gameActivity.updateLetterBlocks(getLetterIndexes(letter), letter); 

		return correctWord.contains(letter.toLowerCase(defaultLanguage));			
	}
	
	private ArrayList<Integer> getLetterIndexes(String letter){
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		if(correctWord.contains(letter.toLowerCase(defaultLanguage))){
			for(int i = 0; i < correctWord.length(); i++){
				if(correctWord.charAt(i) == letter.toLowerCase(defaultLanguage).charAt(0)){
					indexes.add(Integer.valueOf(i));
				}
			}
		}
		return indexes;
	}
	
	public void clearGuesses(){
		wrongChosenLetters.clear();
		rightChosenLetters.clear();
	}


}
