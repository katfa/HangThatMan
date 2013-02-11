package s171183.android1.hioa;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {

	private String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	
	private ArrayList<String> lettersList = new ArrayList<String>();
	
	public static int[] hungMen = { R.drawable.head, R.drawable.body,
			R.drawable.left_arm, R.drawable.right_arm, R.drawable.left_leg, R.drawable.right_leg };

	private ImageView progressImage;
	private GameManager gm;
	
	private ArrayList<TextView> letterBlocks = new ArrayList<TextView>();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		gm = new GameManager(this);

		for (String a : alphabet) {
			lettersList.add(a);
		}

		Bundle b = getIntent().getExtras();
		gm.setCorrectWord((String) b.get("Word"));
		showEmptyLetterBlocks();

		progressImage = (ImageView) findViewById(R.id.progression);

		GridView gv = (GridView) findViewById(R.id.grid_letters);
		ArrayAdapter<String> adapter = new GridAdapter(this,
				R.layout.grid_content, R.id.letter, lettersList, gm);
		gv.setAdapter(adapter);
		
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
		gm.checkGameStatus();
	}
	
	protected void updateProgress(){
		int wrongLetters = gm.getWrongChosenLetters().size();
		System.out.println("wrongletters " + wrongLetters);
		if ( wrongLetters > 0 ){
			progressImage.setImageResource(hungMen[wrongLetters - 1]);
		}
		gm.checkGameStatus();
	}
	
	protected void showWinMessage(){
		Toast.makeText(this, "YOU GUESSED THE WORD!", Toast.LENGTH_SHORT).show();
	}
	
	protected void showGameOverMessage(){
		Toast.makeText(this, "Game over, loser!", Toast.LENGTH_SHORT).show();
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
