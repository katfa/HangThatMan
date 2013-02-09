package s171183.android1.hioa;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class GameActivity extends Activity {

//	private TextView wrongLettersView;
//	private ArrayList<String> wrongChosenLetters, chosenLetters;
	

	private String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	private ArrayList<String> lettersList = new ArrayList<String>();
	
	private int[] hungMen = { R.drawable.head, R.drawable.body,
			R.drawable.left_arm, R.drawable.right_arm, R.drawable.right_leg,
			R.drawable.left_left };


	
	private ImageView progressImage;

	private GameManager gm;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		gm = new GameManager(this);

		for (String a : alphabet) {
			lettersList.add(a);
		}


		Bundle b = getIntent().getExtras();
		gm.setCorrectWord((String) b.get("Word"));
		progressImage = (ImageView) findViewById(R.id.progression);

		GridView gv = (GridView) findViewById(R.id.grid_letters);
		ArrayAdapter<String> adapter = new GridAdapter(this,
				R.layout.grid_content, R.id.letter, lettersList, gm);
		gv.setAdapter(adapter);
		
	}
	
	protected void updateProgress(){
		int wrongLetters = gm.getWrongLetters().size();
		if(wrongLetters == hungMen.length){
			Toast.makeText(this, "GAME OVAH!", Toast.LENGTH_SHORT).show();
			gm = new GameManager(this);
		} else if ( wrongLetters > 0 ){
			progressImage.setImageResource(hungMen[wrongLetters - 1]);
		}
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
				System.out.println("Total chosen letters > "  + gm.getChosenLetters().size());
				System.out.println("Total wrong letters > " + gm.getWrongLetters().size());
				
			}
		});
		return item;
	}
	
	

}
