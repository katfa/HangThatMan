package s171183.android1.hioa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import android.annotation.SuppressLint;

public class GameManager{

	private ArrayList<String> wrongChosenLetters;
	private ArrayList<Character> rightChosenLetters;
	private int totalAllowedTries;
	private String correctWord;
	private Status gameStatus;
	private int wins;
	private int losses;
	
	private GameActivity gameActivity;
	
	public GameManager(GameActivity ga)
	{
		wins = 0;
		losses = 0;
		wrongChosenLetters = new ArrayList<String>();
		rightChosenLetters = new ArrayList<Character>(); 
		gameActivity = ga;
		gameStatus = Status.IN_PROGRESS;
		totalAllowedTries = GameActivity.hungMen.length;

	}
	
	public void setCorrectWord(String word)
	{
		correctWord = word;
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
	
	public void checkGameStatus(){
		if(correctWordGuessed()){
			gameStatus = Status.WIN;
			gameActivity.showWinMessage();
		}
		else if(wrongChosenLetters.size() == totalAllowedTries){
			gameStatus = Status.GAME_OVER;
			gameActivity.showGameOverMessage();
		}else{
			gameStatus = Status.IN_PROGRESS;
		}
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
	
	
	@SuppressLint("DefaultLocale")
	public Boolean chooseLetter(String letter){
		if(!correctWord.contains(letter.toLowerCase())){
			wrongChosenLetters.add(letter);
		} else {
			rightChosenLetters.add(Character.valueOf(letter.toLowerCase().charAt(0)));
		}
		gameActivity.updateProgress();
		gameActivity.updateLetterBlocks(getLetterIndexes(letter), letter); 

		return correctWord.contains(letter.toLowerCase());			
	}
	
	private ArrayList<Integer> getLetterIndexes(String letter){
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		if(correctWord.contains(letter.toLowerCase())){
			for(int i = 0; i < correctWord.length(); i++){
				if(correctWord.charAt(i) == letter.toLowerCase().charAt(0)){
					indexes.add(Integer.valueOf(i));
				}
			}
		}
		return indexes;
	}


}
