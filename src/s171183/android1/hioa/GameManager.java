package s171183.android1.hioa;

import android.annotation.SuppressLint;
import java.util.ArrayList;

public class GameManager{

	private ArrayList<String> wrongLetters, chosenLetters;
	private String correctWord;
//	private int wins;
//	private int losses;
//	
//	private Boolean gameOver;
	private GameActivity gameActivity;
	
	
	public GameManager(GameActivity ga)
	{
//		wins = losses = 0;
		wrongLetters = new ArrayList<String>();
		chosenLetters = new ArrayList<String>(); 
//		gameOver = false;
		gameActivity = ga;

	}
	
	public void setCorrectWord(String word)
	{
		correctWord = word;
		System.out.println(correctWord);
	}
	
	public ArrayList<String> getWrongLetters(){
		return wrongLetters;
	}
	
	public ArrayList<String> getChosenLetters(){
		return chosenLetters;
	}
	
	
	@SuppressLint("DefaultLocale")
	public Boolean chooseLetter(String letter){
		System.out.println("Letter chosem > " + letter);
		chosenLetters.add(letter);
		Boolean inWord = true;
		if(!correctWord.contains(letter.toLowerCase())){
			inWord = false;
			wrongLetters.add(letter);
		}
		gameActivity.updateProgress();
		return inWord;	
			
	}


}
