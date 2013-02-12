package s171183.android1.hioa;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

@SuppressLint({ "NewApi", "ValidFragment" })
public class GameDialog extends DialogFragment {

	public interface GameDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
	
	GameDialogListener gmListener;
	
	private GameManager gm;
	private GameActivity ga;
	
	public GameDialog(GameManager gm, GameActivity ga){
		this.gm = gm;
		this.ga = ga;
	}
	
	@Override
	public void onAttach(Activity a){
		super.onAttach(a);
		try{
			gmListener = (GameDialogListener) a;	
		}
		catch(ClassCastException e){
			throw new ClassCastException(a.toString() + " must implement GameDialogListener.");
		}
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		final GameDialog self = this;
		String message = getMessage(gm.getGameStatus());
		String title = getTitle(gm.getGameStatus());

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(title);
		builder.setMessage(message + "\n" + getString(R.string.play_again)).
			setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ga.onDialogPositiveClick(self);
				}
			}).
			setNegativeButton(getString(R.string.exit), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ga.onDialogNegativeClick(self);
					
				}
			});
		
		return builder.create();
	}
	
	private String getMessage(Status s){
		String message = "";
		switch(s){
			case ROUND_OVER: 	message = getString(R.string.round_over);
							 	break;
			case WIN: 			message = getString(R.string.you_win);
								break;
			case LOSS: 			message = getString(R.string.you_lose);
								break;
			
		}
		return message;
	}
	
	private String getTitle(Status s){
		String title = "";
		switch(s){
			case ROUND_OVER: 	title = getString(R.string.round_over_title);
							 	break;
			case WIN: 			title = getString(R.string.win);
								break;
			case LOSS: 			title = getString(R.string.lose);
								break;
			
		}
		return title;
	}
}
