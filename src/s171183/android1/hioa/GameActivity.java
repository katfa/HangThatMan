package s171183.android1.hioa;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends FragmentActivity implements GameDialog.GameDialogListener {

	private String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	
	private ArrayList<String> lettersList = new ArrayList<String>();
	
	public int[] hungMen = { R.drawable.head, R.drawable.body,
			R.drawable.left_arm, R.drawable.right_arm, R.drawable.left_leg, R.drawable.right_leg };

	private ImageView progressImage;
	private TextView wins;
	private TextView losses;
	private GameManager gm;
	
	private ArrayList<TextView> letterBlocks = new ArrayList<TextView>();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		gm = new GameManager(this, hungMen.length, getResources().getStringArray(R.array.word_collection));

		for (String a : alphabet) {
			lettersList.add(a);
		}

		progressImage = (ImageView) findViewById(R.id.progression);
		wins = (TextView) findViewById(R.id.wins);
		losses = (TextView) findViewById(R.id.losses);
		
		startNewGame();
		
	}
	
	protected void startNewGame(){
		removeOldWord();
		gm.setNewWord();
		gm.clearGuesses();
		showEmptyLetterBlocks();
		progressImage.setImageResource(R.drawable.scaffold);
		refreshKeyboard();
	}
	
	protected void refreshKeyboard(){
		GridView gv = (GridView) findViewById(R.id.grid_letters);
		ArrayAdapter<String> adapter = new GridAdapter(this,
				R.layout.grid_content, R.id.letter, lettersList, gm);
		gv.setAdapter(adapter);
	}
	
	protected void removeOldWord(){
		LinearLayout row = (LinearLayout) findViewById(R.id.correct_letters);
		row.removeViewsInLayout(0, row.getChildCount());
		letterBlocks = new ArrayList<TextView>();
	}
	
	protected void showEmptyLetterBlocks()
	{
		LinearLayout row = (LinearLayout) findViewById(R.id.correct_letters);
		for(int i = 0 ; i < gm.getCorrectWord().length(); i++){
			TextView t = new TextView(this);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
			layoutParams.setMargins(5, 5, 5, 6);
			t.setBackgroundColor(getResources().getColor(R.color.dark_grey));
			t.setPadding(2, 2, 2, 2);
			t.setGravity(Gravity.CENTER_HORIZONTAL);
			t.setText(" ");
			t.setTextSize(30);
			t.setTextColor(Color.WHITE);
			letterBlocks.add(t);
			row.addView(t, layoutParams);
		}
	}
	
	protected void updateLetterBlocks(ArrayList<Integer> indexes, String letter){
		for(Integer i : indexes){
			letterBlocks.get(i).setText(letter);
		}
	}
	
	protected void updateProgress(){
		int wrongLetters = gm.getWrongChosenLetters().size();
		System.out.println("wrongletters " + wrongLetters);
		if ( wrongLetters > 0 ){
			progressImage.setImageResource(hungMen[wrongLetters - 1]);
		}
		gm.checkGameStatus();
	}
	
	protected void updateStats(){
		wins.setText(getString(R.string.wins) + " " + gm.getWins());
		losses.setText(getString(R.string.losses) + " " + gm.getLosses());
	}
	
	protected void showGameDialog(){
		DialogFragment gd = new GameDialog(gm, this);
		gd.show(getSupportFragmentManager(), "Stats");
	}

	protected void showRoundOverDialog(){
		Toast.makeText(this, "Round over, loser!", Toast.LENGTH_SHORT).show();
	}
	

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		startNewGame();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		Intent i = new Intent(this, MainMenuActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}

class GridAdapter extends ArrayAdapter<String> {
	
	private Context myContext;
	private ArrayList<String> letters;
	private GameManager gm;
	Resources r;

	public GridAdapter(Context context, int textViewResourceId, int textViewId,
			ArrayList<String> letters, GameManager gm) {
		super(context, textViewResourceId, textViewId, letters);
		this.myContext = context;
		this.gm = gm;
		this.letters = letters;
		r = myContext.getResources();
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) myContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View item = inflater.inflate(R.layout.grid_content, parent, false);
		final Button letter = (Button) item.findViewById(R.id.letter);
		letter.setText(letters.get(position));

		letter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				letter.setEnabled(false);
				if(gm.chooseLetter(letters.get(position))){
					letter.setBackgroundColor(r.getColor(R.color.green));
				} else {
					letter.setBackgroundColor(r.getColor(R.color.dark_grey));
				}
				
			}
		});
		return item;
	}
	
	

}
