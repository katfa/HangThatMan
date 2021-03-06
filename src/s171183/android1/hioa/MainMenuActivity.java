package s171183.android1.hioa;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainMenuActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		Button startGame = (Button) findViewById(R.id.newGameButton);
		startGame.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startNewGame();
			}
		});
		
		Button rules = (Button) findViewById(R.id.rules);
		rules.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showRulesDialog();
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_menu, menu);
		return true;
	}
	
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window window = getWindow();
		window.setFormat(PixelFormat.RGBA_8888);
	}
	
	public void startNewGame()
	{
		Intent intent = new Intent(this, GameActivity.class );
		startActivity(intent);
	}
	
	public void showRulesDialog()
	{
		DialogFragment dm = new RulesDialog();
		dm.show(getSupportFragmentManager(), "Rules");
		
	}
	
	
	

}
